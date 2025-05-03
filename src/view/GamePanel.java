package view;

import model.*;
import presenter.GamePresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final GamePresenter presenter;
    private final Timer timer;
    private final DeathZone deathZoneDrawer;

    public GamePanel(String playerName) {
        GameModel model = new GameModel();
        presenter = new GamePresenter(model, playerName);
        deathZoneDrawer = new DeathZone();

        setPreferredSize(new Dimension(640, 500));
        setFocusable(true);
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        GameModel model = presenter.getModel();
        GameMap map = model.getGameMap();

        map.getBackground().draw(g, map.getCameraX(), map.getWidth(), map.getHeight());

        // Vẽ khu vực Death Zone
        for (Rectangle zone : map.getDeathZoneBounds()) {
            deathZoneDrawer.draw(g, zone, map.getCameraX());
        }

        // Vẽ các foothold
        map.getFoothold().draw(g, map.getColumns(), map.getCameraX());

        // Vẽ thanh nền ở dưới
        g.setColor(new Color(139, 69, 19));
        g.fillRect(0, map.getHeight() - 40, getWidth(), 40);

        // Vẽ bóng (ball)
        Ball ball = model.getBall();
        g.setColor(Color.RED);
        g.fillOval(ball.x - map.getCameraX(), ball.y, ball.width, ball.height);

        // Hiển thị thông tin game
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Deaths: " + presenter.getDeathCount(), 10, 30);
        g.drawString("Time: " + presenter.getFormattedPlayTime(), 10, 50);

        // Kiểm tra kết thúc game (game over hoặc thắng)
        if (presenter.isDead() || presenter.hasWonFinalMap()) {
            drawEndGameScreen(g);
        }
    }

    private void drawEndGameScreen(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 36));

        if (presenter.isDead()) {
            g.drawString("GAME OVER", 200, 200);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", 230, 250);
            g.drawString("Press Q to Quit", 240, 280);
        } else {
            g.drawString("YOU WIN!", 220, 200);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Time Played: " + presenter.getFormattedPlayTime(), 230, 310);

        // Hiển thị Top 3 người chơi
        List<String> top3 = presenter.getTop3Players();
        if (top3 != null && !top3.isEmpty()) {
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Top 3 Players:", 230, 340);
            int y = 370;
            for (String info : top3) {
                g.drawString(info, 240, y);
                y += 25;
            }
        }
    }

    private class GameKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            presenter.handleKeyPressed(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            presenter.handleKeyReleased(e.getKeyCode());
        }
    }
}
