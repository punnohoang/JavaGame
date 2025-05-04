package model;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final List<GameMap> maps = new ArrayList<>();
    private int currentMapIndex = 0;
    private Ball ball;
    private int score = 0; // Thêm thuộc tính để lưu điểm số

    public GameModel() {
        // Khởi tạo danh sách map
        maps.add(new Map1());
        maps.add(new Map2());
        maps.add(new Map3());
        // 👉 Thêm map mới tại đây nếu cần
        ball = new Ball(maps.get(currentMapIndex)); // Khởi tạo bóng với map đầu tiên
    }

    // Lấy map hiện tại
    public GameMap getGameMap() {
        return maps.get(currentMapIndex);
    }

    // Lấy đối tượng Ball
    public Ball getBall() {
        return ball;
    }

    // Kiểm tra xem Ball có hoàn thành map không
    public boolean isWin() {
        return getGameMap().isWin(ball);
    }

    // Chuyển sang map tiếp theo
    public boolean nextMap() {
        if (currentMapIndex + 1 < maps.size()) {
            currentMapIndex++;
            ball = new Ball(getGameMap()); // Reset ball với map mới
            return true;
        }
        return false; // Không còn map
    }

    // Kiểm tra xem đã đến map cuối cùng chưa
    public boolean isLastMap() {
        return currentMapIndex == maps.size() - 1;
    }

    // Đặt chỉ số map mới (kiểm tra giới hạn)
    public void setCurrentMapIndex(int index) {
        if (index >= 0 && index < maps.size()) {
            currentMapIndex = index;
            ball = new Ball(maps.get(currentMapIndex)); // Cập nhật ball với map mới
        } else {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
    }

    // Lấy chỉ số map hiện tại
    public int getCurrentMapIndex() {
        return currentMapIndex;
    }

    // Tăng điểm số
    public void addScore(int points) {
        score += points;
    }

    // Lấy điểm số hiện tại
    public int getScore() {
        return score;
    }

    // Đặt lại điểm số (dùng khi reset game)
    public void resetScore() {
        score = 0;
    }
}