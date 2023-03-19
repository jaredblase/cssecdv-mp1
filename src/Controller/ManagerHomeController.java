package Controller;

import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ManagerHomeController {
    private final CardLayout contentView = new CardLayout();
    private final JPanel content;

    public ManagerHomeController(ManagerHome view, SQLite db) {
        MgmtHistory mgmtHistory = new MgmtHistory(db);
        MgmtProduct mgmtProduct = new MgmtProduct();

        new MgmtProductController(mgmtProduct, db);

        content = view.getContent();
        content.setLayout(contentView);
        content.add(new Home("WELCOME MANAGER!", new java.awt.Color(153,102,255)), "home");
        content.add(mgmtProduct, Panel.PRODUCTS.name());
        content.add(mgmtHistory, Panel.HISTORY.name());

        view.setProductsBtnListener(this::onProductAction);
        view.setHistoryBtnListener(this::onHistoryAction);
    }

    private void onProductAction(ActionEvent e) {
        contentView.show(content, Panel.PRODUCTS.name());
    }

    private void onHistoryAction(ActionEvent e) {
        contentView.show(content, Panel.HISTORY.name());
    }
}
