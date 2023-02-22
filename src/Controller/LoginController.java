package Controller;

import Model.User;
import View.Login;

public class LoginController {
    private final Login loginView;
    private final Main main;
    private final SQLite db;

    public LoginController(Main main, Login loginView, SQLite db) {
        this.loginView = loginView;
        this.db = db;
        this.main = main;

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
            if (user == null || user.getLocked() || !user.matchPassword(password)) {
                if (user != null) {
                    user.setAttempts(user.getAttempts() + 1);
                    db.setUserAttempts(user.getUsername(), user.getAttempts());
                    user.log(db, "User login failed attempt");
                    if (user.getAttempts() == User.MAX_ATTEMPTS) {
                        user.log(db, "User locked");
                    }
                }

                String error = user == null || !user.getLocked() ? "Invalid username or password." :
                        "Too many failed attempts. Contact an admin to unlock this account.";
                loginView.setErrorMessage(error);
                loginView.clearPasswordField();
                return;
            }

            user.setAttempts(0);
            db.clearUserAttempts(user.getUsername());
            user.log(db, "User login successful");
            main.setUser(user);
            main.showPanel(Panel.HOME);
        } catch (Exception e) {
            loginView.setErrorMessage("A problem occurred on our side. Please try again later.");
        }
    }
}
