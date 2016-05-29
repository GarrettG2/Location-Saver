package com.turt.locsaver;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
		public void onEnable() {
			this.getConfig().addDefault("Prefix", "&c[&4Loc&r&c]");
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
			getCommand("locsaver").setExecutor(new Commands(this));
			Bukkit.getPluginManager().registerEvents(new Commands(this), this);
		}
}