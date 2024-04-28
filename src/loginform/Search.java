package loginform;

import java.awt.*;
 
import java.awt.event.*;
 import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
public class Search {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    JButton back;
    private GridLayout experimentLayout = new GridLayout(1, 1);
    private ResultSet resultSet;

    public Search() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Search!");
        mainFrame.setSize(700, 500);
        mainFrame.getContentPane().setBackground(Color.PINK);

        mainFrame.setLayout(new GridLayout(3, 1));
         headerLabel = new JLabel("", JLabel.CENTER);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(false);
    }

    public void showButtonDemo() throws SQLException, ClassNotFoundException {
        headerLabel.setText("Cafeteria Management System");
        headerLabel.setFont(new Font(null, Font.BOLD, 25));
        String[] columnNames = {"Food Name", "Price", "Quantity"};
        Object[][] data = new Object[100][4];

        String searchFoodName = JOptionPane.showInputDialog(null, "Enter food name to search:");

        if (searchFoodName == null || searchFoodName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(controlPanel,"no item is entered to search by!");
              return;  
        }
        Connection connection = DatabaseConnector.getConnection();
       PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM foodManagement WHERE f_name = ?");
            preparedStatement.setString(1, searchFoodName);
            resultSet = preparedStatement.executeQuery();

            int i = 0;
            while (resultSet.next()) {
                data[i][0] = resultSet.getString("f_name");
                data[i][1] = resultSet.getDouble("f_price");
                data[i][2] = resultSet.getInt("f_quantity");
                i++;
            }

            if (i == 0) {
                JOptionPane.showMessageDialog(mainFrame, "No matching item found.");
            } else {
                mainFrame.setVisible(false);
                JTable table = new JTable(data, columnNames);
                table.setSize(1, 4);
                table.setVisible(true);
                controlPanel.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
                mainFrame.setVisible(true);
                mainFrame.setLocationRelativeTo(null);
            }
        }  
    
  
    }
 