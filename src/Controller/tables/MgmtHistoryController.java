package Controller.tables;

import Controller.SQLite;
import View.MgmtHistory;

public class MgmtHistoryController {
    private final MgmtHistory view;
    private final SQLite db;

    public MgmtHistoryController(MgmtHistory view, SQLite db) {
        this.view = view;
        this.db = db;

        view.setShowTableListener(this::resetTable);
        view.setReloadListener(e -> resetTable());
        view.setFilterListener(this::filterTable);
    }

    public void resetTable() {
        view.clearTableData();
        view.setTableData(db.getHistory());
    }

    public void filterTable(String text) {
        view.clearTableData();

        var filtered = db.getHistory().stream().filter(h ->
                text.contains(h.getUsername()) || h.getUsername().contains(text) ||
                        text.contains(h.getName()) || h.getName().contains(text)
        ).toList();

        view.setTableData(filtered);
    }
}
