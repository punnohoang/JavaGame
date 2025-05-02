package root;

import javax.swing.*;

import view.GamePanel;


public class GameFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameFrame(String playerName) {
        setTitle("Ball Game - Player: " + playerName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel gamePanel = new GamePanel(playerName);
        add(gamePanel);
        pack(); // Tự điều chỉnh kích thước theo panel

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
