package io.GitHub.AoHRuthless.command.commands;

import io.GitHub.AoHRuthless.Frameworks;
import io.GitHub.AoHRuthless.PlayerLauncher;
import io.GitHub.AoHRuthless.command.CommandInterface;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetItemCmd implements CommandInterface
{
	private PlayerLauncher plugin;
	
	public SetItemCmd(PlayerLauncher plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player p = (Player) sender;
		 if(args.length == 4) {
			if(args[1].equalsIgnoreCase("set")) {
				if(p.hasPermission("PlayerLauncher.item.set")) {
					try {
						int input = Integer.parseInt(args[3]);
						Frameworks.setAmount(input);
						Frameworks.setItem(args[2].toUpperCase());
						plugin.saveConfig();
						plugin.reloadConfig();
						p.sendMessage(PlayerLauncher.prefix + "You have set the launch item requirement to " + ChatColor.YELLOW + args[3] + " " + args[2].toUpperCase() + ".");
						return true;
					} catch (NumberFormatException e) {
						p.sendMessage(PlayerLauncher.prefix + PlayerLauncher.invalidargs);
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
		} else {
			p.sendMessage(PlayerLauncher.prefix + PlayerLauncher.invalidargs);
			return true;
		}
	}
}
