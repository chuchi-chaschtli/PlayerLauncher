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

import io.GitHub.AoHRuthless.command.CommandHandler;
import io.GitHub.AoHRuthless.command.commands.*;
import io.GitHub.AoHRuthless.framework.LaunchPadsData;
import io.GitHub.AoHRuthless.resources.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerLauncher extends JavaPlugin {
	public static PlayerLauncher plugin;
	public static FileConfiguration c;
	public static String prefix;
	public static String noperms;
	public static String invalidargs;
	public HashSet<String> launchpad = new HashSet<String>(); 
	
	public static boolean update = false;
	public static String name = "";
	public static String type = "";
	public static String version = "";
	public static String link = "";
	
	public void registerListeners() {
		this.getServer().getPluginManager().registerEvents(new GlobalListener(this), this);
	}
	
	public void registerCommands() {
		CommandHandler handler = new CommandHandler();
		handler.register("launch", new LaunchCmd());
		handler.register("help", new HelpCmd());
		handler.register("version", new VersionCmd(this));
		handler.register("boom", new ExplosiveLaunchCmd(this));
		handler.register("config", new ConfigCmd(this));
		handler.register("delay", new SetDelayCmd(this));
		handler.register("item", new SetItemCmd(this));
		handler.register("player", new LaunchOthersCmd());
		handler.register("pad", new SetLaunchPadCmd());
		getCommand("launch").setExecutor(handler);
	}
	
	public void registerCommandAliases() {
		CommandHandler handler = new CommandHandler();
		getCommand("launch").setExecutor(handler);
		handler.register("?", new HelpCmd());
		handler.register("p", new LaunchOthersCmd());
		handler.register("v", new VersionCmd(this));
		handler.register("e", new ExplosiveLaunchCmd(this));
	}
	
	public void defineVariables() {
		plugin = this;
		noperms = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Launch.Prefix")) + ChatColor.RESET + " You do not have permission to use this command.";
		invalidargs = ChatColor.RESET + "Invalid arguments. Use /l help for a list of commands.";
		c = this.getConfig();
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Launch.Prefix")) + ChatColor.RESET + " ";
	}
	
	private void startMetrics() {
		try {
		    MetricsLite metrics = new MetricsLite(this);
		    metrics.start();
		} catch (IOException e) {
		   Logger.getLogger("[PlayerLauncher]").log(Level.WARNING, "Why you opt-out from metrics :(");
		}
	}
	
	private void updateCheck() {
		if(getConfig().getBoolean("Launch.Update-Notifications", true)) {
			Updater updater = new Updater(this, 63724, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, true);
			update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE; 
			name = updater.getLatestName(); 
			version = updater.getLatestGameVersion();
			type = updater.getLatestType();
			link = updater.getLatestFileLink();
		} else {
			Logger.getLogger("[PlayerLauncher]").log(Level.WARNING, "Why you no like being notified about updates?! :(");
		}
	}
	
	@Override
	public void onEnable() {
		this.defineVariables();
		this.registerCommands();
		this.registerCommandAliases();
		this.saveDefaultConfig();
		this.startMetrics();
		this.updateCheck();
		this.registerListeners();
		LaunchPadsData.saveLaunchPads();
	}
}