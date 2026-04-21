import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Login Form");

        // Panel with background image
        JPanel panel = new JPanel() {
            Image background = new ImageIcon("Red 3D Stopwatch Icon with Minimalist Design on Transparent Background Stock Photo (1).jpeg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        
        ImageIcon lockIcon = new ImageIcon("locked.png");
        Image img = lockIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        lockIcon = new ImageIcon(img);

        JButton loginButton = new JButton("Login", lockIcon);

        // Blue labels
        JLabel userLabel = new JLabel("Username: ");
        userLabel.setForeground(Color.BLUE);

        JLabel passLabel = new JLabel("Password: ");
        passLabel.setForeground(Color.BLUE);

        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("admin") && password.equals("password")) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password");
                }
            }
        });

        frame.add(panel);

        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}