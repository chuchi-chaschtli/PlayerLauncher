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

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VersionCmd implements CommandInterface
{
	private PlayerLauncher plugin;
	
	public VersionCmd(PlayerLauncher plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player p = (Player) sender;
		if(p.hasPermission("PlayerLauncher.version")) {
			p.sendMessage(ChatColor.DARK_PURPLE + "----" + ChatColor.GRAY + " [Plugin Information]" + ChatColor.DARK_PURPLE + " ----");
			p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "Name: " + ChatColor.WHITE + plugin.getDescription().getName());
			p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "Version: " + ChatColor.WHITE + plugin.getDescription().getVersion());
			p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "Website: " + ChatColor.WHITE + plugin.getDescription().getWebsite());
			p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "Author: " + ChatColor.WHITE + plugin.getDescription().getAuthors());
			return true;
		} else {
			p.sendMessage(PlayerLauncher.noperms);
			return true;
		}
	}

}
