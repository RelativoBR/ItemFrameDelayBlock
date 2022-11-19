package com.github.relativobr.listeners;

import com.github.relativobr.ItemDelayBlock;
import com.github.relativobr.managers.PlayerManager;
import com.github.relativobr.model.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import java.time.LocalDateTime;
import java.util.logging.Level;

public class EventsListener implements Listener {

    private static final String INTERACT_EVENT_ITEM_FRAME = "interactEvent-ItemFrame";

    public EventsListener(ItemDelayBlock plugin) {
        this.plugin = plugin;
        this.playerManager = plugin.getPlayerManager();
    }

    private final PlayerManager playerManager;
    private final ItemDelayBlock plugin;


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if(plugin.getDelayItemFrame() > 0) {
            if (entity.getType().equals(EntityType.ITEM_FRAME)
                    || entity.getType().equals(EntityType.GLOW_ITEM_FRAME)) {

                this.plugin.log(Level.INFO, "ItemFrame: onPlayerInteractEntityEvent");

                if (logBlockEventItemFrame(player)) {
                    event.setCancelled(true);
                    this.sendMessageAndLogItemFrame(player);
                }

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

        if(plugin.getDelayItemFrame() > 0) {
            if (entity.getType().equals(EntityType.ITEM_FRAME)
                    || entity.getType().equals(EntityType.GLOW_ITEM_FRAME)) {

                this.plugin.log(Level.INFO, "ItemFrame: onHangingBreakByEntityEvent");

                if (logBlockEventItemFrame(player)) {
                    event.setCancelled(true);
                    this.sendMessageAndLogItemFrame(player);
                }

            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntityEvent (EntityDamageByEntityEvent event) {

        Entity entity = event.getEntity();

        if(plugin.getDelayItemFrame() > 0) {
            if (entity.getType().equals(EntityType.ITEM_FRAME)
                    || entity.getType().equals(EntityType.GLOW_ITEM_FRAME)) {

                this.plugin.log(Level.INFO, "ItemFrame: onEntityDamageByEntityEvent");

                if (event.getDamager() instanceof Player) {
                    Player player = (Player) event.getDamager();
                    if (!logBlockEventItemFrame(player)) {
                        event.setCancelled(true);
                        this.sendMessageAndLogItemFrame(player);
                    }
                }
                if (event.getDamager() instanceof Projectile) {
                    if (((Projectile) event.getDamager()).getShooter() instanceof Player) {
                        Player player = (Player) ((Projectile) event.getDamager()).getShooter();
                        if (!logBlockEventItemFrame(player)) {
                            event.getDamager().remove();
                            event.setCancelled(true);
                            this.sendMessageAndLogItemFrame(player);
                        }
                    }
                }
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHangingPlaceEvent(HangingPlaceEvent event) {

        Player player = event.getPlayer();
        Entity entity = event.getEntity();

        if(player == null){
            return;
        }

        if(plugin.getDelayItemFrame() > 0) {
            if (entity.getType().equals(EntityType.ITEM_FRAME)
                    || entity.getType().equals(EntityType.GLOW_ITEM_FRAME)) {

                this.plugin.log(Level.INFO, "ItemFrame: onHangingPlaceEvent");

                if (logBlockEventItemFrame(player)) {
                    event.setCancelled(true);
                    this.sendMessageAndLogItemFrame(player);
                }
            }
        }

    }

    private boolean logBlockEventItemFrame(Player player) {

        PlayerData playerData = this.playerManager.getPlayerData(player);

        if (playerData == null || playerData.notHasInEventData(INTERACT_EVENT_ITEM_FRAME)) {
            this.plugin.log(Level.INFO, "Player [" + player.getDisplayName() + "] new event ItemFrame");
            this.playerManager.addPlayerData(player, INTERACT_EVENT_ITEM_FRAME);
            return false;
        }

        if (LocalDateTime.now().minusSeconds(plugin.getDelayItemFrame()).isAfter(playerData.getTimeFromEventData(INTERACT_EVENT_ITEM_FRAME))) {
            this.plugin.log(Level.INFO, "Player [" + player.getDisplayName() + "] remove old event ItemFrame");
            this.playerManager.removeEventPlayerData(player, INTERACT_EVENT_ITEM_FRAME);
            return false;
        }

        this.plugin.log(Level.INFO, "Player [" + player.getDisplayName() + "] block event ItemFrame");
        return true;
    }

    private void sendMessageAndLogItemFrame(Player player) {
        player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + this.plugin.getInfoItemFrame());
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 1);
    }

}
