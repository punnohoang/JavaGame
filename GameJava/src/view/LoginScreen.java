package view;

import javax.swing.*;

import root.GameFrame;


public class LoginScreen extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nameField;
    private JButton startButton;

    public LoginScreen() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        nameField = new JTextField(15);
        startButton = new JButton("Start");

        startButton.addActionListener(e -> {
            String playerName = nameField.getText();
            if (!playerName.isEmpty()) {
                dispose(); // tắt màn hình login
                new GameFrame(playerName); // mở game
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter your name:"));
        panel.add(nameField);
        panel.add(startButton);

        add(panel);
        setVisible(true);
    }
}
