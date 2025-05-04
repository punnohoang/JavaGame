package view;

import model.*;
import presenter.GamePresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, GameView {
    private static final long serialVersionUID = 1L;
    private final GamePresenter presenter;
    private final Timer timer;
    private final DeathZone deathZoneDrawer; // Đối tượng để vẽ DeathZone

    public GamePanel(String playerName) {
        GameModel model = new GameModel();
        presenter = new GamePresenter(model, playerName);
        deathZoneDrawer = new DeathZone(); // Khởi tạo đối tượng DeathZone để vẽ

        setPreferredSize(new Dimension(640, 500));
        setFocusable(true);
        requestFocusInWindow(); // Đảm bảo panel nhận focus khi khởi tạo
        addKeyListener(new GameKeyAdapter());

        timer = new Timer(15, this);
        timer.start();
    }


	@Override
    public void actionPerformed(ActionEvent e) {
        presenter.update();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    public void render(Graphics g) {
        GameModel model = presenter.getModel();
        GameMap gameMap = model.getGameMap();

        // Vẽ nền
        gameMap.getBackground().draw(g, gameMap.getCameraX(), gameMap.getWidth(), gameMap.getHeight());

        // Vẽ các vùng chết
        for (Rectangle dz : gameMap.getDeathZoneBounds()) {
            deathZoneDrawer.draw(g, dz, gameMap.getCameraX());
        }

        // Vẽ các cột (Foothold)
        gameMap.getFoothold().draw(g, gameMap.getColumns(), gameMap.getCameraX());

        // Vẽ mặt đất
        g.setColor(new Color(139, 69, 19));
        g.fillRect(0, gameMap.getHeight() - 40, getWidth(), 40);

        // Vẽ bóng
        Ball ball = model.getBall();
        g.setColor(Color.RED);
        g.fillOval(ball.x - gameMap.getCameraX(), ball.y, ball.width, ball.height);

        // Hiển thị số lần chết
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Deaths: " + presenter.getDeathCount(), 10, 30);

        // Hiển thị thông báo khi bóng chết
        if (presenter.isDead()) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", 230, 250);
            g.drawString("Press Q to Quit", 240, 280);
        }
    }

    private class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            presenter.handleKeyPressed(e.getKeyCode());
            if (presenter.isDead() && e.getKeyCode() == KeyEvent.VK_R) {
                presenter.restart(); // Gọi restart
                repaint(); // Cập nhật giao diện sau khi restart
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            presenter.handleKeyReleased(e.getKeyCode());
        }
    }
}