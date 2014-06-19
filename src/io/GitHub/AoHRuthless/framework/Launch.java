package io.GitHub.AoHRuthless.framework;

import io.GitHub.AoHRuthless.PlayerLauncher;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

public class Launch
{
	private PlayerLauncher		plugin;
	private FileConfiguration	config;
	
	private int requiredAmount;
	private String requiredItem;

	public Launch(PlayerLauncher plugin) {
		this.plugin = plugin;
		this.config = plugin.getConfig();
		this.requiredAmount = config.getInt("Launch.Requirement.Amount");
		this.requiredItem = config.getString("Launch.Requirement.Item").toUpperCase().replace("-", "_");
	}

	public boolean playLaunchSound(Player p) {
		String effects = config.getString("Launch.Effects.Sound");
		if (effects == null) {
			return false;
		}
		String[] effect = effects.split(",");
		for (int i = 0; i < effect.length; i++) {
			String[] effectParts = effect[i].split("-");
			
			if (Sound.valueOf(effectParts[0].toUpperCase()) == null) {
				continue;
			}
			
			if (effectParts.length < 2) {
				p.playSound(p.getLocation(),
						Sound.valueOf(effect[i].toUpperCase()), 1F, 1F);
			} else if (effectParts.length > 2) {
				p.playSound(p.getLocation(),
						Sound.valueOf(effectParts[0].toUpperCase()),
						Float.parseFloat(effectParts[1]),
						Float.parseFloat(effectParts[2]));
			} else {
				p.playSound(p.getLocation(),
						Sound.valueOf(effectParts[0].toUpperCase()),
						Float.parseFloat(effectParts[1]), 1F);
			}
		}
		return true;
	}

	public boolean launchFireworksOnPlayer(Player p) {
		if (!config.getBoolean("Launch.Effects.Firework")) {
			return false;
		}
		Firework firework = p.getWorld().spawn(p.getLocation(), Firework.class);
		FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
		Random random = new Random();
		Color randomRGB = Color.fromRGB(random.nextInt(256),
				random.nextInt(256), random.nextInt(256));
		data.addEffects(FireworkEffect.builder().withColor(randomRGB)
				.with(Type.BALL_LARGE).build());
		Vector dir = p.getLocation().getDirection();
		firework.setFireworkMeta(data);
		firework.setVelocity(dir.multiply(config.getInt("Launch.Power") * .35));
		return true;
	}

	public void launchPlayer(Player p, boolean sendMessage, boolean playSound) {
		if (!hasLaunchItems(p)) {
			return;
		}
		Vector dir = p.getLocation().getDirection();
		p.setVelocity(dir.multiply(config.getInt("Launch.Power")));
		p.setFallDistance(-1000.0F);
		
		removeLaunchItems(p);
		
		if (sendMessage) {
			p.sendMessage(PlayerLauncher.PREFIX + ChatColor.translateAlternateColorCodes('&', config.getString("Launch.Message"))); 
		}
		
		if (playSound) {
			playLaunchSound(p);
		}
	}
	
	public void launchExplosively(Player p) {
		Location l = p.getLocation();
		l.getBlock().getWorld().createExplosion(l, config.getInt("Launch.Explosions.Power") * 1F);
		launchPlayer(p, true, false);
	}

	public boolean hasLaunchItems(Player p) {
		if (!p.getInventory().contains(Material.matchMaterial(requiredItem),
				(requiredAmount))) {
			p.sendMessage(ChatColor.GOLD + "You must have "
					+ ChatColor.DARK_RED + requiredAmount + ChatColor.GOLD
					+ " of " + ChatColor.DARK_RED + requiredItem
					+ ChatColor.GOLD + " to launch.");
			return false;
		}
		return true;
	}
	
	public boolean hasLaunchTNT(Player p) {
		int expAmount = config.getInt("Launch.Explosions.Amount");
		if(p.getInventory().contains(Material.TNT, expAmount)) {
			p.getInventory().removeItem(new ItemStack[] {
					new ItemStack(Material.TNT, expAmount)});
			return true;
		} else {
			p.sendMessage(ChatColor.DARK_RED + "You do not have enough " + ChatColor.RED + "TNT" + ChatColor.DARK_RED + " to use this command.");
			return false;
		}
	}
	
	public void removeLaunchItems(Player p) {
 		if(config.getBoolean("Launch.Remove-Required-Items-After-Launch")) {
			p.getInventory().removeItem(new ItemStack[] {
				new ItemStack(Material.matchMaterial(requiredItem), requiredAmount)});
 		}
	}

	public PlayerLauncher getPlugin() {
		return plugin;
	}
	
	public FileConfiguration getConfig() {
		return config;
	}

	public void setLaunchDelay(int i) {
		config.set("Launch.Delay", i);
		saveConfig();
	}

	public void setRequiredAmount(int i) {
		config.set("Launch.Requirement.Amount", i);
		saveConfig();
	}

	public void setRequiredItem(String string) {
		config.set("Launch.Requirement.Item", string);
		saveConfig();
	}

	public void saveConfig() {
		plugin.saveConfig();
	}

	public void reloadConfig() {
		plugin.reloadConfig();
	}
}
