package Controller;

import View.AdminHome;

public class AdminHomeController {
    public AdminHomeController(AdminHome view, SQLite db) {
        new MgmtUserController(view.mgmtUser, db);
    }
}
