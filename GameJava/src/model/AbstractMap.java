package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMap implements GameMap {
	protected final int width = 3000;
	protected final int height = 500;
	protected int cameraX = 0;
	protected final List<Rectangle> columns = new ArrayList<>();
	protected final List<Rectangle> deathZoneBounds = new ArrayList<>();
	protected Background background;
	private static final int MAX_JUMP_HEIGHT = 105;

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean isWin(Ball ball) {
		return false;
	}

	@Override
	public Foothold getFoothold() {
		return null;
	}

	@Override
	public int getCameraX() {
		return cameraX;
	}

	@Override
	public List<Rectangle> getColumns() {
		return columns;
	}

	@Override
	public List<Rectangle> getDeathZoneBounds() {
		return deathZoneBounds;
	}

	@Override
	public Background getBackground() {
		return background;
	}

	@Override
	public void updateCamera(int ballX, int screenWidth) {
		int safeZoneLeft = screenWidth / 3;
		int safeZoneRight = screenWidth * 2 / 3;
		int diff = ballX - cameraX;

		if (diff < safeZoneLeft)
			cameraX = ballX - safeZoneLeft;
		else if (diff > safeZoneRight)
			cameraX = ballX - safeZoneRight;

		cameraX = Math.max(0, Math.min(cameraX, width - screenWidth));
	}

	protected void initColumns(List<int[]> positions, int columnWidth, int baseY) {
		for (int i = 0; i < positions.size(); i++) {
			int x = positions.get(i)[0];
			int height = positions.get(i)[1];
			int y = baseY + 30 - height;
			columns.add(new Rectangle(x, y, columnWidth, height));

			if (i > 0) {
				Rectangle prevCol = columns.get(i - 1);
				Rectangle currCol = columns.get(i);
				int gapBetween = currCol.x - (prevCol.x + prevCol.width);

				if (gapBetween > 0 && prevCol.height > MAX_JUMP_HEIGHT && currCol.height > MAX_JUMP_HEIGHT) {
					int zoneX = prevCol.x + prevCol.width;
					int zoneWidth = gapBetween;
					int zoneY = baseY;
					int zoneHeight = 30;
					deathZoneBounds.add(new Rectangle(zoneX, zoneY, zoneWidth, zoneHeight));
				}
			}
		}
	}

}