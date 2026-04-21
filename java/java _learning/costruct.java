import javax.swing.*;
import java.awt.event.*;

public class costruct {

    public static void main(String[] args) {
        JFrame f = new JFrame("Button Example");

        final JTextField tf =  new JTextField();
        tf.setBounds(50, 50, 150, 20);

        JButton b = new JButton("Click here");
        b.setBounds(50, 80, 95, 30);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tf.setText("Welcome to javapoint");
            }
        });

        f.add(b);
        f.add(tf);

        f.setSize(4000,400);
        f.setLayout(null);
        f.setVisible(true);
    }
}