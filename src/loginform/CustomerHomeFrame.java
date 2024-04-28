package loginform;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
 public class CustomerHomeFrame extends JFrame {
    private JLabel welcomeLabel;
    private JButton logoutButton;
    private JTable foodTable;
    private JScrollPane foodScrollPane;
    private final Connection con;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public CustomerHomeFrame() throws SQLException {
        con = DatabaseConnector.getConnection();
    }

    public void showButtonDemo() {
        setTitle("Customer Home");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        welcomeLabel = new JLabel("well come to our cafteria!");
        welcomeLabel.setFont(new Font("Geomanist", Font.BOLD, 20));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        logoutButton = new JButton("Logout");
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        foodTable = new JTable();
        foodScrollPane = new JScrollPane(foodTable);
        foodTable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel table = (DefaultTableModel) foodTable.getModel();
                int row = foodTable.getSelectedRow();
                String name = (String) table.getValueAt(row, 0);
                PlaceOrder placeorder = new PlaceOrder();
                placeorder.showForm(name);
            }
        });

        add(foodScrollPane, BorderLayout.CENTER);
        setLocationRelativeTo(null);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        if (resultSet != null) {
                            resultSet.close();
                        }
                        if (preparedStatement != null) {
                            preparedStatement.close();
                        }
                        if (con != null) {
                            con.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    setVisible(false);
                    LoginForm loginForm = new LoginForm();
                }
            }
        });

        loadFoodList();
        setVisible(true);
    }

    private void loadFoodList() {
        try {
            String query = "SELECT * FROM foodManagement";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("f_name");
            tableModel.addColumn("f_price");
            tableModel.addColumn("f_quantity");

            while (resultSet.next()) {
                String foodName = resultSet.getString("f_name");
                String foodPrice = resultSet.getString("f_price");
                String foodQuantity = resultSet.getString("f_quantity");
                tableModel.addRow(new Object[]{foodName, foodPrice, foodQuantity});
            }

            foodTable.setModel(tableModel);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}