package com.github.relativobr;

import com.github.relativobr.configuration.Config;
import com.github.relativobr.listeners.EventsListener;
import com.github.relativobr.managers.PlayerManager;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;

public class ItemFrameDelayBlock extends JavaPlugin {

	@Getter
	private PlayerManager playerManager;
	@Getter
	private int delayTime;
	@Getter
	private String notificationMessage;
	@Getter
	private boolean debugMode;

	public void onEnable(){

		this.debugMode = true;
		this.log(Level.INFO, "########################################");
		this.log(Level.INFO, "  ItemFrameDelayBlock - By RelativoBR  ");
		this.log(Level.INFO, "########################################");

		this.playerManager =  new PlayerManager();

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EventsListener(this), this);

		Config cfg = new Config(this);

		this.delayTime = cfg.getInt("delay-time");
		this.notificationMessage = cfg.getString("notification-message");
		this.debugMode = cfg.getBoolean("debug-mode");

		if (isDebugMode()) {
			this.log(Level.INFO, "Show Log: enable");
		} else {
			this.log(Level.INFO, "Show Log: disable");
		}

	}

	public final void log(Level level, String messages) {
		if(!isDebugMode() && level == Level.INFO){
			return;
		}
		this.getLogger().log(level, messages);
	}

}
