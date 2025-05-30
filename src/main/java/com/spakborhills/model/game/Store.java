package com.spakborhills.model.game;

import com.spakborhills.model.items.seeds.*;
import com.spakborhills.model.items.crops.*;
import com.spakborhills.model.items.foods.FoodRegistry;
import com.spakborhills.model.items.recipes.*;
import com.spakborhills.model.items.seeds.*;
import com.spakborhills.model.items.Item;

import java.util.*;

public class Store {
    List<Item> seedsList;
    List<Item> cropsList;
    List<Item> foodsList;

    public Store(){
        seedsList = new ArrayList<>();
        cropsList = new ArrayList<>();
        foodsList = new ArrayList<>();

        initializeStore();
    }

    public void initializeStore(){
        Set<String> availSeeds = SeedRegistry.getAvailableSeedNames();
        for (String seedName : availSeeds){
            Item seed = SeedRegistry.getSeedPrototype(seedName);
            seedsList.add(seed);
        }

        Set<String> availCrops = CropsRegistry.getAvailableCropsNames();
        for (String cropName : availCrops){
            Item crops = CropsRegistry.getCropsPrototype(cropName);
            cropsList.add(crops);
        }

        Set<String> availFoods = CropsRegistry.getAvailableCropsNames();
        for (String foodName : availFoods){
            Item foods = FoodRegistry.getFoodPrototype(foodName);
            foodsList.add(foods);
        }
    }

    public void displayStore(){
        System.out.println("╔════════════════════ STORE ════════════════════╗");

        // display seeds
        System.out.println("║                     SEEDS                      ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        for (Item seed : seedsList){
            System.out.printf("║ %-20s | Price: %-15d ║%n", seed.getName(), seed.getBuyPrice());
        }

        // display crops
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║                     CROPS                      ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        for (Item crop : cropsList) {
            System.out.printf("║ %-20s | Price: %-15d ║%n", crop.getName(), crop.getBuyPrice());
        }

        // display foods
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║                     FOODS                      ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        for (Item food : foodsList) {
            System.out.printf("║ %-20s | Price: %-15d ║%n", food.getName(), food.getBuyPrice());
        }
    }
}
