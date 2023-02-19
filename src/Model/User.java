package Model;

import static Model.PasswordUtils.hashPassword;
import static Model.PasswordUtils.matchHashToPassword;

public class User {
    private int id;
    private String username;
    private String password;
    private int role;
    private int locked;

    public User(String username, char[] password) throws Exception {
        this(username, password, 2);
    }

    public User(String username, char[] password, int role) throws Exception {
        this(username, password, role, 0);
    }

    public User(String username, char[] password, int role, int locked) throws Exception {
        this.username = username;
        this.password = hashPassword(password);
        this.role = role;
        this.locked = locked;
    }

    public User(int id, String username, String password, int role, int locked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.locked = locked;
    }

    public boolean matchPassword(char[] password) {
        return matchHashToPassword(this.password, password);
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(char[] password) throws Exception {
        this.password = hashPassword(password);
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }
}
