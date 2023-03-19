package Controller;

import Controller.rolehome.AdminHomeController;
import Controller.rolehome.ClientHomeController;
import Controller.rolehome.ManagerHomeController;
import Controller.rolehome.StaffHomeController;
import View.*;
import View.Frame;

import javax.swing.*;
import java.awt.*;

public class HomeController {
    public HomeController(Main main, Frame frame, SQLite db) {
        CardLayout contentView = new CardLayout();

        AdminHome adminHomePnl = new AdminHome();
        ManagerHome managerHomePnl = new ManagerHome();
        StaffHome staffHomePnl = new StaffHome();
        ClientHome clientHomePnl = new ClientHome();

        new AdminHomeController(adminHomePnl, db);
        new ManagerHomeController(managerHomePnl, db);
        new StaffHomeController(staffHomePnl, db);
        new ClientHomeController(clientHomePnl, db);

        JPanel content = frame.getContent();
        content.setLayout(contentView);
        content.add(adminHomePnl, Panel.ADMIN.name());
        content.add(managerHomePnl, Panel.MANAGER.name());
        content.add(staffHomePnl, Panel.STAFF.name());
        content.add(clientHomePnl, Panel.CLIENT.name());

        frame.setAdminActionListener(e -> {
            adminHomePnl.showPnl("home");
            contentView.show(content, Panel.ADMIN.name());
        });

        frame.setManagerActionListener(e -> {
            managerHomePnl.showPnl("home");
            contentView.show(content, Panel.MANAGER.name());
        });

        frame.setClientActionListener(e -> {
            clientHomePnl.showPnl("home");
            contentView.show(content, Panel.CLIENT.name());
        });

        frame.setStaffActionListener(e -> {
            staffHomePnl.showPnl("home");
            contentView.show(content, Panel.STAFF.name());
        });

        frame.setLogoutActionListener(e -> {
//            db.addUserEventLog(user, "User logout", null);
//            user = null;
            main.showPanel(Panel.LOGIN);
        });
    }
}
