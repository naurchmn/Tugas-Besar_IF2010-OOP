package com.spakborhills.gui;

import com.spakborhills.items.Item_Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.item[0] = new Item_Key();
        gp.item[0].worldX = 23 * gp.tileSize;
        gp.item[0].worldY = 7 * gp.tileSize;

        gp.item[1] = new Item_Key();
        gp.item[1].worldX = 23 * gp.tileSize;
        gp.item[1].worldY = 40 * gp.tileSize;
    }
}


