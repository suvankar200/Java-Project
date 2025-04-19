import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class EmployeeDashboard extends JFrame {
    private JPanel contentPane;
    
    public EmployeeDashboard(int id, String name, double salary, String dept) {
        setTitle("Employee Management System - Dashboard");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem logoutItem = new JMenuItem("Logout");
        menu.add(logoutItem);
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
        JLabel rollLabel = createStyledLabel("Employee Salary: " + salary, labelFont);
        JLabel deptLabel = createStyledLabel("Employee Department: " + dept, labelFont);
       
        
        detailsPanel.add(idLabel);
        detailsPanel.add(nameLabel);
        detailsPanel.add(rollLabel);
        detailsPanel.add(deptLabel);
        
        
        contentPane.add(detailsPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        
        
        // Logout functionality
        logoutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                DatabaseConnection connection = new DatabaseConnection();
                connection.showFrame();
            }
        });
        
        // Make frame visible
        setVisible(true);
    }
    
    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setBorder(new EmptyBorder(5, 10, 5, 10));
        return label;
    }
}
