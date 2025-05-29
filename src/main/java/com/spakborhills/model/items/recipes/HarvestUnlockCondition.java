package com.spakborhills.model.items.recipes;

import com.spakborhills.controller.PlayerStats;

public class HarvestUnlockCondition implements UnlockCondition {
    private final String description;

    public HarvestUnlockCondition(String description) {
        this.description = description;
    }

    @Override
    public boolean check(String eventType, Object data, PlayerStats playerStats) {
        // This condition is met if the player has harvested anything at least once.
        // Assuming PlayerStats has a method like 'hasHarvestedAnyCrop()'
        if ("HARVESTED_ANY_CROP".equals(eventType) && playerStats != null) {
            // The 'data' object here could be the harvested crop, but we just need to know *if* any harvesting occurred.
            // You'll need to ensure PlayerStats accurately tracks this.
            return playerStats.hasHarvestedAnyCrop(); // This method needs to exist in PlayerStats
        }
        return false;
    }

    @Override
    public String getUnlockDescription() {
        return description;
    }
}
