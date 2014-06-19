/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar <http://dev.bukkit.org/bukkit-plugins/playerlauncher/>
 * 
 * This file, VersionCmd.java, is part of the plugin PlayerLauncher.
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

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VersionCmd implements CommandInterface
{

	@Override
	public boolean execute(CommandSender sender, Command cmd,
			Launch l, String[] args) {
		Player p = (Player) sender;
		PlayerLauncher plugin = l.getPlugin();
		
		if(p.hasPermission("PlayerLauncher.version")) {
			String prefix = PlayerLauncher.PREFIX + ChatColor.YELLOW;
			p.sendMessage(ChatColor.DARK_PURPLE + "----" + ChatColor.GRAY + " [Plugin Information]" + ChatColor.DARK_PURPLE + " ----");
			p.sendMessage(prefix + "Name: " + ChatColor.WHITE + plugin.getDescription().getName());
			p.sendMessage(prefix + "Version: " + ChatColor.WHITE + plugin.getDescription().getVersion());
			p.sendMessage(prefix + "Website: " + ChatColor.WHITE + plugin.getDescription().getWebsite());
			p.sendMessage(prefix + "Author: " + ChatColor.WHITE + plugin.getDescription().getAuthors());
			return true;
		} else {
			p.sendMessage(PlayerLauncher.NOPERMS);
			return true;
		}
	}

}
