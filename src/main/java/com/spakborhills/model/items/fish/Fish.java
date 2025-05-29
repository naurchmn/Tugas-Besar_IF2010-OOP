package com.spakborhills.model.items.fish;

import com.spakborhills.model.game.Season;
import com.spakborhills.model.game.Weather;
import com.spakborhills.model.items.Item;
import com.spakborhills.model.items.Rarity;

import java.util.*;

public class Fish extends Item implements Cloneable {
    private String name;
    private List<Season> season = new ArrayList<>();
    private List<Integer> startTimes = new ArrayList<>();
    private List<Integer> endTimes = new ArrayList<>();
    private List<Weather> weather = new ArrayList<>();
    private List<String> location = new ArrayList<String>();
    private final Rarity rarity;
    private int sellPrice;


    public Fish (String name,  List<Season> season, List<Integer> startTimes, List<Integer> endTimes, List<Weather> weather,
                 List<String> location, Rarity rarity){
        super();
        this.name = name;
        this.season = season;
        this.startTimes = startTimes;
        this.endTimes = endTimes;
        this.weather = weather;
        this.location = location;
        this.rarity = rarity;

        setSellPrice();
    }

    @Override
    public String getName() {
        return name;
    }

    public Fish clone(){
        try{
            Fish cloned = (Fish) super.clone();
            cloned.season = new ArrayList<>(this.season);
            cloned.startTimes = new ArrayList<>(this.startTimes);
            cloned.endTimes = new ArrayList<>(this.endTimes);
            cloned.weather = new ArrayList<>(this.weather);
            cloned.location = new ArrayList<>(this.location);
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cannot clone Fish", e);
        }
    }
    public int countSeasons() {
        return season.size();
    }
    public int countWeather(){
        return weather.size();
    }
    public int countLocation(){
        return location.size();
    }
    public int countTime() {
        int total = 0;
        for (int i = 0; i<startTimes.size(); i++) {
            int count = Math.abs(endTimes.get(i) - startTimes.get(i));
            total += count;
        }
        return total;

    }

    public Rarity getRarity(){ return rarity; }
    @Override
    public int getSellPrice() { return sellPrice; }

    public void setSellPrice() {
        int c = switch (rarity) {
            case COMMON -> 10;
            case REGULAR -> 5;
            case LEGENDARY -> 25;
        };
        int calc;
        calc = (4 / countSeasons()) * (24 / countTime()) * (2 / countWeather()) * (4 / countLocation()) * c;
        this.sellPrice = calc;
    }

}

