package Controller;

import Model.PasswordException;
import Model.User;
import View.Register;

import java.sql.SQLException;
import java.util.Arrays;

import static Model.PasswordUtils.validatePassword;

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
        if (username.length() == 0) {
            regView.setErrorMessage("Username cannot be blank.");
            return;
        }

        try {
            validatePassword(password);

            if (password.length != confirm.length || !Arrays.equals(password, confirm)) {
                throw new PasswordException("Password and confirm password do not match.");
            }

            User user = new User(username, password);
            db.addUser(user);
            main.showPanel(Panel.LOGIN);
        } catch (SQLException e) {
            regView.setErrorMessage(e.getErrorCode() == 19 ? "Username is already taken." : "An error has occurred on our end. Please try again later.");
        } catch (PasswordException e) {
            regView.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            regView.setErrorMessage("An error has occurred on our end. Please try again later.");
        }
    }
}
