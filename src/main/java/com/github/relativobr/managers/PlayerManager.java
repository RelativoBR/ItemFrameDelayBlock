package com.github.relativobr.managers;

import com.github.relativobr.model.PlayerData;
import org.bukkit.entity.Player;
import java.time.LocalDateTime;
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

    public void addPlayerData(Player player) {

        PlayerData p = getPlayerData(player);

        if (p == null) {
            PlayerData build = PlayerData.builder()
                    .playerUUID(player.getUniqueId().toString())
                    .playerName(player.getName())
                    .time(LocalDateTime.now())
                    .build();
            playerData.putIfAbsent(build.getPlayerUUID(), build);
        } else {
            p.setTime(LocalDateTime.now());
            playerData.replace(p.getPlayerUUID(), p);
        }
    }

    public void removePlayerData(PlayerData player) {
        if (player != null) {
            playerData.remove(player.getPlayerUUID());
        }
    }

}
