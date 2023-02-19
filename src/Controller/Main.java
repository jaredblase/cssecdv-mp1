package Controller;


import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import View.AdminHome;
import View.ClientHome;
import View.Frame;
import View.Login;
import View.ManagerHome;
import View.Register;
import View.StaffHome;

import java.awt.CardLayout;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JPanel;


public class Main {

    public SQLite sqlite = new SQLite();
    private final JPanel container = new JPanel();
    private final CardLayout frameView = new CardLayout();

    public static void main(String[] args) {
        new Main().init();
    }

    public void init() {
//        initDatabase();
        CardLayout contentView = new CardLayout();
        JPanel content = new JPanel();
        Frame frame = new Frame(container, content);

        AdminHome adminHomePnl = new AdminHome();
        ManagerHome managerHomePnl = new ManagerHome();
        StaffHome staffHomePnl = new StaffHome();
        ClientHome clientHomePnl = new ClientHome();
        Login loginPnl = new Login();
        Register registerPnl = new Register();

        adminHomePnl.init(sqlite);
        clientHomePnl.init(sqlite);
        managerHomePnl.init(sqlite);
        staffHomePnl.init(sqlite);

        container.setLayout(frameView);
        container.add(loginPnl, Panel.LOGIN.name());
        container.add(registerPnl, Panel.REGISTER.name());
        frameView.show(container, Panel.LOGIN.name());

        content.setLayout(contentView);
        content.add(adminHomePnl, Panel.ADMIN.name());
        content.add(managerHomePnl, Panel.MANAGER.name());
        content.add(staffHomePnl, Panel.STAFF.name());
        content.add(clientHomePnl, Panel.CLIENT.name());

        new RegisterController(this, registerPnl, sqlite);
        new LoginController(this, loginPnl, sqlite);

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

        frame.setLogoutActionListener(e -> showPanel(Panel.LOGIN));

        frame.init();
    }

    public void showPanel(Panel panel) {
        frameView.show(container, panel.name());
    }

    private void initDatabase() {
        // Create a database
        sqlite.createNewDatabase();

        // Drop users table if needed
        sqlite.dropHistoryTable();
        sqlite.dropLogsTable();
        sqlite.dropProductTable();
        sqlite.dropUserTable();

        // Create users table if not exist
        sqlite.createHistoryTable();
        sqlite.createLogsTable();
        sqlite.createProductTable();
        sqlite.createUserTable();

        // Add sample history
        sqlite.addHistory("admin", "Antivirus", 1, "2019-04-03 14:30:00.000");
        sqlite.addHistory("manager", "Firewall", 1, "2019-04-03 14:30:01.000");
        sqlite.addHistory("staff", "Scanner", 1, "2019-04-03 14:30:02.000");

        // Add sample logs
        sqlite.addLogs("NOTICE", "admin", "User creation successful", new Timestamp(new Date().getTime()).toString());
        sqlite.addLogs("NOTICE", "manager", "User creation successful", new Timestamp(new Date().getTime()).toString());
        sqlite.addLogs("NOTICE", "admin", "User creation successful", new Timestamp(new Date().getTime()).toString());

        // Add sample product
        sqlite.addProduct("Antivirus", 5, 500.0);
        sqlite.addProduct("Firewall", 3, 1000.0);
        sqlite.addProduct("Scanner", 10, 100.0);

        // Add sample users
        char[] password = {'q', 'w', 'e', 'r', 't', 'y', '1', '2', '3', '4'};
        try {
            sqlite.addUser(new User("admin", password, 5));
            sqlite.addUser(new User("manager", password, 4));
            sqlite.addUser(new User("staff", password, 3));
            sqlite.addUser(new User("client1", password, 2));
            sqlite.addUser(new User("client2", password, 2));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Arrays.fill(password, '0');
        }

        // Get users
        ArrayList<History> histories = sqlite.getHistory();
        for (int nCtr = 0; nCtr < histories.size(); nCtr++) {
            System.out.println("===== History " + histories.get(nCtr).getId() + " =====");
            System.out.println(" Username: " + histories.get(nCtr).getUsername());
            System.out.println(" Name: " + histories.get(nCtr).getName());
            System.out.println(" Stock: " + histories.get(nCtr).getStock());
            System.out.println(" Timestamp: " + histories.get(nCtr).getTimestamp());
        }

        // Get users
        ArrayList<Logs> logs = sqlite.getLogs();
        for (int nCtr = 0; nCtr < logs.size(); nCtr++) {
            System.out.println("===== Logs " + logs.get(nCtr).getId() + " =====");
            System.out.println(" Username: " + logs.get(nCtr).getEvent());
            System.out.println(" Password: " + logs.get(nCtr).getUsername());
            System.out.println(" Role: " + logs.get(nCtr).getDesc());
            System.out.println(" Timestamp: " + logs.get(nCtr).getTimestamp());
        }

        // Get users
        ArrayList<Product> products = sqlite.getProduct();
        for (int nCtr = 0; nCtr < products.size(); nCtr++) {
            System.out.println("===== Product " + products.get(nCtr).getId() + " =====");
            System.out.println(" Name: " + products.get(nCtr).getName());
            System.out.println(" Stock: " + products.get(nCtr).getStock());
            System.out.println(" Price: " + products.get(nCtr).getPrice());
        }
        // Get users
        ArrayList<User> users = sqlite.getUsers();
        for (int nCtr = 0; nCtr < users.size(); nCtr++) {
            System.out.println("===== User " + users.get(nCtr).getId() + " =====");
            System.out.println(" Username: " + users.get(nCtr).getUsername());
            System.out.println(" Password: " + users.get(nCtr).getPassword());
            System.out.println(" Role: " + users.get(nCtr).getRole());
            System.out.println(" Locked: " + users.get(nCtr).getLocked());
        }
    }
}
