package com.spakborhills.model.items.recipes;

import com.spakborhills.model.items.foods.Food;
import com.spakborhills.model.items.foods.FoodRegistry;
import com.spakborhills.model.items.crops.Crops;
import com.spakborhills.model.items.crops.CropsRegistry;
import com.spakborhills.model.items.fish.Fish;
import com.spakborhills.model.items.fish.FishRegistry;
import com.spakborhills.model.items.Item;

import java.io.*;
import java.util.*;

public class RecipeRegistry {
    private static final Map<String, Recipe> recipePrototypes = new HashMap<>();

    static{
        // BAGUETTE
        Map<Item, Integer> baguetteIngredients = new HashMap<>();
        baguetteIngredients.put(CropsRegistry.getCropsPrototype("Wheat"), 3);
        Recipe baguette = new Recipe("recipe_2", FoodRegistry.getFoodPrototype("Baguette"), baguetteIngredients, null, false);
        registerFood(baguette);

        // WINE
        Map<Item, Integer> wineIngredients = new HashMap<>();
        wineIngredients.put(CropsRegistry.getCropsPrototype("Grape"), 2);
        Recipe wine = new Recipe("recipe_5", FoodRegistry.getFoodPrototype("Wine"), wineIngredients, null, false);
        registerFood(wine);

        // PUMPKIN PIE
        Map<Item, Integer> pumpkinpieIngredients = new HashMap<>();
        pumpkinpieIngredients.put(CropsRegistry.getCropsPrototype("Pumpkin"), 1);
        pumpkinpieIngredients.put(CropsRegistry.getCropsPrototype("Wheat"), 1);
        pumpkinpieIngredients.put(FoodRegistry.getFoodPrototype("Egg"), 1);
        Recipe pumpkinpie = new Recipe("recipe_9", FoodRegistry.getFoodPrototype("Pumpkin Pie"), pumpkinpieIngredients, null, false);
        registerFood(pumpkinpie);

        // SPAKBOR SALAD
        Map<Item, Integer> spakborsaladIngredients = new HashMap<>();
        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Melon"), 1);
        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Cranberry"), 1);
        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Blueberry"), 1);
        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Tomato"), 1);
        Recipe spakborsalad = new Recipe("recipe_7", FoodRegistry.getFoodPrototype("Spakbor Salad"), spakborsaladIngredients, null, false);
        registerFood(spakborsalad);

        // FISH N' CHIPS
        Map<Item, Integer> fishChipsIngredients = new HashMap<>();
        fishChipsIngredients.put(FishRegistry.getAnyFishWildcard(), 2);
        fishChipsIngredients.put(CropsRegistry.getCropsPrototype("Wheat"), 1);
        fishChipsIngredients.put(CropsRegistry.getCropsPrototype("Potato"), 1);
        Recipe fishChips = new Recipe("recipe_1", "Fish n' Chips Recipe", FoodRegistry.getFoodPrototype("Fish n' Chips"), fishChipsIngredients, null, false, 100, 50); // false for defaultUnlocked
        registerRecipe(fishChips);

        // SASHIMI (recipe_3)
        Map<Item, Integer> sashimiIngredients = new HashMap<>();
        sashimiIngredients.put(FishRegistry.getFishPrototype("Salmon"), 3); // Salmon x3
        UnlockCondition sashimiUnlock = new FishCountUnlockCondition(10, "Catch 10 fish");
        Recipe sashimi = new Recipe("recipe_3", FoodRegistry.getFoodPrototype("Sashimi"), sashimiIngredients, sashimiUnlock, false);
        registerFood(sashimi);

        // FUGU
        Map<Item, Integer> fuguIngredients = new HashMap<>();
        fuguIngredients.put(FishRegistry.getFishPrototype("Pufferfish"), 1); // Pufferfish x1
        UnlockCondition fuguUnlock = new SpecificFishUnlockCondition("Pufferfish", "Catch a Pufferfish");
        Recipe fugu = new Recipe("recipe_4", FoodRegistry.getFoodPrototype("Fugu"), fuguIngredients, fuguUnlock, false);
        registerFood(fugu);

        // VEGGIE SOUP 
        Map<Item, Integer> veggieSoupIngredients = new HashMap<>();
        veggieSoupIngredients.put(CropsRegistry.getCropsPrototype("Cauliflower"), 1);
        veggieSoupIngredients.put(CropsRegistry.getCropsPrototype("Parsnip"), 1);
        veggieSoupIngredients.put(CropsRegistry.getCropsPrototype("Potato"), 1);
        veggieSoupIngredients.put(CropsRegistry.getCropsPrototype("Tomato"), 1);
        UnlockCondition veggieSoupUnlock = new HarvestUnlockCondition("Harvest for the first time");
        Recipe veggieSoup = new Recipe("recipe_7", FoodRegistry.getFoodPrototype("Veggie Soup"), veggieSoupIngredients, veggieSoupUnlock, false);
        registerFood(veggieSoup);

        
    // FISH SANDWICH (recipe_10) - Set to not defaultUnlocked and given prices
        Map<Item, Integer> fishSandwichIngredients = new HashMap<>();
        fishSandwichIngredients.put(FishRegistry.getAnyFishWildcard(), 1);
        fishSandwichIngredients.put(CropsRegistry.getCropsPrototype("Wheat"), 2);
        fishSandwichIngredients.put(CropsRegistry.getCropsPrototype("Tomato"), 1);
        fishSandwichIngredients.put(CropsRegistry.getCropsPrototype("Hot Pepper"), 1);
        Recipe fishSandwich = new Recipe("recipe_10", "Fish Sandwich Recipe", FoodRegistry.getFoodPrototype("Fish Sandwich"), fishSandwichIngredients, null, false, 100, 50); // false for defaultUnlocked
        registerRecipe(fishSandwich);

        // FISH STEW (recipe_8) - NEW, with ItemAcquisitionUnlockCondition for Hot Pepper
        Map<Item, Integer> fishStewIngredients = new HashMap<>();
        fishStewIngredients.put(FishRegistry.getAnyFishWildcard(), 2);
        fishStewIngredients.put(CropsRegistry.getCropsPrototype("Hot Pepper"), 1);
        fishStewIngredients.put(CropsRegistry.getCropsPrototype("Cauliflower"), 2);
        UnlockCondition fishStewUnlock = new ItemAcquisitionUnlockCondition("Hot Pepper", "Dapatkan “Hot Pepper” terlebih dahulu");
        Recipe fishStew = new Recipe("recipe_8", "Fish Stew Recipe", FoodRegistry.getFoodPrototype("Fish Stew"), fishStewIngredients, fishStewUnlock, false, 120, 60);
        registerRecipe(fishStew);

        // THE LEGENDS OF SPAKBOR (recipe_11) - NEW, with FishingUnlockCondition for Legend Fish
        Map<Item, Integer> legendsOfSpakborIngredients = new HashMap<>();
        legendsOfSpakborIngredients.put(FishRegistry.getLegendFish(), 1); // Assumes FishRegistry.getLegendFish() exists
        legendsOfSpakborIngredients.put(CropsRegistry.getCropsPrototype("Potato"), 2);
        legendsOfSpakborIngredients.put(CropsRegistry.getCropsPrototype("Parsnip"), 1);
        legendsOfSpakborIngredients.put(CropsRegistry.getCropsPrototype("Tomato"), 1);
        legendsOfSpakborIngredients.put(CropsRegistry.getCropsPrototype("Eggplant"), 1); // Assumes Eggplant is a crop
        UnlockCondition legendsOfSpakborUnlock = new FishingUnlockCondition("Legend Fish", "Memancing “Legend”");
        Recipe legendsOfSpakbor = new Recipe("recipe_11", "The Legends of Spakbor Recipe", FoodRegistry.getFoodPrototype("The Legends of Spakbor"), legendsOfSpakborIngredients, legendsOfSpakborUnlock, false, 500, 250);
        registerRecipe(legendsOfSpakbor);
    }

    public static void registerFood(Recipe prototype) {
        if (prototype != null && prototype.getRecipeName() != null) {
            recipePrototypes.put(prototype.getRecipeName(), prototype);
        }
    }

    public static Recipe getRecipePrototype(String name) {
        Recipe prototype = recipePrototypes.get(name);
        if (prototype != null) {
            return prototype.clone();
        }
        return null;
    }

    public static Set<String> getAvailableRecipeNames() {
        return new HashSet<>(recipePrototypes.keySet());
    }

}
