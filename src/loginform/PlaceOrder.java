package loginform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class PlaceOrder extends JFrame {
    private JLabel headerLabel;
    private JPanel controlPanel;
    private JButton back;
    private JLabel foodLabel, quantityLabel, paymentLabel;
    private JLabel foodDetailLabel;
    private JTextField tfFoodName, tfQuantity, tfAccount,tfDetail;
    private JButton generateReceipt;
    private int orderId;

    public PlaceOrder() {
        prepareGUI();
    }

    private void prepareGUI() {
        setTitle(" Place Order");
        setSize(900, 600);
        getContentPane().setBackground(Color.BLUE);
        setLayout(new GridLayout(4, 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        headerLabel = new JLabel("", JLabel.CENTER);
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(6, 2, 5, 10));
        add(headerLabel);
        add(controlPanel);
        controlPanel.setSize(700, 400);
        setLocationRelativeTo(null);
        
        back = new JButton("BACK TO menu");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CustomerHomeFrame foodList = new CustomerHomeFrame();
                    foodList.showButtonDemo();
                     
                } catch (SQLException ex) {
                    Logger.getLogger(PlaceOrder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        setVisible(true);
    }

    public void showForm(String name) {
        headerLabel.setText("Cafeteria Management System");
        headerLabel.setFont(new Font(null, Font.BOLD, 27));
        headerLabel.setForeground(Color.WHITE);
        foodLabel = new JLabel("  Food Name");
        tfFoodName = new JTextField(20);
        quantityLabel = new JLabel("  Quantity");
        tfQuantity = new JTextField();
        tfFoodName.setText(name);
        paymentLabel = new JLabel("  Enter account number ");
        tfAccount = new JTextField(10);
        foodDetailLabel=new JLabel("  writeDetail :");
         tfDetail = new JTextField(10);
        JButton orderButton = new JButton("Place Order");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeOrder();
                generateReceipt.setEnabled(true);
            }
        });
        generateReceipt = new JButton("G_Receipt");
        generateReceipt.setEnabled(false);
        generateReceipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateBill();
            }
        });
        controlPanel.add(foodLabel);
        controlPanel.add(tfFoodName);
        controlPanel.add(quantityLabel);
        controlPanel.add(tfQuantity);
        controlPanel.add(paymentLabel);
        controlPanel.add(tfAccount);
        controlPanel.add(foodDetailLabel);
        controlPanel.add(tfDetail);
        controlPanel.add(orderButton);
        controlPanel.add(generateReceipt);
        controlPanel.add(back);
        pack();
    }
 private void placeOrder() {
        String foodName = tfFoodName.getText();
        String quantity = tfQuantity.getText();
        String account = tfAccount.getText();
        String detail = tfDetail.getText();

        if (foodName.isEmpty() || quantity.isEmpty() || account.isEmpty() || detail.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields correctly");
        } else {
            try (Connection con = DatabaseConnector.getConnection()) {
                PreparedStatement pstFood = con.prepareStatement("SELECT * FROM foodManagement WHERE f_name=?");
                pstFood.setString(1, foodName);
                ResultSet rsFood = pstFood.executeQuery();
                if (rsFood.next()) {
                    int availableQuantity = rsFood.getInt("f_quantity");
                    double price = rsFood.getDouble("f_price");
                    try {
                        int quantityInt = Integer.parseInt(quantity);
                        if (quantityInt <= availableQuantity) {
                            double totalPrice = quantityInt * price + (price * quantityInt * 15 / 100);
                            int confirm = JOptionPane.showConfirmDialog(null,
                                    "Total amount of " + totalPrice + " birr will be debited from your account. Press Yes to continue payment, or No to cancel.",
                                    "Confirm Amount", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                PreparedStatement pstOrder = con.prepareStatement(
                                        "INSERT INTO foodManagement_orders (f_name, f_price, f_quantity,details) VALUES (?, ?, ?,?)",
                                        Statement.RETURN_GENERATED_KEYS);
                                pstOrder.setString(1, foodName);
                                pstOrder.setDouble(2, totalPrice);
                                pstOrder.setInt(3, quantityInt);
                                 pstOrder.setString(4, detail);
                                pstOrder.executeUpdate();
                                ResultSet generatedKeys = pstOrder.getGeneratedKeys();
                                if (generatedKeys.next()) {
                                    orderId = generatedKeys.getInt(1);
                                    JOptionPane.showMessageDialog(null,
                                            "Order placed successfully!\nOrder ID: " + orderId + "\nTotal Price: "
                                                    + totalPrice);
                                    generateReceipt.setEnabled(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Failed to retrieve the order ID");
                                    generateReceipt.setEnabled(false);
                                }
                                int updatedQuantity = availableQuantity - quantityInt;
                                PreparedStatement pstUpdate = con
                                        .prepareStatement("UPDATE foodManagement SET f_quantity=? WHERE f_name=?");
                                pstUpdate.setInt(1, updatedQuantity);
                                pstUpdate.setString(2, foodName);
                                pstUpdate.executeUpdate();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "The requested quantity is not available");
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid quantity value");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Food not found");
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlaceOrder.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error connecting to the database", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void generateBill() {
        if (orderId != 0) {
            try (Connection con = DatabaseConnector.getConnection()) {
                PreparedStatement pstOrder = con.prepareStatement("SELECT * FROM foodManagement_orders WHERE order_id=?");
                pstOrder.setInt(1, orderId);
                ResultSet rsOrder = pstOrder.executeQuery();
                if (rsOrder.next()) {
                    String foodName = rsOrder.getString("f_name");
                    double totalPrice = rsOrder.getDouble("f_price");
                    int quantity = rsOrder.getInt("f_quantity");
                    String detail=rsOrder.getString("details");
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String date = dtf.format(now);
                    JOptionPane.showMessageDialog(null, "Order ID: " + orderId + "\nFood Name: " + foodName
                            + "\nTotal Price: " + totalPrice + "\nQuantity: " + quantity +"\nfood detail :"+detail+ "\nDate: " + date);
                } else {
                    JOptionPane.showMessageDialog(null, "Order not found");
                }
            } catch (SQLException ex) {
                Logger.getLogger(PlaceOrder.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error connecting to the database", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No order placed yet!");
        }
    }
   }