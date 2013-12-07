package io.GitHub.AoHRuthless.command.commands;

import io.GitHub.AoHRuthless.PlayerLauncher;
import io.GitHub.AoHRuthless.command.CommandInterface;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigCmd implements CommandInterface
{
	private PlayerLauncher plugin;
	
	public ConfigCmd(PlayerLauncher plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player p = (Player) sender;
		if(args[1].equalsIgnoreCase("reload")) {
			if(p.hasPermission("PlayerLauncher.config.reload")) {
				plugin.reloadConfig();
				p.sendMessage(PlayerLauncher.prefix  + "PlayerLauncher configuration reloaded.");
				return true;
			}
			if(!p.hasPermission("PlayerLauncher.config.reload")) {
				p.sendMessage(PlayerLauncher.noperms);
				return true;
			}
		}
		if(args[1].equalsIgnoreCase("save")) {
			if(p.hasPermission("PlayerLauncher.config.save")) {
				plugin.saveConfig();
				p.sendMessage(PlayerLauncher.prefix + "PlayerLauncher configuration saved.");
				return true;
			}
			if(!p.hasPermission("PlayerLauncher.config.save")) {
				p.sendMessage(PlayerLauncher.noperms);
				return true;
			}
		}
		return false;
	}

}
