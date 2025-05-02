package model;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final List<GameMap> maps = new ArrayList<>();
    private int currentMapIndex = 0;
    private Ball ball;
    
    public GameModel() {
        maps.add(new Map1());
        maps.add(new Map2());
        maps.add(new Map3());
        // 👉 Thêm map mới tại đây nếu cần
        ball = new Ball(maps.get(currentMapIndex));
    }

    public GameMap getGameMap() {
        return maps.get(currentMapIndex);
    }

    public Ball getBall() {
        return ball;
    }

    public boolean isWin() {
        return getGameMap().isWin(ball);
    }

    public boolean nextMap() {
        if (currentMapIndex + 1 < maps.size()) {
            currentMapIndex++;
            ball = new Ball(getGameMap());  // chuyển map, reset ball
            return true;
        }
        return false; // không còn map
    }

    public boolean isLastMap() {
        return currentMapIndex == maps.size() - 1;
    }

	public void setCurrentMapIndex(int int1) {
		currentMapIndex++;
		
	}

	public int getCurrentMapIndex() {
		// TODO Auto-generated method stub
		return currentMapIndex;
	  
	}

}
