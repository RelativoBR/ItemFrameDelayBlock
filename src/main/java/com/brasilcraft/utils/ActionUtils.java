package com.brasilcraft.utils;

import com.brasilcraft.BrasilCraftCustomPlugin;
import com.brasilcraft.managers.MessagesManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ActionUtils {

    public static void message(Player player,String actionLine){
        player.sendMessage(MessagesManager.getColoredMessage(actionLine));
    }

    public static void consoleMessage(String actionLine){
        Bukkit.getConsoleSender().sendMessage(MessagesManager.getColoredMessage(actionLine));
    }

    public static void jsonMessage(Player player,String actionLine){
        BaseComponent[] base = ComponentSerializer.parse(actionLine);
        player.spigot().sendMessage(base);
    }

    public static void consoleCommand(String actionLine){
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        Bukkit.dispatchCommand(sender, actionLine);
    }

    public static void playerCommand(Player player,String actionLine){
        player.performCommand(actionLine);
    }

    public static void playerCommandAsOp(Player player,String actionLine){
        boolean isOp = player.isOp();
        player.setOp(true);
        player.performCommand(actionLine);
        if(!isOp) {
            player.setOp(false);
        }
    }

    public static void playerSendChat(Player player,String actionLine){
        player.chat(MessagesManager.getColoredMessage(actionLine));
    }

    public static void teleport(Player player, String actionLine, Event minecraftEvent){
        String[] sep = actionLine.split(";");
        World world = Bukkit.getWorld(sep[0]);
        double x = Double.valueOf(sep[1]);
        double y = Double.valueOf(sep[2]);
        double z = Double.valueOf(sep[3]);
        float yaw = Float.valueOf(sep[4]);
        float pitch = Float.valueOf(sep[5]);
        Location l = new Location(world,x,y,z,yaw,pitch);

        if(minecraftEvent instanceof PlayerRespawnEvent) {
            PlayerRespawnEvent respawnEvent = (PlayerRespawnEvent) minecraftEvent;
            respawnEvent.setRespawnLocation(l);
        }else {
            player.teleport(l);
        }
    }

    public static void givePotionEffect(Player player,String actionLine){
        String[] sep = actionLine.split(";");
        PotionEffectType potionEffectType = PotionEffectType.getByName(sep[0]);
        int duration = Integer.valueOf(sep[1]);
        int level = Integer.valueOf(sep[2])-1;
        boolean showParticles = true;
        if(sep.length >= 4) {
            showParticles = Boolean.valueOf(sep[3]);
        }
        PotionEffect effect = new PotionEffect(potionEffectType,duration,level,false,showParticles);
        player.addPotionEffect(effect);
    }

    public static void removePotionEffect(Player player,String actionLine){
        PotionEffectType potionEffectType = PotionEffectType.getByName(actionLine);
        player.removePotionEffect(potionEffectType);
    }

    public static void cancelEvent(Event minecraftEvent){
        if(minecraftEvent != null && minecraftEvent instanceof Cancellable) {
            Cancellable cancellableEvent = (Cancellable) minecraftEvent;
            cancellableEvent.setCancelled(true);
        }
    }

    public static void kick(Player player,String actionLine){
        player.kickPlayer(MessagesManager.getColoredMessage(actionLine));
    }

    public static void playSound(Player player,String actionLine){
        String[] sep = actionLine.split(";");
        Sound sound = null;
        int volume = 0;
        float pitch = 0;
        try {
            sound = Sound.valueOf(sep[0]);
            volume = Integer.valueOf(sep[1]);
            pitch = Float.valueOf(sep[2]);
        }catch(Exception e ) {
            Bukkit.getConsoleSender().sendMessage(BrasilCraftCustomPlugin.prefix+
                    MessagesManager.getColoredMessage(" &7Sound Name: &c"+sep[0]+" &7is not valid. Change it in the config!"));
            return;
        }

        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void playSoundResourcePack(Player player,String actionLine){
        String[] sep = actionLine.split(";");
        String sound = sep[0];
        int volume = Integer.valueOf(sep[1]);
        float pitch = Float.valueOf(sep[2]);
        player.playSound(player.getLocation(), sound, volume, pitch);
    }


    public static void keepItems(String actionLine,Event minecraftEvent){
        if(minecraftEvent instanceof PlayerDeathEvent) {
            PlayerDeathEvent deathEvent = (PlayerDeathEvent) minecraftEvent;
            if(actionLine.equals("items") || actionLine.equals("all")) {
                deathEvent.setKeepInventory(true);
                deathEvent.getDrops().clear();
            }
            if(actionLine.equals("xp") || actionLine.equals("all")) {
                deathEvent.setKeepLevel(true);
                deathEvent.setDroppedExp(0);
            }
        }
    }

}
