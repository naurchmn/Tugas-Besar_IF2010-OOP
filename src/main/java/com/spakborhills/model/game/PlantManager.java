package com.spakborhills.model.game;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PlantManager implements Observer {
    Map<Point, PlantInfo> plants = new HashMap<>();

        public void setPlant(Point location, PlantInfo plant) {
            plants.put(location, plant);
        }

        public void removePlant(Point location) {
            plants.remove(location);
        }

        public Map<Point, PlantInfo> getPlants() {
            return plants;
        }

        public void clearAllPlants(){
            if(plants.isEmpty()) return;
            else {
                plants.clear();
                System.out.println("All plants died due to changed season");
            }
        }

    @Override
    public void update(String eventType, Object data) {
        if ("SEASON CHANGED".equals(eventType)) {
            clearAllPlants();
        }
    }
}
