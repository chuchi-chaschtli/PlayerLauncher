/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar
 * 
 * This file, LauncherCommands.java, is part of the plugin PlayerLauncher.
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

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class LauncherCommands implements CommandExecutor {
	private PlayerLauncher plugin;
	
	/**
	 * @param plugin
	 */
	public LauncherCommands(PlayerLauncher plugin) {
		this.plugin = plugin;
	}

	/**
	 * The bread and butter of the plugin.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		final FileConfiguration c = plugin.getConfig();
		Player target = null; // Will be used when secondary players are involved.
		if(cmd.getName().equalsIgnoreCase("launch") && c.getBoolean("Launch.Enabled", true)) {
			if(sender instanceof Player) { //If the command user isn't the console ...
				final Player s = (Player) sender; // Command Sender
				if(args.length == 0) {
					/**
					 * This command is used for launching yourself.
					 */
					if(s.hasPermission("PlayerLauncher.launch")) {
						if(!s.getLocation().getBlock().isLiquid()) {
							if(s.getInventory().contains(Material.matchMaterial(c.getString("Launch.Requirement.Item")), (c.getInt("Launch.Requirement.Amount")))) {
								s.sendMessage(PlayerLauncher.prefix + ChatColor.GOLD + "Launching ...");
								Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() { // A scheduler task will execute the Runnable after a set duration.
									@Override
									public void run() {
										s.setVelocity(new Vector(40, 10, 40)); // Velocity of the launch
										Vector dir = s.getLocation().getDirection(); // Direction of the Launch
										s.setVelocity(dir.multiply(c.getInt("Launch.Power"))); // Power of the launch.
										s.setFallDistance(-1000.0F); // The Fall damage prevention
										s.sendMessage(PlayerLauncher.prefix + ChatColor.translateAlternateColorCodes('&', c.getString("Launch.Message"))); 
									}
								}, c.getInt("Launch.Delay") * 20L); // Get the scheduler duration.
								if(c.getBoolean("Launch.Remove-Required-Items-After-Launch", true)) {
									s.getInventory().removeItem(new ItemStack[] { //Removes the item.
										new ItemStack(Material.matchMaterial(c.getString("Launch.Requirement.Item")), c.getInt("Launch.Requirement.Amount"))});
									return true;
								}
								if(c.getBoolean("Launch.Remove-Required-Items-After-Launch", false)) {
									return true;
								}
							}
							if(!s.getInventory().contains(Material.matchMaterial(c.getString("Launch.Requirement.Item")), (c.getInt("Launch.Requirement.Amount")))) {
								if(!s.hasPermission("PlayerLauncher.bypass")) { 
									s.sendMessage(PlayerLauncher.prefix + ChatColor.GOLD + "You need to have " + ChatColor.DARK_RED + (c.getInt("Launch.Requirement.Amount")) + ChatColor.GOLD + " of " + ChatColor.DARK_RED + Material.matchMaterial(c.getString("Launch.Requirement.Item")) + ChatColor.GOLD + " to launch.");
									return true;
								}
								if(s.hasPermission("PlayerLauncher.bypass") && (s.hasPermission("PlayerLauncher.launch"))) { 
									s.sendMessage(PlayerLauncher.prefix + ChatColor.GOLD + "Launching ...");
									s.setVelocity(new Vector(40, 10, 40));
									Vector dir = s.getLocation().getDirection();
									s.setVelocity(dir.multiply(c.getInt("Launch.Power")));
									s.setFallDistance(-1000.0F);
									s.sendMessage(PlayerLauncher.prefix + ChatColor.translateAlternateColorCodes('&', c.getString("Launch.Message")));
									return true;
								}
							}
						}
						if(s.getLocation().getBlock().isLiquid()) {
							s.sendMessage(ChatColor.DARK_RED + "You cannot launch from a liquid. :(");
							return true;
						}
					}		
					if(!s.hasPermission("PlayerLauncher.launch")) {
						s.sendMessage(PlayerLauncher.noperms);
						return true;
					}
				}
				/**
				 * The help command to view a list of commands.
				 */
				if(args.length == 1) {
					if(!args[0].equalsIgnoreCase("p")) { // Extra Checks, not completely necessary but are relics from when I was an inefficient coder.
						if(!args[0].equalsIgnoreCase("pad")) {
							if(!args[0].equalsIgnoreCase("reload")) {
								if(args[0].equalsIgnoreCase("help")) {
									if(s.hasPermission("PlayerLauncher.help")) {
										if(s.hasPermission("PlayerLauncher.admin")) {
											s.sendMessage(ChatColor.DARK_PURPLE + "----" + ChatColor.GRAY + " PlayerLauncher [" + plugin.getDescription().getVersion() + "]" + ChatColor.DARK_PURPLE + " ----");
											s.sendMessage(ChatColor.LIGHT_PURPLE + "/l help" + ChatColor.GRAY + " - " + ChatColor.WHITE + "View all PlayerLauncher commands.");
											s.sendMessage(ChatColor.LIGHT_PURPLE + "/l" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Launch yourself in a chosen direction.");
											s.sendMessage(ChatColor.LIGHT_PURPLE + "/l p [playername]" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Launch another player in a given direction.");
											s.sendMessage(ChatColor.LIGHT_PURPLE + "/l config [save|reload]" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Save or Reload the configuration file.");
											s.sendMessage(ChatColor.LIGHT_PURPLE + "/l delay set [seconds]" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the delay to launch a player.");
											s.sendMessage(ChatColor.LIGHT_PURPLE + "/l item set [item] [amount]" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Set the item that a player needs to launch.");
											s.sendMessage(ChatColor.LIGHT_PURPLE + "/l [-e]" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Launch with a bang!");
											s.sendMessage(ChatColor.LIGHT_PURPLE + "/l version" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Get the plugin information.");
											return true;
										}
										if(!s.hasPermission("PlayerLauncher.admin") && s.hasPermission("PlayerLauncher.help")) { 
											s.sendMessage(ChatColor.DARK_PURPLE + "----" + ChatColor.GRAY + " PlayerLauncher [" + plugin.getDescription().getVersion() + "]" + ChatColor.DARK_PURPLE + " ----");
											s.sendMessage(ChatColor.LIGHT_PURPLE + "/l help" + ChatColor.GRAY + " - " + ChatColor.WHITE + "View the PlayerLauncher commands.");
											s.sendMessage(ChatColor.LIGHT_PURPLE + "/l" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Launch yourself in a chosen direction.");
											s.sendMessage(ChatColor.LIGHT_PURPLE + "/l version" + ChatColor.GRAY + " - " + ChatColor.WHITE + "Get the plugin information.");
											return true;
										}
									}
									else if(!s.hasPermission("PlayerLauncher.help") && !s.hasPermission("PlayerLauncher.admin")) {
										s.sendMessage(PlayerLauncher.noperms);
										return true;
									}
								}
								/**
								 * Explosive Launching command.
								 */
								if(args[0].equalsIgnoreCase("-e")) { 
									if(s.hasPermission("PlayerLauncher.launch.explosion")) {
										if(c.getBoolean("Launch.Explosions.Enabled", true)) {
											Location l = s.getLocation(); //Gets the sender location.
											if(s.getInventory().contains(Material.TNT, c.getInt("Launch.Explosions.Amount"))) { 
												if(!s.hasPermission("PlayerLauncher.bypass") && !s.hasPermission("PlayerLauncher.admin")) { 
													s.sendMessage(PlayerLauncher.prefix + ChatColor.GOLD + "Launching ...");
													Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
														@Override
														public void run() {
															s.setVelocity(new Vector(40, 10, 40));
															Vector dir = s.getLocation().getDirection();
															s.setVelocity(dir.multiply(c.getInt("Launch.Power")));
															s.setFallDistance(-1000.0F);
															s.setHealth(20d); //If the sender miraculously survives, they are healed.
															s.sendMessage(PlayerLauncher.prefix + ChatColor.translateAlternateColorCodes('&', c.getString("Launch.Message")));
														}
													}, c.getInt("Launch.Delay") * 20L); 
													s.getInventory().removeItem(new ItemStack[] { //Removes TNT from the sender's inventory.
															new ItemStack(Material.TNT, c.getInt("Launch.Explosions.Amount"))}); 
													l.getBlock().getWorld().createExplosion(l, c.getInt("Launch.Explosions.Power") * 1F); // This is how the TNT explosion is created.
													return true;
												}
												if(s.hasPermission("PlayerLauncher.bypass") || s.hasPermission("PlayerLauncher.admin")) {
													s.sendMessage(PlayerLauncher.prefix + ChatColor.GOLD + "Launching ...");
													l.getBlock().getWorld().createExplosion(l, c.getInt("Launch.Explosions.Power") * 1F);
													s.setHealth(20d);
													s.setVelocity(new Vector(40, 10, 40));
													Vector dir = s.getLocation().getDirection();
													s.setVelocity(dir.multiply(c.getInt("Launch.Power")));
													s.setFallDistance(-1000.0F);
													s.sendMessage(PlayerLauncher.prefix + ChatColor.translateAlternateColorCodes('&', c.getString("Launch.Message")));
													s.getInventory().removeItem(new ItemStack[] {
														new ItemStack(Material.TNT, c.getInt("Launch.Explosions.Amount"))});
													return true;
												}
											}
											if(!s.getInventory().contains(Material.TNT, c.getInt("Launch.Explosions.Amount"))) {
												s.sendMessage(ChatColor.DARK_RED + "You do not have enough " + ChatColor.RED + "TNT" + ChatColor.DARK_RED + " to use this command.");
												return true;
											}
										}
										if(c.getBoolean("Launch.Explosions.Enabled", false)) {
											s.sendMessage(ChatColor.DARK_RED + "Sorry, this feature has been disabled.");
											return true;
										}
									}
									if(!s.hasPermission("PlayerLauncher.launch.explosion")) {
										s.sendMessage(PlayerLauncher.noperms);
										return true;
									}
								/**
								 * View the important plugin information as per the plugin.yml
								 */
								} else if(args[0].equalsIgnoreCase("version")){
									if(s.hasPermission("PlayerLauncher.version")) {
										s.sendMessage(ChatColor.DARK_PURPLE + "----" + ChatColor.GRAY + " [Plugin Information]" + ChatColor.DARK_PURPLE + " ----");
										s.sendMessage(ChatColor.GOLD + "Name: " + ChatColor.YELLOW + plugin.getDescription().getName());
										s.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.YELLOW + plugin.getDescription().getVersion());
										s.sendMessage(ChatColor.GOLD + "Website: " + ChatColor.YELLOW + plugin.getDescription().getWebsite());
										s.sendMessage(ChatColor.GOLD + "Author: " + ChatColor.YELLOW + plugin.getDescription().getAuthors());
										return true;
									} else {
										s.sendMessage(PlayerLauncher.noperms);
										return true;
									}
								}
							}
						}
					}
					if(args[0].equalsIgnoreCase("p")) {
						s.sendMessage(PlayerLauncher.invalidargs);
						return true;
					}
				}
				if(args.length > 1) {
					if(!args[0].equalsIgnoreCase("p") && !args[0].equalsIgnoreCase("delay") && !args[0].equalsIgnoreCase("item") && !args[0].equalsIgnoreCase("version") && !args[0].equalsIgnoreCase("config")) {
						s.sendMessage(PlayerLauncher.invalidargs);
						return true;
					}
				}
				/**
				 * Want to launch other players? This is the command.
				 */
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("p") && !args[0].equalsIgnoreCase("delay")) {
						target = Bukkit.getServer().getPlayer(args[1]); //Sets the target as the first argument in the sender's command.
						if(s.hasPermission("PlayerLauncher.launch.others")) {
							if(target == null) {
								if(args[0].equalsIgnoreCase("p")) {
									s.sendMessage(ChatColor.RED + args[1] + ChatColor.DARK_RED + " is offline or does not exist." );	
									return true;
								}
							}
							 if(target != null) { // Note: There is no runnable involved. Otherwise, the setup is the same, but replaces the sender with target.
								 s.sendMessage(PlayerLauncher.prefix + ChatColor.GREEN + "You are now launching " + ChatColor.DARK_GREEN + target.getName() + ChatColor.GREEN + ".");
								 target.setVelocity(new Vector(40,10,40));
								 Vector dir = target.getLocation().getDirection();
								 target.setVelocity(dir.multiply(c.getInt("Launch.Power")));
								 target.setFallDistance(-1000.0F);
								 target.sendMessage(PlayerLauncher.prefix + ChatColor.translateAlternateColorCodes('&', c.getString("Launch.Message")));
								 return true;	
							 }
							 if(!s.hasPermission("PlayerLauncher.launch.others")) {
				            	s.sendMessage(PlayerLauncher.noperms);
								return true;							
							}
						}
					}
					/**
					 * Commands relating to the configuration file.
					 */
					if(args[0].equalsIgnoreCase("config")) {
						if(args[1].equalsIgnoreCase("reload")) {
							if(s.hasPermission("PlayerLauncher.config.reload")) {
								plugin.reloadConfig();
								s.sendMessage(ChatColor.LIGHT_PURPLE + "PlayerLauncher configuration reloaded.");
								return true;
							}
							if(!s.hasPermission("PlayerLauncher.config.reload")) {
								s.sendMessage(PlayerLauncher.noperms);
								return true;
							}
						}
						if(args[1].equalsIgnoreCase("save")) {
							if(s.hasPermission("PlayerLauncher.config.save")) {
								plugin.saveConfig();
								s.sendMessage(ChatColor.LIGHT_PURPLE + "PlayerLauncher configuration saved.");
								return true;
							}
							if(!s.hasPermission("PlayerLauncher.config.save")) {
								s.sendMessage(PlayerLauncher.noperms);
								return true;
							}
						}
					} else if(args[0].equalsIgnoreCase("config") || args[0].equalsIgnoreCase("p")){
						s.sendMessage(PlayerLauncher.invalidargs);
						s.sendMessage(ChatColor.YELLOW + "Usage:" + ChatColor.GOLD + " /launch config [save|reload]");
						return true;
					}
				}
				/**
				 * Error messages ...
				 */
				if(args.length > 2) {
					if(args[0].equalsIgnoreCase("p")) {
						s.sendMessage(PlayerLauncher.invalidargs);
						s.sendMessage(ChatColor.YELLOW + "Usage:" + ChatColor.GOLD + " /launch p [playername]");
						return true;
					}
					if(args[0].equalsIgnoreCase("config")) {
						s.sendMessage(PlayerLauncher.invalidargs);
						s.sendMessage(ChatColor.YELLOW + "Usage:" + ChatColor.GOLD + " /launch config [save|reload]");
						return true;
					}
				}
				/**
				 * Want to set the launch delay in-game?
				 */
				if(args.length == 3) {
					if(args[0].equalsIgnoreCase("delay")) {
						if(args[1].equalsIgnoreCase("set")) {
							if(s.hasPermission("PlayerLauncher.delay.set")) {
								int input = Integer.parseInt(args[2]);
								plugin.setDelay(input);
								plugin.reloadConfig();
								s.sendMessage(PlayerLauncher.prefix + ChatColor.LIGHT_PURPLE + "You have set the delay to " + ChatColor.GREEN + c.getInt("Launch.Delay") + " seconds.");
								return true;
							}
							if(!s.hasPermission("PlayerLauncher.delay.set")) {
								s.sendMessage(PlayerLauncher.noperms);
								return true;	
							}
						}
						if(!args[1].equalsIgnoreCase("set")) {
							s.sendMessage(PlayerLauncher.invalidargs);
							return true;
						}
					}
					if(!args[0].equalsIgnoreCase("delay")) {
						s.sendMessage(PlayerLauncher.invalidargs);
						return true;
					}
				}
				if(args.length > 3) {
					if(args[0].equalsIgnoreCase("delay")) {
						s.sendMessage(PlayerLauncher.invalidargs);
						return true;
					}
				}
				if(args.length < 3 && args.length > 0) {
					if(args[0].equalsIgnoreCase("delay")) {
						if(!args[0].equalsIgnoreCase("pad")) {
							s.sendMessage(PlayerLauncher.invalidargs);
							return true;
						}
					}
				}
				/**
				 * You can also set the launch item requirement and amount in game.
				 */
				if(args.length == 4) {
					if(args[0].equalsIgnoreCase("item")) {
						if(args[1].equalsIgnoreCase("set")) {
							if(s.hasPermission("PlayerLauncher.item.set")) {
								int input = Integer.parseInt(args[3]);
								plugin.setAmount(input);
								plugin.setItem(args[2]);
								plugin.reloadConfig();
								s.sendMessage(PlayerLauncher.prefix + ChatColor.LIGHT_PURPLE + "You have set the launch item to " + ChatColor.GREEN + c.getString("Launch.Requirement.Item") + ChatColor.LIGHT_PURPLE + " and the launch amount to " + ChatColor.GREEN + c.getInt("Launch.Requirement.Amount") + ".");
								return true;
							} else {
								s.sendMessage(PlayerLauncher.noperms);
								return true;
							}
						}
						if(!args[1].equalsIgnoreCase("set")) {
							s.sendMessage(PlayerLauncher.invalidargs);
							return true;
						}
					}
					if(!args[0].equalsIgnoreCase("item")) {
						s.sendMessage(PlayerLauncher.invalidargs);
						return true;
					}
				}
				if(args.length < 4 && args.length > 0) {
					if(args[0].equalsIgnoreCase("item")) {
						if(!args[0].equalsIgnoreCase("pad")) {
							s.sendMessage(PlayerLauncher.invalidargs);
							return true;
						}
					}
				}
				/**
				 * More error messages ...
				 */
				if(args.length > 4) {
					if(args[0].equalsIgnoreCase("item") || args[1].equalsIgnoreCase("set")) {
						s.sendMessage(PlayerLauncher.invalidargs);
						return true;
					}
				}
				if(args.length == 1) {
					if(!args[0].equalsIgnoreCase("help") && !args[0].equalsIgnoreCase("up") && !args[0].equalsIgnoreCase("-e")) {
						s.sendMessage(PlayerLauncher.invalidargs);
						return true;
					}
				}
			} else {
				plugin.getLogger().log(Level.SEVERE, "You must be a player to use this command.");
				return true;
			}
		} else if(c.getBoolean("Launch.Enabled", false)) {
			sender.sendMessage(PlayerLauncher.prefix + ChatColor.DARK_RED + "Sorry, PlayerLauncher has been disabled.");
		}
		return false;
	}
}
