package loginform;
 import java.awt.*;
 import java.sql.*;
 import javax.swing.*;
class ItemInfo extends JFrame{
    private JLabel headerLabel;
    private JPanel controlPanel;
   ItemInfo() {
         setTitle("Showing all items");
        setSize(700, 500);
       getContentPane().setBackground(Color.BLUE);
         setLayout(new FlowLayout());
        headerLabel = new JLabel("", JLabel.CENTER);
        controlPanel = new JPanel();
        controlPanel.setSize(700, 500);
        controlPanel.setLayout(new FlowLayout());
       add(headerLabel);
        add(controlPanel);
        headerLabel.setText("Cafteria Management System");
        headerLabel.setFont(new Font(null, Font.BOLD, 25));
      String[] columnNames = {"Food Name", "Price", "Quantity"};
        Object[][] data = new Object[100][4];
     PreparedStatement pst;
        ResultSet rs;
        try {
           Connection con = DatabaseConnector.getConnection(); 
           pst = con.prepareStatement("SELECT * FROM foodManagement");
            rs = pst.executeQuery();
            int i = 0;
            while (rs.next()) {
                 data[i][0] = rs.getString("f_name");
                data[i][1] = rs.getDouble("f_price");
                data[i][2] = rs.getInt("f_quantity");
                i++;
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
       JOptionPane.showMessageDialog(null,"connection not closed");
               }
            }
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Error!");
        }  
  JTable table = new JTable(data, columnNames);
        table.setSize(400, 400);
        controlPanel.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
         setVisible(true);
        setLocationRelativeTo(null);
    }
}