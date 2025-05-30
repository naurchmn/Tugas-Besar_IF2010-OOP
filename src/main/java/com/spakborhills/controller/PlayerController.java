package com.spakborhills.controller;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane; // tambahkan import di atas file

//import com.spakborhills.model.entity.NPCRegistry;
import com.spakborhills.model.entity.Player;
import com.spakborhills.model.entity.PlayerView;
import com.spakborhills.model.entity.RelationshipStatus;
import com.spakborhills.model.entity.npc.NPC;
import com.spakborhills.model.game.*;
import com.spakborhills.model.items.Item;
import com.spakborhills.model.items.behavior.Edible;
import com.spakborhills.model.items.behavior.Usable;
import com.spakborhills.model.items.crops.Crops;
import com.spakborhills.model.items.fish.Fish;
import com.spakborhills.model.items.fish.FishRegistry;
import com.spakborhills.model.items.foods.Food;
import com.spakborhills.model.items.seeds.Seed;
import com.spakborhills.view.gui.GamePanel;
import com.spakborhills.view.gui.MainFrame;
import java.awt.*;
import java.util.Set;
import java.util.Random;

public class PlayerController {

    private Player player;
    private PlayerView drawPlayer;
    GameTime gameTime = GameTime.getInstance();
    private GamePanel gp;
    private Random rand = new Random();

    public PlayerController(Player player, PlayerView drawPlayer, GamePanel gp) {
        this.player = player;
        this.drawPlayer = drawPlayer;
        this.gp = gp;
    }

