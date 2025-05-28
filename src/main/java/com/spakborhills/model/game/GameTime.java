package com.spakborhills.model.game;

import java.awt.*;

public class GameTime {
    private Season season = Season.SPRING;
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
        totalGameMinutes = 0; //testing purpose
        startTime = 0;
        inGameMinutes = 0;
        inGameHours = 6;
        inGameDays = 1;
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

    public void displayGameTime(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setFont(new Font("Comic Sans", Font.PLAIN, 30));
        g2.drawString(season.seasonToString(inGameSeason) + ", " + inGameDays, 400, 30);
        g2.drawString(inGameHours + ":" + inGameMinutes, 500, 60);
    }

    public void advanceGameTime(int gameMinutes){
        totalGameMinutes += gameMinutes;
        inGameMinutes += gameMinutes;
        normalizeTime();
    }

    private void normalizeTime(){ //normalisasi satuan waktu
        if (inGameMinutes >= 60) {
            inGameHours += inGameMinutes / 60;
            inGameMinutes %= 60;
        }

        if (inGameHours >= 24) {
            inGameDays += 1;
            inGameHours = 0;
        }

        if (inGameDays >= 10) {
            inGameSeason += 1;
            inGameDays = 1;
        }

        if (inGameSeason >= 3) {
            inGameSeason = 0;
        }
    }

    public int getTotalGameMinutes() {
        return totalGameMinutes;
    }

    public void startNewDay(int minuteTo2){
        inGameDays += 1;
        inGameHours = 6;
        inGameMinutes = 0;
        totalGameMinutes += 2400 + minuteTo2;
        normalizeTime();
    }
}
