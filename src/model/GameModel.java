package model;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final List<GameMap> maps = new ArrayList<>();
    private int currentMapIndex = 0;
    private Ball ball;
    private boolean paused = false;
    private long startTime;
    private long pausedTime;
    private long currentTime;

    public GameModel() {
        maps.add(new Map1());
        maps.add(new Map2());
        maps.add(new Map3());
        // 👉 Thêm map mới tại đây nếu cần
        ball = new Ball(maps.get(currentMapIndex));
        startTime = System.nanoTime();
        pausedTime = 0;
        currentTime = 0;
    }

    public GameMap getGameMap() {
        return maps.get(currentMapIndex);
    }

    public Ball getBall() {
        return ball;
    }

    public boolean isWin() {
        return getGameMap().isWin(ball);
    }

    public boolean nextMap() {
        if (currentMapIndex + 1 < maps.size()) {
            currentMapIndex++;
            ball = new Ball(getGameMap()); // chuyển map, reset ball
            return true;
        }
        return false; // không còn map
    }

    public boolean isLastMap() {
        return currentMapIndex == maps.size() - 1;
    }

    public void setCurrentMapIndex(int index) {
        currentMapIndex = index;
    }

    public int getCurrentMapIndex() {
        return currentMapIndex;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        if (!paused) {
            // Điều chỉnh startTime để tiếp tục tính thời gian
            startTime = System.nanoTime() - currentTime * 1_000_000 - pausedTime;
        }
    }

    public void updateTime() {
        if (!paused) {
            currentTime = (System.nanoTime() - startTime - pausedTime) / 1_000_000; // ms
        }
    }

    public String getTimeString() {
        long seconds = currentTime / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds); // MM:SS
    }
}