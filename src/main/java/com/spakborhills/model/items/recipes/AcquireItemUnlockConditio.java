package com.spakborhills.model.items.recipes;

import com.spakborhills.controller.PlayerStats;
import com.spakborhills.model.items.Item; // Import the Item class

public class AcquireItemUnlockCondition implements UnlockCondition {
    private final String targetItemName;
    private final String description;

    public AcquireItemUnlockCondition(String targetItemName, String description) {
        this.targetItemName = targetItemName;
        this.description = description;
    }

    @Override
    public boolean check(String eventType, Object data, PlayerStats playerStats) {
        // This condition is met if the player has acquired the specific item.
        // Assuming PlayerStats has a method like 'hasAcquiredItem(String itemName)'
        if ("ITEM_ACQUIRED".equals(eventType) && playerStats != null) {
            if (data instanceof Item) {
                return ((Item) data).getName().equals(targetItemName);
            } else if (data instanceof String) { // If 'data' is passed as the item's name string
                return ((String) data).equals(targetItemName);
            }
            // Alternatively, you could directly check PlayerStats if it tracks acquired items
            // return playerStats.hasAcquiredItem(targetItemName); // This method needs to exist in PlayerStats
        }
        return false;
    }

    @Override
    public String getUnlockDescription() {
        return description;
    }
}
