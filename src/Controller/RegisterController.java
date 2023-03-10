package Controller;

import Model.PasswordException;
import Model.User;
import Model.UsernameException;
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
            db.addUserEventLog(user, "User creation successful", null);
            main.showPanel(Panel.LOGIN);
        } catch (PasswordException | UsernameException e) {
            regView.setErrorMessage(e.getMessage());
        } catch (SQLException e) {
            if (e.getErrorCode() == 19) {
                regView.setErrorMessage("Username is already taken!");
                return;
            }

            regView.setErrorMessage("An error has occurred on our end. Please try again later.");
            e.printStackTrace();
        }
    }
}
