package com.turt.locsaver;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Commands implements CommandExecutor, Listener {

	Main pl;

	public Commands(Main instance) {
		pl = instance;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String command, String[] args) {
		Player p = (Player) cs;
		String uuid = p.getUniqueId().toString();
		String prefix = cc("&c[&c&lLoc&r&c]&f ");
		String noperm = cc(prefix + p.getName() + " &e, you lack the required permissions!");
		String nosaved = cc(prefix + "&e&lthere was no saved location found!");
		double locx = p.getLocation().getX();
		double locy = p.getLocation().getY();
		double locz = p.getLocation().getZ();
		double cx = pl.getConfig().getDouble("Players." + uuid + ".Last-Location.X");
		double cy = pl.getConfig().getDouble("Players." + uuid + ".Last-Location.Y");
		double cz = pl.getConfig().getDouble("Players." + uuid + ".Last-Location.Z");
		Location lastloc = new Location(Bukkit.getServer().getWorld(p.getWorld().getName()), cx, cy, cz);

		if (p.hasPermission("locsaver.commands.*") || p.hasPermission("locsaver.commands.loc") || p.isOp()) {
			if (args.length == 0) {
				p.sendMessage(cc(prefix + "&euse [/locsaver, loc, ls] [save/tele]"));
				p.sendMessage(cc(prefix + "&eLocation-Saver, by ComputerTurtle"));
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("tele")) {
					p.sendMessage(cc(prefix + "&eyou've been teleported to your last saved location!"));
					if (pl.getConfig().contains("Players." + uuid)) {
						p.teleport(lastloc);
						p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2, 2);
					} else {
						p.sendMessage(nosaved);
					}
				}
				if (args[0].equalsIgnoreCase("save")) {
					p.sendMessage(cc(prefix + "&eyour location has been saved!"));
					p.sendMessage(cc(prefix + locx + " (&c&lX&f), " + locy + " (&c&lY&f), " + locz + " (&c&lZ&f)"));

					if (pl.getConfig().contains("Players." + uuid)) {
						pl.getConfig().set("Players." + uuid + ".Last-Location.X", locx);
						pl.getConfig().set("Players." + uuid + ".Last-Location.Y", locy);
						pl.getConfig().set("Players." + uuid + ".Last-Location.Z", locz);
						pl.saveConfig();
					} else {
						pl.getConfig().set("Players." + uuid + ".Name", p.getName());
						pl.getConfig().set("Players." + uuid + ".Last-Location.X", locx);
						pl.getConfig().set("Players." + uuid + ".Last-Location.Y", locy);
						pl.getConfig().set("Players." + uuid + ".Last-Location.Z", locz);
						pl.saveConfig();
					}
				}
			}
		} else {
			p.sendMessage(noperm);
		}
		return false;
	}

	public String cc(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
