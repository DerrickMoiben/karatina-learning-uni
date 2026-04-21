import javax.swing.*;
import java.awt.event.*;

public class lesson1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("MY app");

        JPanel panel = new JPanel();

        //add componets 
        JLabel label = new JLabel("Enter you name:");
        JTextField textField = new JTextField(15);
        JButton button = new JButton("Submit");
        String name = textField.getText();

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText();
                JOptionPane.showMessageDialog(null, "Welcome " + name);
            }
        });

        
        panel.add(label);
        panel.add(textField);
        panel.add(button);
        

        frame.add(panel);

        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}