package io.GitHub.AoHRuthless;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

	public static void launchPlayer(Player p) {
		p.setVelocity(new Vector(40, 10, 40));
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