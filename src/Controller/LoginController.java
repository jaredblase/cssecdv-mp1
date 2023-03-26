package Controller;

import Model.SessionManager;
import Model.User;
import View.Login;

public class LoginController {
    private final Login loginView;
    private final SQLite db;

    public LoginController(Main main, Login loginView, SQLite db) {
        this.loginView = loginView;
        this.db = db;

        loginView.setRegisterListener(e -> main.showPanel(Panel.REGISTER));
        loginView.setForgotPassword(e -> main.showPanel(Panel.FORGOT_PASSWORD));
        loginView.setLoginListener(this::loginAction);
    }

    public void loginAction(String username, char[] password) {
        if (username.length() == 0) {
            loginView.setErrorMessage("Username cannot be empty.");
            return;
        }

        if (password.length == 0) {
            loginView.setErrorMessage("Password cannot be empty.");
            return;
        }

        try {
            User user = db.getUserByUsername(username.toLowerCase());
            if (user == null || user.getIsLocked() || !user.matchPassword(password)) {
                if (user != null) {
                    user.addAttempt();
                    db.saveUserAttempts(user);
                    db.addUserEventLog(user, "User login failed attempt", null);
                    if (user.getIsLocked()) {
                        db.addUserEventLog(user, "User locked", null);
                    }
                }

                String error = user == null || !user.getIsLocked() ? "Invalid username or password." :
                        "This account has been locked. Please contact an admin for unlocking process.";
                loginView.setErrorMessage(error);
                loginView.clearPasswordField();
                return;
            }

            user.clearAttempts();
            db.saveUserAttempts(user);
            db.addUserEventLog(user, "User login successful", null);

            SessionManager.login(db, username);
        } catch (Exception e) {
            if (db.DEBUG_MODE) e.printStackTrace();
            loginView.setErrorMessage("A problem occurred on our side. Please try again later.");
        }
    }
}
