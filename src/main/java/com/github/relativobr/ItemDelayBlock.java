package com.github.relativobr;

import com.github.relativobr.core.Config;
import com.github.relativobr.listeners.EventsListener;
import com.github.relativobr.managers.PlayerManager;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class ItemDelayBlock extends JavaPlugin {

	@Getter
	private PlayerManager playerManager;
	@Getter
	private int delayItemFrame;
	@Getter
	private String infoItemFrame;

	private boolean showLogInfo = true;

	public void onEnable(){

		this.log(Level.INFO, "########################################");
		this.log(Level.INFO, "ItemDelayBlock - By RelativoBR");
		this.log(Level.INFO, "########################################");

		this.playerManager =  new PlayerManager();

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EventsListener(this), this);

		Config cfg = new Config(this);

		this.delayItemFrame = cfg.getInt("delay-item-frame");
		this.infoItemFrame = cfg.getString("info-item-frame");
		this.showLogInfo = cfg.getBoolean("show-log-info");

		if (showLogInfo) {
			this.log(Level.INFO, "Show Log Info: enable");
		} else {
			this.log(Level.INFO, "Show Log Info: disable");
		}

	}

	public final void log(Level level, String messages) {
		if(!showLogInfo && level == Level.INFO){
			return;
		}
		this.getLogger().log(level, messages);
	}

}
