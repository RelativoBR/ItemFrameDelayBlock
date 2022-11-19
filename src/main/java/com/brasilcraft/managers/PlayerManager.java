package com.brasilcraft.managers;

import com.brasilcraft.BrasilCraftCustomPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerManager {

    private BrasilCraftCustomPlugin plugin;
    private ArrayList<PlayerData> playerData;
    public PlayerManager(BrasilCraftCustomPlugin plugin){
        this.plugin = plugin;
        this.playerData = new ArrayList<PlayerData>();
    }

    public ArrayList<PlayerData> getPlayerData() {
        return playerData;
    }

    public void setPlayerData(ArrayList<PlayerData> playerData) {
        this.playerData = playerData;
    }

    public PlayerData getPlayerData(Player player){
        for(PlayerData p : playerData){
            if(p.getPlayerUUID().equals(player.getUniqueId().toString())){
                return p;
            }
        }
        return null;
    }

    public PlayerData getPlayerDataByName(String playerName){
        for(PlayerData p : playerData){
            if(p.getPlayerName().equals(playerName)){
                return p;
            }
        }
        return null;
    }

    private PlayerData createPlayerData(Player player){
        PlayerData p = getPlayerData(player);
        if(p == null){
            p = new PlayerData(player.getUniqueId().toString(),player.getName());
            playerData.add(p);
        }
        return p;
    }

}
