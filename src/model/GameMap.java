package model;

import java.awt.*;
import java.util.List;

public interface GameMap {
    int getWidth();
    int getHeight();
    int getCameraX();
    List<Rectangle> getColumns();
    void updateCamera(int ballX, int screenWidth);
    boolean isWin(Ball ball);
    Background getBackground();
    Foothold getFoothold();
    List<Rectangle> getDeathZoneBounds();
}