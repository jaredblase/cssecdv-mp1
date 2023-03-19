package Controller;

import View.MgmtProduct;

public class MgmtProductController {
    private final MgmtProduct view;
    private final SQLite db;

    public MgmtProductController(MgmtProduct view, SQLite db) {
        this.view = view;
        this.db = db;

        view.setShowTableListener(this::onShowTable);
    }

    public void onShowTable() {
        view.clearTableData();
        view.setTableData(db.getProducts());
    }
}
