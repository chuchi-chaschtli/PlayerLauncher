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
import io.GitHub.AoHRuthless.framework.Frameworks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LaunchCmd implements CommandInterface 
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		final Player s = (Player) sender;
		if(s.hasPermission("PlayerLauncher.launch")) {
			if(!s.getLocation().getBlock().isLiquid()) {
				if(!s.hasPermission("PlayerLauncher.bypass")) {
					if(Frameworks.hasItems(s)) { 
						s.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "Launching ...");
						Bukkit.getScheduler().scheduleSyncDelayedTask(PlayerLauncher.plugin, new Runnable() {
							@Override
							public void run() {
								Frameworks.launchPlayer(s);
								Frameworks.playEffects(s, s.getLocation());
								Frameworks.fireworks(s, s.getLocation());
							}
						}, PlayerLauncher.c.getInt("Launch.Delay") * 20L);
						Frameworks.removeItems(s);
						return true;
					}
				} else {
					s.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "Launching ...");
					Frameworks.launchPlayer(s);
					Frameworks.playEffects(s, s.getLocation());
					Frameworks.fireworks(s, s.getLocation());
					return true;
				}
			} else {
				s.sendMessage(ChatColor.DARK_RED + "You cannot launch from a liquid. :(");
				return true;
			}
		} else {
			s.sendMessage(PlayerLauncher.noperms);
			return true;
		}
		return false;
	}

}
