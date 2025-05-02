package presenter;

import model.GameModel;
import model.Ball;
import util.DatabaseManager;

import java.awt.event.KeyEvent;
import java.util.List;

public class GamePresenter {
    private final GameModel model;
    private final String playerName;
    public boolean left, right;
    private int deathCount = 0;
    private boolean wasDead = false;
    private boolean hasWonFinalMap = false;
    private boolean gameOver = false;

    public GamePresenter(GameModel model, String playerName) {
        this.model = model;
        this.playerName = playerName;
    }

    public void update() {
        if (gameOver) return;

        Ball ball = model.getBall();
        ball.update(left, right);

        if (ball.isDead() && !wasDead && !model.isWin()) {
            deathCount++;
            System.out.println("Ball died! Deaths: " + deathCount);
            wasDead = true;
        } else if (!ball.isDead()) {
            wasDead = false;
        }

        checkWinAndSwitchMap();
    }

    private void checkWinAndSwitchMap() {
        if (model.getBall().isDead()) return;

        if (model.isWin()) {
            int currentMap = model.getCurrentMapIndex();

            if (!hasWonFinalMap) {
                switch (currentMap) {
                    case 0, 1 -> DatabaseManager.updateScore(playerName, 30);
                    case 2 -> DatabaseManager.updateScore(playerName, 40);
                }
            }

            if (!model.isLastMap()) {
                model.nextMap();
                System.out.println("Switched to next map!");
            } else if (!hasWonFinalMap) {
                hasWonFinalMap = true;
                gameOver = true;

                System.out.println("🎉 You won the final map!");

                // ✅ Ghi số lần chết khi thắng
                DatabaseManager.recordFinalResult(playerName, deathCount);
            }
        }
    }

    public void jump() {
        model.getBall().jump();
    }

    public GameModel getModel() {
        return model;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public boolean isDead() {
        return model.getBall().isDead();
    }

    public boolean hasWonFinalMap() {
        return hasWonFinalMap;
    }

    public List<String> getTop3Players() {
        return DatabaseManager.getTop3Players(); // giả định DatabaseManager có hàm này
    }

    public void restart() {
        Ball ball = model.getBall();
        ball.setPosition(0, 400);
        model.getGameMap().updateCamera(ball.x, 640);
        left = false;
        right = false;
        wasDead = false;
        // Giữ lại deathCount nếu muốn thống kê tổng kết sau
    }

    public void handleKeyPressed(int keyCode) {
        if (isDead()) {
            if (keyCode == KeyEvent.VK_R) {
                restart();
            } else if (keyCode == KeyEvent.VK_Q) {
                System.exit(0);
            }
            return;
        }

        if (gameOver) return;

        switch (keyCode) {
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_UP -> jump();
        }
    }

    public void handleKeyReleased(int keyCode) {
        if (gameOver) return;

        switch (keyCode) {
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_RIGHT -> right = false;
        }
    }
}
