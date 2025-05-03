package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://PUNNO:1433;databaseName=Score;encrypt=false;trustServerCertificate=true;";
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
        String sql = "INSERT INTO HighScores (playerName, score, deathCount, playTime) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playerName);
            stmt.setInt(2, 0); // Điểm khởi đầu
            stmt.setInt(3, 0); // Số lần chết khởi đầu
            stmt.setInt(4, 0); // Thời gian chơi khởi đầu
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật điểm
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

    // Lấy top 3 người chơi có điểm cao nhất
    public static List<String> getTop3Players() {
        List<String> topPlayers = new ArrayList<>();
        String sql = "SELECT playerName, score, playTime FROM HighScores ORDER BY score DESC, playTime ASC OFFSET 0 ROWS FETCH NEXT 3 ROWS ONLY";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
            	String player = rs.getString("playerName");
            	int score = rs.getInt("score");
            	int time = rs.getInt("playTime");
            	topPlayers.add(player + " - " + score + " điểm - " + time + "s");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topPlayers;
    }

    // Cập nhật số lần chết
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

    // Cập nhật thời gian chơi
    public static void recordPlayTime(String playerName, int playTime) {
        String sql = "UPDATE HighScores SET playTime = ? WHERE playerName = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playTime);
            stmt.setString(2, playerName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("⚠️ Không thể cập nhật playTime. Kiểm tra lại cột playTime trong bảng HighScores.");
            e.printStackTrace();
        }
    }
}
