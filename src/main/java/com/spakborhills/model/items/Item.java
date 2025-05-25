package com.spakborhills.model.items;

public class Item {
    private String name;
    private int sellPrice;
    private int buyPrice;

    public Item() {
        this.name = "";
        this.sellPrice = 0;
        this.buyPrice = 0;
    }

    public Item(String name, int sellPrice, int buyPrice) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getSellPrice() {
        return sellPrice;
    }
    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }
    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }
}
