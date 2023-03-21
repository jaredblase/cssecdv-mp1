package Controller.tables;

import Controller.SQLite;
import Model.Product;
import View.MgmtProduct;

import java.sql.SQLException;

public class MgmtProductController {
    private final MgmtProduct view;
    private final SQLite db;

    public MgmtProductController(MgmtProduct view, SQLite db) {
        this.view = view;
        this.db = db;

        view.setShowTableListener(this::resetTable);
        view.setPurchaseListener(this::onPurchase);
        view.setAddListener(this::onAdd);
        view.setEditListener(this::onEdit);
        view.setDeleteListener(this::onDelete);
    }

    public void onPurchase(int index, String quantity) {
        view.setErrorMessage("");

        // TODO: authorize client

        try {
            var qty = Integer.parseInt(quantity);

            if (qty <= 0) {
                throw new NumberFormatException();
            }

            var productName = view.getProductNameAt(index);
            db.addPurchase("admin", productName, qty); // TODO: Use user in session.
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

    public void onAdd(String name, String stock, String price) {
        view.setErrorMessage("");

        try {
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
            e.printStackTrace();
        } catch (Exception e) {
            view.setErrorMessage(e.getMessage());
        }
    }

    public void onEdit(int idx, String stock, String price) {
        view.setErrorMessage("");

        try {
            db.updateProduct(parseProduct(view.getProductNameAt(idx), stock, price));
            resetTable();
            view.closeDialog();
        } catch (SQLException e) {
            view.setErrorMessage("An error has occurred on our end. Please try again later.");
            e.printStackTrace();
        } catch (Exception e) {
            view.setErrorMessage(e.getMessage());
        }
    }

    public void onDelete(int idx) {
        try {
            db.deleteProductByName(view.getProductNameAt(idx));
            resetTable();
            view.closeDialog();
        } catch (SQLException e) {
            view.setErrorMessage("An error has occurred on our end. Please try again later.");
            e.printStackTrace();
        }
    }

    public void resetTable() {
        view.clearTableData();
        view.setTableData(db.getProducts());
    }
}
