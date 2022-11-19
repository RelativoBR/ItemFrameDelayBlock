package com.brasilcraft.listeners;

import com.brasilcraft.BrasilCraftCustomPlugin;
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

import java.time.LocalDateTime;

public class PlayerEventsListener implements Listener {

    private final BrasilCraftCustomPlugin plugin;

    public PlayerEventsListener(BrasilCraftCustomPlugin plugin) {
        this.plugin = plugin;
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

        if(block.getType() == Material.ITEM_FRAME || block.getType() == Material.GLOW_ITEM_FRAME){
            String eventNameItemFrame = "interactItemFrame";
            System.out.println("frame-onBlockInteract");

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
            String eventNameItemFrame = "interactItemFrame";
            System.out.println("frame-onEntityInteract");
            ItemFrame itemFrame = (ItemFrame) entity;
            ItemStack item = itemFrame.getItem();
            Material air = Material.AIR;

            if(item.getType() != air){

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

                String itemName = item.getItemMeta() != null && item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().toString();
                player.sendMessage("Precisa esperar 3 segundos para incluir outro item ["+itemName+"] na moldura!");
                System.out.println("Player [" + player.getDisplayName() + "] usou uma ItemFrame muito rápido [" + itemName + "]");
                event.setCancelled(true);

            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(block.getType() == Material.ITEM_FRAME || block.getType() == Material.GLOW_ITEM_FRAME){
            String eventNameItemFrame = "interactItemFrame";
            System.out.println("frame-onBlockPlace");

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

            player.sendMessage("Precisa esperar 3 segundos para colocar a moldura!");
            System.out.println("Player [" + player.getDisplayName() + "] tentou colocar ItemFrame muito rápido");
            event.setCancelled(true);

        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(block.getType() == Material.ITEM_FRAME || block.getType() == Material.GLOW_ITEM_FRAME){
            String eventNameItemFrame = "interactItemFrame";
            System.out.println("frame-onBlockBreak");

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

                player.sendMessage("Precisa esperar 3 segundos para quebrar a moldura!");
                System.out.println("Player [" + player.getDisplayName() + "] tentou quebrar ItemFrame muito rápido");
                event.setCancelled(true);

        }

    }

}
