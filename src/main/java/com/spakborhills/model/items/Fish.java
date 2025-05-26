package com.spakborhills.model.items;

import com.spakborhills.model.game.GameTime;
import java.util.*;

public class Fish extends Item {
    private String name;
    //private List<Season> season = new ArrayList<Season>();
    private List<GameTime> startTime = new ArrayList<GameTime>();
    private List<GameTime> endTime = new ArrayList<GameTime>();
    //private List<Weather> weather = new ArrayList<Weather>();
    private List<String> location = new ArrayList<String>();
    private Rarity rarity;
    private int sellPrice;


    /*public Fish (String name,  List<Season> season, List<Time> startTime, List<Time> endTime, List<Weather> weather,
                 List<String> location, Rarity rarity, int sellPrice){
        super(name);

        this.season = season;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weather = weather;
        this.location = location;
        this.rarity = rarity;

        setSellPrice(sellPrice);
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

    public void setSellPrice(int sellPrice) {
        int c = switch (rarity) {
            case COMMON -> 10;
            case REGULAR -> 5;
            case LEGENDARY -> 25;
        };
        int calc = (4 / countSeasons()) * (2 / countWeather()) * (4 / countLocation()) * c; // belom ada 24 / banyak season
    }*/
}

