/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar <http://dev.bukkit.org/bukkit-plugins/playerlauncher/>
 * 
 * This file, PlayerLauncher.java, is part of the plugin PlayerLauncher.
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

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

public class Frameworks {
	
	public static boolean input(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }	
	
	public static void setDelay(int i) {
		PlayerLauncher.c.set("Launch.Delay", i);
		PlayerLauncher.plugin.saveConfig();
	}

	public static void setAmount(int i) {
		PlayerLauncher.c.set("Launch.Requirement.Amount", i);
		PlayerLauncher.plugin.saveConfig();
	}
	

	public static void setItem(String string) {
		PlayerLauncher.c.set("Launch.Requirement.Item", string);
		PlayerLauncher.plugin.saveConfig();
	}
	
	public static void fireworks(Player p, Location loc) {
		Firework firework = p.getWorld().spawn(loc, Firework.class);
        FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
        data.addEffects(FireworkEffect.builder().withColor(Color.YELLOW).with(Type.BALL_LARGE).build());
		Vector dir = p.getLocation().getDirection();
        firework.setFireworkMeta(data);
        firework.setVelocity(dir.multiply(PlayerLauncher.c.getInt("Launch.Power") * .35));
	}

	public static void launchPlayer(Player p) {
		Vector dir = p.getLocation().getDirection();
		p.setVelocity(dir.multiply(PlayerLauncher.c.getInt("Launch.Power")));
		p.setFallDistance(-1000.0F);
		p.sendMessage(PlayerLauncher.prefix + ChatColor.translateAlternateColorCodes('&', PlayerLauncher.c.getString("Launch.Message"))); 
	}
	
	public static boolean removeItems(Player p) {
 		if(PlayerLauncher.c.getBoolean("Launch.Remove-Required-Items-After-Launch", true)) {
			p.getInventory().removeItem(new ItemStack[] {
				new ItemStack(Material.matchMaterial(PlayerLauncher.c.getString("Launch.Requirement.Item")), PlayerLauncher.c.getInt("Launch.Requirement.Amount"))});
			return false;
 		}
		return true;
	}
	
	public static boolean hasItems(Player p) {
		if(!p.getInventory().contains(Material.matchMaterial(PlayerLauncher.c.getString("Launch.Requirement.Item")), (PlayerLauncher.c.getInt("Launch.Requirement.Amount")))) {
			p.sendMessage(ChatColor.GOLD + "You must have " + ChatColor.DARK_RED + PlayerLauncher.c.getInt("Launch.Requirement.Amount") + ChatColor.GOLD + " of " + ChatColor.DARK_RED + PlayerLauncher.c.getString("Launch.Requirement.Item").toUpperCase() + ChatColor.GOLD + " to launch.");
			return false;
		}
		return true;
	}
	
	public static boolean hasTNT(Player p) {
		if(p.getInventory().contains(Material.TNT, PlayerLauncher.c.getInt("Launch.Explosions.Amount"))) {
			p.getInventory().removeItem(new ItemStack[] {
					new ItemStack(Material.TNT, PlayerLauncher.c.getInt("Launch.Explosions.Amount"))});
			return true;
		} else {
			p.sendMessage(ChatColor.DARK_RED + "You do not have enough " + ChatColor.RED + "TNT" + ChatColor.DARK_RED + " to use this command.");
			return true;
		}
	}
}