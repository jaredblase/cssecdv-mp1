package Model;

import static Model.PasswordUtils.*;

public class User {
    private int id;
    private String username;
    private String password;
    private Role role;
    private int attempts;
    public final static int MAX_ATTEMPTS = 3;

    public User(String username, char[] password) throws UsernameException, PasswordException {
        this(username, password, Role.CLIENT);
    }

    public User(String username, char[] password, Role role) throws UsernameException, PasswordException {
        this(username, password, role, 0);
    }

    public User(String username, char[] password, Role role, int attempts) throws UsernameException, PasswordException {
        this.setUsername(username);
        this.setPassword(password);
        this.setRole(role);
        this.attempts = attempts;
    }

    public User(int id, String username, String password, int role, int attempts) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.setRole(role);
        this.attempts = attempts;
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

    private void setUsername(String username) throws UsernameException {
        if (username.length() == 0 || username.length() > 20) {
            throw new UsernameException("Username should be between 1 to 20 characters");
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

    public Role getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = Role.valueOf(role);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getAttempts() {
        return attempts;
    }

    public void addAttempt() {
        this.attempts++;
    }

    public void clearAttempts() {
        this.attempts = 0;
    }

    public boolean getIsLocked() {
        return attempts >= User.MAX_ATTEMPTS;
    }
}
