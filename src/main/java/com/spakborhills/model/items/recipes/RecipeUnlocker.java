package com.spakborhills.model.items.recipes;

import com.spakborhills.controller.PlayerStats;
import com.spakborhills.model.game.Observer;
import java.util.*;

public class RecipeUnlocker implements Observer {
    private List<Recipe> allRecipes;
    private PlayerStats playerStats;

    public RecipeUnlocker(List<Recipe> allRecipes, PlayerStats playerStats){
        this.allRecipes = allRecipes;
        this.playerStats = playerStats;
    }

    @Override
    public void update(String eventType, Object data){
        for (Recipe recipe : allRecipes){
            if (!recipe.isUnlocked() && recipe.getUnlockCondition() != null){
                if (recipe.getUnlockCondition().getRequiredEventType().equals(eventType)){
                    if (recipe.getUnlockCondition().check(eventType, data, playerStats)){
                        recipe.unlock();
                    }
                }
            }
        }
    }
}
