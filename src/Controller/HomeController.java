package Controller;

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

        clientHomePnl.init(db);
        managerHomePnl.init(db);
        staffHomePnl.init(db);

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

        new AdminHomeController(adminHomePnl, db);
    }
}
