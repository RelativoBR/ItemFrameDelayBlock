package com.github.relativobr.managers;

import com.github.relativobr.model.PlayerData;
import org.bukkit.entity.Player;
import java.util.*;

public class PlayerManager {

    private static Map<String, PlayerData> playerData = new HashMap<>();;

    public PlayerManager() {
        playerData = new HashMap<>();
    }

    public PlayerData getPlayerData(Player player) {
        if(playerData.containsKey(player.getUniqueId().toString())){
            return playerData.get(player.getUniqueId().toString());
        }
        return null;
    }

    public void addPlayerData(Player player, String event) {

        PlayerData p = getPlayerData(player);

        if (p == null) {
            PlayerData build = PlayerData.builder()
                    .playerUUID(player.getUniqueId().toString())
                    .playerName(player.getName())
                    .build();
            build.addEventData(event);
            playerData.remove(build.getPlayerUUID());
            playerData.put(build.getPlayerUUID(), build);

        } else {
            this.updateEventPlayerData(player, event);
        }

    }

    public void updateEventPlayerData(Player player, String event) {

        PlayerData p = getPlayerData(player);

        if (p != null && p.hasInEventData(event)) {
            p.addEventData(event);
            playerData.replace(p.getPlayerUUID(), p);
        }

    }

    public void removeEventPlayerData(Player player, String event) {

        PlayerData p = getPlayerData(player);
        if (p != null && p.hasInEventData(event)) {
            p.removeEventData(event);
            playerData.replace(p.getPlayerUUID(), p);
        }

    }

}
