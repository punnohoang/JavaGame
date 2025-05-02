package model;

import java.awt.*;

public class Map3 extends AbstractMap {
    private final Rectangle arrowRect;
    private final Foothold foothold;

    public Map3() {
        // Nền riêng cho Map3
        background = new Background(new Color(255, 200, 200)); // Màu nền cho Map3

        // Các cột riêng cho Map3
        int[] columnHeights = {
            60, 90, 0, 100, 150, 30, 90, 150, 90, 120, 180, 45
        };
        initColumns(columnHeights, 100, 60, 40, 430);

        // Vùng chiến thắng cuối bản đồ
        arrowRect = new Rectangle(2950, 370, 40, 500);

        // Khởi tạo foothold
        foothold = new Foothold();
    }

    @Override
    public boolean isWin(Ball ball) {
        return arrowRect.intersects(ball.getBounds());
    }

    @Override
    public Foothold getFoothold() {
        return foothold;
    }
}
