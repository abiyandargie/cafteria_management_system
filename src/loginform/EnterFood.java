package loginform;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
 class EnterFood {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    private JLabel nameLabel, priceLabel, quantityLabel;
    GridLayout experimentLayout = new GridLayout(0, 2);
    JButton back;
 EnterFood() {
        prepareGUI();
    }
   private void prepareGUI() {
        mainFrame = new JFrame("Insert a new food item!");
        mainFrame.setSize(700, 500);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.getContentPane().setBackground(Color.BLUE);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
         back = new JButton("BACK TO menu");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 
                try {
                    Home home =new Home();
                    home.showButtonDemo();
                } catch (SQLException | ClassNotFoundException ex) {
                   Logger.getLogger(UpdateFood.class.getName()).log(Level.SEVERE, null, ex);    }   }   });    }
   public void showButtonDemo() {
        headerLabel.setText("Cafeteria Management System");
        headerLabel.setFont(new Font(null, Font.BOLD, 27));
        nameLabel = new JLabel("Enter food Name");
        JTextField nameField = new JTextField();
        nameField.setSize(100, 40);
        priceLabel = new JLabel("Enter Price/item");
        JTextField priceField = new JTextField();
        priceField.setSize(100, 40);
      quantityLabel = new JLabel("Enter total Quantity/unit");
        JTextField quantityField = new JTextField();
        quantityField.setSize(100, 40);
     JButton okButton = new JButton("OK");
     okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String itemName = nameField.getText();
                    double itemPrice = Double.parseDouble(priceField.getText());
                    int itemQuantity = Integer.parseInt(quantityField.getText());
                     Connection con = DatabaseConnector.getConnection();
                    PreparedStatement selectStmt = con.prepareStatement("SELECT * FROM foodManagement WHERE f_name = ?");
                    PreparedStatement insertStmt = con.prepareStatement("INSERT INTO foodManagement(f_name, f_price, f_quantity) VALUES (?,?,?)");
                    selectStmt.setString(1, itemName);
                    ResultSet resultSet = selectStmt.executeQuery();
                      if (resultSet.next()) {
                        JOptionPane.showMessageDialog(null, itemName+"    already exists in the database you can update" );
                    } else {
                        insertStmt.setString(1, itemName);
                        insertStmt.setDouble(2, itemPrice);
                        insertStmt.setInt(3, itemQuantity);
                        insertStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, itemName+ "Done Inserting " );
                    }
                    mainFrame.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(EnterFood.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
        });
      JButton clear =new JButton("CLEAR");
      clear.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
              nameField.setText("");
              priceField.setText("");
              quantityField.setText("");
           }
      });
         JPanel jp = new JPanel(null);
        jp.add(nameLabel);
        jp.add(nameField);
        jp.add(priceLabel);
        jp.add(priceField);
        jp.add(quantityLabel);
        jp.add(quantityField);
        jp.setSize(500, 500);
        jp.setLayout(experimentLayout);
        controlPanel.add(jp);
        jp.add(okButton);
        jp.add(clear);
        jp.add(back);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}