package model;

import java.awt.*;

public class Map1 extends AbstractMap {
    private final Rectangle arrowRect;
    private final Foothold foothold;

    public Map1() {
        background = new Background(new Color(200, 255, 255));
        int[] columnHeights = {60, 130, 180, 0, 45, 120, 140}; // Điều chỉnh độ cao
        initColumns(columnHeights, 100, 90, 40, 430);
        arrowRect = new Rectangle(2950, 370, 40, 500);
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