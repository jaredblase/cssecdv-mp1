/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
//[255,102,51]
import Controller.SQLite;
import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BeepXD
 */
public class ClientHome extends javax.swing.JPanel {

    public MgmtHistory mgmtHistory;
    public MgmtProduct mgmtProduct;
    private final CardLayout contentView = new CardLayout();
    
    public ClientHome() {
        initComponents();
    }
    
    public void init(SQLite sqlite){
        mgmtHistory = new MgmtHistory(sqlite);
        mgmtProduct = new MgmtProduct();

        Content.setLayout(contentView);
        Content.add(new Home("WELCOME CLIENT!", new java.awt.Color(255,102,51)), "home");
        Content.add(mgmtHistory, "mgmtHistory");
        Content.add(mgmtProduct, "mgmtProduct");
    }
    
    public void showPnl(String panelName){
        contentView.show(Content, panelName);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        productsBtn = new javax.swing.JButton();
        Content = new javax.swing.JPanel();
        historyBtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 102, 51));

        productsBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        productsBtn.setText("PRODUCTS");
        productsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productsBtnActionPerformed(evt);
            }
        });

        Content.setBackground(new java.awt.Color(255, 102, 51));

        javax.swing.GroupLayout ContentLayout = new javax.swing.GroupLayout(Content);
        Content.setLayout(ContentLayout);
        ContentLayout.setHorizontalGroup(
            ContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ContentLayout.setVerticalGroup(
            ContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 271, Short.MAX_VALUE)
        );

        historyBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        historyBtn.setText("HISTORY");
        historyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(productsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(historyBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(historyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void productsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productsBtnActionPerformed
        productsBtn.setForeground(Color.red);
        historyBtn.setForeground(Color.black);
        contentView.show(Content, "mgmtProduct");
    }//GEN-LAST:event_productsBtnActionPerformed

    private void historyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyBtnActionPerformed
        mgmtHistory.init();
        productsBtn.setForeground(Color.black);
        historyBtn.setForeground(Color.red);
        contentView.show(Content, "mgmtHistory");
    }//GEN-LAST:event_historyBtnActionPerformed
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Content;
    private javax.swing.JButton historyBtn;
    private javax.swing.JButton productsBtn;
    // End of variables declaration//GEN-END:variables
}
