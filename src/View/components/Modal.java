package View.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Modal {
    private final JLabel errorLbl = new JLabel("");
    final JOptionPane optionPane = new JOptionPane();
    private final JDialog dialog;
    private final ArrayList<Object> messages = new ArrayList<>();
    private Callback callback;

    public Modal(Component c) {
        errorLbl.setFont(new Font("Tahoma", Font.BOLD, 12));
        errorLbl.setForeground(Color.RED);
        errorLbl.setHorizontalAlignment(SwingConstants.CENTER);

        dialog = new JDialog(SwingUtilities.getWindowAncestor(c));
        dialog.setContentPane(optionPane);

        optionPane.addPropertyChangeListener(e -> {
            String prop = e.getPropertyName();
            if (!dialog.isVisible() || e.getSource() != optionPane || !prop.equals(JOptionPane.VALUE_PROPERTY) ||
                    e.getNewValue().equals(JOptionPane.UNINITIALIZED_VALUE)) return;

            int value = (Integer) e.getNewValue();
            if (value == JOptionPane.CANCEL_OPTION) {
                dialog.setVisible(false);
                return;
            }

            if (callback != null) {
                callback.callback();
            }
        });
    }

    public static void design(JTextField component, String text) {
        component.setSize(70, 600);
        component.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 18));
        component.setBackground(new java.awt.Color(240, 240, 240));
        component.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        component.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), text, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", Font.PLAIN, 12)));
    }

    public void setup(String title, int messageType, int optionType) {
        errorLbl.setText("");
        errorLbl.setPreferredSize(new Dimension(200, 30));
        messages.clear();

        dialog.setTitle(title);
        optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
        optionPane.setMessageType(messageType);
        optionPane.setOptionType(optionType);

    }

    public void addMessages(Object message, Object... messages) {
        this.messages.add(message);
        this.messages.addAll(Arrays.asList(messages));
    }

    public void show() {
        messages.add(errorLbl);
        optionPane.setMessage(messages.toArray());
        dialog.pack();

        var d = dialog.getPreferredSize();
        dialog.setMinimumSize(new Dimension(d.width + 50, d.height));
        dialog.setLocationRelativeTo(dialog.getOwner());
        dialog.setVisible(true);
    }

    public void hide() {
        dialog.setVisible(false);
    }

    public void setErrorMessage(String text) {
        errorLbl.setText("<html>" + text + "</html>");
        optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void callback();
    }
}
