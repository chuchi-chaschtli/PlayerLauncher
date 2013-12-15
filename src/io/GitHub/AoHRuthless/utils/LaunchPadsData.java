package io.GitHub.AoHRuthless.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import io.GitHub.AoHRuthless.PlayerLauncher;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LaunchPadsData 
{
	public static FileConfiguration launchpads; 
	public static File launchPadsFile;
	
	public static void reloadLaunchPads() {
	    if (launchPadsFile == null) {
	    launchPadsFile = new File(PlayerLauncher.plugin.getDataFolder(), "launchpads.yml");
	    }
	    launchpads = YamlConfiguration.loadConfiguration(launchPadsFile);
	    InputStream defConfigStream = PlayerLauncher.plugin.getResource("launchpads.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        launchpads.setDefaults(defConfig);
	    }
	}
	
	public static FileConfiguration getLaunchPads() {
	    if (launchpads == null) {
	        reloadLaunchPads();
	    }
	    return launchpads;
	}
	
	public static void saveLaunchPads() {
	    if (launchpads == null || launchPadsFile == null) {
	        return;
	    }
	    try {
	        getLaunchPads().save(launchPadsFile);
	    } catch (IOException ex) {
	    	PlayerLauncher.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + launchPadsFile, ex);
	    }
	}
	
	public void saveDefaultLaunchPads() {
		if(launchPadsFile == null) {
			launchPadsFile = new File(PlayerLauncher.plugin.getDataFolder(), "launchpads.yml");
		}
		if(!launchPadsFile.exists()) {
			PlayerLauncher.plugin.saveResource("launchpads.yml", false);
		}
	}
}
