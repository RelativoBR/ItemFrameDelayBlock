package com.brasilcraft.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ItemEventsListener implements Listener {

    public ItemEventsListener() {
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

    }

}
