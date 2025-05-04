package model;

import utils.MapDataLoader;

import java.awt.*;
import java.util.List;

public class Map1 extends AbstractMap {
    private final Rectangle arrowRect;
    private final Foothold foothold;

    public Map1() {
        background = new Background(new Color(200, 255, 255));
        
        // Đọc độ cao cột từ file
        List<int[]> columnPositions =  MapDataLoader.readColumn("C:\\Users\\AD MIN\\git\\repository\\GameJava\\src\\model\\Map1.txt");
		initColumns(columnPositions, 40, 430); // width=40, baseY=430


        arrowRect = new Rectangle(2975, 370, 40, 500);
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
