package loginform;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 import javax.swing.JButton;
import javax.swing.*;
final class UpdateFood {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JLabel name, price, quantity;
    GridLayout experimentLayout = new GridLayout(0, 2);
    ResultSet rs;
    JButton back;
 UpdateFood() {
        prepareGUI();
    }
    void prepareGUI() {
        mainFrame = new JFrame("UPDATE ITEM");
        mainFrame.setSize(700, 500);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.getContentPane().setBackground(Color.BLUE);
       headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 400);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
        back = new JButton("BACK TO menu");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 
                try {
                    Home home =new Home();
                    home.showButtonDemo();
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(UpdateFood.class.getName()).log(Level.SEVERE, null, ex);
                }
               }
        });
    }
 public void showButtonDemo() {
        headerLabel.setText("Cafteria Management System");
        headerLabel.setFont(new Font(null, Font.BOLD, 27));
 name = new JLabel("Enter Name");
        JTextField tf2 = new JTextField();
        tf2.setSize(100, 30);
  price = new JLabel("Enter Price");
        JTextField tf3 = new JTextField();
        tf3.setSize(100, 30);
 quantity = new JLabel("Enter Quantity");
        JTextField tf4 = new JTextField();
        tf4.setSize(100, 30);
 JButton updateButton = new JButton("UPDATE");
  updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                     PreparedStatement pst;
                     Connection conect = DatabaseConnector.getConnection();
                    pst = conect.prepareStatement("SELECT * FROM foodManagement WHERE f_name=?");
                    pst.setString(1, tf2.getText());
                     rs = pst.executeQuery();
                  if (rs.next()) {
                        pst = conect.prepareStatement("UPDATE foodManagement SET f_quantity=?, f_price=? WHERE f_name=?");
                        pst.setInt(1, Integer.parseInt(tf4.getText()));
                        pst.setDouble(2, Double.parseDouble(tf3.getText()));
                        pst.setString(3, tf2.getText());
                        pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Done Updating " + tf2.getText());
                    } else {
                        JOptionPane.showMessageDialog(null,tf2.getText() +" not found in the database please insert as new item: " );
                    }
                  } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, tf2.getText()+ "  updating error " );
                    JOptionPane.showMessageDialog(null,ex);
                  }
              }
        });
          JButton clear =new JButton("CLEAR");
      clear.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
              tf4.setText("");
              tf3.setText("");
              tf2.setText("");
            }
      });
  JPanel jp = new JPanel();
        jp.add(name);
        jp.add(tf2);
        jp.add(price);
        jp.add(tf3);
        jp.add(quantity);
        jp.add(tf4);
        
        jp.setSize(200, 200);
        jp.setLayout(experimentLayout);
        controlPanel.add(jp);
        jp.add(updateButton);
        jp.add(clear);
        jp.add(back);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }
}