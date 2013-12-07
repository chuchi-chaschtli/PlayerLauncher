package io.GitHub.AoHRuthless.command.commands;

import io.GitHub.AoHRuthless.Frameworks;
import io.GitHub.AoHRuthless.PlayerLauncher;
import io.GitHub.AoHRuthless.command.CommandInterface;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LaunchCmd implements CommandInterface 
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		final Player s = (Player) sender;
		if(s.hasPermission("PlayerLauncher.launch")) {
			if(!s.getLocation().getBlock().isLiquid()) {
				if(!s.hasPermission("PlayerLauncher.bypass")) {
					if(Frameworks.hasItems(s)) { 
						s.sendMessage(PlayerLauncher.prefix + ChatColor.GOLD + "Launching ...");
						Bukkit.getScheduler().scheduleSyncDelayedTask(PlayerLauncher.plugin, new Runnable() {
							@Override
							public void run() {
								Frameworks.launchPlayer(s);
							}
						}, PlayerLauncher.c.getInt("Launch.Delay") * 20L);
						Frameworks.removeItems(s);
						return true;
					}
				} else {
					s.sendMessage(PlayerLauncher.prefix + ChatColor.GOLD + "Launching ...");
					Frameworks.launchPlayer(s);
					return true;
				}
			} else {
				s.sendMessage(ChatColor.DARK_RED + "You cannot launch from a liquid. :(");
				return true;
			}
		} else {
			s.sendMessage(PlayerLauncher.noperms);
			return true;
		}
		return false;
	}

}
