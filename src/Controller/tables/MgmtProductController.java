package Controller.tables;

import Controller.SQLite;
import View.MgmtProduct;

public class MgmtProductController {
    private final MgmtProduct view;
    private final SQLite db;

    public MgmtProductController(MgmtProduct view, SQLite db) {
        this.view = view;
        this.db = db;

        view.setShowTableListener(this::resetTable);
        view.setPurchaseListener(this::onPurchase);
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
            view.setTableData(db.getProductById(productName), index);
            view.closeDialog();
        } catch (NumberFormatException e) {
            view.setErrorMessage("Input must be a positive whole number.");
        } catch (Exception e) {
            view.setErrorMessage(e.getMessage());
        }
    }

    public void resetTable() {
        view.clearTableData();
        view.setTableData(db.getProducts());
    }
}
