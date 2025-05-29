package com.spakborhills.controller;

import java.util.Scanner;

//import com.spakborhills.model.entity.NPCRegistry;
import com.spakborhills.model.entity.Player;
import com.spakborhills.model.entity.PlayerView;
import com.spakborhills.model.game.GameTime;
import com.spakborhills.model.items.Item;
import com.spakborhills.model.items.behavior.Edible;
import com.spakborhills.model.items.behavior.Usable;
import com.spakborhills.model.items.foods.Food;
import com.spakborhills.model.items.seeds.Seed;
import com.spakborhills.view.gui.GamePanel;
import com.spakborhills.view.gui.MainFrame;

public class PlayerController {

    private Player player;
    private PlayerView drawPlayer;

    public PlayerController(Player player, PlayerView drawPlayer) {
        this.player = player;
        this.drawPlayer = drawPlayer;
    }

    // General Action
    public void chooseItem(){
        Scanner sc = new Scanner(System.in);
        String itemUse = sc.nextLine();
        
        if (itemUse.equals("back")){
            return;
        }

        Item foundItem = null;
        for (Item item : player.getInventory().keySet()){
            if (item.getName().equals(itemUse)){
                foundItem = item;
                break;
            }
        }
        if (foundItem == null) {
            System.out.println("Item not found!");
        }
        else{
            player.setItemHeld((foundItem));
            System.out.println("Item held: " + foundItem.getName());
        }
    }

    // FARM ACTION
    public void tilling(){
        System.out.println("Do you want to do tilling?");
    }
    public void recoverLand(){}
    public void planting(){}
    public void watering(){}
    public void harvesting(){}

    // AT HOUSE ACTION
    public void eating(Item food){}
    public void cooking(){}
    public void sleeping(int energyLeft, int sleepHour, int sleepMinute){
        GameTime gameTime = GameTime.getInstance();
        if (energyLeft < 0.1 * player.getMaxEnergy()){
            player.setEnergy(player.getEnergy() / 2);
        }
        else if (energyLeft == 0){
            player.setEnergy(10);
        }
        else {
            player.setEnergy(player.getMaxEnergy());
        }
        if ((sleepHour > -1 && sleepHour < 3)){
            int hourTo2 = 5 - sleepHour;
            int minuteTo2 = (60 - sleepMinute) + hourTo2 * 60;
            gameTime.advanceGameTime(minuteTo2);
        }
        else{
            int hourTo2 = 23 - sleepHour;
            int minuteTo2 = (60 - sleepMinute) + hourTo2 * 60;
            gameTime.startNewDay(minuteTo2);
        }
    }
    public void watching(){
        GameTime gameTime = GameTime.getInstance();
        System.out.println("Today's weather is : " + gameTime.getWeather());
    }

    // WORLD ACTION
    public void fishing(){}
//    public void proposing(NPCRegistry npc){}
    public void marrying(){}
    public void visiting(){
        System.out.println("Do you want to do visiting?");
    }
    public void gifting(){}
    public void selling(){}

    // getter setter
    public PlayerView getPlayerView() {
        return drawPlayer;
    }

}
