package com.spakborhills.controller;

//import com.spakborhills.model.entity.NPCRegistry;
import com.spakborhills.model.entity.Player;
import com.spakborhills.model.entity.PlayerView;
import com.spakborhills.view.gui.GamePanel;
import com.spakborhills.view.gui.MainFrame;

public class PlayerController {

    private Player player;
    private PlayerView drawPlayer;

    public PlayerController(Player player, PlayerView drawPlayer) {
        this.player = player;
        this.drawPlayer = drawPlayer;
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
    public void eating(){}
    public void cooking(){}
    public void sleeping(){}
    public void watching(){}

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
