package model;

import java.awt.*;

public class Foothold {
    public void draw(Graphics g, java.util.List<Rectangle> columns, int cameraX) {
        g.setColor(Color.GRAY);
        for (Rectangle col : columns) {
            g.fillRect(col.x - cameraX, col.y, col.width, col.height);
        }
    }
}