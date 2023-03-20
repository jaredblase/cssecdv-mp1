package Controller.rolehome;

import Controller.tables.MgmtProductController;
import Controller.SQLite;
import View.Home;
import View.MgmtProduct;
import View.StaffHome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StaffHomeController {
    private final CardLayout contentView = new CardLayout();
    private final JPanel content;

    public StaffHomeController(StaffHome view, SQLite db) {
        MgmtProduct mgmtProduct = new MgmtProduct();
        new MgmtProductController(mgmtProduct, db);

        content = view.getContent();
        content.setLayout(contentView);
        content.add(new Home("WELCOME STAFF!", new java.awt.Color(0, 204, 102)), "home");
        content.add(mgmtProduct, Controller.Panel.PRODUCTS.name());

        view.setProductsBtnListener(this::onProductAction);
    }

    private void onProductAction(ActionEvent e) {
        contentView.show(content, Controller.Panel.PRODUCTS.name());
    }
}
