package Controller;

import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class SQLite {
    public int DEBUG_MODE = 0;
    String driverURL = "jdbc:sqlite:" + "database.db";

    public void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            if (conn != null) {
                System.out.println("Database database.db created.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createHistoryTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS history (
                 id INTEGER PRIMARY KEY AUTOINCREMENT,
                 username TEXT NOT NULL,
                 `name` TEXT NOT NULL,
                 price REAL NOT NULL,
                 quantity INTEGER DEFAULT 0,
                 `timestamp` TEXT NOT NULL
                );""";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db created.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createLogsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS logs (
                 id INTEGER PRIMARY KEY AUTOINCREMENT,
                 event TEXT NOT NULL,
                 username TEXT NOT NULL,
                 `desc` TEXT NOT NULL,
                 `timestamp` TEXT NOT NULL
                );""";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db created.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createProductTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS product (
                 id INTEGER PRIMARY KEY AUTOINCREMENT,
                 `name` TEXT NOT NULL UNIQUE,
                 stock INTEGER DEFAULT 0,
                 price REAL DEFAULT 0.00
                );""";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db created.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createUserTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                 id INTEGER PRIMARY KEY AUTOINCREMENT,
                 username TEXT NOT NULL UNIQUE,
                 password TEXT NOT NULL,
                 role INTEGER DEFAULT 2,
                 attempts INTEGER DEFAULT 0
                );""";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db created.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void dropHistoryTable() {
        String sql = "DROP TABLE IF EXISTS history;";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table history in database.db dropped.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void dropLogsTable() {
        String sql = "DROP TABLE IF EXISTS logs;";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table logs in database.db dropped.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void dropProductTable() {
        String sql = "DROP TABLE IF EXISTS product;";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table product in database.db dropped.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void dropUserTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table users in database.db dropped.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addHistory(History history) {
        String sql = "INSERT INTO history(username,`name`,quantity,price,`timestamp`) VALUES(?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, history.getUsername());
            stmt.setString(2, history.getName());
            stmt.setInt(3, history.getQuantity());
            stmt.setDouble(4, history.getPrice());
            stmt.setString(5, history.getTimestamp().toString());
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addLogs(String event, String username, String desc, String timestamp) {
        String sql = "INSERT INTO logs(event,username,`desc`,`timestamp`) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event);
            stmt.setString(2, username);
            stmt.setString(3, desc);
            stmt.setString(4, timestamp == null ? new Timestamp(new Date().getTime()).toString() : timestamp);
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addUserEventLog(User user, String desc, String timestamp) {
        addLogs("NOTICE", user.getUsername(), desc, timestamp);
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO product(`name`,stock,price) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getStock());
            stmt.setDouble(3, product.getPrice());
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users(username,password,`role`,attempts) VALUES(?,?,?,?)";

        Connection conn = DriverManager.getConnection(driverURL);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setInt(3, user.getRole());
        stmt.setInt(4, user.getAttempts());
        stmt.executeUpdate();
    }

    public void saveUserAttempts(User u) {
        String sql = "UPDATE users SET attempts=? WHERE username=?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, u.getAttempts());
            stmt.setString(2, u.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUserPassword(User u) throws SQLException {
        String sql = "UPDATE users SET password=? WHERE username=?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getPassword());
            stmt.setString(2, u.getUsername());
            stmt.executeUpdate();
        }
    }

    public ArrayList<History> getHistory() {
        String sql = "SELECT * FROM history";
        ArrayList<History> histories = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getFloat("price"),
                        rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return histories;
    }

    public ArrayList<Logs> getLogs() {
        String sql = "SELECT id, event, username, `desc`, `timestamp` FROM logs";
        ArrayList<Logs> logs = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                logs.add(new Logs(rs.getInt("id"),
                        rs.getString("event"),
                        rs.getString("username"),
                        rs.getString("desc"),
                        rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return logs;
    }

    public ArrayList<Product> getProducts() {
        String sql = "SELECT id, `name`, stock, price FROM product";
        ArrayList<Product> products = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(new Product(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getFloat("price")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return products;
    }

    public ArrayList<User> getUsers() {
        String sql = "SELECT id, username, password, `role`, attempts FROM users";
        ArrayList<User> users = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("role"),
                        rs.getInt("attempts")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT id, username, password, role, attempts FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("role"),
                        rs.getInt("attempts"));
            }
        }

        return null;
    }

    public void removeUserByUsername(String username) {
        String sql = "DELETE FROM users WHERE username=?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
            System.out.println("User " + username + " has been deleted.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Product getProductById(String name) {
        String sql = "SELECT `name`, stock, price FROM product WHERE `name`=?";
        Product product = null;
        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            product = new Product(rs.getString("name"),
                    rs.getInt("stock"),
                    rs.getFloat("price"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return product;
    }
}