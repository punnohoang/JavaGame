package util;

import java.util.List;

public class TestDatabase {
    public static void main(String[] args) {
        DatabaseManager.insertNewPlayer("TestPlayer");
        System.out.println("Thêm người chơi thành công");

        DatabaseManager.updateScore("TestPlayer", 100);
        System.out.println("Cập nhật điểm thành công");

        List<String> topPlayers = DatabaseManager.getTop3Players();
        System.out.println("Top 3 người chơi:");
        for (String player : topPlayers) {
            System.out.println(player);
        }

        DatabaseManager.recordFinalResult("TestPlayer", 3);
        System.out.println("Ghi kết quả thành công");
    }
}