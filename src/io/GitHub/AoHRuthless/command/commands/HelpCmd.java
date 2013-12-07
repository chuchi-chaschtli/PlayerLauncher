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
					p.sendMessage(ChatColor.LIGHT_PURPLE + "/l help" + ChatColor.GRAY + " - " + ChatColor.WHITE + "View all PlayerLauncher commands.");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "/l" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Launch yourself in a chosen direction.");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "/l p [playername]" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Launch another player in a given direction.");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "/l config [save|reload]" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Save or Reload the configuration file.");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "/l delay set [seconds]" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the delay to launch a player.");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "/l item set [item] [amount]" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the item that a player needs to launch.");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "/l [-e]" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Launch with a bang!");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "/l version" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Get the plugin information.");
					return true;
				} else {
					p.sendMessage(ChatColor.DARK_PURPLE + "----" + ChatColor.GRAY + " PlayerLauncher [" + PlayerLauncher.plugin.getDescription().getVersion() +"]" + ChatColor.DARK_PURPLE + " ----");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "/l help" + ChatColor.GRAY + " - " + ChatColor.WHITE + "View the PlayerLauncher commands.");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "/l" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Launch yourself in a chosen direction.");
					p.sendMessage(ChatColor.LIGHT_PURPLE + "/l version" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Get the plugin information.");
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
