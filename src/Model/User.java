package Model;

import Controller.SQLite;

import java.sql.Timestamp;
import java.util.Date;

import static Model.PasswordUtils.*;

public class User {
    private int id;
    private String username;
    private String password;
    private int role;
    private int attempts;
    public final static int MAX_ATTEMPTS = 3;

    public User(String username, char[] password) throws UsernameException, PasswordException {
        this(username, password, 2);
    }

    public User(String username, char[] password, int role) throws UsernameException, PasswordException {
        this(username, password, role, 0);
    }

    public User(String username, char[] password, int role, int attempts) throws UsernameException, PasswordException {
        this.setUsername(username);
        this.setPassword(password);
        this.role = role;
        this.attempts = attempts;
    }

    public User(int id, String username, String password, int role, int attempts) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.attempts = attempts;
    }

    public boolean matchPassword(char[] password) {
        return matchHashToPassword(this.password, password);
    }

    public void log(SQLite sqlite, String desc) {
        sqlite.addLogs("NOTICE", this.username, desc, new Timestamp(new Date().getTime()).toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws UsernameException {
        if (username.length() > 20) {
            throw new UsernameException("Username should be less than 20 characters");
        }

        if (username.contains(" ")) {
            throw new UsernameException("Username should not contain a space character");
        }

        this.username = username.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(char[] password) throws PasswordException {
        validatePassword(password);
        this.password = hashPassword(password);
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public boolean getLocked() {
        return attempts >= User.MAX_ATTEMPTS;
    }
}
