package io.GitHub.AoHRuthless.command;

import io.GitHub.AoHRuthless.PlayerLauncher;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands {
	
	public static boolean isPlayer(CommandSender sender) {
		return(sender instanceof Player);
	}
	
	public static boolean commandName(Command command, String name) {
		return command.getName().equalsIgnoreCase(name);
	}
	
	public static boolean isEnabled() {
		return(PlayerLauncher.c.getBoolean("Launch.Enabled"));
	}

}
