package Controller;

import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class SQLite {
    public boolean DEBUG_MODE = false;
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

    public void createSessionsTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS sessions (
                 id TEXT PRIMARY KEY,
                 username TEXT NOT NULL,
                 `timestamp` TEXT NOT NULL
                );""";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table sessions in database.db created.");
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

    public void dropSessionsTable() {
        String sql = "DROP TABLE IF EXISTS sessions;";

        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table sessions in database.db dropped.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addHistory(History history) {
        try (Connection conn = DriverManager.getConnection(driverURL)) {
            addHistory(conn, history);
        } catch (Exception ex) {
            if (DEBUG_MODE) ex.printStackTrace();
        }
    }

    public void addHistory(Connection conn, History history) throws Exception {
        String sql = "INSERT INTO history(username,`name`,quantity,price,`timestamp`) VALUES(?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, history.getUsername());
            stmt.setString(2, history.getName());
            stmt.setInt(3, history.getQuantity());
            stmt.setDouble(4, history.getPrice());
            stmt.setString(5, history.getTimestamp().toString());
            stmt.executeUpdate();
        }
    }

    public void addLogs(String event, String username, String desc, String timestamp) throws SQLException {
        String sql = "INSERT INTO logs(event,username,`desc`,`timestamp`) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event);
            stmt.setString(2, username);
            stmt.setString(3, desc);
            stmt.setString(4, timestamp == null ? new Timestamp(new Date().getTime()).toString() : timestamp);
            stmt.executeUpdate();
        }
    }

    public void addUserEventLog(User user, String desc, String timestamp) throws SQLException {
        addLogs("NOTICE", user.getUsername(), desc, timestamp);
    }

    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO product(`name`,stock,price) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getStock());
            stmt.setDouble(3, product.getPrice());
            stmt.executeUpdate();
        }
    }

    public void updateProduct(Product product) throws SQLException {
        var sql = "UPDATE product SET stock = ?, price = ? WHERE `name` = ?";

        try (var conn = DriverManager.getConnection(driverURL);
             var stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, product.getStock());
            stmt.setFloat(2, product.getPrice());
            stmt.setString(3, product.getName());
            stmt.executeUpdate();
        }
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users(username,password,`role`,attempts) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getRole().getCode());
            stmt.setInt(4, user.getAttempts());
            stmt.executeUpdate();
        }
    }

    public void addSession(String sessId, String username) throws SQLException {
        String sql = "INSERT INTO sessions(id, username, `timestamp`) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sessId);
            stmt.setString(2, username);
            stmt.setString(3, new Timestamp(new Date().getTime()).toString());
            stmt.executeUpdate();
        }
    }

    public void saveUserAttempts(User u) throws SQLException {
        String sql = "UPDATE users SET attempts=? WHERE username=?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, u.getAttempts());
            stmt.setString(2, u.getUsername());
            stmt.executeUpdate();
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

    public void updateUserRoleByUsername(String username, Role role) throws SQLException, NullPointerException {
        if (role == null) throw new NullPointerException();

        String sql = "UPDATE users SET `role`=? WHERE username=?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, role.getCode());
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
    }

    public void toggleUserLockByUsername(String username) throws SQLException {
        String sql = "UPDATE users SET attempts = (CASE attempts WHEN 0 THEN 200 ELSE 0 END) WHERE username=?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }

    public void addPurchase(String username, String productName, int quantity) throws Exception {
        var prod = this.getProductById(productName);

        if (this.getUserByUsername(username) == null) {
            throw new Exception("User does not exist.");
        }

        if (prod == null) {
            throw new Exception("Product does not exist.");
        }

        if (prod.getStock() < quantity) {
            throw new Exception("Quantity input is greater than available stocks.");
        }

        var sql = "UPDATE product SET stock = stock - ? WHERE `name` = ?";

        try (var conn = DriverManager.getConnection(driverURL)) {
            try (var stmt = conn.prepareStatement(sql)) {
                conn.setAutoCommit(false);  // initialize transaction

                stmt.setInt(1, quantity);
                stmt.setString(2, productName);
                stmt.executeUpdate();

                this.addHistory(conn, new History(username, prod, quantity));

                conn.commit();
            } catch (Exception ex) {
                conn.rollback();

                if (DEBUG_MODE) ex.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
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
            if (DEBUG_MODE) ex.printStackTrace();
        }
        return histories;
    }

    public ArrayList<History> getUserHistoryByUsername(String username) {
        String sql = "SELECT * FROM history WHERE username = ?";
        ArrayList<History> histories = new ArrayList<>();

        try (var conn = DriverManager.getConnection(driverURL);
             var stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getFloat("price"),
                        rs.getString("timestamp")));
            }
        } catch (Exception ex) {
            if (DEBUG_MODE) ex.printStackTrace();
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
            if (DEBUG_MODE) ex.printStackTrace();
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
            if (DEBUG_MODE) ex.printStackTrace();
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
            if (DEBUG_MODE) ex.printStackTrace();
        }
        return users;
    }

    public Session getSessionById(String id) throws SQLException {
        String sql = "SELECT * FROM sessions WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? new Session(
                    rs.getString("id"),
                    rs.getString("username"),
                    rs.getString("timestamp")
            ) : null;
        }
    }

    public void deleteSessionById(String sessId) throws SQLException {
        String sql = "DELETE FROM sessions WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sessId);
            stmt.executeUpdate();
        }
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

    public void deleteUserByUsername(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username=?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
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
            if (DEBUG_MODE) ex.printStackTrace();
        }
        return product;
    }

    public void deleteProductByName(String name) throws SQLException {
        String sql = "DELETE FROM product WHERE `name` = ?";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    public void deleteLogs() throws SQLException {
        try (Connection conn = DriverManager.getConnection(driverURL);
             Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate("DELETE FROM logs");
        }
    }
}
