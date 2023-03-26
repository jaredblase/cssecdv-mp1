package Controller.tables;

import Controller.SQLite;
import Model.Role;
import Model.SessionManager;
import View.MgmtLogs;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class MgmtLogsController {
    private final MgmtLogs view;
    private final SQLite db;

    public MgmtLogsController(MgmtLogs view, SQLite db) {
        this.view = view;
        this.db = db;

        view.setShowTableListener(this::resetTable);
        view.setDebugListener(this::toggleDebug);
        view.setClearListener(this::clearLogs);
    }

    private void resetTable() {
        view.clearTableData();
        System.out.println(db.DEBUG_MODE);
        try {
            if (SessionManager.getAuthorizedUser(db, Role.ADMINISTRATOR) != null) {
                view.setTableData(db.getLogs());
            }
        } catch (SQLException e) {
            if (db.DEBUG_MODE) e.printStackTrace();
        }
    }

    private void clearLogs(ActionEvent e) {
        try {
            if (SessionManager.getAuthorizedUser(db, Role.ADMINISTRATOR) == null) return;
            db.deleteLogs();
            view.clearTableData();
        } catch (SQLException ex) {
            if (db.DEBUG_MODE) ex.printStackTrace();
        }
    }

    private void toggleDebug(ActionEvent e) {
        var newMode = !db.DEBUG_MODE;

        try {
            var session = SessionManager.getSession(db);
            if (session == null) throw new Exception("Session not found!");

            session.setDebugMode(newMode);
            db.updateSession(session);
            db.DEBUG_MODE = newMode;
        } catch (Exception err) {
            if (db.DEBUG_MODE) err.printStackTrace();
        }
    }
}
