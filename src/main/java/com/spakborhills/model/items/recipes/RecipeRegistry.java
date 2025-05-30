package com.spakborhills.model.items.recipes;

import com.spakborhills.model.items.foods.Food;
import com.spakborhills.model.items.foods.FoodRegistry;
import com.spakborhills.model.items.crops.Crops;
import com.spakborhills.model.items.crops.CropsRegistry;
import com.spakborhills.model.items.fish.Fish;
import com.spakborhills.model.items.fish.FishRegistry;
import com.spakborhills.model.items.Item;

import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.util.*;

public class RecipeRegistry {
    private static final Map<String, Recipe> recipePrototypes = new HashMap<>();

    static{

        /* BAWAAN/DEFAULT RECIPES */

        // BAGUETTE
        Map<Item, Integer> baguetteIngredients = new HashMap<>();
        baguetteIngredients.put(CropsRegistry.getCropsPrototype("Wheat"), 3);
        Recipe baguette = new Recipe("recipe_2", FoodRegistry.getFoodPrototype("Baguette"), baguetteIngredients, null, false);
        registerRecipe(baguette);

        // WINE
        Map<Item, Integer> wineIngredients = new HashMap<>();
        wineIngredients.put(CropsRegistry.getCropsPrototype("Grape"), 2);
        Recipe wine = new Recipe("recipe_5", FoodRegistry.getFoodPrototype("Wine"), wineIngredients, null, false);
        registerRecipe(wine);

        // PUMPKIN PIE
        Map<Item, Integer> pumpkinpieIngredients = new HashMap<>();
        pumpkinpieIngredients.put(CropsRegistry.getCropsPrototype("Pumpkin"), 1);
        pumpkinpieIngredients.put(CropsRegistry.getCropsPrototype("Wheat"), 1);
        pumpkinpieIngredients.put(FoodRegistry.getFoodPrototype("Egg"), 1);
        Recipe pumpkinpie = new Recipe("recipe_9", FoodRegistry.getFoodPrototype("Pumpkin Pie"), pumpkinpieIngredients, null, false);
        registerRecipe(pumpkinpie);

        // SPAKBOR SALAD
        Map<Item, Integer> spakborsaladIngredients = new HashMap<>();
        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Melon"), 1);
        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Cranberry"), 1);
        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Blueberry"), 1);
        spakborsaladIngredients.put(CropsRegistry.getCropsPrototype("Tomato"), 1);
        Recipe spakborsalad = new Recipe("recipe_7", FoodRegistry.getFoodPrototype("Spakbor Salad"), spakborsaladIngredients, null, false);
        registerRecipe(spakborsalad);

        /* BELI DI STORE */

        //Fish n'Chips
        Map<Item, Integer> fishnchipsIngredients = new HashMap<>();
        fishnchipsIngredients.put(new IngredientPlaceholder("Any Fish"), 2);
        fishnchipsIngredients.put(CropsRegistry.getCropsPrototype("Wheat"), 1);
        fishnchipsIngredients.put(CropsRegistry.getCropsPrototype("Potato"), 1);
        Recipe fishnchips = new Recipe("recipe_1", FoodRegistry.getFoodPrototype("Fish n'Chips"), fishnchipsIngredients, new AcqPurchaseUnlockCondition("Fish n'Chips"), false);
        registerRecipe(fishnchips);

        // Fish Sandwich
        Map<Item, Integer> fishsandwichIngredients = new HashMap<>();
        fishsandwichIngredients.put(new IngredientPlaceholder("Any Fish"), 1);
        fishsandwichIngredients.put(CropsRegistry.getCropsPrototype("Wheat"), 2);
        fishnchipsIngredients.put(CropsRegistry.getCropsPrototype("Tomato"), 1);
        fishnchipsIngredients.put(CropsRegistry.getCropsPrototype("Hot Pepper"), 1);
        Recipe fishsandwich = new Recipe("recipe_10", FoodRegistry.getFoodPrototype("Fish Sandwich"), fishsandwichIngredients, new AcqPurchaseUnlockCondition("Fish Sandwich"), false);
        registerRecipe(fishsandwich);

        /* FISHING */
        
        // Sashimi
        Map<Item, Integer> sashimiIngredients = new HashMap<>();
        sashimiIngredients.put(FishRegistry.getFishPrototype("Salmon"), 3);
        Recipe sashimi = new Recipe("recipe_3", FoodRegistry.getFoodPrototype("Sashimi"), sashimiIngredients, new AcqFishUnlockCondition(), false);
        registerRecipe(sashimi);

        // Fugu
        Map<Item, Integer> fuguIngredients = new HashMap<>();
        fuguIngredients.put(FishRegistry.getFishPrototype("Pufferfish"), 1);
        Recipe fugu = new Recipe("recipe_4", FoodRegistry.getFoodPrototype("Fugu"), fuguIngredients, new AcqFishUnlockCondition("Pufferfish"), false);
        registerRecipe(fugu);

        // The Legends of Spakbor
        Map<Item, Integer> thelegendsofspakborIngredients = new HashMap<>();
        thelegendsofspakborIngredients.put(FishRegistry.getFishPrototype("Legend"), 1);
        thelegendsofspakborIngredients.put(CropsRegistry.getCropsPrototype("Potato"), 2);
        thelegendsofspakborIngredients.put(CropsRegistry.getCropsPrototype("Parsnip"), 1);
        thelegendsofspakborIngredients.put(CropsRegistry.getCropsPrototype("Tomato"), 1);
        thelegendsofspakborIngredients.put(CropsRegistry.getCropsPrototype("Eggplant"), 1);
        Recipe thelegendsofspakbor = new Recipe("recipe_11", FoodRegistry.getFoodPrototype("The Legends of Spakbor"), thelegendsofspakborIngredients, new AcqFishUnlockCondition("Legend"), false);
        registerRecipe(thelegendsofspakbor);

        /* HARVESTING */

        // Veggie Soup
        Map<Item, Integer> veggiesoupIngredients = new HashMap<>();
        veggiesoupIngredients.put(CropsRegistry.getCropsPrototype("Cauliflower"), 1);
        veggiesoupIngredients.put(CropsRegistry.getCropsPrototype("Parsnip"), 1);
        veggiesoupIngredients.put(CropsRegistry.getCropsPrototype("Potato"), 1);
        veggiesoupIngredients.put(CropsRegistry.getCropsPrototype("Tomato"), 1);
        Recipe veggiesoup = new Recipe("recipe_7", FoodRegistry.getFoodPrototype("Veggie Soup"), veggiesoupIngredients, new AcqHarvestUnlockCondition(null), false);
        registerRecipe(veggiesoup);

        // Fish Stew
        Map<Item, Integer> fishstewIngredients = new HashMap<>();
        fishstewIngredients.put(new IngredientPlaceholder("Any Fish"), 2);
        fishstewIngredients.put(CropsRegistry.getCropsPrototype("Hot Pepper"), 1);
        fishstewIngredients.put(CropsRegistry.getCropsPrototype("Cauliflower"), 2);
        Recipe fishstew = new Recipe("recipe_8", FoodRegistry.getFoodPrototype("Fish Stew"), fishstewIngredients, new AcqHarvestUnlockCondition("Hot Pepper"), false);
        registerRecipe(fishstew);
    }

    public static void registerRecipe(Recipe prototype) {
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
