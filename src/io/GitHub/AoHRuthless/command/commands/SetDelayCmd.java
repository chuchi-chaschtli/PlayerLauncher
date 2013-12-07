package io.GitHub.AoHRuthless.command.commands;

import io.GitHub.AoHRuthless.Frameworks;
import io.GitHub.AoHRuthless.PlayerLauncher;
import io.GitHub.AoHRuthless.command.CommandInterface;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetDelayCmd implements CommandInterface
{
	private PlayerLauncher plugin;
	
	public SetDelayCmd(PlayerLauncher plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player p = (Player) sender;
		if(args.length == 3) {
			if(args[1].equalsIgnoreCase("set")) {
				if(p.hasPermission("PlayerLauncher.delay.set")) {
					try {
						int input = Integer.parseInt(args[2]);
						Frameworks.setDelay(input);
						plugin.reloadConfig();
						p.sendMessage(PlayerLauncher.prefix + "You have set the delay to " + ChatColor.YELLOW + args[2] + ChatColor.WHITE + " seconds.");
						return true;
					} catch (NumberFormatException e) {
						p.sendMessage(PlayerLauncher.prefix + PlayerLauncher.invalidargs);
						return true;
					}
				}
				if(!p.hasPermission("PlayerLauncher.delay.set")) {
					p.sendMessage(PlayerLauncher.noperms);
					return true;	
				}
			} else {
				p.sendMessage(PlayerLauncher.prefix + PlayerLauncher.invalidargs);	
				return true;
			}
		} else {
			p.sendMessage(PlayerLauncher.prefix + PlayerLauncher.invalidargs);	
			return true;
		}
		return false;
	}

}
