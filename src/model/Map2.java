package model;

import java.awt.*;

public class Map2 extends AbstractMap {
    private final Rectangle arrowRect;
    private final Foothold foothold;

    public Map2() {
        // Nền riêng cho Map2
        background = new Background(new Color(255, 200, 200));
        // Cột riêng cho Map2
        int[] columnHeights = {80, 120, 150, 60, 0, 70, 120, 140, 80, 130, 170, 100};
        initColumns(columnHeights, 100, 60, 40, 430);

        // Đích đến (mũi tên)
        arrowRect = new Rectangle(2950, 370, 40, 500);

        // Foothold mặc định (màu xám)
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
