package com.brasilcraft;

import com.brasilcraft.listeners.*;
import com.brasilcraft.managers.*;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BrasilCraftCustomPlugin extends JavaPlugin {

	public static String prefix = MessagesManager.getColoredMessage("&4[&bBrasilCraftCustomPlugin&4]");

	private PlayerManager playerManager;
	private MessagesManager messagesManager;

	public void onEnable(){

		this.playerManager =  new PlayerManager();

		this.registerEvents();

        Bukkit.getConsoleSender().sendMessage(MessagesManager.getColoredMessage(prefix+" &eThanks for using my plugin!   &f~BrasilCraft"));

	}

	public void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerEventsListener(this), this);
		pm.registerEvents(new ItemEventsListener(this), this);

	}

	public void reloadEvents(){
		HandlerList.unregisterAll(this);
		registerEvents();
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public MessagesManager getMessagesManager() {
		return messagesManager;
	}

	public void setMessagesManager(MessagesManager messagesManager) {
		this.messagesManager = messagesManager;
	}

}
