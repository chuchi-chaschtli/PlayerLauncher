/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar
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
import io.GitHub.AoHRuthless.command.commands.ExplosiveLaunchCmd;
import io.GitHub.AoHRuthless.command.commands.HelpCmd;
import io.GitHub.AoHRuthless.command.commands.LaunchCmd;
import io.GitHub.AoHRuthless.command.commands.VersionCmd;
import io.GitHub.AoHRuthless.metrics.MetricsLite;

import java.io.IOException;
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
	
	/**
	 * Input will be used later in setting the launch item.
	 * @param s
	 */
	public boolean input(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }	
	
	/**
	 * This will be called with the command to set the launch delay.
	 * @param i
	 */
	public void setDelay(int i) {
		getConfig().set("Launch.Delay", i);
		saveConfig();
	}
	
	/**
	 * Will be called in setting Launch Item.
	 * @param i
	 */
	public void setAmount(int i) {
		getConfig().set("Launch.Requirement.Amount", i);
		saveConfig();
	}
	
	 /**
	  * Will be called with the in-game command to set the launch item.
	  * @param string
	  */
	public void setItem(String string) {
		getConfig().set("Launch.Requirement.Item", string);
		saveConfig();
	}
	
	public void registerListeners() {
		this.getServer().getPluginManager().registerEvents(new LauncherListener(this), this);
	}
	
	public void registerCommands() {
		CommandHandler handler = new CommandHandler();
		handler.register("launch", new LaunchCmd());
		handler.register("help", new HelpCmd());
		handler.register("version", new VersionCmd(this));
		handler.register("boom", new ExplosiveLaunchCmd(this));
		getCommand("launch").setExecutor(new CommandHandler());
	}
	
	public void defineVariables() {
		plugin = this;
		noperms = ChatColor.DARK_RED + "You do not have permission to use this command.";
		invalidargs = "Invalid arguments. Use /l help for a list of commands.";
		c = this.getConfig();
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("Launch.Prefix")) + " ";
	}
	
	public void startMetrics() {
		try {
		    MetricsLite metrics = new MetricsLite(this);
		    metrics.start();
		} catch (IOException e) {
		   Logger.getLogger("[PlayerLauncher]").log(Level.WARNING, "Why you opt-out from metrics :(");
		}
	}
	
	@Override
	public void onEnable() {
		this.defineVariables();
		this.registerCommands();
		this.registerListeners();
		this.saveDefaultConfig();
		this.startMetrics();
		Logger.getLogger("[PlayerLauncher]");
	}
	
	@Override
	public void onDisable() {
		Logger.getLogger("[PlayerLauncher]");
	}
}