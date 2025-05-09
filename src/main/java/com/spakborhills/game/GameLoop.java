package com.spakborhills.game;

public class GameLoop implements Runnable {
    private Thread gameThread;
    private boolean isRunning = false;
    private final int fps;
    private final Runnable updateLogic;
    private final Runnable repaintPanel;
    
    public GameLoop(int fps, Runnable updateLogic, Runnable repaintPanel) {
        this.fps = fps;
        this.updateLogic = updateLogic;
        this.repaintPanel = repaintPanel;
    }

    public void startGame() {
        if (gameThread == null || !gameThread.isAlive()) {
            isRunning = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
        else{
            isRunning = true;
        }
    }

    public void pause() {
        isRunning = false;
    }


    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / fps;
        long lastTime = System.nanoTime();
        long currentTime;
        double delta = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (isRunning && delta >= 1) {
                updateLogic.run();
                repaintPanel.run();
                delta--;
            }

            if (!isRunning) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}