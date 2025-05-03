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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    public void render(Graphics g) {
        GameModel model = presenter.getModel();
        GameMap gameMap = model.getGameMap();

        gameMap.getBackground().draw(g, gameMap.getCameraX(), gameMap.getWidth(), gameMap.getHeight());

        for (Rectangle dz : gameMap.getDeathZoneBounds()) {
            deathZoneDrawer.draw(g, dz, gameMap.getCameraX());
        }

        gameMap.getFoothold().draw(g, gameMap.getColumns(), gameMap.getCameraX());

        g.setColor(new Color(139, 69, 19));
        g.fillRect(0, gameMap.getHeight() - 40, getWidth(), 40);

        Ball ball = model.getBall();
        g.setColor(Color.RED);
        g.fillOval(ball.x - gameMap.getCameraX(), ball.y, ball.width, ball.height);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Deaths: " + presenter.getDeathCount(), 10, 30);
        g.drawString("Time: " + model.getTimeString(), 10, 50); // Hiển thị thời gian

        if (model.isPaused()) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("PAUSED", 220, 200);
        }

        if (presenter.isDead() || presenter.hasWonFinalMap()) {
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

            List<String> top3 = presenter.getTop3Players();
            if (top3 != null) {
                g.setFont(new Font("Arial", Font.BOLD, 18));
                g.drawString("Top 3 Players:", 230, 320);
                int y = 350;
                for (String playerInfo : top3) {
                    g.drawString(playerInfo, 240, y);
                    y += 25;
                }
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