    // General Action
    public void chooseItem(){
        String itemUse = JOptionPane.showInputDialog(null, "Masukkan nama item yang ingin dipilih atau back untuk kembali:", "Pilih Item", JOptionPane.QUESTION_MESSAGE);

        if (itemUse == null || itemUse.equalsIgnoreCase("back")) {
            return; // User menekan Cancel atau mengetik "back"
        }

        Item foundItem = null;
        for (Item item : player.getInventory().getPlayerInventory().keySet()){
            if (item.getName().equalsIgnoreCase(itemUse)){
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

    public boolean rightTool(String toolName){
        if(player.getItemHeld() == null){
            return false;
        }
        else return player.getItemHeld().getName().equals(toolName);
    }

    // FARM ACTION
    public void tilling(){
        if (gp.getCurrentMap().equals("farm")) {
            String currentTileType = drawPlayer.getCurrentTileType(); // Mengambil tile yang sedang diinjak player
            int playerTileCol = (drawPlayer.getWorldX() + gp.getTileSize() / 2) / gp.getTileSize();
            int playerTileRow = (drawPlayer.getWorldY() + gp.getTileSize() / 2) / gp.getTileSize();

            if (currentTileType.equals("000.png")) { // Tilling dilakukan hanya jika player berada di atas tile 000.png
                // Tile diubah menjadi 004.png setelah tilling
                gp.tileM.changeTile(playerTileCol, playerTileRow, "004.png");
            } else {
                System.out.println("You cannot do tilling here"); // Jika tidak berada di atas tile 000.png maka olayer tidak dapat melakukan tilling
            }
        }
    }

    public void recoverLand(){
        String currentTileType = drawPlayer.getCurrentTileType(); // Mengambil tile yang sedang diinjak oleh player
        int playerTileCol = (drawPlayer.getWorldX() + gp.getTileSize() / 2) / gp.getTileSize();
        int playerTileRow = (drawPlayer.getWorldY() + gp.getTileSize() / 2) / gp.getTileSize();

        gp.tileM.changeTile(playerTileCol, playerTileRow, "000.png");
    }

    public void planting(){
        String currentTileType = drawPlayer.getCurrentTileType(); // Mengambil tile yang sedang diinjak oleh player
        int playerTileCol = (drawPlayer.getWorldX() + gp.getTileSize() / 2) / gp.getTileSize();
        int playerTileRow = (drawPlayer.getWorldY() + gp.getTileSize() / 2) / gp.getTileSize();

        if (currentTileType.equals("004.png")) { // Kalau sekarang tilenya 004.png
            gp.tileM.changeTile(playerTileCol, playerTileRow, "148.png");
        } else if (currentTileType.equals("147.png")) { // Kalau sekarang tilenya 147.png
            gp.tileM.changeTile(playerTileCol, playerTileRow, "146.png");
        } else {
            System.out.println("Cannot till this tile: " + currentTileType);
        }
    }

    public void watering(){
        String currentTileType = drawPlayer.getCurrentTileType(); // Mengambil tile yang sedang diinjak oleh player
        int playerTileCol = (drawPlayer.getWorldX() + gp.getTileSize() / 2) / gp.getTileSize();
        int playerTileRow = (drawPlayer.getWorldY() + gp.getTileSize() / 2) / gp.getTileSize();

        if (currentTileType.equals("004.png")) {
            gp.tileM.changeTile(playerTileCol, playerTileRow, "147.png");
        } else if (currentTileType.equals("148.png")) {
            gp.tileM.changeTile(playerTileCol, playerTileRow, "146.png");
        }
    }

    public void harvesting(){
        String currentTileType = drawPlayer.getCurrentTileType(); // Mengambil tile yang sedang diinjak oleh player
        int playerTileCol = (drawPlayer.getWorldX() + gp.getTileSize() / 2) / gp.getTileSize();
        int playerTileRow = (drawPlayer.getWorldY() + gp.getTileSize() / 2) / gp.getTileSize();

        this.recoverLand();
        gp.showTemporaryPopUp("/assets/PopUps/HarvestPopUp.png", 2500, 200);
    }

    // AT HOUSE ACTION
    public void visiting(){
        System.out.println("Do you want to do visiting?");
    }
    public void getInTheHouse(){
        System.out.println("You get in the house");
    }
    public void getOutTheHouse(){
        System.out.println("You get out the house");
        if (gp.getCurrentMap().equals("house default")){
            gp.setCurrentMap("farm");
            drawPlayer.setDefaultValues(118, 120);
        } else {
            gp.returnToPreviousMap();
        }
    }
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
    public void cooking(){

    }
    public void sleeping(int energyLeft, int sleepHour, int sleepMinute){
        gp.showTemporaryPopUp("/assets/PopUps/SleepPopUp.png", 2500, 0);
        gp.setCurrentMap("house default");
        drawPlayer.setDefaultValues(117, 119);

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
        if (gameTime.getWeather() == Weather.SUNNY) {
            gp.showTemporaryPopUp("/assets/PopUps/SunnyPopUp.png", 2500, 0);
        } else if (gameTime.getWeather() == Weather.RAINY) {
            gp.showTemporaryPopUp("/assets/PopUps/RainyPopUp.png", 2500, 0);
        }

    }

    // WORLD ACTION
    public void fishing(){
        System.out.println("You are fishing..."); // Tetap di konsol untuk debug

        Season season = gameTime.getSeason();
        int fishingHour = gameTime.getInGameHours();
        Weather weather = gameTime.getWeather();
        int playerRow = drawPlayer.worldY / gp.getTileSize();
        int playerCol = drawPlayer.worldX / gp.getTileSize();
        String fishingArea = gp.getPlayerFishingArea(playerRow, playerCol);

        if (fishingArea == null){
            System.out.println("This is not a fishing area");
            return;
        }

//        Set<String> fishes = FishRegistry.getAvailableFishNames();
        ArrayList<Fish> qualifiedFishList = new ArrayList<>();
        for (String fishName : FishRegistry.getAvailableFishNames()){
            Fish fish = FishRegistry.getFishPrototype(fishName);
            int start = fish.getStartSpawnTime();
            int end = fish.getEndSpawnTime();
            boolean timeOk;
            if (start < end) {
                timeOk = fishingHour >= start && fishingHour < end;
            } else {
                timeOk = fishingHour >= start || fishingHour < end;
            }
            if (fish.getSeason().contains(season) && fish.getWeather().contains(weather) && timeOk && fish.getLocation().contains(fishingArea)){
                qualifiedFishList.add(fish);
            }
        }

        System.out.println("Available fish : ");
        for (Fish fish : qualifiedFishList){
            System.out.print(fish.getName() + " ");
        }
        System.out.println();

        Fish caughtFish = null;
        if (!qualifiedFishList.isEmpty()) {
            caughtFish = qualifiedFishList.get(rand.nextInt(qualifiedFishList.size()));
            System.out.println("Fish on the line : " + caughtFish.getName());

            boolean success = caughtFish.fishingGame();
            if (success){
                player.getInventory().add(caughtFish.clone(), 1);
                System.out.println("You caught a " + caughtFish.getName() + "!");
            }
            else{
                System.out.println("The fish got away!");
            }
        }
        else {
            System.out.println("There are no fish here");
            return;
        }

        System.out.println("Advance time by: 15 minutes, before: " + gameTime.getInGameHours() + ":" + gameTime.getInGameMinutes());
        gameTime.advanceGameTime(15);
        System.out.println("After: " + gameTime.getInGameHours() + ":" + gameTime.getInGameMinutes());

        player.setEnergy(player.getEnergy() - 5);
    }

    // TO NPC ACTION
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
        System.out.println("You are now married to " + npc.getName());
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
