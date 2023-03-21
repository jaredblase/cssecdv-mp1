package Controller.tables;

import Controller.SQLite;
import Model.History;
import Model.Role;
import Model.SessionManager;
import Model.User;
import View.MgmtHistory;

import java.util.ArrayList;

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

    private ArrayList<History> getData() {
        User user = SessionManager.getUser(db);

        if (user == null) {
            SessionManager.logout();
        } else if (user.getRole() == Role.MANAGER) {
            return db.getHistory();
        } else if (user.getRole() == Role.CLIENT) {
            return db.getUserHistoryByUsername(user.getUsername());
        }

        return new ArrayList<>();
    }

    public void resetTable() {
        view.clearTableData();
        view.setTableData(getData());
    }

    public void filterTable(String text) {
        view.clearTableData();

        var filtered = getData().stream().filter(h ->
                text.contains(h.getUsername()) || h.getUsername().contains(text) ||
                        text.contains(h.getName()) || h.getName().contains(text)
        ).toList();

        view.setTableData(filtered);
    }
}
