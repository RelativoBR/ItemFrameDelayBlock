package com.brasilcraft.listeners;

import com.brasilcraft.BrasilCraftCustomPlugin;
import com.brasilcraft.managers.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;

public class ItemEventsListener implements Listener {

    private final BrasilCraftCustomPlugin plugin;

    public ItemEventsListener(BrasilCraftCustomPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(!Bukkit.getVersion().contains("1.8") && !Bukkit.getVersion().contains("1.9")) {
            if(!event.getAction().equals(Action.PHYSICAL) && (event.getHand() == null || !event.getHand().equals(EquipmentSlot.HAND))) {
                return;
            }
        }
        if(item == null){
            return;
        }

        if(item.getType() == Material.ITEM_FRAME || item.getType() == Material.GLOW_ITEM_FRAME){
            String eventNameItemFrame = "interactItemFrame";
            System.out.println("frame-onItemInteract");

            PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
            if(playerData == null || playerData.notHasInEventData(eventNameItemFrame)){
                System.out.println("Player [" + player.getDisplayName() + "] gerando evento de bloqueio ItemFrame 3 segundos");
                plugin.getPlayerManager().updatePlayerData(player, eventNameItemFrame);
                return;
            }

            if(LocalDateTime.now().minusSeconds(3).isAfter(playerData.getLastTime())){
                System.out.println("Player [" + player.getDisplayName() + "] removendo evento de bloqueio ItemFrame 3 segundos");
                plugin.getPlayerManager().removeEventPlayerData(player, eventNameItemFrame);
                return;
            }

            player.sendMessage("Precisa esperar 3 segundos para interagir com a moldura!");
            System.out.println("Player [" + player.getDisplayName() + "] tentou interagir com a ItemFrame muito rápido");
            event.setCancelled(true);

        }

    }

}
