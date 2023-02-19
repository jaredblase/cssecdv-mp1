package Controller;

import Model.User;
import View.Register;

import java.util.Arrays;

public class RegisterController {
    private final Register regView;
    private final Main main;
    private final SQLite db;

    public RegisterController(Main main, Register loginView, SQLite db) {
        this.regView = loginView;
        this.db = db;
        this.main = main;

        regView.setBackListener(e -> main.showPanel(Panel.LOGIN));
        regView.setRegisterListener(this::registerAction);
    }

    public void registerAction(String username, char[] password, char[] confirm) {
        try {
            if (password.length != confirm.length || !Arrays.equals(password, confirm)) {
                return;
            }

            User user = new User(username, password);
            db.addUser(user);
            main.showPanel(Panel.LOGIN);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Arrays.fill(password, '0');
            Arrays.fill(confirm, '0');
        }
    }
}
