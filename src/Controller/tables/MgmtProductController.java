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
    }

    public void resetTable() {
        view.clearTableData();
        view.setTableData(db.getProducts());
    }
}
