package com.brasilcraft.listeners;

import com.brasilcraft.managers.PlayerManager;
import com.brasilcraft.managers.PlayerData;
import com.brasilcraft.utils.ActionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerEventsListener implements Listener {

    private PlayerManager playerManager;

    public PlayerEventsListener() {
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if(!Bukkit.getVersion().contains("1.8") && !Bukkit.getVersion().contains("1.9")) {
            if(!event.getAction().equals(Action.PHYSICAL) && (event.getHand() == null || !event.getHand().equals(EquipmentSlot.HAND))) {
                return;
            }
        }
        if(block == null){
            return;
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityInteract(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if(!Bukkit.getVersion().contains("1.8") && !event.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }
        if(entity == null){
            return;
        }

        if(entity instanceof ItemFrame){
            System.out.println("frame");
            ItemFrame itemFrame = (ItemFrame) entity;
            ItemStack item = itemFrame.getItem();
            Material air = Material.AIR;

            if(item.getType() != air){

                PlayerData playerData = playerManager.getPlayerData(player);
                if(playerData == null){
                    return;
                }

                //playerData.getEventData()

                String itemName = item.getItemMeta() != null ? item.getItemMeta().getDisplayName() : item.getType().toString();
                player.sendMessage("Precisa esperar 3 segundos para incluir outro item ["+itemName+"] na moldura!");
                System.out.println("Player [" + player.getDisplayName() + "] usou uma moldura muito rápido com item [" + itemName + "]");

                ActionUtils.cancelEvent(event);
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();


    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();


    }

}
