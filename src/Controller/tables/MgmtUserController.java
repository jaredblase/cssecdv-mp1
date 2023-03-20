package Controller.tables;

import Controller.SQLite;
import Model.PasswordException;
import Model.User;
import Model.UsernameException;
import View.MgmtUser;

import java.sql.SQLException;
import java.util.Arrays;

import static Model.PasswordUtils.validatePassword;

public class MgmtUserController {
    private final MgmtUser view;
    private final SQLite db;

    public MgmtUserController(MgmtUser view, SQLite db) {
        this.view = view;
        this.db = db;

        view.setShowTableListener(this::resetTable);
        view.setChangePasswordListener(this::onChangePassword);
    }
    
    private void resetTable() {
        view.clearTableData();
        view.setTableData(db.getUsers());
    }

    private void onChangePassword(int idx, char[] password, char[] confirm) {
        try {
            validatePassword(password);

            if (password.length != confirm.length || !Arrays.equals(password, confirm)) {
                throw new PasswordException("Password and confirmation do not match!");
            }

            String username = view.getUsernameAt(idx);
            db.saveUserPassword(new User(username, password));
            view.setTableData(db.getUserByUsername(username), idx);
            view.closeDialog();
        } catch (UsernameException | PasswordException e) {
            view.setErrorMessage(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            view.setErrorMessage("An error has occurred on our end. Please try again later.");
        }
    }
}
