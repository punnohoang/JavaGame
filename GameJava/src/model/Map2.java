package model;

import java.awt.*;
import java.util.List;

import utils.MapDataLoader;

public class Map2 extends AbstractMap {
    private final Rectangle arrowRect;
    private final Foothold foothold;

    public Map2() {
        // Nền riêng cho Map2
        background = new Background(new Color(255, 200, 200));
        // Cột riêng cho Map2
        List<int[]> columnPositions =  MapDataLoader.readColumn("C:\\Users\\AD MIN\\eclipse-workspace\\GameJava\\src\\model\\Map2.txt");
		initColumns(columnPositions, 40, 430); // width=40, baseY=430

        // Đích đến (mũi tên)
        arrowRect = new Rectangle(2950, 370, 40, 60);

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
