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
import io.GitHub.AoHRuthless.framework.Launch;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCmd implements CommandInterface 
{
	private void msg(Player p, String command, String description) {
		p.sendMessage(PlayerLauncher.PREFIX + ChatColor.YELLOW + command + " " + ChatColor.RESET + description);
	}
	
	@Override
	public boolean execute(CommandSender sender, Command cmd,
			Launch l, String[] args) {
		if(Commands.isPlayer(sender)) {
			Player p = (Player) sender;
			if(p.hasPermission("PlayerLauncher.help")) {
				if(p.hasPermission("PlayerLauncher.admin")) {
					p.sendMessage(ChatColor.DARK_PURPLE + "----" + ChatColor.GRAY + " PlayerLauncher [" + l.getPlugin().getDescription().getVersion() + "]" + ChatColor.DARK_PURPLE + " ----");
					msg(p, "/l help", "View all PlayerLauncher commands.");
					msg(p, "/l", "Launch yourself in a chosen direction.");
					msg(p, "/l p <player>", "Launch another player in a given direction.");
					msg(p, "/l config <save|reload>", "Save or reload the configuration.");
					msg(p, "/l delay set <seconds>", "Set the delay to launch a player.");
					msg(p, "/l item set <item> <amount>", "Set the item that a player needs to launch.");
					msg(p, "/l boom", "Launch with an explosion.");
					msg(p, "/l version", "View the plugin information.");
					msg(p, "/l pad", "Create a new launch pad.");
					return true;
				} else {
					p.sendMessage(ChatColor.DARK_PURPLE + "----"
							+ ChatColor.GRAY + " PlayerLauncher ["
							+ l.getPlugin().getDescription().getVersion() + "]"
							+ ChatColor.DARK_PURPLE + " ----");
					msg(p, "/l help", "View all PlayerLauncher commands.");
					msg(p, "/l", "Launch yourself in a chosen direction.");
					msg(p, "/l version", "View the plugin information.");
					return true;
				}
			} else {
				p.sendMessage(PlayerLauncher.NOPERMS);
			}
		}
		return false;
	}
}
