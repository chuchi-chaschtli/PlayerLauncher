/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar
 * 
 * This file, LauncherEventHandler.java, is part of the plugin PlayerLauncher.
 * 
 * PlayerLauncher is a free software: You can redistribute it or modify it
 * under the terms of the GNU General Public License published by the Free
 * Software Foundation, either version 3 of the license of any later version.
 * 
 * PlayerLauncher is distributed in the intent of being useful. However, there
 * is NO WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You can view a copy of the GNU General Public License at 
 * <http://www.gnu.org/licenses/> if you have not received a copy.
 */

package io.GitHub.AoHRuthless;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class LauncherListener implements Listener {
	private PlayerLauncher plugin;
	 
	/**
	 * 
	 * @param plugin
	 */
    public LauncherListener(PlayerLauncher plugin) {
         this.plugin = plugin;
    }

    /**
     * What if the player is walking around and happens to step on the Launch pad?
     * If they have permissions, we want to send them flying into the air.
     */
	@EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
		FileConfiguration c = plugin.getConfig();
		Player send = event.getPlayer();	
		if(c.getBoolean("Launch.Enabled", true)) {
			if(send.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.matchMaterial(plugin.getConfig().getString("Launch.Launch-Pad")))){
				if(send.hasPermission("PlayerLauncher.launch.pad")) {
					send.setVelocity(new Vector(40, 10, 40));
					Vector dir = send.getLocation().getDirection();
					send.setVelocity(dir.multiply(plugin.getConfig().getInt("Launch.Power")));
					send.setFallDistance(-1000.0F);
				}
				if(!send.hasPermission("PlayerLauncher.launch.pad")) {
					// Instead of spamming the player error messages, we'll just cancel the launch.
					event.setCancelled(false);
				}
        	}
        }
    }
	
	/**
	 * This event is similar to the Movement Event above.
	 * However, Pressure Plates are a strange sort of block and we use this method instead.
	 * The task is the same but allows pressure plates to function properly if they are not the launch-pad block.
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		FileConfiguration c = plugin.getConfig();
		Player send = event.getPlayer();
		Action a = event.getAction();
		if(c.getBoolean("Launch.Enabled", true)) {
			if(a.equals(Action.PHYSICAL)) {
				if(event.getClickedBlock().getType() == Material.WOOD_PLATE) {
					if(Material.matchMaterial(plugin.getConfig().getString("Launch.Launch-Pad")) == Material.WOOD_PLATE) {
						if(send.hasPermission("PlayerLauncher.launch.pad")) {
							send.setVelocity(new Vector(40, 10, 40));
							Vector dir = send.getLocation().getDirection();
							send.setVelocity(dir.multiply(plugin.getConfig().getInt("Launch.Power")));
							send.setFallDistance(-1000.0F);
						}
						if(!send.hasPermission("PlayerLauncher.launch.pad")) {
							event.setCancelled(false);
						}
					}
				}
				if(event.getClickedBlock().getType() == Material.STONE_PLATE) {
					if(Material.matchMaterial(plugin.getConfig().getString("Launch.Launch-Pad")) == Material.STONE_PLATE) {
						if(send.hasPermission("PlayerLauncher.launch.pad")) {
							send.setVelocity(new Vector(40, 10, 40));
							Vector dir = send.getLocation().getDirection();
							send.setVelocity(dir.multiply(plugin.getConfig().getInt("Launch.Power")));
							send.setFallDistance(-1000.0F);
						}
						if(!send.hasPermission("PlayerLauncher.launch.pad")) {
							event.setCancelled(false);
						}
					}
				}
			}
		}
	}
}