package loginform;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewOrdersForm extends JFrame {
 private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void showForm() {
         setTitle("View Orders");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        setSize(700,500);
       JLabel titleLabel = new JLabel("View Orders");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Geomanist", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
     DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.setFont(new Font("Geomanist", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Geomanist", Font.BOLD, 14));
      // Add table columns
        model.addColumn("Order ID");
        model.addColumn("Food Name");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("foodDetails");

        fetchOrdersFromDatabase(model);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }
    private void fetchOrdersFromDatabase(DefaultTableModel model) {
         
              try {
                  
                  Connection connection = DatabaseConnector.getConnection();
                  preparedStatement = connection.prepareStatement("SELECT * FROM foodManagement_orders");
                  resultSet = preparedStatement.executeQuery();
                  
                  while (resultSet.next()) {
                      int orderId = resultSet.getInt("order_id");
                      String foodName = resultSet.getString("f_name");
                      double price = resultSet.getDouble("f_price");
                      int quantity = resultSet.getInt("f_quantity");
                      String detail = resultSet.getString("details");
                      model.addRow(new Object[]{orderId, foodName, price, quantity,detail});
                  }
                  try {
                      if (resultSet != null) {
                          resultSet.close();
                      }
                      if (preparedStatement != null) {
                          preparedStatement.close();
                      }
                  } catch (SQLException e) {
                      JOptionPane.showMessageDialog(null,"something error");
                  }
              } catch (SQLException ex) {
                Logger.getLogger(ViewOrdersForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 