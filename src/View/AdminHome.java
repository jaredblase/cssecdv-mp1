/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
//[255,102,51]

import javax.swing.*;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

/**
 * @author BeepXD
 */
public class AdminHome extends javax.swing.JPanel {
    private ActionListener usersBtnListener;
    private ActionListener logsBtnListener;

    public AdminHome() {
        initComponents();
    }

    public void showPnl(String panelName) {
        ((CardLayout) this.Content.getLayout()).show(Content, panelName);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usersBtn = new javax.swing.JButton();
        Content = new javax.swing.JPanel();
        logsBtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 153, 255));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        usersBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        usersBtn.setText("USERS");
        usersBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersBtnActionPerformed(evt);
            }
        });

        Content.setBackground(new java.awt.Color(51, 153, 255));

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

        logsBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        logsBtn.setText("LOGS");
        logsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logsBtnActionPerformed(evt);
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
                        .addComponent(usersBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(logsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usersBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void usersBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersBtnActionPerformed
        usersBtn.setForeground(Color.red);
        logsBtn.setForeground(Color.black);

        if (usersBtnListener != null) {
            usersBtnListener.actionPerformed(evt);
        }
    }//GEN-LAST:event_usersBtnActionPerformed

    private void logsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logsBtnActionPerformed
        usersBtn.setForeground(Color.black);
        logsBtn.setForeground(Color.red);

        if (logsBtnListener != null) {
            logsBtnListener.actionPerformed(evt);
        }
    }//GEN-LAST:event_logsBtnActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        usersBtn.setForeground(Color.black);
        logsBtn.setForeground(Color.black);
    }//GEN-LAST:event_formComponentShown

    public JPanel getContent() {
        return this.Content;
    }

    public void setUsersBtnListener(ActionListener usersBtnListener) {
        this.usersBtnListener = usersBtnListener;
    }

    public void setLogsBtnListener(ActionListener logsBtnListener) {
        this.logsBtnListener = logsBtnListener;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Content;
    private javax.swing.JButton logsBtn;
    private javax.swing.JButton usersBtn;
    // End of variables declaration//GEN-END:variables
}
