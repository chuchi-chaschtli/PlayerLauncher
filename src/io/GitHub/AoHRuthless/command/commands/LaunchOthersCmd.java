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
