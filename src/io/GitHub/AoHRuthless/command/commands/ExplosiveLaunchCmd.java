package io.GitHub.AoHRuthless.command.commands;

import io.GitHub.AoHRuthless.Frameworks;
import io.GitHub.AoHRuthless.PlayerLauncher;
import io.GitHub.AoHRuthless.command.CommandInterface;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ExplosiveLaunchCmd implements CommandInterface
{
	private PlayerLauncher plugin;
	
	public ExplosiveLaunchCmd(PlayerLauncher plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		final FileConfiguration c = plugin.getConfig();
		final Player p = (Player) sender;
		if(p.hasPermission("PlayerLauncher.launch.explosion")) {
			if(c.getBoolean("Launch.Explosions.Enabled", true)) {
				final Location l = p.getLocation(); //Gets the sender location.
				if(!p.hasPermission("PlayerLauncher.bypass")) { 
					if(Frameworks.hasTNT(p)) { 
						p.sendMessage(PlayerLauncher.prefix + ChatColor.GOLD + "Launching ...");
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							@Override
							public void run() {
								l.getBlock().getWorld().createExplosion(l, c.getInt("Launch.Explosions.Power") * 1F);
								Frameworks.launchPlayer(p);
							}
						}, c.getInt("Launch.Delay") * 20L); 
					}
					return true;
				} else {
					p.sendMessage(PlayerLauncher.prefix + ChatColor.GOLD + "Launching ...");
					l.getBlock().getWorld().createExplosion(l, c.getInt("Launch.Explosions.Power") * 1F);
					Frameworks.launchPlayer(p);
					return true;
				}
			} else {
				p.sendMessage(ChatColor.DARK_RED + "Sorry, this feature has been disabled.");
				return true;
			}
		} else {
			p.sendMessage(PlayerLauncher.noperms);
			return true;
		}
	}

}
