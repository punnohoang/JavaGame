package model;

public class GameTimer {
    private long startTime;
    private long elapsedTime; // milliseconds
    private boolean running;

    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }

    public void stop() {
        elapsedTime = getElapsedTime();
        running = false;
    }

    public void reset() {
        elapsedTime = 0;
        startTime = System.currentTimeMillis();
    }

    public long getElapsedTime() {
        if (running) {
            return System.currentTimeMillis() - startTime;
        } else {
            return elapsedTime;
        }
    }

    public String getFormattedTime() {
        long seconds = getElapsedTime() / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
