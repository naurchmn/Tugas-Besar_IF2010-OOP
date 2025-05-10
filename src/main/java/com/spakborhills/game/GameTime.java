package com.spakborhills.game;

public class GameTime {
    private long startTime;
    private int totalGameMinutes;

    private final int gameMinutesPerSecond = 5; //konversi waktu 1 detik =  5 menit in game
    double timePerInGameMinute = 1.0 / gameMinutesPerSecond;
    private double carryOverTime = 0; //waktu sisa update, buat presisi

    //waktu in game
    private int inGameMinutes;
    private int inGameHours;
    private int inGameDays;
    private int inGameSeason;


    public GameTime() {
        startTime = 0;
        inGameMinutes = 0;
        inGameHours = 0;
        inGameDays = 0;
        inGameSeason = 0;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void updateGameTime() {
        long currentTime = System.nanoTime();
        double deltaTime = (double) (currentTime - startTime) / 1_000_000_000;
        deltaTime += carryOverTime;

        //waktu diupdate tiap 0.2 detik alias tiap 1 menit in game
        while(deltaTime >= timePerInGameMinute) {
            inGameMinutes += 1;
            totalGameMinutes += 1;
            normalizeTime();
            deltaTime -= timePerInGameMinute;
        }

        carryOverTime = deltaTime;
        startTime = currentTime;
    }

    public void displayGameTime() {
        System.out.println(inGameDays + ":" + inGameHours + ":" + inGameMinutes);
    }

    public void advanceGameTime(int gameMinutes){
        totalGameMinutes += 1;
        inGameMinutes += gameMinutes;
        normalizeTime();
    }

    private void normalizeTime(){ //normalisasi satuan waktu
        if (inGameMinutes >= 60) {
            inGameHours += inGameMinutes / 60;
            inGameMinutes %= 60;
        }

        if (inGameHours >= 24) {
            inGameDays += inGameHours / 24;
            inGameHours %= 24;
        }

        if (inGameDays >= 10) {
            inGameSeason += inGameDays / 10;
            inGameDays %= 10;
        }

        if (inGameSeason >= 4) {
            inGameSeason = 0;
        }
    }

    public int getTotalGameMinutes() {
        return totalGameMinutes;
    }
}
