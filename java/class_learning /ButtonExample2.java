import java.awt.event.*;
import javax.swing.*;

public class ButtonExample2 {
    public static void main(String[] args) {

        JFrame f = new JFrame("Button Example");

        final JTextField tf = new JTextField();
        tf.setBounds(50, 50, 150, 20);

        final JTextField tf1 = new JTextField();
        tf1.setBounds(50, 80, 150, 20); 

        JButton submit = new JButton("Submit");
        submit.setBounds(50, 120, 95, 30);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tf.setText("Welcome to Javatpoint.");
                tf1.setText("Welcome to Java.");
            }
        });

        JButton Reset = new JButton("Reset");
        Reset.setBounds(160, 120, 95, 30); 

        Reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tf.setText("");
                tf1.setText("");
            }
        });

        f.add(tf);
        f.add(tf1);
        f.add(Reset);
        f.add(submit);

        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
    }
}