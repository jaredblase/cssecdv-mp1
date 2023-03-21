package Controller.tables;

import Controller.SQLite;
import Model.PasswordException;
import Model.Role;
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
        view.setDeleteUserListener(this::onDelete);
        view.setLockUserListener(this::onLock);
        view.setEditUserListener(this::onEditRole);
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

    private void onEditRole(int idx, String role) {
        try {
            var r = Role.valueOf(Integer.parseInt(role));
            db.updateUserRoleByUsername(view.getUsernameAt(idx), r);
        } catch (SQLException e) {
            e.printStackTrace();
            view.setErrorMessage("An error has occurred on our end. Please try again later.");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            resetTable();
        }
    }

    private void onDelete(int idx) {
        try {
            db.deleteUserByUsername(view.getUsernameAt(idx));
            view.closeDialog();
        } catch (SQLException e) {
            e.printStackTrace();
            view.setErrorMessage("An error has occurred on our end. Please try again later.");
        } finally {
            resetTable();
        }
    }

    private void onLock(int idx) {
        try {
            db.toggleUserLockByUsername(view.getUsernameAt(idx));
        } catch (SQLException e) {
            e.printStackTrace();
            view.setErrorMessage("An error has occurred on our end. Please try again later.");
        } finally {
            resetTable();
        }
    }
}
