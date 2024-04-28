package loginform;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;
 public class Home {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    public Home() {
        prepareGUI();
    }
 private void prepareGUI() {
      
        mainFrame = new JFrame("CafteriaManagement System");
        mainFrame.setBounds(100, 100, 700, 500);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.getContentPane().setBackground(Color.BLUE);
        headerLabel = new JLabel("", JLabel.CENTER);
          controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2,5,5,10));
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        
        mainFrame.setVisible(true);
       }
   public void showButtonDemo() throws SQLException, ClassNotFoundException {
       headerLabel.setText("Cafteria Management System");
         headerLabel.setFont(new Font(null, Font.BOLD, 27));
        headerLabel.setForeground(Color.WHITE);
          JButton foodInfoButton = new JButton("MenuList");
        foodInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  ItemInfo ii = new ItemInfo();
            }
        });

        JButton fsButton = new JButton("SearchItem");
        fsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search sc = new Search();
                try {
                    sc.showButtonDemo();
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JButton reportButton = new JButton("GenerateReport");
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenerateReport generateReport = new GenerateReport();
                generateReport.generateReport();
            }
        });

        JButton IfButton = new JButton("InsertNewItem");
        IfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(true);
                EnterFood ef = new EnterFood();
                ef.showButtonDemo();
            }
        });

        JButton ufButton = new JButton("UpdateItem");
        ufButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateFood uf = new UpdateFood();
                uf.showButtonDemo();
            }
        });
 JButton dlButton = new JButton("DeleteItem");
        dlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteFood dl = new DeleteFood();
                dl.showButtonDemo();
            }
        });
    JButton viewOrder = new JButton("ViewCustOrder");
        viewOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewOrdersForm viewOrdersForm = new ViewOrdersForm();
                viewOrdersForm.showForm();
            }
        });
    JButton clearButton = new JButton("ResetOrderT");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = DatabaseConnector.getConnection()) {
                    PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE foodManagement_orders");
                    
                    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset foodManagement_orders table?", "", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        statement.executeUpdate();
                        JOptionPane.showMessageDialog(null, "foodManagement_orders table reset successfully!!");
                    }
                    else {
           JOptionPane.showMessageDialog(null, "foodManagement_orders table not reset!!");
 }
                    statement.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        controlPanel.add(ufButton);
        controlPanel.add(IfButton);
        controlPanel.add(fsButton);
        controlPanel.add(reportButton);
        controlPanel.add(foodInfoButton);
        controlPanel.add(dlButton);
        controlPanel.add(viewOrder);
        controlPanel.add(clearButton);
        mainFrame.setLocationRelativeTo(null);
    }
}