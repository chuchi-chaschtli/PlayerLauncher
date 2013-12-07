/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar <http://dev.bukkit.org/bukkit-plugins/playerlauncher/>
 * 
 * This file, LaunchOthersCmd.java, is part of the plugin PlayerLauncher.
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

import io.GitHub.AoHRuthless.Frameworks;
import io.GitHub.AoHRuthless.PlayerLauncher;
import io.GitHub.AoHRuthless.command.CommandInterface;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LaunchOthersCmd implements CommandInterface
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player p = (Player) sender;
		Player target = null;
		if(args.length == 2) {
			if(p.hasPermission("PlayerLauncher.launch.others")) {
				target = Bukkit.getServer().getPlayer(args[1]);
				if(target == null) {
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + args[1] + ChatColor.WHITE + " is offline or does not exist.");
					return true;
				} else {
					p.sendMessage(PlayerLauncher.prefix + ChatColor.YELLOW + "You are now launching " + args[1] + "!");
					Frameworks.launchPlayer(target);
					Frameworks.fireworks(target, target.getLocation());
					return true;
				}
			} else {
				p.sendMessage(PlayerLauncher.noperms);
				return true;
			}
		} else {
			p.sendMessage(PlayerLauncher.prefix + PlayerLauncher.invalidargs);
			return true;
		}
	}

}
