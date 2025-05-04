package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://DESKTOP-9CKDHNV:1433;databaseName=Score;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "123456789";

    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Gọi khi người chơi nhập tên để bắt đầu chơi
    public static void insertNewPlayer(String playerName) {
        String sql = "INSERT INTO HighScores (playerName, score, deathCount) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playerName);
            stmt.setInt(2, 0); // Điểm khởi đầu là 0
            stmt.setInt(3, 0); // Số lần chết khởi đầu là 0
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Gọi sau mỗi lần thắng màn để cộng điểm
    public static void updateScore(String playerName, int additionalScore) {
        String sql = "UPDATE HighScores SET score = score + ? WHERE playerName = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, additionalScore);
            stmt.setString(2, playerName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy 3 người chơi có điểm cao nhất
    public static List<String> getTop3Players() {
        List<String> topPlayers = new ArrayList<>();
        String sql = "SELECT playerName, score FROM HighScores ORDER BY score DESC OFFSET 0 ROWS FETCH NEXT 3 ROWS ONLY";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String player = rs.getString("playerName");
                int score = rs.getInt("score");
                topPlayers.add(player + " - " + score + " điểm");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topPlayers;
    }

    // Lưu kết quả của người chơi (score và deathCount)
    public static void savePlayerResult(String playerName, int score, int deathCount) {
        String checkSql = "SELECT COUNT(*) FROM HighScores WHERE playerName = ?";
        String updateSql = "UPDATE HighScores SET score = ?, deathCount = ? WHERE playerName = ?";
        String insertSql = "INSERT INTO HighScores (playerName, score, deathCount) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Kiểm tra xem playerName đã tồn tại chưa
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, playerName);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                boolean exists = rs.getInt(1) > 0;

                if (exists) {
                    // Cập nhật score và deathCount nếu playerName đã tồn tại
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, score);
                        updateStmt.setInt(2, deathCount);
                        updateStmt.setString(3, playerName);
                        updateStmt.executeUpdate();
                    }
                } else {
                    // Thêm bản ghi mới nếu playerName chưa tồn tại
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setString(1, playerName);
                        insertStmt.setInt(2, score);
                        insertStmt.setInt(3, deathCount);
                        insertStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}