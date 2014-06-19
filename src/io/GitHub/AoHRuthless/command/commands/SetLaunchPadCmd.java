/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar <http://dev.bukkit.org/bukkit-plugins/playerlauncher/>
 * 
 * This file, SetLaunchPadCmd.java, is part of the plugin PlayerLauncher.
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

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetLaunchPadCmd implements CommandInterface
{
	@Override
	public boolean execute(CommandSender sender, Command cmd, Launch l,
			String[] args) {
		
		if (args.length != 1) {
			sender.sendMessage(PlayerLauncher.PREFIX
					+ PlayerLauncher.INVALIDARGS);
			return false;
		}
		
		Player p = (Player) sender;
		if (!p.hasPermission("PlayerLauncher.launch.pad.set")) {
			p.sendMessage(PlayerLauncher.NOPERMS);
			return false;
		}
		
		if (args[0].equalsIgnoreCase("pad")) {
			p.setItemInHand(new ItemStack(Material.matchMaterial(l.getConfig()
					.getString("Launch.Launch-Pad"))));
			p.sendMessage(PlayerLauncher.PREFIX
					+ "Place this block to create a new launch pad.");
			l.getPlugin().getLaunchPads().add(p.getName());
			return true;
		}
		return false;
	}
}
