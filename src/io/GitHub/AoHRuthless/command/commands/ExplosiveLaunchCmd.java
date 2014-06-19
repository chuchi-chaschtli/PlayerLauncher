/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar <http://dev.bukkit.org/bukkit-plugins/playerlauncher/>
 * 
 * This file, ExplosiveLaunchCmd.java, is part of the plugin PlayerLauncher.
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ExplosiveLaunchCmd implements CommandInterface
{
	@Override
	public boolean execute(CommandSender sender, Command cmd,
			final Launch l, String[] args) {
		final FileConfiguration c = l.getConfig();
		final Player p = (Player) sender;
		
		if (!c.getBoolean("Launch.Explosions.Enabled")) {
			p.sendMessage(ChatColor.DARK_RED + "Sorry, this feature has been disabled.");
			return false;
		}
		
		if (p.hasPermission("PlayerLauncher.launch.explosion")) {
			p.sendMessage(PlayerLauncher.PREFIX + ChatColor.YELLOW + "Launching ...");
			if (!p.hasPermission("PlayerLauncher.bypass")) { 
				if (l.hasLaunchTNT(p)) { 
					p.sendMessage(PlayerLauncher.PREFIX + ChatColor.YELLOW + "Launching ...");
					Bukkit.getScheduler().scheduleSyncDelayedTask(l.getPlugin(), new Runnable() {
						@Override
						public void run() {
							l.launchExplosively(p);
						}
					}, c.getInt("Launch.Delay") * 20L); 
				}
				return true;
			} else {
				l.launchExplosively(p);
				return true;
			}
		} else {
			p.sendMessage(PlayerLauncher.NOPERMS);
			return true;
		}
	}

}
