/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar <http://dev.bukkit.org/bukkit-plugins/playerlauncher/>
 * 
 * This file, SetDelayCmd.java, is part of the plugin PlayerLauncher.
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

public class SetDelayCmd implements CommandInterface
{
	@Override
	public boolean execute(CommandSender sender, Command cmd, Launch l,
			String[] args) {
		Player p = (Player) sender;
		
		if (args.length < 3 || !args[1].equalsIgnoreCase("set")) {
			p.sendMessage(PlayerLauncher.PREFIX + PlayerLauncher.INVALIDARGS);
			return false;
		}
		
		if (p.hasPermission("PlayerLauncher.delay.set")) {
			try {
				int input = Integer.parseInt(args[2]);
				l.setLaunchDelay(input);
				l.reloadConfig();
				p.sendMessage(PlayerLauncher.PREFIX
						+ "You have set the delay to " + ChatColor.YELLOW
						+ args[2] + ChatColor.WHITE + " seconds.");
				return true;
			} catch (NumberFormatException e) {
				p.sendMessage(PlayerLauncher.PREFIX
						+ PlayerLauncher.INVALIDARGS);
				return false;
			}
		} else {
			p.sendMessage(PlayerLauncher.NOPERMS);
			return false;
		}
	}
}
