package com.spakborhills.model.items;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Item_House extends SuperItem {
    public Item_House () {
        name = "House";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Items/house.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        collision = true;
    }
}
