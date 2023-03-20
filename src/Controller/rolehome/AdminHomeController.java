package Controller.rolehome;

import Controller.tables.MgmtLogsController;
import Controller.tables.MgmtUserController;
import Controller.Panel;
import Controller.SQLite;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminHomeController {
    private final CardLayout contentView = new CardLayout();
    private final JPanel content;

    public AdminHomeController(AdminHome view, SQLite db) {
        MgmtHistory mgmtHistory = new MgmtHistory();
        MgmtLogs mgmtLogs = new MgmtLogs();
        MgmtUser mgmtUser = new MgmtUser();

        new MgmtUserController(mgmtUser, db);
        new MgmtLogsController(mgmtLogs, db);

        content = view.getContent();
        content.setLayout(contentView);
        content.add(new Home("WELCOME ADMIN!", new java.awt.Color(51, 153, 255)), "home");
        content.add(mgmtUser, Controller.Panel.USERS.name());
        content.add(mgmtHistory, Controller.Panel.HISTORY.name());
        content.add(mgmtLogs, Controller.Panel.LOGS.name());

        view.setUsersBtnListener(this::onUsersAction);
        view.setLogsBtnListener(this::onLogsAction);
    }

    private void onUsersAction(ActionEvent e) {
        contentView.show(content, Controller.Panel.USERS.name());
    }

    private void onLogsAction(ActionEvent e) {
        contentView.show(content, Panel.LOGS.name());
    }
}
