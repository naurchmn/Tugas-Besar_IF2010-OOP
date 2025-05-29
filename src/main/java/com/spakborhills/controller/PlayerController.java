package com.spakborhills.controller;

import java.util.Scanner;

//import com.spakborhills.model.entity.NPCRegistry;
import com.spakborhills.model.entity.Player;
import com.spakborhills.model.entity.PlayerView;
import com.spakborhills.model.entity.RelationshipStatus;
import com.spakborhills.model.entity.npc.NPC;
import com.spakborhills.model.game.GameTime;
import com.spakborhills.model.items.Item;
import com.spakborhills.model.items.behavior.Edible;
import com.spakborhills.model.items.behavior.Usable;
import com.spakborhills.model.items.crops.Crops;
import com.spakborhills.model.items.fish.Fish;
import com.spakborhills.model.items.foods.Food;
import com.spakborhills.model.items.seeds.Seed;
import com.spakborhills.view.gui.GamePanel;
import com.spakborhills.view.gui.MainFrame;

public class PlayerController {

    private Player player;
    private PlayerView drawPlayer;
    private static final Scanner sc = new Scanner(System.in);
    GameTime gameTime = GameTime.getInstance();

    public PlayerController(Player player, PlayerView drawPlayer) {
        this.player = player;
        this.drawPlayer = drawPlayer;
    }

    // General Action
    public void chooseItem(){
        String itemUse = sc.nextLine();
        
        if (itemUse.equals("back")){
            return;
        }

        Item foundItem = null;
        for (Item item : player.getInventory().getPlayerInventory().keySet()){
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
    public void eating(Edible food){
        int energy = 0;
        if (food instanceof Food){
            energy = ((Food)food).getEnergy();
        }
        else if (food instanceof Crops){
            energy = 3;
        }
        else if (food instanceof Fish){
            energy = 1;
        }
        player.setEnergy(energy);
        gameTime.advanceGameTime(5);
    }
    public void cooking(String recipeName) { 
        if (!player.isInHouse()) { 
            System.out.println("You can only cook inside your house!");
            return;
        }

       
        Recipe recipe = RecipeRegistry.getRecipePrototype(recipeName);
        if (recipe == null) {
            System.out.println("Recipe '" + recipeName + "' not found.");
            return;
        }

        
        if (!recipe.isUnlocked()) {
            System.out.println("Recipe '" + recipe.getRecipeName() + "' is not yet unlocked. Unlock condition: " + recipe.getUnlockCondition().getUnlockDescription());
            return;
        }

        
        final int ENERGY_COST_COOKING = 10;
        if (player.getEnergy() < ENERGY_COST_COOKING) { // Assuming player.getEnergy() exists
            System.out.println("Not enough energy to start cooking! (-" + ENERGY_COST_COOKING + " energy required)");
            return;
        }

        
        if (!player.getInventory().hasIngredients(recipe.getIngredients())) {
            System.out.println("Missing ingredients for " + recipe.getRecipeName() + "!");
            // The hasIngredients method already prints which ingredient is missing.
            return;
        }

        
        Item fuelItem = null;
        
        for (Item item : AllGameItems.getAllGameItems()) { 
            if (item.getName().equals("Fuel")) {
                fuelItem = item;
                break;
            }
        }
        if (fuelItem == null) {
            
            fuelItem = new Item("Fuel", 1, 1); 
        }

        
        if (!player.getInventory().hasItem(fuelItem, recipe.getFuelRequired())) { // Assuming Recipe class also has getFuelRequired()
            System.out.println("Not enough Fuel to cook " + recipe.getRecipeName() + "! " + recipe.getFuelRequired() + " Fuel required.");
            return;
        }

        
        player.setEnergy(player.getEnergy() - ENERGY_COST_COOKING);
        System.out.println("Started cooking " + recipe.getRecipeName() + "! (-" + ENERGY_COST_COOKING + " Energy)");

        
        player.getInventory().removeIngredients(recipe.getIngredients());
        player.getInventory().removeItem(fuelItem, recipe.getFuelRequired());

        
        System.out.println("Cooking will be completed in 1 hour (passive action).");
        gameTime.advanceGameTime(60); 

        
        player.getInventory().add(recipe.getProduct(), 1); 
        System.out.println("Finished cooking " + recipe.getRecipeName() + ". " + recipe.getProduct().getName() + " added to inventory!");
    }


    public void sleeping(int energyLeft, int sleepHour, int sleepMinute){
        if (energyLeft < 0.1 * player.getMaxEnergy()){
            player.setEnergy(player.getMaxEnergy() / 2);
            System.out.println("Anda terbangun dalam keadaan lelah");
        }
        else if (energyLeft == 0){
            player.setEnergy(10);
            System.out.println("Anda terbangun tanpa tenaga");
        }
        else {
            player.setEnergy(player.getMaxEnergy());
            System.out.println("SEMANGAT PAGI! PAGI PAGI PAGI LUAR BIASA!");
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
        gameTime.advanceGameTime(15);
        player.setEnergy(player.getEnergy() - 5);
        System.out.println("Today's weather is : " + gameTime.getWeather());
    }

    // WORLD ACTION
    public void fishing(){

    }
    public void proposing(NPC npc){
        gameTime.advanceGameTime(60);
        if (npc.getHeartPoints() == NPC.getMaxHeartPoints()){
            System.out.println("Aku mau mas");
            npc.setRelationshipStatus(RelationshipStatus.FIANCE);
            player.setEnergy(player.getEnergy() - 10);
        }
        else{
            System.out.println("Maaf aku belum siap");
            player.setEnergy(player.getEnergy() - 20);
        }
    }
    public void marrying(NPC npc){
    }
    public void visiting(){
        System.out.println("Do you want to do visiting?");
    }
    public void gifting(NPC npc, Item item){ //PLayerController.gifting(NPCRegistry.getNPCPrototype("Dasco")
        int heartPoints;
        if (npc.getLovedItems().contains(item)){
            heartPoints = 25;
            System.out.println("Ih makasih banget lohh aku suka deh!");
        }
        else if (npc.getLikedItems().contains(item)){
            heartPoints = 20;
            System.out.println("Wow terima kasih!");
        }
        else if (npc.getHatedItems().contains(item)){
            heartPoints = -25;
            System.out.println("Maksud lu apa?!");
        }
        else {
            heartPoints = 0;
            System.out.println("Terima kasih ya");
        }
        npc.setHeartPoints(npc.getHeartPoints() + heartPoints);
        gameTime.advanceGameTime(10);
        player.setEnergy(player.getEnergy() - 5);
    }
    public void selling(){}

    // getter setter
    public PlayerView getPlayerView() {
        return drawPlayer;
    }

}
