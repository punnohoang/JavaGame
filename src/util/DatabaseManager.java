package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://DESKTOP-L8D5TII:1433;databaseName=Score;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "1122334455";

    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void insertNewPlayer(String playerName) {
        String sql = "INSERT INTO HighScores (playerName, score) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playerName);
            stmt.setInt(2, 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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

    public static void recordFinalResult(String playerName, int deathCount) {
        String sql = "UPDATE HighScores SET deathCount = ? WHERE playerName = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, deathCount);
            stmt.setString(2, playerName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}