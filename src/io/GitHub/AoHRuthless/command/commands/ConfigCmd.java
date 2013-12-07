/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar <http://dev.bukkit.org/bukkit-plugins/playerlauncher/>
 * 
 * This file, PlayerLauncher.java, is part of the plugin PlayerLauncher.
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

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigCmd implements CommandInterface
{
	private PlayerLauncher plugin;
	
	public ConfigCmd(PlayerLauncher plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player p = (Player) sender;
		if(args[1].equalsIgnoreCase("reload")) {
			if(p.hasPermission("PlayerLauncher.config.reload")) {
				plugin.reloadConfig();
				p.sendMessage(PlayerLauncher.prefix  + "PlayerLauncher configuration reloaded.");
				return true;
			}
			if(!p.hasPermission("PlayerLauncher.config.reload")) {
				p.sendMessage(PlayerLauncher.noperms);
				return true;
			}
		}
		if(args[1].equalsIgnoreCase("save")) {
			if(p.hasPermission("PlayerLauncher.config.save")) {
				plugin.saveConfig();
				p.sendMessage(PlayerLauncher.prefix + "PlayerLauncher configuration saved.");
				return true;
			}
			if(!p.hasPermission("PlayerLauncher.config.save")) {
				p.sendMessage(PlayerLauncher.noperms);
				return true;
			}
		}
		return false;
	}

}
