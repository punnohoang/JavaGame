//package util;
//
//import model.Ball;
//import model.GameModel;
//
//import java.io.*;
//
//public class FileManager {
//    // Đường dẫn đầy đủ đến tệp save.txt
//    private static final String FILE_PATH = "C:\\Users\\hoang\\Downloads\\java.game (1)\\java.game\\save.txt";
//
//    // Lưu trạng thái game vào tệp
//    public static void saveGameState(GameModel gameModel) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
//            // Lưu các thông tin trạng thái game dưới dạng chuỗi
//            writer.write("CurrentMapIndex:" + gameModel.getCurrentMapIndex() + "\n");
//            Ball ball = gameModel.getBall();
//            writer.write("BallX:" + ball.x + "\n");
//            writer.write("BallY:" + ball.y + "\n");
//            writer.write("BallVelocityX:" + ball.velocityX + "\n");
//            writer.write("BallVelocityY:" + ball.velocityY + "\n");
//            writer.write("BallDead:" + ball.isDead() + "\n");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
