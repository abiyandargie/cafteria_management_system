package loginform;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
 public class GenerateReport extends JFrame {
    private final String[] columnNames = {"Food Name", "Price", "Quantity"};
    private JTable cart;
    private final JLabel totalP = new JLabel();
    private final Object[][] data = new Object[1000][3];
    private int i = 0;
    public GenerateReport() {
        setBackground(Color.BLUE);
        this.setLayout(new GridLayout(2, 2));
         JPanel jp2 = new JPanel();
        jp2.setSize(700, 400);
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        cart = new JTable(model);
        cart.setSize(300, 450);
        jp2.setLayout(new FlowLayout());
        jp2.add(totalP);
        jp2.add(new JScrollPane(cart, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        JButton TotalPerday = new JButton("TotaLprice");
        TotalPerday.setSize(40, 50);
        jp2.add(TotalPerday);
        TotalPerday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  if (cart != null) {
                      try {
                          Connection con = DatabaseConnector.getConnection();
                          Statement stmt=con.createStatement();
                          ResultSet rs = stmt.executeQuery("SELECT * FROM foodManagement_orders");
                          double totalPrice=0;
                          while(rs.next()){
                              double fPrice = rs.getDouble("f_price");
                              totalPrice+=fPrice;
                          }
                          double vat = 15;
                          double withoutVat = totalPrice - totalPrice * vat / 100;
                          double vatAmount = totalPrice - withoutVat;
                           DecimalFormat df = new DecimalFormat("#.##");
                          String formattedTotalCost = df.format(totalPrice);
                          String formattedWithoutVat = df.format(withoutVat);
                          String formattedVatAmount = df.format(vatAmount);
                          StringBuilder sb = new StringBuilder();
                          sb.append("  Total Cost:\n\n")
                                  .append(formattedTotalCost).append("  birr with VAT 15%\n\n")
                                  .append(formattedWithoutVat).append("  birr without VAT\n\n")
                                  .append("Total VAT: ").append(formattedVatAmount).append("\n\n");
                          String message = sb.toString();
                           JOptionPane.showMessageDialog(null, message);
                      } catch (SQLException ex) {
                          Logger.getLogger(GenerateReport.class.getName()).log(Level.SEVERE, null, ex);
                      }   }     }     });       
        this.add(jp2);
        this.setSize(600, 550);
        setLocationRelativeTo(null);
        this.setVisible(true); }
  void generateReport() {
        try {
           Connection con = DatabaseConnector.getConnection();
           Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM foodManagement_orders");
            while (rs.next()) {
                String foodName = rs.getString("f_name");
                double foodPrice = rs.getDouble("f_price");
                int foodQuantity = rs.getInt("f_quantity");
                 data[i][0] = foodName;
                data[i][1] = foodPrice;
                data[i][2] =foodQuantity;
                  i++;           }
            DefaultTableModel model = (DefaultTableModel) cart.getModel();
            model.setDataVector(data, columnNames);
        } catch (SQLException ex) {
            Logger.getLogger(GenerateReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
}