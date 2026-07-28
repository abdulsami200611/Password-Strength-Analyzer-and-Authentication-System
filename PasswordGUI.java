import java.awt.*;
import javax.swing.*;

public class PasswordGUI extends JFrame {

    private AuthSystem system = new AuthSystem();

    public PasswordGUI() {
        setTitle("Password Manager");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        showMainMenu();
    }

    private void showMainMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton registerBtn = new JButton("Register");
        JButton loginBtn = new JButton("Login");
        JButton changePassBtn = new JButton("Change Password");
        JButton showUsersBtn = new JButton("Show Users");
        JButton exitBtn = new JButton("Exit");

        panel.add(registerBtn);
        panel.add(loginBtn);
        panel.add(changePassBtn);
        panel.add(showUsersBtn);
        panel.add(exitBtn);

        add(panel);

        // ===== Actions =====

        registerBtn.addActionListener(e -> registerUser());
        loginBtn.addActionListener(e -> loginUser());
        changePassBtn.addActionListener(e -> changePassword());
        showUsersBtn.addActionListener(e -> system.showUsers());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    // ========== REGISTER ==========
    private void registerUser() {
        String username = JOptionPane.showInputDialog("Enter Username:");
        String password = JOptionPane.showInputDialog("Enter Password:");

        if (username != null && password != null) {
            system.register(username, password);
            JOptionPane.showMessageDialog(this, "Check console for result");
        }
    }

    // ========== LOGIN ==========
    private void loginUser() {
        String username = JOptionPane.showInputDialog("Enter Username:");
        String password = JOptionPane.showInputDialog("Enter Password:");

        if (username != null && password != null) {
            boolean success = system.login(username, password);

            if (success) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Login Failed!");
            }
        }
    }

    // ========== CHANGE PASSWORD ==========
    private void changePassword() {
        String username = JOptionPane.showInputDialog("Enter Username:");
        String oldPass = JOptionPane.showInputDialog("Enter Old Password:");
        String newPass = JOptionPane.showInputDialog("Enter New Password:");

        if (username != null && oldPass != null && newPass != null) {
            system.changePassword(username, oldPass, newPass);
            JOptionPane.showMessageDialog(this, "Check console for result");
        }
    }

    // ========== MAIN ==========
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PasswordGUI().setVisible(true);
        });
    }
}