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
		String nosaved = cc(prefix + "&ethere was no saved location found!");
		double locx = p.getLocation().getX();
		double locy = p.getLocation().getY();
		double locz = p.getLocation().getZ();
		double cx = pl.getConfig().getDouble("Players." + uuid + ".Last-Location.X");
		double cy = pl.getConfig().getDouble("Players." + uuid + ".Last-Location.Y");
		double cz = pl.getConfig().getDouble("Players." + uuid + ".Last-Location.Z");
		Location lastloc = new Location(Bukkit.getServer().getWorld(p.getWorld().getName()), cx, cy, cz);

		if (p.hasPermission("locsaver.commands.loc") || p.isOp()) {
			if (args.length == 0) {
				p.sendMessage(cc(prefix + "&euse [/locsaver, loc, ls] [save/tele/check]"));
				p.sendMessage(cc(prefix + "&eLocation-Saver, by ComputerTurtle"));
			} else if (args.length == 1) {
				/*
				 * 
				 * COMMANDS: CHECK
				 * 
				 */
				if (args[0].equalsIgnoreCase("check")) {
					if (pl.getConfig().contains("Players." + uuid)) {
						if (locx == cx && locy == cy && locz == cz) {
							p.sendMessage(cc(prefix + "&eYou're standing on your current saved location!"));
						} else {
							p.sendMessage(cc(prefix + "&eYour current saved location coords:"));
							p.sendMessage(cc(
									prefix + "&c(&lX&r&c) &f" + pl.getConfig().getInt("Players." + uuid + ".Last-Location.X")));
							p.sendMessage(cc(
									prefix + "&c(&lY&r&c) &f" + pl.getConfig().getInt("Players." + uuid + ".Last-Location.Y")));
							p.sendMessage(cc(
									prefix + "&c(&lZ&r&c) &f" + pl.getConfig().getInt("Players." + uuid + ".Last-Location.Z")));
						}
					} else {
						p.sendMessage(nosaved);
					}
				}
				/*
				 * 
				 * COMMANDS: TELE
				 * 
				 */
				if (args[0].equalsIgnoreCase("tele")) {
					if (pl.getConfig().contains("Players." + uuid)) {
						if (locx == cx && locy == cy && locz == cz) {
							p.sendMessage(cc(prefix + "&eYou're standing on your current saved location!"));
						} else {
						p.sendMessage(cc(prefix + "&eYou've been teleported to your last saved location!"));
						p.teleport(lastloc);
						p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2, 2);
						}
					} else {
						p.sendMessage(nosaved);
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2, 2);
					}
				}
				/*
				 * 
				 * COMMANDS: SAVE
				 * 
				 */
				if (args[0].equalsIgnoreCase("save")) {
					if (pl.getConfig().contains("Players." + uuid)) {
						if (locx == cx && locy == cy && locz == cz) {
							p.sendMessage(cc(prefix + "&eYou're standing on your current saved location!"));
						} else {
						pl.getConfig().set("Players." + uuid + ".Last-Location.X", locx);
						pl.getConfig().set("Players." + uuid + ".Last-Location.Y", locy);
						pl.getConfig().set("Players." + uuid + ".Last-Location.Z", locz);
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2, 2);
						pl.saveConfig();
						p.sendMessage(cc(prefix + "&eYour location has been saved!"));
						}
					} else {
						pl.getConfig().set("Players." + uuid + ".Name", p.getName());
						pl.getConfig().set("Players." + uuid + ".Last-Location.X", locx);
						pl.getConfig().set("Players." + uuid + ".Last-Location.Y", locy);
						pl.getConfig().set("Players." + uuid + ".Last-Location.Z", locz);
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2, 2);
						pl.saveConfig();
						p.sendMessage(cc(prefix + locx + " (&c&lX&f), " + locy + " (&c&lY&f), " + locz + " (&c&lZ&f)"));
					}
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("check")) {
					Player t = Bukkit.getServer().getPlayer(args[1]);
					String tuuid = t.getUniqueId().toString();
					if (t.getName() == p.getName()) {
						p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 2, 2);
						p.sendMessage(cc(prefix + "&eUse &f/ls check&e to search up your saved location!"));
					} else {
						if (pl.getConfig().contains("Players." + tuuid)) {
							
						} else {
							p.sendMessage(nosaved);
						}
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
