package Controller;

import Controller.rolehome.AdminHomeController;
import Controller.rolehome.ClientHomeController;
import Controller.rolehome.ManagerHomeController;
import Controller.rolehome.StaffHomeController;
import Model.SessionManager;
import Model.User;
import View.*;
import View.Frame;

import javax.swing.*;
import java.awt.*;

public class HomeController {
    public HomeController(Main main, Frame frame, SQLite db) {
        CardLayout contentView = new CardLayout();
        JPanel content = frame.getContent();
        content.removeAll();
        content.setLayout(contentView);

        User user = SessionManager.getUser(db);

        if (user == null) {
            main.showPanel(Panel.LOGIN);
            return;
        }

        frame.setAdminVisible(false);
        frame.setManagerVisible(false);
        frame.setStaffVisible(false);
        frame.setClientVisible(false);

        switch (user.getRole()) {
            case ADMINISTRATOR -> {
                AdminHome adminHomePnl = new AdminHome();
                new AdminHomeController(adminHomePnl, db);

                frame.setAdminVisible(true);
                frame.setAdminActionListener(e -> {
                    adminHomePnl.showPnl("home");
                    contentView.show(content, Panel.ADMIN.name());
                });
                content.add(adminHomePnl, Panel.ADMIN.name());
            }

            case MANAGER -> {
                ManagerHome managerHomePnl = new ManagerHome();
                new ManagerHomeController(managerHomePnl, db);

                frame.setManagerVisible(true);
                frame.setManagerActionListener(e -> {
                    managerHomePnl.showPnl("home");
                    contentView.show(content, Panel.MANAGER.name());
                });
                content.add(managerHomePnl, Panel.MANAGER.name());
            }

            case STAFF -> {
                StaffHome staffHomePnl = new StaffHome();
                new StaffHomeController(staffHomePnl, db);

                frame.setStaffVisible(true);
                frame.setStaffActionListener(e -> {
                    staffHomePnl.showPnl("home");
                    contentView.show(content, Panel.STAFF.name());
                });
                content.add(staffHomePnl, Panel.STAFF.name());
            }

            case CLIENT -> {
                ClientHome clientHomePnl = new ClientHome();
                new ClientHomeController(clientHomePnl, db);

                frame.setClientVisible(true);
                frame.setClientActionListener(e -> {
                    clientHomePnl.showPnl("home");
                    contentView.show(content, Panel.CLIENT.name());
                });
                content.add(clientHomePnl, Panel.CLIENT.name());
            }
        }

        frame.setLogoutActionListener(e -> SessionManager.logout());
        main.showPanel(Panel.HOME);
    }
}
