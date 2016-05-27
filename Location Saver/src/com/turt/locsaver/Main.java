package com.turt.locsaver;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
		public void onEnable() {
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
			getCommand("saveloc").setExecutor(new Commands(this));
			getCommand("teleloc").setExecutor(new Commands(this));
			Bukkit.getPluginManager().registerEvents(new Commands(this), this);
		}
}