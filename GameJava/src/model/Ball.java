package model;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Ball {
    public int x = 0, y = 400;
    public final int width = 30, height = 30;
    public int velocityX = 0, velocityY = 0;
    public boolean onGround = true;
    public final int gravity = 1;
    public final int groundY = 430;
    private boolean isDead = false;

    private GameMap gameMap;
    private final int screenWidth = 640;
    private Set<Rectangle> passedColumns;
    private int lastSafeColumnX = 0;

    public Ball(GameMap gameMap) {
        this.gameMap = gameMap;
        this.passedColumns = new HashSet<>();
    }

    public void update(boolean left, boolean right) {
        if (isDead) {
            return;
        }

        if (left) velocityX = -5;
        else if (right) velocityX = 5;
        else velocityX = 0;

        boolean collided = false;
        for (Rectangle col : gameMap.getColumns()) {
            int top = col.y;
            if (x + width > col.x && x < col.x + col.width && y + height <= top && y + height + velocityY >= top) {
                y = top - height;
                velocityY = 0;
                onGround = true;
                collided = true;
                break;
            }
        }

        if (!collided) {
            if (y + velocityY >= groundY) {
                y = groundY;
                velocityY = 0;
                onGround = true;
            } else {
                velocityY += gravity;
                if (velocityY > 20) velocityY = 20;
                onGround = false;
            }
        }

        int nextX = x + velocityX;
        Rectangle nextRect = new Rectangle(nextX, y, width, height);
        boolean blocked = false;
        for (Rectangle col : gameMap.getColumns()) {
            if (nextRect.intersects(col)) {
                blocked = true;
                break;
            }

            // Chỉ thêm cột vào passedColumns nếu bóng vượt qua cột và cột chưa được đánh dấu
            if (!passedColumns.contains(col) && nextX + width > col.x + col.width) {
                passedColumns.add(col);
            }
        }

        // Kiểm tra va chạm với vùng chết
        for (Rectangle dz : gameMap.getDeathZoneBounds()) {
            if (nextRect.intersects(dz) || new Rectangle(x, y + velocityY, width, height).intersects(dz)) {
                isDead = true;
                updateLastSafeColumnX();
                break;
            }
        }

        if (!blocked) x = nextX;
        y += velocityY;

        gameMap.updateCamera(x, screenWidth);

        if (x < 0) x = 0;
        if (x > gameMap.getWidth() - width) x = gameMap.getWidth() - width;
    }

    public void jump() {
        if (onGround && !isDead) {
            velocityY = -15;
            onGround = false;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setMap(GameMap newMap) {
        this.gameMap = newMap;
        this.passedColumns.clear();
        this.lastSafeColumnX = 0;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isDead = false;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        this.isDead = dead;
        if (dead) {
            updateLastSafeColumnX();
        }
    }

    public int getPassedColumnsCount() {
        return passedColumns.size();
    }

    private void updateLastSafeColumnX() {
        if (!passedColumns.isEmpty()) {
            int maxX = 0;
            for (Rectangle col : passedColumns) {
                if (col.x > maxX) {
                    maxX = col.x;
                }
            }
            this.lastSafeColumnX = maxX;
        }
    }

    public int getLastSafeColumnX() {
        return lastSafeColumnX;
    }

    public void clearPassedColumns() {
        passedColumns.clear();
        lastSafeColumnX = 0;
    }

    public void addPassedColumn(Rectangle column) {
        passedColumns.add(column);
    }
}