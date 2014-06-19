package io.GitHub.AoHRuthless;

import io.GitHub.AoHRuthless.framework.LaunchPadsData;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GlobalListener implements Listener 
{
	private Map<Block, Location> pads;
	private PlayerLauncher plugin;
	private ConfigurationSection cs;
	private Material padMaterial;
	
	public GlobalListener(PlayerLauncher plugin) {
		this.plugin = plugin;
		this.pads = new HashMap<Block, Location>();
		this.cs	= LaunchPadsData.getLaunchPads(plugin).getConfigurationSection("Pads");
		this.padMaterial = Material.matchMaterial(plugin.getConfig().getString("Launch.Launch-Pad").toUpperCase().replace("-", "_"));
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlockPlaced();
		String name = p.getName();
		if(plugin.getLaunchPads().contains(name)) {
			if(b.getType() == padMaterial) {
				Location loc = b.getLocation();
				
				Double x = loc.getX();
				Double y = loc.getY();
				Double z = loc.getZ();
				
				Random rand = new Random();
				int randomNumber = rand.nextInt(1000000000);
				
				LaunchPadsData.getLaunchPads(plugin).createSection("Pads." + Integer.toString(randomNumber));
				String path = "Pads." + randomNumber + ".";
				LaunchPadsData.getLaunchPads(plugin).set(path + "X" , x);
				LaunchPadsData.getLaunchPads(plugin).set(path + "Y", y);
				LaunchPadsData.getLaunchPads(plugin).set(path + "Z", z);
				LaunchPadsData.saveLaunchPads(plugin);
				
				pads.put(b, loc);
				plugin.getLaunchPads().remove(name);
				p.sendMessage(PlayerLauncher.PREFIX + "You have set a new launch pad at this location!");
			}
		}
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerMove(PlayerMoveEvent e) {
		if (cs == null) return;
		
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		Block ground = loc.getBlock().getRelative(BlockFace.DOWN);
		Double x = ground.getLocation().getX();
		Double y = ground.getLocation().getY();
		Double z = ground.getLocation().getZ();
		
		if(ground.getType() == padMaterial) {
			if(p.hasPermission("PlayerLauncher.launch.pad")) {
				for(String s : cs.getKeys(true)) {
					if ((cs.getDouble(s + ".X") ==  x) &&
							cs.getDouble(s + ".Y") ==  y &&
							cs.getDouble(s + ".Z") ==  z) {
						plugin.getLaunchHandler().launchPlayer(p, false, false);
					}
				}
			}
		}
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction().equals(Action.PHYSICAL)) {
			if (cs == null) return;
			
			Player p = e.getPlayer();
			Block b = e.getClickedBlock();
			if(b.getType() == Material.matchMaterial(plugin.getConfig().getString("Launch.Launch-Pad"))) {
				Double x = b.getLocation().getX();
				Double y = b.getLocation().getY();
				Double z = b.getLocation().getZ();
				
				if(p.hasPermission("PlayerLauncher.launch.pad")) {
					for(String s : cs.getKeys(true)) {
						if ((cs.getDouble(s + ".X") ==  x) &&
								cs.getDouble(s + ".Y") ==  y &&
								cs.getDouble(s + ".Z") ==  z) {
							plugin.getLaunchHandler().launchPlayer(p, false, false);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		ConfigurationSection cs = LaunchPadsData.getLaunchPads(plugin).getConfigurationSection("Pads");
		if (cs == null) return;
		
		Player p = e.getPlayer();
		Block b = e.getBlock();
		Double x = b.getLocation().getX();
		Double y = b.getLocation().getY();
		Double z = b.getLocation().getZ();
		
		if(b.getType() == Material.matchMaterial(plugin.getConfig().getString("Launch.Launch-Pad"))) {
			for(String s : cs.getKeys(true)) {
				if ((cs.getDouble(s + ".X") ==  x) &&
						cs.getDouble(s + ".Y") ==  y &&
						cs.getDouble(s + ".Z") ==  z) {
					if(!p.hasPermission("PlayerLauncher.launch.pad.break")) {
						e.setCancelled(true);
						p.sendMessage(PlayerLauncher.NOPERMS);
					} else {
						e.setCancelled(false);
						cs.set(s, null);
						p.sendMessage(PlayerLauncher.PREFIX + "You have broken a launch pad.");
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!p.isOp() && !p.hasPermission("PlayerLauncher.admin")) return;
		
		if(!PlayerLauncher.update) return;
		
		p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "+"
				+ ChatColor.DARK_GRAY + "] " + ChatColor.DARK_GREEN + PlayerLauncher.name
				+ " is available.");
		p.sendMessage(ChatColor.DARK_GRAY
				+ "[" + ChatColor.DARK_GREEN + "+"
				+ ChatColor.DARK_GRAY + "] " + ChatColor.DARK_GREEN
				+ "Download the latest file at http://dev.bukkit.org/server-mods/playerlauncher/");
	}

}
