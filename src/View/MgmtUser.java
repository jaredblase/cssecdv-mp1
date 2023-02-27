/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.SQLite;
import Model.User;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MgmtUser extends javax.swing.JPanel {

    public SQLite sqlite;
    public DefaultTableModel tableModel;
    private ChangePasswordListener changePasswordListener;
    private ShowComponentListener showTableListener; 
    private final JLabel errorLbl = new JLabel("");
    final JOptionPane optionPane = new JOptionPane();
    private final JDialog dialog;

    public MgmtUser(SQLite sqlite) {
        initComponents();
        this.sqlite = sqlite;
        tableModel = (DefaultTableModel) table.getModel();
        table.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));

        errorLbl.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorLbl.setForeground(Color.RED);
        errorLbl.setHorizontalAlignment(SwingConstants.CENTER);

        dialog = new JDialog(SwingUtilities.getWindowAncestor(this));
        dialog.setContentPane(optionPane);

//        UNCOMMENT TO DISABLE BUTTONS
//        editBtn.setVisible(false);
//        deleteBtn.setVisible(false);
//        lockBtn.setVisible(false);
//        chgpassBtn.setVisible(false);
    }

    public void clearTableData() {
        tableModel.setRowCount(0);
    }

    public void setTableData(User user, int index) {
        var data = new Object[]{
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
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
                    user.getRole(),
                    user.getIsLocked()
            });
        }
    }

    public String getUsernameAt(int idx) {
        return (String) table.getModel().getValueAt(idx, 0);
    }

    public void designer(JTextField component, String text) {
        component.setSize(70, 600);
        component.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 18));
        component.setBackground(new java.awt.Color(240, 240, 240));
        component.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        component.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), text, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));
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
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Username", "Password", "Role", "Locked"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
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
        if (table.getSelectedRow() >= 0) {
            String[] options = {"1-DISABLED", "2-CLIENT", "3-STAFF", "4-MANAGER", "5-ADMIN"};
            JComboBox optionList = new JComboBox(options);

            optionList.setSelectedIndex((int) tableModel.getValueAt(table.getSelectedRow(), 2) - 1);

            String result = (String) JOptionPane.showInputDialog(null, "USER: " + tableModel.getValueAt(table.getSelectedRow(), 0),
                    "EDIT USER ROLE", JOptionPane.QUESTION_MESSAGE, null, options, options[(int) tableModel.getValueAt(table.getSelectedRow(), 2) - 1]);

            if (result != null) {
                System.out.println(tableModel.getValueAt(table.getSelectedRow(), 0));
                System.out.println(result.charAt(0));
            }
        }
    }//GEN-LAST:event_editRoleBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        if (table.getSelectedRow() >= 0) {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + tableModel.getValueAt(table.getSelectedRow(), 0) + "?", "DELETE USER", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                System.out.println(tableModel.getValueAt(table.getSelectedRow(), 0));
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void lockBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lockBtnActionPerformed
        if (table.getSelectedRow() >= 0) {
            String state = "lock";
            if ("1".equals(tableModel.getValueAt(table.getSelectedRow(), 3) + "")) {
                state = "unlock";
            }

            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to " + state + " " + tableModel.getValueAt(table.getSelectedRow(), 0) + "?", "DELETE USER", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                System.out.println(tableModel.getValueAt(table.getSelectedRow(), 0));
            }
        }
    }//GEN-LAST:event_lockBtnActionPerformed

    private void chgpassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chgpassBtnActionPerformed
        int rowIdx = table.getSelectedRow();
        if (rowIdx < 0) return;

        final JPasswordField passwordFld = new JPasswordField();
        final JPasswordField confFld = new JPasswordField();

        designer(passwordFld, "PASSWORD");
        designer(confFld, "CONFIRM PASSWORD");
        errorLbl.setText("");
        errorLbl.setPreferredSize(new Dimension(200, 30));
        Object[] message = {"Enter New Password:", passwordFld, confFld, errorLbl};

        dialog.setTitle("CHANGE PASSWORD");
        optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
        optionPane.setMessage(message);
        optionPane.setMessageType(JOptionPane.PLAIN_MESSAGE);
        optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);

        optionPane.addPropertyChangeListener(e -> {
            String prop = e.getPropertyName();
            if (!dialog.isVisible() || e.getSource() != optionPane || !prop.equals(JOptionPane.VALUE_PROPERTY) ||
                    e.getNewValue().equals(JOptionPane.UNINITIALIZED_VALUE)) return;

            int value = (Integer) e.getNewValue();
            if (value == JOptionPane.CANCEL_OPTION) {
                dialog.setVisible(false);
                return;
            }

            char[] password = passwordFld.getPassword();
            char[] confirm = confFld.getPassword();

            changePasswordListener.onChangePassword(rowIdx, password, confirm);

            Arrays.fill(password, '0');
            Arrays.fill(confirm, '0');
        });

        dialog.pack();
        var d = dialog.getPreferredSize();
        dialog.setMinimumSize(new Dimension(d.width + 50, d.height));
        dialog.setLocationRelativeTo(dialog.getOwner());
        dialog.setVisible(true);
    }//GEN-LAST:event_chgpassBtnActionPerformed

    private void tableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tableAncestorAdded
        if (showTableListener != null) {
            showTableListener.onShow();
        }
    }//GEN-LAST:event_tableAncestorAdded

    public void setErrorMessage(String text) {
        // add text wrap behavior
        errorLbl.setText("<html>" + text + "</html>");
        optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
    }

    public void closeDialog() {
        dialog.setVisible(false);
    }

    public void setChangePasswordListener(ChangePasswordListener changePasswordListener) {
        this.changePasswordListener = changePasswordListener;
    }
    
    public void setShowTableListener(ShowComponentListener showTableListener) {
        this.showTableListener = showTableListener;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    public interface ChangePasswordListener {
        void onChangePassword(int idx, char[] password, char[] confirm);
    }
    
    public interface ShowComponentListener {
        void onShow();
    }
}
