
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EmployeeDashboard extends JFrame {

    private final JPanel contentPane;
    private final int employeeId;
    private String employeeName;

    public EmployeeDashboard(int id, String name, double salary, String dept) {
        this.employeeId = id;
        this.employeeName = name;
        setTitle("Employee Management System - Dashboard");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem logoutItem = new JMenuItem("Logout");
        JMenuItem resetPasswordItem = new JMenuItem("Reset Password");
        JMenuItem updateInfoItem = new JMenuItem("Update Info");
        JMenuItem deleteAccountItem = new JMenuItem("Delete Account");
        menu.add(logoutItem);
        menu.add(resetPasswordItem);
        menu.add(updateInfoItem);
        menu.add(deleteAccountItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Main content panel
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        // Welcome Panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(51, 153, 255));
        welcomePanel.setPreferredSize(new Dimension(800, 100));
        JLabel welcomeLabel = new JLabel("Welcome, " + name + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomeLabel);
        contentPane.add(welcomePanel, BorderLayout.NORTH);

        // Details Panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(5, 1, 10, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Employee Information"));

        // Style for information labels
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        JLabel idLabel = createStyledLabel("Employee ID: " + id, labelFont);
        JLabel nameLabel = createStyledLabel("Employee Name: " + name, labelFont);
        JLabel salaryLabel = createStyledLabel("Employee Salary: " + salary, labelFont);
        JLabel deptLabel = createStyledLabel("Employee Department: " + dept, labelFont);

        detailsPanel.add(idLabel);
        detailsPanel.add(nameLabel);
        detailsPanel.add(salaryLabel);
        detailsPanel.add(deptLabel);

        contentPane.add(detailsPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        buttonPanel.add(logoutButton);

        logoutButton.addActionListener(_ -> System.exit(0));

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        logoutItem.addActionListener(_ -> System.exit(0));

        resetPasswordItem.addActionListener(_ -> {
            String newPassword = JOptionPane.showInputDialog("Enter new password:");
            resetPassword(newPassword);
        });

        updateInfoItem.addActionListener(_ -> {
            // Code for updating employee information
        });

        deleteAccountItem.addActionListener(_ -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?", "Delete Account", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteAccount();
            }
        });
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.BLACK);
        return label;
    }

    private void resetPassword(String newPassword) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeInfo", "root", "2004");
            PreparedStatement ps = con.prepareStatement("UPDATE employee SET employee_password = ? WHERE employee_id = ?");
            ps.setString(1, newPassword);
            ps.setInt(2, employeeId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Password updated successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error resetting password: " + e.getMessage());
        }
    }

    private void deleteAccount() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeInfo", "root", "2004");
            PreparedStatement ps = con.prepareStatement("DELETE FROM employee WHERE employee_id = ?");
            ps.setInt(1, employeeId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Account deleted successfully!");
            System.exit(0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting account: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new EmployeeDashboard(1, "John Doe", 50000, "HR").setVisible(true);
    }
}
