package model;

import java.awt.*;

public class DeathZone {
    public void draw(Graphics g, Rectangle zone, int cameraX) {
        // Vẽ khung vùng chết (chỉ để debug, có thể bỏ)
        g.setColor(Color.MAGENTA);
        g.drawRect(zone.x - cameraX, zone.y, zone.width, zone.height);

        // Vẽ gai nhọn
        g.setColor(Color.DARK_GRAY);
        int spikeCount = zone.width / 10;
        for (int i = 0; i < spikeCount; i++) {
            int[] xPoints = {
                    zone.x - cameraX + i * 10,
                    zone.x - cameraX + i * 10 + 5,
                    zone.x - cameraX + i * 10 + 10
            };
            int[] yPoints = {
                    zone.y + zone.height,
                    zone.y,
                    zone.y + zone.height
            };
            g.fillPolygon(xPoints, yPoints, 3);
        }
    }
}