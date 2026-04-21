import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MyFirstWindow {
    public static void main(String[] args) {
        JFrame f = new JFrame ("Scientific caculatoe");

        JButton b = new JButton("Add");

        b.setBounds(130, 100, 100, 40);

        f.add(b);

        f.setSize(400, 500);

        f.setLayout(null);

        f.setVisible(true);
    }
    
}
