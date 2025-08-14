package hrmanagement.view.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginDialog extends JDialog {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private boolean succeeded = false;

    public LoginDialog(Frame owner) {
        super(owner, "Login", true);
        setLayout(new BorderLayout(5, 5));
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            if (authenticate(getUsername(), getPassword())) {
                succeeded = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginDialog.this,
                        "Invalid username or password",
                        "Login",
                        JOptionPane.ERROR_MESSAGE);
                // reset username and password
                usernameField.setText("");
                passwordField.setText("");
                succeeded = false;
            }
        });
        buttonPanel.add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    private boolean authenticate(String username, String password) {
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}
