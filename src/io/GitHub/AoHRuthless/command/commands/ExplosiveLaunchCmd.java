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
import io.GitHub.AoHRuthless.framework.Frameworks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ExplosiveLaunchCmd implements CommandInterface
{
	private PlayerLauncher plugin;
	
	public ExplosiveLaunchCmd(PlayerLauncher plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		final FileConfiguration c = plugin.getConfig();
		final Player p = (Player) sender;
		
		if (p.hasPermission("PlayerLauncher.launch.explosion")) {
			if (c.getBoolean("Launch.Explosions.Enabled", false)) {
				p.sendMessage(ChatColor.DARK_RED + "Sorry, this feature has been disabled.");
				return false;
			}
			
			final Location l = p.getLocation(); 
			if (!p.hasPermission("PlayerLauncher.bypass")) { 
				if (io.GitHub.AoHRuthless.framework.Frameworks.hasTNT(p)) { 
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "Launching ...");
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							l.getBlock().getWorld().createExplosion(l, c.getInt("Launch.Explosions.Power") * 1F);
							Frameworks.launchPlayer(p);
						}
					}, c.getInt("Launch.Delay") * 20L); 
				}
				return true;
			} else {
				p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "Launching ...");
				l.getBlock().getWorld().createExplosion(l, c.getInt("Launch.Explosions.Power") * 1F);
				Frameworks.launchPlayer(p);
				return true;
			}
		} else {
			p.sendMessage(PlayerLauncher.noperms);
			return true;
		}
	}

}
