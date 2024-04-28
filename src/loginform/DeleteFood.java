package loginform;
 import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.*;
import javax.swing.*;
 public class DeleteFood {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    private JLabel nameLabel;
    private JTextField nameField;
  DeleteFood() {
        prepareGUI();
    }
 
 private void prepareGUI() {
        mainFrame = new JFrame("Delete!");
        mainFrame.setSize(700, 500);
        mainFrame.getContentPane().setBackground(Color.BLUE);
        mainFrame.setLayout(new GridLayout(3, 1));
        headerLabel = new JLabel("", JLabel.CENTER);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    public void showButtonDemo() {
        headerLabel.setText("Cafeteria Management System");
        headerLabel.setFont(new Font(null, Font.BOLD, 27));
        headerLabel.setForeground(Color.ORANGE);
        
        nameLabel = new JLabel("Enter Name");
        nameField = new JTextField();
        nameField.setSize(100, 30);

        JButton deleteButton = new JButton("DELETE");
      
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection con = DatabaseConnector.getConnection();
                        PreparedStatement selectStatement = con.prepareStatement("SELECT * FROM foodManagement WHERE f_name=?");
                        PreparedStatement deleteStatement = con.prepareStatement("DELETE FROM foodManagement WHERE f_name=?")) {
                    selectStatement.setString(1, nameField.getText());
                    
                    ResultSet rs = selectStatement.executeQuery();
                    if (rs.next()) {
                        deleteStatement.setString(1, nameField.getText());
                        deleteStatement.executeUpdate();
                        
                        JOptionPane.showMessageDialog(null, nameField.getText()+"   Deleted from database" );
                    } else {
                        JOptionPane.showMessageDialog(null,nameField.getText()+"  not found in the database or enter correct name " );
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DeleteFood.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JButton clear = new JButton("CLEAR");
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
            }
        });
     JPanel jp = new JPanel();
        jp.add(nameLabel);
        jp.add(nameField);
        jp.setSize(700, 400);
        jp.setLayout(new GridLayout(1, 1));
        controlPanel.add(jp);
        jp.add(deleteButton);
        jp.add(clear);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }
}