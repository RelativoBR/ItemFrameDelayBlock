package com.brasilcraft.managers;

import org.bukkit.entity.Player;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private Map<String, PlayerData> playerData;

    public PlayerManager() {
        this.playerData = new HashMap<>();
    }


    public PlayerData getPlayerData(Player player) {
        if(playerData.containsKey(player.getUniqueId().toString())){
            return playerData.get(player.getUniqueId().toString());
        }
        return null;
    }

    public void updatePlayerData(Player player, String event) {

        PlayerData p = getPlayerData(player);

        if (p == null) {
            PlayerData build = PlayerData.builder()
                    .playerUUID(player.getUniqueId().toString())
                    .playerName(player.getName())
                    .eventName(Collections.singleton(event))
                    .lastTime(LocalDateTime.now()).build();
            this.playerData.putIfAbsent(player.getUniqueId().toString(), build);

        } else if (p.notHasInEventData(event)) {
            p.addEventName(event);
            this.playerData.replace(p.getPlayerUUID(), p);
        }

    }

    public void removeEventPlayerData(Player player, String event) {

        PlayerData p = getPlayerData(player);

        if (p != null && p.hasInEventData(event)) {
            p.getEventName().remove(event);
            this.playerData.replace(p.getPlayerUUID(), p);
        }

    }

}
