
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class DatabaseConnection extends JFrame {

    void retrieveData(String n, String p, JFrame frameToClose, Component parentComponent) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeInfo", "root", "2004");
            System.out.println("Database connected successfully!");

            ps = con.prepareStatement("SELECT * FROM employee WHERE employee_name = ? AND employee_password = ?");
            ps.setString(1, n);
            ps.setString(2, p);

            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("employee_id");
                String name = rs.getString("employee_name");
                double salary = rs.getDouble("employee_salary");
                String dept = rs.getString("employee_department");

                // Update last login time
                PreparedStatement psUpdate = con.prepareStatement("UPDATE employee SET last_login = CURRENT_TIMESTAMP WHERE employee_id = ?");
                psUpdate.setInt(1, id);
                psUpdate.executeUpdate();

                // Close login frame after successful login
                frameToClose.dispose();

                // Show options to the user
                showOptions(id, name, salary, dept);
            } else {
                JOptionPane.showMessageDialog(parentComponent, "Invalid credentials or user not found!",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(parentComponent, "Error: " + e.getMessage(),
                    "Exception", JOptionPane.ERROR_MESSAGE);
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    void showOptions(int id, String name, double salary, String dept) {
        String[] options = {
            "1. Add an Admin View",
            "2. Update/Delete Employee Data",
            "3. Change/Reset Password",
            "4. Store Last Login Time",
            "5. Add Employee" // New option
        };

        String choice = (String) JOptionPane.showInputDialog(
                null,
                "Choose an option:",
                "Options",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == null) {
            JOptionPane.showMessageDialog(null, "No option selected. Exiting.");
            System.exit(0);
        }

        switch (choice) {
            case "1. Add an Admin View":
                showAdminView();
                break;
            case "2. Update/Delete Employee Data":
                authenticateAndUpdateOrDelete();
                break;
            case "3. Change/Reset Password":
                resetPassword();
                break;
            case "4. Store Last Login Time":
                JOptionPane.showMessageDialog(null, "Last login time is already being stored!");
                break;
            case "5. Add Employee": // Handle the new option
                addEmployee();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid option selected!");
        }
    }

    void showAdminView() {
        // Code to display all employee records
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeInfo", "root", "2004"); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM employee")) {

            StringBuilder sb = new StringBuilder("Employee Records:\n\n");
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("employee_id"))
                        .append(", Name: ").append(rs.getString("employee_name"))
                        .append(", Salary: ").append(rs.getDouble("employee_salary"))
                        .append(", Department: ").append(rs.getString("employee_department"))
                        .append(", Last Login: ").append(rs.getTimestamp("last_login"))
                        .append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.toString(), "Admin View", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching employee records: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void authenticateAndUpdateOrDelete() {
        if (!verifyPredefinedPassword()) {
            return; // Exit if password verification fails
        }

        String idStr = JOptionPane.showInputDialog("Enter Employee ID:");
        try {
            int id = Integer.parseInt(idStr);
            updateOrDeleteEmployee(id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Employee ID entered!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void updateOrDeleteEmployee(int id) {
        if (!verifyPredefinedPassword()) {
            return; // Exit if password verification fails
        }

        String[] options = {"Update Employee Data", "Delete Employee"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose an action:",
                "Update/Delete Employee",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            // Update employee data
            String newName = JOptionPane.showInputDialog("Enter new name:");
            String newDept = JOptionPane.showInputDialog("Enter new department:");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeInfo", "root", "2004"); PreparedStatement ps = con.prepareStatement("UPDATE employee SET employee_name = ?, employee_department = ? WHERE employee_id = ?")) {

                ps.setString(1, newName);
                ps.setString(2, newDept);
                ps.setInt(3, id);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Employee data updated successfully!");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error updating employee data: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (choice == 1) {
            // Delete employee
            try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeInfo", "root", "2004"); PreparedStatement ps = con.prepareStatement("DELETE FROM employee WHERE employee_id = ?")) {

                ps.setInt(1, id);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Employee deleted successfully!");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error deleting employee: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void resetPassword() {
        String idStr = JOptionPane.showInputDialog("Enter Employee ID:");
        try {
            int id = Integer.parseInt(idStr);
            String newPassword = JOptionPane.showInputDialog("Enter new password:");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeInfo", "root", "2004"); PreparedStatement ps = con.prepareStatement("UPDATE employee SET employee_password = ? WHERE employee_id = ?")) {

                ps.setString(1, newPassword);
                ps.setInt(2, id);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Password updated successfully!");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error resetting password: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Employee ID entered!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void addEmployee() {
        if (!verifyPredefinedPassword()) {
            return; // Exit if password verification fails
        }

        try {
            // Input employee details
            String name = JOptionPane.showInputDialog("Enter Employee Name:");
            String password = JOptionPane.showInputDialog("Enter Employee Password:");
            String salaryStr = JOptionPane.showInputDialog("Enter Employee Salary:");
            String department = JOptionPane.showInputDialog("Enter Employee Department:");

            // Validate inputs
            if (name == null || password == null || salaryStr == null || department == null
                    || name.isEmpty() || password.isEmpty() || salaryStr.isEmpty() || department.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double salary;
            try {
                salary = Double.parseDouble(salaryStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid salary entered!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Insert employee into the database
            try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/employeeInfo", "root", "2004"); PreparedStatement ps = con.prepareStatement("INSERT INTO employee (employee_name, employee_password, employee_salary, employee_department) VALUES (?, ?, ?, ?)")) {

                ps.setString(1, name);
                ps.setString(2, password);
                ps.setDouble(3, salary);
                ps.setString(4, department);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Employee added successfully!");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error adding employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean verifyPredefinedPassword() {
        String predefinedPassword = "2025";
        String enteredPassword = JOptionPane.showInputDialog("Enter the predefined password to proceed:");
        if (predefinedPassword.equals(enteredPassword)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect password. Access denied!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    void showFrame() {
        DatabaseConnection obj = new DatabaseConnection();

        JFrame f = new JFrame("Employee Login");
        f.setSize(450, 350);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setBackground(new Color(240, 240, 240));

        // Center the frame on screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - f.getWidth()) / 2;
        int y = (screenSize.height - f.getHeight()) / 2;
        f.setLocation(x, y);

        // Header Panel with gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 102, 204), getWidth(), 0, new Color(0, 153, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBounds(0, 0, 450, 80);
        headerPanel.setLayout(null);

        JLabel titleLabel = new JLabel("EMPLOYEE LOGIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 450, 80);
        headerPanel.add(titleLabel);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBounds(50, 100, 350, 200);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(30, 30, 100, 25);
        l1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField nameField = new JTextField();
        nameField.setBounds(140, 30, 180, 30);
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(30, 80, 100, 25);
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JPasswordField passField = new JPasswordField();
        passField.setBounds(140, 80, 180, 30);
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JButton b1 = new JButton("LOGIN");
        b1.setBounds(120, 130, 120, 35);
        b1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(0, 102, 204));
        b1.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        b1.setFocusPainted(false);
        b1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b1.addActionListener(e1 -> {
            String Name = nameField.getText();
            String Password = new String(passField.getPassword());
            try {
                obj.retrieveData(Name, Password, f, nameField);
            } catch (Exception ex) {
                System.err.println("An error occurred while retrieving data: " + ex.getMessage());
                JOptionPane.showMessageDialog(f, "An error occurred while retrieving data.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        contentPanel.add(l1);
        contentPanel.add(nameField);
        contentPanel.add(l2);
        contentPanel.add(passField);
        contentPanel.add(b1);

        f.add(headerPanel);
        f.add(contentPanel);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        DatabaseConnection obj = new DatabaseConnection();
        obj.showFrame();
    }
}
