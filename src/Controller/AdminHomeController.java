package Controller;

import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminHomeController {
    private final CardLayout contentView = new CardLayout();
    private final JPanel content;

    public AdminHomeController(AdminHome view, SQLite db) {
        MgmtHistory mgmtHistory = new MgmtHistory(db);
        MgmtLogs mgmtLogs = new MgmtLogs(db);
        MgmtUser mgmtUser = new MgmtUser();

        new MgmtUserController(mgmtUser, db);

        content = view.getContent();
        content.setLayout(contentView);
        content.add(new Home("WELCOME ADMIN!", new java.awt.Color(51, 153, 255)), "home");
        content.add(mgmtUser, Panel.USERS.name());
        content.add(mgmtHistory, Panel.HISTORY.name());
        content.add(mgmtLogs, Panel.LOGS.name());

        view.setUsersBtnListener(this::onUsersAction);
        view.setLogsBtnListener(this::onLogsAction);
    }

    private void onUsersAction(ActionEvent e) {
        contentView.show(content, Panel.USERS.name());
    }

    private void onLogsAction(ActionEvent e) {
        contentView.show(content, Panel.LOGS.name());
    }
}
