package view;

import javax.swing.*;
import util.DatabaseManager;
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

        // Khi người chơi nhấn nút "Start"
        startButton.addActionListener(e -> {
            String playerName = nameField.getText();
            if (!playerName.isEmpty()) {
                // Lưu tên người chơi vào cơ sở dữ liệu với điểm ban đầu = 0
                DatabaseManager.insertNewPlayer(playerName);
                
                dispose(); // tắt màn hình login
                new GameFrame(playerName); // mở game với tên người chơi
            } else {
                // Nếu không nhập tên thì hiển thị thông báo
                JOptionPane.showMessageDialog(this, "Please enter a name to start the game.");
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
