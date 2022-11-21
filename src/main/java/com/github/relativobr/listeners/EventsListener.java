package com.github.relativobr.listeners;

import com.github.relativobr.ItemFrameDelayBlock;
import com.github.relativobr.model.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;

import java.time.LocalDateTime;
import java.util.logging.Level;

public class EventsListener implements Listener {
    public EventsListener(ItemFrameDelayBlock plugin) {
        this.plugin = plugin;
    }

    private final ItemFrameDelayBlock plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (plugin.getDelayTime() > 0 && (entity.getType().equals(EntityType.ITEM_FRAME)
                || entity.getType().equals(EntityType.GLOW_ITEM_FRAME))) {

            this.plugin.log(Level.INFO, "onPlayerInteractEntityEvent");

            if (checkDelayEventType(player)) {
                event.setCancelled(true);
                this.sendMessageInfoEvent(player, this.plugin.getNotificationMessage());
            }

        }

    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent event) {

        if (event.getRemover() == null || !(event.getRemover() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getRemover();
        Entity entity = event.getEntity();

        if (plugin.getDelayTime() > 0 && (entity.getType().equals(EntityType.ITEM_FRAME)
                || entity.getType().equals(EntityType.GLOW_ITEM_FRAME))) {

            this.plugin.log(Level.INFO, "onHangingBreakByEntityEvent");

            if (checkDelayEventType(player)) {
                event.setCancelled(true);
                this.sendMessageInfoEvent(player, this.plugin.getNotificationMessage());
            }

        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

        Entity entity = event.getEntity();

        if (plugin.getDelayTime() > 0 && (entity.getType().equals(EntityType.ITEM_FRAME)
                || entity.getType().equals(EntityType.GLOW_ITEM_FRAME))) {

            this.plugin.log(Level.INFO, "onEntityDamageByEntityEvent");

            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                if (checkDelayEventType(player)) {
                    event.setCancelled(true);
                    this.sendMessageInfoEvent(player, this.plugin.getNotificationMessage());
                }
            }
            if (event.getDamager() instanceof Projectile && (((Projectile) event.getDamager()).getShooter() instanceof Player)) {
                Player player = (Player) ((Projectile) event.getDamager()).getShooter();
                if (checkDelayEventType(player)) {
                    event.getDamager().remove();
                    event.setCancelled(true);
                    this.sendMessageInfoEvent(player, this.plugin.getNotificationMessage());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHangingPlaceEvent(HangingPlaceEvent event) {

        Player player = event.getPlayer();
        Entity entity = event.getEntity();

        if (player == null) {
            return;
        }

        if (plugin.getDelayTime() > 0 && (entity.getType().equals(EntityType.ITEM_FRAME)
                || entity.getType().equals(EntityType.GLOW_ITEM_FRAME))) {

            this.plugin.log(Level.INFO, "onHangingPlaceEvent");

            if (checkDelayEventType(player)) {
                event.setCancelled(true);
                this.sendMessageInfoEvent(player, this.plugin.getNotificationMessage());
            }
        }

    }

    private boolean checkDelayEventType(Player player) {

        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);

        if (playerData == null) {
            this.plugin.log(Level.INFO, "Player [" + player.getDisplayName() + "] new event");
            plugin.getPlayerManager().addPlayerData(player);
            return false;
        }

        if (LocalDateTime.now().minusSeconds(plugin.getDelayTime()).isAfter(playerData.getTime())) {
            this.plugin.log(Level.INFO, "Player [" + player.getDisplayName() + "] remove old event");
            plugin.getPlayerManager().removePlayerData(playerData);
            return false;
        }

        this.plugin.log(Level.INFO, "Player [" + player.getDisplayName() + "] block event ");
        return true;
    }

    private void sendMessageInfoEvent(Player player, String infoEvent) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',infoEvent));
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
    }

}
