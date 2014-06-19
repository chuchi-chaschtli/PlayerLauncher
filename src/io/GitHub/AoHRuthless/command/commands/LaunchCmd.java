/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar <http://dev.bukkit.org/bukkit-plugins/playerlauncher/>
 * 
 * This file, LaunchCmd.java, is part of the plugin PlayerLauncher.
 * 
 * PlayerLauncher is a free software: You can redistribute it or modify it
 * under the terms of the GNU General Public License published by the Free
 * Software Foundation, either version 3 of the license of any later version.
 * 
 * PlayerLauncher is distributed in the intent of being useful. However, there
 * is NO WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You can view a copy of the GNU General Public License at 
 * <http://www.gnu.org/licenses/> if you have not received a copy.
 */
package io.GitHub.AoHRuthless.command.commands;

import io.GitHub.AoHRuthless.PlayerLauncher;
import io.GitHub.AoHRuthless.command.CommandInterface;
import io.GitHub.AoHRuthless.framework.Launch;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LaunchCmd  implements CommandInterface
{

	@Override
	public boolean execute(CommandSender sender, Command cmd,
			final Launch l, String[] args) {
		final Player p = (Player) sender;
		
		if (!p.hasPermission("PlayerLauncher.launch")) {
			p.sendMessage(PlayerLauncher.NOPERMS);
			return false;
		}
		
		if (p.getLocation().getBlock().isLiquid()) {
			p.sendMessage(ChatColor.DARK_RED + "You cannot launch from a liquid. :(");
			return false;
		}
		
		p.sendMessage(PlayerLauncher.PREFIX + ChatColor.YELLOW + "Launching ...");
		if (p.hasPermission("PlayerLauncher.bypass")) {
			l.launchPlayer(p, true, true);
			l.launchFireworksOnPlayer(p);
			return true;
		} else {
			Bukkit.getScheduler().scheduleSyncDelayedTask(l.getPlugin(), new Runnable() {
				public void run() {
					l.launchPlayer(p, true, true);
					l.launchFireworksOnPlayer(p);
				}
			}, l.getConfig().getInt("Launch.Delay") * 20L);
			return true;
		}
	}

}
