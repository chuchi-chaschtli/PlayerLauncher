/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar <http://dev.bukkit.org/bukkit-plugins/playerlauncher/>
 * 
 * This file, HelpCmd.java, is part of the plugin PlayerLauncher.
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
import io.GitHub.AoHRuthless.command.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCmd implements CommandInterface 
{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if(Commands.isPlayer(sender)) {
			Player p = (Player) sender;
			if(p.hasPermission("PlayerLauncher.help")) {
				if(p.hasPermission("PlayerLauncher.admin")) {
					p.sendMessage(ChatColor.DARK_PURPLE + "----" + ChatColor.GRAY + " PlayerLauncher [" + PlayerLauncher.plugin.getDescription().getVersion() + "]" + ChatColor.DARK_PURPLE + " ----");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l help " +  ChatColor.WHITE + "View all PlayerLauncher commands.");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l " +  ChatColor.WHITE + "Launch yourself in a chosen direction.");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l p <playername> " +  ChatColor.WHITE + "Launch another player in a given direction.");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l config <save|reload> " +  ChatColor.WHITE + "Save or Reload the configuration file.");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l delay set <seconds> " +  ChatColor.WHITE + "Set the delay to launch a player.");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l item set <item> <amount> " +  ChatColor.WHITE + "Set the item that a player needs to launch.");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l boom " +  ChatColor.WHITE + "Launch with an explosion.");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l version " +  ChatColor.WHITE + "View the plugin information.");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l pad " +  ChatColor.WHITE + "Create a new launch pad.");
					return true;
				} else {
					p.sendMessage(ChatColor.DARK_PURPLE + "----" + ChatColor.GRAY + " PlayerLauncher [" + PlayerLauncher.plugin.getDescription().getVersion() +"]" + ChatColor.DARK_PURPLE + " ----");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l help " +  ChatColor.WHITE + "View the PlayerLauncher commands.");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l " +  ChatColor.WHITE + "Launch yourself in a chosen direction.");
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "/l version "+ ChatColor.WHITE + "View the plugin information.");
					return true;
				}
			} else {
				p.sendMessage(PlayerLauncher.noperms);
				return true;
			}
		}
		return false;
	}
}
