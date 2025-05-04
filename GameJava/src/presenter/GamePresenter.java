package presenter;

import model.GameModel;
import model.Ball;
import util.DatabaseManager;

import java.awt.Rectangle;
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
    private int lastPassedColumnsCount = 0;

    public GamePresenter(GameModel model, String playerName) {
        this.model = model;
        this.playerName = playerName;
    }

    public void update() {
        if (gameOver)
            return;

        Ball ball = model.getBall();
        ball.update(left, right);

        int currentPassedColumns = ball.getPassedColumnsCount();
        if (currentPassedColumns > lastPassedColumnsCount) {
            int newColumns = currentPassedColumns - lastPassedColumnsCount;
            model.addScore(newColumns);
            System.out.println("score: " + model.getScore());
            lastPassedColumnsCount = currentPassedColumns;
        }

        if (ball.isDead() && !wasDead && !model.isWin()) {
            deathCount++;
            System.out.println("Ball died! Deaths: " + deathCount);
            // Trừ 10 điểm khi chết, nếu điểm < 10 thì đặt về 0
            int currentScore = model.getScore();
            if (currentScore >= 10) {
                model.addScore(-10);
            } else {
                model.addScore(-currentScore); // Điểm nhỏ hơn 10 thì đặt về 0
            }
            System.out.println("Lost 10 points! New score: " + model.getScore());
            wasDead = true;
        } else if (!ball.isDead()) {
            wasDead = false;
        }

        checkWinAndSwitchMap();
    }

    private void checkWinAndSwitchMap() {
        if (model.getBall().isDead())
            return;

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
                lastPassedColumnsCount = 0;
            } else if (!hasWonFinalMap) {
                model.addScore(10);
                hasWonFinalMap = true;
                gameOver = true;
                System.out.println("🎉 You won the final map! Final score: " + model.getScore() + ", Deaths: " + deathCount);
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
        return DatabaseManager.getTop3Players();
    }

    public void restart() {
        Ball ball = model.getBall();

        /*
        // Cơ chế 1: Trở về vị trí ban đầu của map
        ball.setPosition(0, 400);
        ball.clearPassedColumns();
        model.getGameMap().updateCamera(0, 640);
        left = false;
        right = false;
        wasDead = false;
        lastPassedColumnsCount = 0;
        */
        
        // Cơ chế 2: Trở về cột ngay trước vùng chết
        int newX = 0;
        int newY = 400;
        int columnsToRestore = 0;

        if (ball.getPassedColumnsCount() > 0) {
            int lastColumnX = ball.getLastSafeColumnX();
            for (Rectangle column : model.getGameMap().getColumns()) {
                if (column.x == lastColumnX) {
                    newX = column.x;
                    newY = column.y - ball.height;
                    break;
                }
            }
            ball.clearPassedColumns();
            for (Rectangle column : model.getGameMap().getColumns()) {
                if (column.x <= lastColumnX) {
                    ball.addPassedColumn(column);
                    columnsToRestore++;
                }
            }
        }

        ball.setPosition(newX, newY);
        model.getGameMap().updateCamera(newX, 640);
        left = false;
        right = false;
        wasDead = false;
        lastPassedColumnsCount = columnsToRestore;
        
    }

    public void handleKeyPressed(int keyCode) {
        if (isDead()) {
            if (keyCode == KeyEvent.VK_R) {
                System.out.println("Restart triggered!");
                restart();
            } else if (keyCode == KeyEvent.VK_Q) {
                System.exit(0);
            }
            return;
        }

        if (gameOver)
            return;

        switch (keyCode) {
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_UP -> jump();
        }
    }

    public void handleKeyReleased(int keyCode) {
        if (gameOver)
            return;

        switch (keyCode) {
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_RIGHT -> right = false;
        }
    }

    public int getScore() {
        return model.getScore();
    }
}