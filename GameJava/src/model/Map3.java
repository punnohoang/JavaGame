package model;

import java.awt.*;
import java.util.List;

import utils.MapDataLoader;

public class Map3 extends AbstractMap {
	private final Rectangle arrowRect;
	private final Foothold foothold;

	public Map3() {
		// Nền riêng cho Map3
		background = new Background(new Color(255, 200, 200)); // Màu nền cho Map3

		// Các cột riêng cho Map3
        List<int[]> columnPositions =  MapDataLoader.readColumn("C:\\Users\\AD MIN\\eclipse-workspace\\GameJava\\src\\model\\Map3.txt");
		initColumns(columnPositions, 40, 430); // width=40, baseY=430

		// Vùng chiến thắng cuối bản đồ
		arrowRect = new Rectangle(2975, 370, 40, 60);

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
