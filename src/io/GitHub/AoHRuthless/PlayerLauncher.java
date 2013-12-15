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
import io.GitHub.AoHRuthless.command.commands.ConfigCmd;
import io.GitHub.AoHRuthless.command.commands.ExplosiveLaunchCmd;
import io.GitHub.AoHRuthless.command.commands.HelpCmd;
import io.GitHub.AoHRuthless.command.commands.LaunchCmd;
import io.GitHub.AoHRuthless.command.commands.LaunchOthersCmd;
import io.GitHub.AoHRuthless.command.commands.SetDelayCmd;
import io.GitHub.AoHRuthless.command.commands.SetItemCmd;
import io.GitHub.AoHRuthless.command.commands.SetLaunchPadCmd;
import io.GitHub.AoHRuthless.command.commands.VersionCmd;
import io.GitHub.AoHRuthless.metrics.MetricsLite;
import io.GitHub.AoHRuthless.utils.LaunchPadsData;

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
	public static HashSet<String> launchpad = new HashSet<String>(); 
	
	public void registerListeners() {
		this.getServer().getPluginManager().registerEvents(new GlobalListener(), this);
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
		noperms = ChatColor.GOLD + "[PlayerLauncher]" + ChatColor.WHITE + " You do not have permission to use this command.";
		invalidargs = ChatColor.RESET + "Invalid arguments. Use /l help for a list of commands.";
		c = this.getConfig();
		prefix = ChatColor.GOLD + "[PlayerLauncher]" + ChatColor.RESET + " ";
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
		this.registerCommandAliases();
		this.registerListeners();
		this.saveDefaultConfig();
		this.startMetrics();
		LaunchPadsData.saveLaunchPads();
	}
}