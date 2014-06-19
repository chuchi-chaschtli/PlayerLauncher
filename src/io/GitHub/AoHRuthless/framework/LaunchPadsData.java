package io.GitHub.AoHRuthless.framework;

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
	
	public static void reloadLaunchPads(PlayerLauncher plugin) {
	    if (launchPadsFile == null) {
	    launchPadsFile = new File(plugin.getDataFolder(), "launchpads.yml");
	    }
	    launchpads = YamlConfiguration.loadConfiguration(launchPadsFile);
	    InputStream defConfigStream = plugin.getResource("launchpads.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        launchpads.setDefaults(defConfig);
	    }
	}
	
	public static FileConfiguration getLaunchPads(PlayerLauncher plugin) {
	    if (launchpads == null) {
	        reloadLaunchPads(plugin);
	    }
	    return launchpads;
	}
	
	public static void saveLaunchPads(PlayerLauncher plugin) {
	    if (launchpads == null || launchPadsFile == null) {
	        return;
	    }
	    try {
	        getLaunchPads(plugin).save(launchPadsFile);
	    } catch (IOException ex) {
	    	plugin.getLogger().log(Level.SEVERE, "Could not save config to " + launchPadsFile, ex);
	    }
	}
	
	public void saveDefaultLaunchPads(PlayerLauncher plugin) {
		if(launchPadsFile == null) {
			launchPadsFile = new File(plugin.getDataFolder(), "launchpads.yml");
		}
		if(!launchPadsFile.exists()) {
			plugin.saveResource("launchpads.yml", false);
		}
	}
}
