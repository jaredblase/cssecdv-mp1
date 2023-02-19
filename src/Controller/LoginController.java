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
            User user = db.getUserByUsername(username);
            if (user == null || !user.matchPassword(password)) {
                loginView.setErrorMessage("Invalid username or password.");
                loginView.clearPasswordField();
                return;
            }
            main.showPanel(Panel.HOME);
        } catch (Exception e) {
            loginView.setErrorMessage("An problem occurred on our side. Please try again later.");
        }
    }
}
