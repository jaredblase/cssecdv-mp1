package Controller.rolehome;

import Controller.tables.MgmtProductController;
import Controller.Panel;
import Controller.SQLite;
import View.ClientHome;
import View.Home;
import View.MgmtHistory;
import View.MgmtProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ClientHomeController {
    private final JPanel content;
    private final CardLayout contentView = new CardLayout();

    public ClientHomeController(ClientHome view, SQLite db) {
        MgmtHistory mgmtHistory = new MgmtHistory();
        MgmtProduct mgmtProduct = new MgmtProduct();

        new MgmtProductController(mgmtProduct, db);

        content = view.getContent();
        content.setLayout(contentView);
        content.add(new Home("WELCOME CLIENT!", new java.awt.Color(255,102,51)), "home");
        content.add(mgmtProduct, Controller.Panel.PRODUCTS.name());
        content.add(mgmtHistory, Controller.Panel.HISTORY.name());

        view.setProductsBtnListener(this::onProductAction);
        view.setHistoryBtnListener(this::onHistoryAction);
    }

    private void onProductAction(ActionEvent e) {
        contentView.show(content, Controller.Panel.PRODUCTS.name());
    }

    private void onHistoryAction(ActionEvent e) {
        contentView.show(content, Panel.HISTORY.name());
    }
}
