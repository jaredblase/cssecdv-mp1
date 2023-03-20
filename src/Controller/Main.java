package Controller;

import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import View.ForgotPassword;
import View.Frame;
import View.Login;
import View.Register;

import java.awt.CardLayout;

import java.util.Arrays;
import javax.swing.JPanel;


public class Main {
    private final JPanel container = new JPanel();
    private final CardLayout frameView = new CardLayout();

    public static void main(String[] args) {
        new Main().init();
    }

    public void init() {
        final SQLite sqlite = new SQLite();
//        initDatabase(sqlite);
        Frame frame = new Frame(container, new JPanel());

        Login loginPnl = new Login();
        Register registerPnl = new Register();
        ForgotPassword forgotPasswordPnl = new ForgotPassword();

        container.setLayout(frameView);
        container.add(loginPnl, Panel.LOGIN.name());
        container.add(registerPnl, Panel.REGISTER.name());
        container.add(forgotPasswordPnl, Panel.FORGOT_PASSWORD.name());
        frameView.show(container, Panel.LOGIN.name());

        new RegisterController(this, registerPnl, sqlite);
        new LoginController(this, loginPnl, sqlite);
        new ForgotPasswordController(this, forgotPasswordPnl);
        new HomeController(this, frame, sqlite);

        frame.init();
    }

    public void showPanel(Panel panel) {
        frameView.show(container, panel.name());
    }

    private void initDatabase(SQLite sqlite) {
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

        var products = new Product[] {
                new Product("Antivirus", 5, 500.0F),
                new Product("Firewall", 3, 1000.0F),
                new Product("Scanner", 10, 100.0F)
        };

        // Add sample products
        for (Product p : products) {
            sqlite.addProduct(p);
        }

        // Add sample history
        sqlite.addHistory(new History("admin", products[0], 1));
        sqlite.addHistory(new History("manager", products[1], 1));
        sqlite.addHistory(new History("staff", products[2], 1));

        // Add sample logs
        sqlite.addLogs("NOTICE", "admin", "User creation successful", null);
        sqlite.addLogs("NOTICE", "manager", "User creation successful", null);
        sqlite.addLogs("NOTICE", "admin", "User creation successful", null);


        // Add sample users
        char[] password = {'Q', 'w', 'e', 'r', 't', 'y', '1', '2', '3', '4', '.'};
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

        for (History history : sqlite.getHistory()) {
            System.out.println("===== History " + history.getId() + " =====");
            System.out.println(" Username: " + history.getUsername());
            System.out.println(" Name: " + history.getName());
            System.out.println(" Stock: " + history.getQuantity());
            System.out.println(" Timestamp: " + history.getTimestamp());
        }

        for (Logs log : sqlite.getLogs()) {
            System.out.println("===== Logs " + log.getId() + " =====");
            System.out.println(" Username: " + log.getEvent());
            System.out.println(" Password: " + log.getUsername());
            System.out.println(" Role: " + log.getDesc());
            System.out.println(" Timestamp: " + log.getTimestamp());
        }

        for (Product product : sqlite.getProducts()) {
            System.out.println("===== Product " + product.getId() + " =====");
            System.out.println(" Name: " + product.getName());
            System.out.println(" Stock: " + product.getStock());
            System.out.println(" Price: " + product.getPrice());
        }

        for (User user : sqlite.getUsers()) {
            System.out.println("===== User " + user.getId() + " =====");
            System.out.println(" Username: " + user.getUsername());
            System.out.println(" Password: " + user.getPassword());
            System.out.println(" Role: " + user.getRole());
            System.out.println(" Locked: " + user.getIsLocked());
        }
    }
}
