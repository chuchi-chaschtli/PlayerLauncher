package io.GitHub.AoHRuthless;

import io.GitHub.AoHRuthless.utils.Frameworks;
import io.GitHub.AoHRuthless.utils.LaunchPadsData;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class GlobalListener implements Listener 
{
	private HashMap<Block, Location> pads = new HashMap<Block, Location>();
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlockPlaced();
		String name = p.getName();
		if(PlayerLauncher.launchpad.contains(name)) {
			if(b.getType() == Material.matchMaterial(PlayerLauncher.plugin.getConfig().getString("Launch.Launch-Pad"))) {
				Location loc = b.getLocation();
				Double x = loc.getX();
				Double y = loc.getY();
				Double z = loc.getZ();
				Random rand = new Random();
				int randomNumber = rand.nextInt(1000000000);
				LaunchPadsData.getLaunchPads().createSection("Pads." + Integer.toString(randomNumber));
				String path = "Pads." + randomNumber + ".";
				LaunchPadsData.getLaunchPads().set(path + "X" , x);
				LaunchPadsData.getLaunchPads().set(path + "Y", y);
				LaunchPadsData.getLaunchPads().set(path + "Z", z);
				LaunchPadsData.saveLaunchPads();
				pads.put(b, loc);
				PlayerLauncher.launchpad.remove(name);
				p.sendMessage(PlayerLauncher.prefix + "You have set a new launch pad at this location!");
			}
		}
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerMove(PlayerMoveEvent e) {
		ConfigurationSection cs = LaunchPadsData.getLaunchPads().getConfigurationSection("Pads");
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		Block ground = loc.getBlock().getRelative(BlockFace.DOWN);
		Double x = ground.getLocation().getX();
		Double y = ground.getLocation().getY();
		Double z = ground.getLocation().getZ();
		if(ground.getType() == Material.matchMaterial(PlayerLauncher.plugin.getConfig().getString("Launch.Launch-Pad"))) {
			if(p.hasPermission("PlayerLauncher.launch.pad")) {
				for(String s : cs.getKeys(true)) {
					if ((cs.getDouble(s + ".X") ==  x) &&
							cs.getDouble(s + ".Y") ==  y &&
							cs.getDouble(s + ".Z") ==  z) {
						Frameworks.silentLaunch(p);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		ConfigurationSection cs = LaunchPadsData.getLaunchPads().getConfigurationSection("Pads");
		Player p = e.getPlayer();
		Block b = e.getBlock();
		Double x = b.getLocation().getX();
		Double y = b.getLocation().getY();
		Double z = b.getLocation().getZ();
		if(b.getType() == Material.matchMaterial(PlayerLauncher.plugin.getConfig().getString("Launch.Launch-Pad"))) {
			for(String s : cs.getKeys(true)) {
				if ((cs.getDouble(s + ".X") ==  x) &&
						cs.getDouble(s + ".Y") ==  y &&
						cs.getDouble(s + ".Z") ==  z) {
					if(!p.hasPermission("PlayerLauncher.launch.pad.break")) {
						e.setCancelled(true);
						p.sendMessage(PlayerLauncher.noperms);
					} else {
						e.setCancelled(false);
						p.sendMessage(PlayerLauncher.prefix + "You have broken a launch pad.");
					}
				}
			}
		}
	}

}
