import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class DatabaseConnection extends JFrame {
    public DatabaseConnection() { 
        new JTextField();
    }

    void retriveData(String n, String p, Component parentComponent) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/employeeInfo",
                    "root",
                    "2004");
            System.out.println("Database connected successfully!");

            ps = con.prepareStatement(
                    "SELECT * FROM employee WHERE employee_name = ? AND employee_password = ?");
            ps.setString(1, n);
            ps.setString(2, p);

            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("employee_id");
                String name = rs.getString("employee_name");
                double salary = rs.getDouble("employee_salary");
                String dept = rs.getString("employee_department");
        

                // Modified to use parentComponent
                Window win = SwingUtilities.getWindowAncestor(parentComponent);
                win.dispose();

                EmployeeDashboard dashboard = new EmployeeDashboard(id, name, salary, dept);
                dashboard.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials or user not found!", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
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

        // Add a header panel with gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 102, 204), getWidth(), 0,
                        new Color(0, 153, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBounds(0, 0, 450, 80);

        JLabel titleLabel = new JLabel("EMPLOYEE LOGIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 0, 450, 80);
        headerPanel.add(titleLabel);

        // Main content panel
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

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Name = nameField.getText();
                String Password = new String(passField.getPassword());
                try {
                    obj.retriveData(Name, Password, nameField);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(f, "An error occurred while retrieving data.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
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