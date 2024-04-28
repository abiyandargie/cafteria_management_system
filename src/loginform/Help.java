package loginform;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.GridLayout;

public class Help extends JFrame {
    Help() {
        try {
            String[] photoNames = {
                "1.png",
                "2.png",
                "3.png",
                "4.png",
                "5.png",
                "2.png"
            };

            String[] photoDescriptions = {
                "1. click loginbutton to enter as customer and to place order",
                "2. click the food that you want to order from the menu list",
                "3. fill spaces as required. i.e food quantity acount number to pay and food detail. ",
                "4. after filling all click place placeOrder button. and confirm payment amount!",
                "5. generated  bill example by clicking G_receipt button",
                "6. then u can select other food by clicking back button and  then reapeate the above process"
            };
  
            JPanel panel = new JPanel(new GridLayout(2, 3,20,20));

            for (int i = 0; i < 6; i++) {
                ImageIcon image = new ImageIcon(getClass().getResource(photoNames[i]));
                JLabel photoLabel = new JLabel(image);
                JTextArea descriptionArea = new JTextArea(photoDescriptions[i]);
                descriptionArea.setEditable(false);

                JPanel photoPanel = new JPanel();
                photoPanel.setLayout(new GridLayout(2, 1));
                photoPanel.add(photoLabel);
                photoPanel.add(new JScrollPane(descriptionArea));
                panel.add(photoPanel);
            }

            add(panel);
        } catch (Exception e) {
            System.out.println("Image loading error: " + e.getMessage());
        }

        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}