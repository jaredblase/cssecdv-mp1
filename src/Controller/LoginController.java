package Controller;

import Model.User;
import View.Login;

import java.util.Arrays;

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
        try {
            User user = db.getUserByUsername(username);
            if (user == null || !user.matchPassword(password)) {
                return;
            }

            main.showPanel(Panel.HOME);
        } finally {
            Arrays.fill(password, '0');
        }
    }
}
