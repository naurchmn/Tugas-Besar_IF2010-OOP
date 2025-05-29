package com.spakborhills.controller;

public class Gold {
    private int amount;
    private static int totalGold;

    public Gold() {
        this.amount = 0;
    }

    public Gold(int initialAmount) {
        this.amount = Math.max(0, initialAmount);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = Math.max(0, amount);
    }

    public void add(int value) {
        if (value > 0) {
            amount += value;
        }
    }

    public boolean subtract(int value) {
        if (value <= amount) {
            amount -= value;
            return true;
        }
        return false;
    }

    public boolean canAfford(int value) {
        return amount >= value;
    }

    @Override
    public String toString() {
        return amount + "g";
    }
}

