package com.spakborhills.items;

import java.util.Timer;

public class Seeds extends Item { // extends items
    String name; // nantinya di items
    String season;
    String seedName;
    Timer time;
    String weather;
    String location;

    // constructor
    public Seeds(String name, String season, String seedName, String weather, String location) {
        this.name = name;
        this.season = season;
        this.seedName = seedName;
        this.weather = weather;
        this.location = location;
        this.time = new Timer();
    }
}