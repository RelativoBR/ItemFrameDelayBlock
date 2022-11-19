package com.github.relativobr.listeners;

import com.github.relativobr.ItemDelayBlock;
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
    /*
    //todo BUG
    [14:30:59 INFO]: [ItemDelayBlock] interactEvent-ItemFrame->onHangingBreakByEntityEvent
    [14:30:59 INFO]: [ItemDelayBlock] Player [RelativoBR] new event interactEvent-ItemFrame(notHasInEventData)
    [14:30:59 INFO]: [ItemDelayBlock] interactEvent-ItemFrame->onEntityDamageByEntityEvent
    [14:30:59 INFO]: [ItemDelayBlock] Player [RelativoBR] new event interactEvent-ItemFrame(notHasInEventData)
    [14:30:59 INFO]: [ItemDelayBlock] interactEvent-ItemFrame->onHangingBreakByEntityEvent
    [14:30:59 INFO]: [ItemDelayBlock] Player [RelativoBR] new event interactEvent-ItemFrame(notHasInEventData)
    [14:31:00 INFO]: [ItemDelayBlock] interactEvent-ItemFrame->onHangingBreakByEntityEvent
    [14:31:00 INFO]: [ItemDelayBlock] Player [RelativoBR] new event interactEvent-ItemFrame(notHasInEventData)
    [14:31:00 INFO]: [ItemDelayBlock] interactEvent-ItemFrame->onHangingBreakByEntityEvent
    [14:31:00 INFO]: [ItemDelayBlock] Player [RelativoBR] new event interactEvent-ItemFrame(notHasInEventData)
    [14:31:00 INFO]: [ItemDelayBlock] interactEvent-ItemFrame->onHangingBreakByEntityEvent
    [14:31:00 INFO]: [ItemDelayBlock] Player [RelativoBR] new event interactEvent-ItemFrame(notHasInEventData)
     */
    private static final String INTERACT_EVENT_ITEM_FRAME = "interactEvent-ItemFrame";

    public EventsListener(ItemDelayBlock plugin) {
        this.plugin = plugin;
    }

    private final ItemDelayBlock plugin;


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (plugin.getDelayItemFrame() > 0 && (entity.getType().equals(EntityType.ITEM_FRAME)
                || entity.getType().equals(EntityType.GLOW_ITEM_FRAME))) {

            this.plugin.log(Level.INFO, INTERACT_EVENT_ITEM_FRAME + "->onPlayerInteractEntityEvent");

            if (checkDelayEventType(player, INTERACT_EVENT_ITEM_FRAME)) {
                event.setCancelled(true);
                this.sendMessageInfoEvent(player, this.plugin.getInfoItemFrame());
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

        if (plugin.getDelayItemFrame() > 0 && (entity.getType().equals(EntityType.ITEM_FRAME)
                || entity.getType().equals(EntityType.GLOW_ITEM_FRAME))) {

            this.plugin.log(Level.INFO, INTERACT_EVENT_ITEM_FRAME + "->onHangingBreakByEntityEvent");

            if (checkDelayEventType(player, INTERACT_EVENT_ITEM_FRAME)) {
                event.setCancelled(true);
                this.sendMessageInfoEvent(player, this.plugin.getInfoItemFrame());
            }

        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

        Entity entity = event.getEntity();

        if (plugin.getDelayItemFrame() > 0 && (entity.getType().equals(EntityType.ITEM_FRAME)
                || entity.getType().equals(EntityType.GLOW_ITEM_FRAME))) {

            this.plugin.log(Level.INFO, INTERACT_EVENT_ITEM_FRAME + "->onEntityDamageByEntityEvent");

            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getDamager();
                if (checkDelayEventType(player, INTERACT_EVENT_ITEM_FRAME)) {
                    event.setCancelled(true);
                    this.sendMessageInfoEvent(player, this.plugin.getInfoItemFrame());
                }
            }
            if (event.getDamager() instanceof Projectile && (((Projectile) event.getDamager()).getShooter() instanceof Player)) {
                Player player = (Player) ((Projectile) event.getDamager()).getShooter();
                if (checkDelayEventType(player, INTERACT_EVENT_ITEM_FRAME)) {
                    event.getDamager().remove();
                    event.setCancelled(true);
                    this.sendMessageInfoEvent(player, this.plugin.getInfoItemFrame());
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

        if (plugin.getDelayItemFrame() > 0 && (entity.getType().equals(EntityType.ITEM_FRAME)
                || entity.getType().equals(EntityType.GLOW_ITEM_FRAME))) {

            this.plugin.log(Level.INFO, INTERACT_EVENT_ITEM_FRAME + "->onHangingPlaceEvent");

            if (checkDelayEventType(player, INTERACT_EVENT_ITEM_FRAME)) {
                event.setCancelled(true);
                this.sendMessageInfoEvent(player, this.plugin.getInfoItemFrame());
            }
        }

    }

    private boolean checkDelayEventType(Player player, String typeEvent) {

        PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);

        if (playerData == null) {
            this.plugin.log(Level.INFO, "Player [" + player.getDisplayName() + "] new event " + typeEvent + "(playerData null)");
            plugin.getPlayerManager().addPlayerData(player, typeEvent);
            return false;
        }

        if (playerData.notHasInEventData(typeEvent)) {
            this.plugin.log(Level.INFO, "Player [" + player.getDisplayName() + "] new event " + typeEvent + "(notHasInEventData)");
            plugin.getPlayerManager().addPlayerData(player, typeEvent);
            return false;
        }

        if (LocalDateTime.now().minusSeconds(plugin.getDelayItemFrame()).isAfter(playerData.getTimeFromEventData(typeEvent))) {
            this.plugin.log(Level.INFO, "Player [" + player.getDisplayName() + "] remove old event " + typeEvent + "(time " + playerData.getTimeFromEventData(typeEvent) + ")");
            plugin.getPlayerManager().removeEventPlayerData(player, typeEvent);
            return false;
        }

        this.plugin.log(Level.INFO, "Player [" + player.getDisplayName() + "] block event " + typeEvent);
        return true;
    }

    private void sendMessageInfoEvent(Player player, String infoEvent) {
        player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + infoEvent);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 1);
    }

}
