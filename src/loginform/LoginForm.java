package loginform;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class LoginForm extends JFrame {
 JLabel idLabel;
    JLabel passLabel;
    JLabel roleLabel;
    JLabel background;
    JLabel headerLabel;
    JTextField id;
    JPasswordField pass;
    JComboBox<String> roleDropdown;
    JButton submit;
    JButton Help;
    Connection connection;
    public static void main(String[] args) {
        LoginForm login = new LoginForm();
    }

    LoginForm() {
        setTitle("Cafeteria Management System");

        try {
            ImageIcon image = new ImageIcon(getClass().getResource("download.jpg"));
            background = new JLabel(image);
            background.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
            add(background);
        } catch (Exception e) {
            System.out.println("Image loading error: " + e.getMessage());
        }
 background.setBounds(0, 0, 600, 600);
        add(background);

        headerLabel = new JLabel("Cafeteria Management System");
        headerLabel.setBounds(200, 50, 400, 30);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.GREEN);
        background.add(headerLabel);
        idLabel = new JLabel("ID_NUMBER:");
        idLabel.setBounds(150, 120, 100, 30);
        idLabel.setForeground(Color.BLACK);
        background.add(idLabel);

        id = new JTextField();
        id.setBounds(250, 120, 200, 30);
        background.add(id);
        passLabel = new JLabel("PASSWORD:");
        passLabel.setBounds(150, 170, 100, 30);
        passLabel.setForeground(Color.BLACK);
        background.add(passLabel);
        pass = new JPasswordField();
        pass.setBounds(250, 170, 200, 30);
        background.add(pass);

        roleLabel = new JLabel("SelectRole:");
        roleLabel.setBounds(150, 220, 100, 30);
        roleLabel.setForeground(Color.white);
        roleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        background.add(roleLabel);

        String[] roles = {"Customer", "Admin", "Cashier"};
        roleDropdown = new JComboBox<>(roles);
        roleDropdown.setBounds(300, 220, 100, 30);
        background.add(roleDropdown);
        Help = new JButton("HELP");
        Help.setBounds(500, 500, 100, 30);
        Help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Help help=new Help();
            }
        });
        submit = new JButton("Login");
        submit.setBounds(400, 400, 100, 30);
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (validatePassword()) {
                        String selectedRole = (String) roleDropdown.getSelectedItem();
                        if (selectedRole.equals("Admin")) {
                            // Go to Home class for Admin
                            Home home = new Home();
                            home.showButtonDemo();
                        } else if (selectedRole.equals("Customer")) {
                            // Go to CustomerHomeFrame for Customer
                            CustomerHomeFrame customerHome = new CustomerHomeFrame();
                            customerHome.showButtonDemo();
                            customerHome.setVisible(true);
                        } else if (selectedRole.equals("Cashier")) {
                            // Go to ViewOrder for Cashier
                            ViewOrdersForm viewOrder = new ViewOrdersForm();
                            viewOrder.showForm();
                            viewOrder.setVisible(true);
                        }
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        closeConnection();
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid ID or Password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        background.add(submit);
        background.add(Help);
        setLayout(null);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private boolean validatePassword() throws ClassNotFoundException {
        String enteredId = id.getText();
        String enteredPass = new String(pass.getPassword());
        String selectedRole = (String) roleDropdown.getSelectedItem();

        try {
            PreparedStatement statement;
            ResultSet result;
            String query = "";

            if (selectedRole.equals("Admin")) {
                query = "SELECT * FROM admin_users WHERE username = ? AND password = ?";
            } else if (selectedRole.equals("Customer")) {
                query = "SELECT * FROM customer_users WHERE username = ? AND password = ?";
                      } else if (selectedRole.equals("Cashier")) {
                    query = "SELECT * FROM cashiery WHERE username = ? AND password = ?";
                }

                connection = DatabaseConnector.getConnection();
                statement = connection.prepareStatement(query);
                statement.setString(1, enteredId);
                statement.setString(2, enteredPass);
                result = statement.executeQuery();

                if (result.next()) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
  private void closeConnection() throws SQLException {
            if (connection != null) {
                connection.close();
            }
        }
    }