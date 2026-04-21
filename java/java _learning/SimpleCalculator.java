import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleCalculator implements ActionListener {
    
    
    JTextField display;
    
    
    JButton[] numButtons = new JButton[10];
    
    
    JButton addBtn, subBtn, mulBtn, divBtn, equalsBtn, clearBtn, decimalBtn;
    
    
    double num1 = 0, num2 = 0, result = 0;
    char operator;
    
    public SimpleCalculator() {
        
        JFrame frame = new JFrame("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 450);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        
        
        display = new JTextField();
        display.setBounds(30, 20, 280, 50);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);  // User can't type, only press buttons
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        frame.add(display);
        
        
        JPanel panel = new JPanel();
        panel.setBounds(30, 90, 280, 280);
        panel.setLayout(new GridLayout(4, 4, 10, 10));  // 4x4 grid with gaps
        panel.setBackground(Color.DARK_GRAY);
        
        
        for (int i = 0; i < 10; i++) {
            numButtons[i] = new JButton(String.valueOf(i));
            numButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            numButtons[i].setFocusable(false);
            numButtons[i].setBackground(Color.LIGHT_GRAY);
            numButtons[i].addActionListener(this);
        }
        
        
        addBtn = new JButton("+");
        subBtn = new JButton("-");
        mulBtn = new JButton("*");
        divBtn = new JButton("/");
        equalsBtn = new JButton("=");
        clearBtn = new JButton("C");
        decimalBtn = new JButton(".");
        
        
        JButton[] opButtons = {addBtn, subBtn, mulBtn, divBtn, equalsBtn, clearBtn, decimalBtn};
        for (JButton btn : opButtons) {
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.setFocusable(false);
            btn.addActionListener(this);
            
            
            if (btn == equalsBtn) {
                btn.setBackground(Color.RED);
                btn.setForeground(Color.WHITE);
            } else if (btn == clearBtn) {
                btn.setBackground(Color.ORANGE);
            } else {
                btn.setBackground(Color.CYAN);
            }
        }
        
        
        
        panel.add(numButtons[7]);
        panel.add(numButtons[8]);
        panel.add(numButtons[9]);
        panel.add(divBtn);
        
    
        panel.add(numButtons[4]);
        panel.add(numButtons[5]);
        panel.add(numButtons[6]);
        panel.add(mulBtn);
        
        
        panel.add(numButtons[1]);
        panel.add(numButtons[2]);
        panel.add(numButtons[3]);
        panel.add(subBtn);
        
        
        panel.add(numButtons[0]);
        panel.add(decimalBtn);
        panel.add(equalsBtn);
        panel.add(addBtn);
        
        
        frame.add(panel);
        
    
        clearBtn.setBounds(30, 380, 280, 30);
        clearBtn.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(clearBtn);
        
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numButtons[i]) {
                display.setText(display.getText() + i);
                return;
            }
        }
        
        
        if (e.getSource() == decimalBtn) {
            
            if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
        }
        
        
        if (e.getSource() == addBtn) {
            try {
                num1 = Double.parseDouble(display.getText());
                operator = '+';
                display.setText("");  // Clear for second number
            } catch (NumberFormatException ex) {
                display.setText("Error: Enter a number first");
            }
        }
        else if (e.getSource() == subBtn) {
            try {
                num1 = Double.parseDouble(display.getText());
                operator = '-';
                display.setText("");
            } catch (NumberFormatException ex) {
                display.setText("Error: Enter a number first");
            }
        }
        else if (e.getSource() == mulBtn) {
            try {
                num1 = Double.parseDouble(display.getText());
                operator = '*';
                display.setText("");
            } catch (NumberFormatException ex) {
                display.setText("Error: Enter a number first");
            }
        }
        else if (e.getSource() == divBtn) {
            try {
                num1 = Double.parseDouble(display.getText());
                operator = '/';
                display.setText("");
            } catch (NumberFormatException ex) {
                display.setText("Error: Enter a number first");
            }
        }
        
        // Handle Equals button
        else if (e.getSource() == equalsBtn) {
            try {
                
                if (display.getText().isEmpty()) {
                    display.setText("Error: No number entered");
                    return;
                }
                
                num2 = Double.parseDouble(display.getText());
                
                
                switch(operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        
                        if (num2 == 0) {
                            display.setText("Error: Cannot divide by zero");
                            return;
                        }
                        result = num1 / num2;
                        break;
                    default:
                        display.setText("Error: No operator selected");
                        return;
                }
                
                
                if (result == (int)result) {
                    display.setText(String.valueOf((int)result));
                } else {
                    display.setText(String.valueOf(result));
                }
                
            } catch (NumberFormatException ex) {
                display.setText("Error: Invalid number");
            }
        }
        
        
        else if (e.getSource() == clearBtn) {
            display.setText("");
            num1 = num2 = result = 0;
            operator = ' ';
        }
    }
    
    public static void main(String[] args) {
        new SimpleCalculator();
    }
}