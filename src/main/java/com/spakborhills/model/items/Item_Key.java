package com.spakborhills.model.items;

import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Item_Key extends SuperItem {
    public Item_Key () {
        name = "Key";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/Items/hoe.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = false;
    }

}
