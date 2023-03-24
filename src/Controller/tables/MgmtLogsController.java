package Controller.tables;

import Controller.SQLite;
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
        view.setTableData(db.getLogs());
    }

    private void clearLogs(ActionEvent e) {
        try {
            db.deleteLogs();
            view.clearTableData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void toggleDebug(ActionEvent e) {
        db.DEBUG_MODE = 1 - db.DEBUG_MODE;
    }
}
