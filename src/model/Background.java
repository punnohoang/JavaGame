package model;

import java.awt.*;

public class Background {
    private Color color;

    // Constructor nhận màu sắc để vẽ nền
    public Background(Color color) {
        this.color = color;
    }

    public void draw(Graphics g, int cameraX, int screenWidth, int screenHeight) {
        g.setColor(color);
        g.fillRect(0, 0, screenWidth, screenHeight);  // Vẽ một hình chữ nhật toàn màn hình
    }
}
