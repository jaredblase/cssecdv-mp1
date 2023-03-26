package Controller.tables;

import Controller.SQLite;
import Model.History;
import Model.Role;
import Model.SessionManager;
import Model.User;
import View.MgmtHistory;

import java.sql.SQLException;
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
        try {
            User user = SessionManager.getAuthorizedUser(db, Role.MANAGER, Role.CLIENT);

            if (user == null) return null;

            if (user.getRole() == Role.MANAGER) {
                return db.getHistory();
            } else if (user.getRole() == Role.CLIENT) {
                return db.getUserHistoryByUsername(user.getUsername());
            }
        } catch (SQLException e) {
            if (db.DEBUG_MODE) e.printStackTrace();
        }

        return null;
    }

    public void resetTable() {
        view.clearTableData();

        var data = getData();
        if (data != null) {
            view.setTableData(data);
        }
    }

    public void filterTable(String text) {
        view.clearTableData();

        var data = getData();
        if (data == null) return;

        var filtered = getData().stream().filter(h ->
                text.contains(h.getUsername()) || h.getUsername().contains(text) ||
                        text.contains(h.getName()) || h.getName().contains(text)
        ).toList();

        view.setTableData(filtered);
    }
}
