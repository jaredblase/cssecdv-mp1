package Controller;

import Controller.rolehome.AdminHomeController;
import Controller.rolehome.ClientHomeController;
import Controller.rolehome.ManagerHomeController;
import Controller.rolehome.StaffHomeController;
import Model.Session;
import Model.SessionManager;
import Model.User;
import View.*;
import View.Frame;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class HomeController {
    public HomeController(Main main, Frame frame, SQLite db) {
        CardLayout contentView = new CardLayout();
        JPanel content = frame.getContent();
        content.removeAll();
        content.setLayout(contentView);

        Session session = null;
        User user = null;

        try {
            session = SessionManager.getSession(db);
            user = SessionManager.getUser(db, session);
        } catch (SQLException e) {
            if (SQLite.DEBUG_MODE) e.printStackTrace();
        }

        if (user == null) {
            main.showPanel(Panel.LOGIN);
            return;
        }

        frame.setAdminVisible(false);
        frame.setManagerVisible(false);
        frame.setStaffVisible(false);
        frame.setClientVisible(false);

        SessionManager.SessionListener showHome;
        db.DEBUG_MODE = false;

        switch (user.getRole()) {
            case ADMINISTRATOR -> {
                AdminHome adminHomePnl = new AdminHome();
                new AdminHomeController(adminHomePnl, db);
                frame.setAdminVisible(true);
                showHome = () -> {
                    adminHomePnl.showPnl("home");
                    contentView.show(content, Panel.ADMIN.name());
                };

                db.DEBUG_MODE = session.isDebugMode();
                frame.setAdminActionListener(e -> showHome.onAction());
                content.add(adminHomePnl, Panel.ADMIN.name());
            }

            case MANAGER -> {
                ManagerHome managerHomePnl = new ManagerHome();
                new ManagerHomeController(managerHomePnl, db);
                frame.setManagerVisible(true);
                showHome = () -> {
                    managerHomePnl.showPnl("home");
                    contentView.show(content, Panel.MANAGER.name());
                };
                frame.setManagerActionListener(e -> showHome.onAction());
                content.add(managerHomePnl, Panel.MANAGER.name());
            }

            case STAFF -> {
                StaffHome staffHomePnl = new StaffHome();
                new StaffHomeController(staffHomePnl, db);
                frame.setStaffVisible(true);
                showHome = () -> {
                    staffHomePnl.showPnl("home");
                    contentView.show(content, Panel.STAFF.name());
                };
                frame.setStaffActionListener(e -> showHome.onAction());
                content.add(staffHomePnl, Panel.STAFF.name());
            }

            case CLIENT -> {
                ClientHome clientHomePnl = new ClientHome();
                new ClientHomeController(clientHomePnl, db);
                frame.setClientVisible(true);
                showHome = () -> {
                    clientHomePnl.showPnl("home");
                    contentView.show(content, Panel.CLIENT.name());
                };
                frame.setClientActionListener(e -> showHome.onAction());
                content.add(clientHomePnl, Panel.CLIENT.name());
            }
            default -> showHome = null;
        }

        if (showHome != null) {
            SessionManager.setUnauthorizedListener(showHome);
        }
        frame.setLogoutActionListener(e -> SessionManager.logout(db));
        main.showPanel(Panel.HOME);
    }
}
