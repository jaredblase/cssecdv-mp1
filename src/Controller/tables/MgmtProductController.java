package Controller.tables;

import Controller.SQLite;
import Model.*;
import View.MgmtProduct;

import java.sql.SQLException;

public class MgmtProductController {
    private final MgmtProduct view;
    private final SQLite db;

    public MgmtProductController(MgmtProduct view, SQLite db) {
        this.view = view;
        this.db = db;

        try {
            User user = SessionManager.getAuthorizedUser(db, Role.MANAGER, Role.STAFF, Role.CLIENT);
            if (user != null) {
                if (user.getRole() == Role.MANAGER || user.getRole() == Role.STAFF) {
                    view.setAdminControlVisible(true);
                } else if (user.getRole() == Role.CLIENT) {
                    view.setAdminControlVisible(false);
                }
            }
        } catch (SQLException e) {
            if (db.DEBUG_MODE) e.printStackTrace();
        }

        view.setShowTableListener(this::resetTable);
        view.setPurchaseListener(this::onPurchase);
        view.setAddListener(this::onAdd);
        view.setEditListener(this::onEdit);
        view.setDeleteListener(this::onDelete);
    }

    private void onPurchase(int index, String quantity) {
        view.setErrorMessage("");

        User user = null;

        try {
            user = SessionManager.getAuthorizedUser(db, Role.CLIENT);
        } catch (SQLException e) {
            if (db.DEBUG_MODE) e.printStackTrace();
        }

        if (user == null) return;

        try {
            var qty = Integer.parseInt(quantity);

            if (qty <= 0) {
                throw new NumberFormatException();
            }

            var productName = view.getProductNameAt(index);
            db.addPurchase(user.getUsername(), productName, qty);
            view.closeDialog();
        } catch (NumberFormatException e) {
            view.setErrorMessage("Input must be a positive whole number.");
        } catch (Exception e) {
            view.setErrorMessage(e.getMessage());
        } finally {
            resetTable();
        }
    }

    private static Product parseProduct(String name, String stock, String price) throws Exception {
        int qty;
        float prc;

        try {
            qty = Integer.parseInt(stock);
        } catch (NumberFormatException e) {
            throw new Exception("Stock must be a non-negative integer.");
        }

        try {
            prc = Float.parseFloat(price);
        } catch (NumberFormatException e) {
            throw new Exception("Price must be non-negative decimal number.");
        }

        return new Product(name, qty, prc);
    }

    private void onAdd(String name, String stock, String price) {
        view.setErrorMessage("");

        try {
            if (SessionManager.getAuthorizedUser(db, Role.MANAGER, Role.STAFF) == null) return;
            db.addProduct(parseProduct(name, stock, price));
            resetTable();
            view.closeDialog();
        } catch (SQLException e) {
            view.setErrorMessage("");

            if (e.getErrorCode() == 19) {
                view.setErrorMessage("Product name is already taken!");
                return;
            }

            view.setErrorMessage("An error has occurred on our end. Please try again later.");
            if (db.DEBUG_MODE) e.printStackTrace();
        } catch (Exception e) {
            view.setErrorMessage(e.getMessage());
        }
    }

    private void onEdit(int idx, String stock, String price) {
        view.setErrorMessage("");

        try {
            if (SessionManager.getAuthorizedUser(db, Role.MANAGER, Role.STAFF) == null) return;
            db.updateProduct(parseProduct(view.getProductNameAt(idx), stock, price));
            resetTable();
            view.closeDialog();
        } catch (SQLException e) {
            view.setErrorMessage("An error has occurred on our end. Please try again later.");
            if (db.DEBUG_MODE) e.printStackTrace();
        } catch (Exception e) {
            view.setErrorMessage(e.getMessage());
        }
    }

    private void onDelete(int idx) {
        try {
            if (SessionManager.getAuthorizedUser(db, Role.MANAGER, Role.STAFF) == null) return;
            db.deleteProductByName(view.getProductNameAt(idx));
            resetTable();
            view.closeDialog();
        } catch (SQLException e) {
            view.setErrorMessage("An error has occurred on our end. Please try again later.");
            e.printStackTrace();
        }
    }

    private void resetTable() {
        try {
            if (SessionManager.getAuthorizedUser(db, Role.MANAGER, Role.STAFF, Role.CLIENT) == null) return;

            view.clearTableData();
            view.setTableData(db.getProducts());
        } catch (SQLException e) {
            if (db.DEBUG_MODE) e.printStackTrace();
        }
    }
}
