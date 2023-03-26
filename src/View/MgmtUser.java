/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.User;
import View.components.Modal;

import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MgmtUser extends javax.swing.JPanel {
    public DefaultTableModel tableModel;
    private ChangePasswordListener changePasswordListener;
    private ShowComponentListener showTableListener;
    private IndexActionListener deleteUserListener;
    private IndexActionListener lockUserListener;
    private EditUserListener editUserListener;
    private final Modal modal;

    public MgmtUser() {
        initComponents();
        tableModel = (DefaultTableModel) table.getModel();
        table.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        modal = new Modal(this);
    }

    public void clearTableData() {
        tableModel.setRowCount(0);
    }

    public void setTableData(User user, int index) {
        var data = new Object[]{
                user.getUsername(),
                user.getPassword(),
                user.getRole().getCode(),
                user.getIsLocked()
        };

        for (int i = 0; i < data.length; i++) {
            tableModel.setValueAt(data[i], index, i);
        }
    }

    public void setTableData(List<User> users) {
        for (User user : users) {
            tableModel.addRow(new Object[]{
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole().getCode(),
                    user.getIsLocked()
            });
        }
    }

    public String getUsernameAt(int idx) {
        return (String) table.getModel().getValueAt(idx, 0);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        final javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        final javax.swing.JButton editRoleBtn = new javax.swing.JButton();
        final javax.swing.JButton deleteBtn = new javax.swing.JButton();
        final javax.swing.JButton lockBtn = new javax.swing.JButton();
        final javax.swing.JButton chgpassBtn = new javax.swing.JButton();

        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Username", "Password", "Role", "Locked"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);
        table.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tableAncestorAdded(evt);
            }

            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }

            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(160);
            table.getColumnModel().getColumn(1).setPreferredWidth(400);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        editRoleBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        editRoleBtn.setText("EDIT ROLE");
        editRoleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editRoleBtnActionPerformed(evt);
            }
        });

        deleteBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        deleteBtn.setText("DELETE");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        lockBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lockBtn.setText("LOCK/UNLOCK");
        lockBtn.setToolTipText("");
        lockBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lockBtnActionPerformed(evt);
            }
        });

        chgpassBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chgpassBtn.setText("CHANGE PASS");
        chgpassBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chgpassBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(editRoleBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(0, 0, 0)
                                                .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(0, 0, 0)
                                                .addComponent(lockBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(0, 0, 0)
                                                .addComponent(chgpassBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1))
                                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(chgpassBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(editRoleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lockBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editRoleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editRoleBtnActionPerformed
        var idx = table.getSelectedRow();
        if (idx < 0) return;

        String[] options = {"2-CLIENT", "3-STAFF", "4-MANAGER", "5-ADMIN"};
        JComboBox optionList = new JComboBox(options);

        optionList.setSelectedIndex((int) tableModel.getValueAt(table.getSelectedRow(), 2) - 2);

        String result = (String) JOptionPane.showInputDialog(null, "USER: " + getUsernameAt(idx),
                "EDIT USER ROLE", JOptionPane.QUESTION_MESSAGE, null, options, options[(int) tableModel.getValueAt(idx, 2) - 2]);

        if (result != null && editUserListener != null) {
            editUserListener.onEdit(idx, result.substring(0, 1));
        }
    }//GEN-LAST:event_editRoleBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        var idx = table.getSelectedRow();
        if (idx < 0) return;

        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + this.getUsernameAt(idx) + "?", "DELETE USER", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            deleteUserListener.onAction(idx);
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void lockBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lockBtnActionPerformed
        var idx = table.getSelectedRow();
        if (idx < 0) return;

        String state = (boolean) tableModel.getValueAt(idx, 3) ? "unlock" : "lock";

        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to " + state + " " + getUsernameAt(idx) + "?", "DELETE USER", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION && lockUserListener != null) {
            lockUserListener.onAction(idx);
        }
    }//GEN-LAST:event_lockBtnActionPerformed

    private void chgpassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chgpassBtnActionPerformed
        int rowIdx = table.getSelectedRow();
        if (rowIdx < 0) return;

        final JPasswordField passwordFld = new JPasswordField();
        final JPasswordField confFld = new JPasswordField();

        Modal.design(passwordFld, "PASSWORD");
        Modal.design(confFld, "CONFIRM PASSWORD");

        modal.setup("CHANGE PASSWORD", JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        modal.addMessages("Enter New Password:", passwordFld, confFld);
        modal.setCallback(() -> {
            char[] password = passwordFld.getPassword();
            char[] confirm = confFld.getPassword();

            changePasswordListener.onChangePassword(rowIdx, password, confirm);

            Arrays.fill(password, '0');
            Arrays.fill(confirm, '0');
        });
        modal.show();
    }//GEN-LAST:event_chgpassBtnActionPerformed

    private void tableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tableAncestorAdded
        if (showTableListener != null) {
            showTableListener.onShow();
        }
    }//GEN-LAST:event_tableAncestorAdded

    public void setErrorMessage(String text) {
        modal.setErrorMessage(text);
    }

    public void closeDialog() {
        modal.hide();
    }

    public void setChangePasswordListener(ChangePasswordListener changePasswordListener) {
        this.changePasswordListener = changePasswordListener;
    }

    public void setShowTableListener(ShowComponentListener showTableListener) {
        this.showTableListener = showTableListener;
    }

    public void setDeleteUserListener(IndexActionListener deleteUserListener) {
        this.deleteUserListener = deleteUserListener;
    }

    public void setLockUserListener(IndexActionListener lockUserListener) {
        this.lockUserListener = lockUserListener;
    }

    public void setEditUserListener(EditUserListener editUserListener) {
        this.editUserListener = editUserListener;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    public interface ChangePasswordListener {
        void onChangePassword(int idx, char[] password, char[] confirm);
    }

    public interface IndexActionListener {
        void onAction(int idx);
    }

    public interface EditUserListener {
        void onEdit(int idx, String role);
    }
}
