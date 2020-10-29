package xyz.powerthecoder.ModerationMenu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener{
	
	public Inventory inv;
	
	private static String savedArgs;
	public static String getArgs() {
		return savedArgs;
	}
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		createInv();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("menu")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("You are console NOT HUMAN");
				return true;
			}
			else {
				Player player = (Player) sender;
				if (args[0] == null) {
					player.sendMessage("Please enter user's name");
					return true;
				}
				else {
					// Open GUI
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) {
						player.sendMessage("Player is offline");
						return true;
					}
					else {
						savedArgs = args[0];
						player.openInventory(inv);
						return true;	
					}
				}
			}
		}
		
		// about
		if (label.equalsIgnoreCase("about")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("---------Leos Mod Menu--------");
				sender.sendMessage("      Developer: Leo Power");
				sender.sendMessage("GitHub: github.com/powerthecoder/");
				sender.sendMessage("  Website: powerthecoder.xyz/");
				sender.sendMessage("--------------------------------");
				return true;
			}
			Player player = (Player) sender;
			player.sendMessage("---------Leos Mod Menu--------");
			player.sendMessage("      Developer: Leo Power");
			player.sendMessage("GitHub: github.com/powerthecoder/");
			player.sendMessage("  Website: powerthecoder.xyz/");
			player.sendMessage("--------------------------------");
			return true;
		}
		
		return false;
	}
	
	@EventHandler()
	public void onClick(InventoryClickEvent event) {
		if(!event.getInventory().equals(inv))
			return;
		if (event.getCurrentItem() == null) return;
		if (event.getCurrentItem().getItemMeta() == null) return;
		if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		
		event.setCancelled(true);
		
		Player player = (Player) event.getWhoClicked();
		Player target = Bukkit.getPlayer(savedArgs);
		if (event.getSlot() == 0) {
			// BAN
			player.sendMessage(ChatColor.RED + "Banning " + ChatColor.BOLD + target);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/ban " + target + " You have been banned from this server.");
		}
		if (event.getSlot() == 1) {
			// KICK
			player.sendMessage(ChatColor.RED + "Kicking " + ChatColor.BOLD + target);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/kick " + target + " You have been kicked from this server.");
		}
		if (event.getSlot() == 2) {
			// OP
			player.sendMessage(ChatColor.RED + "Adding Operator to " + ChatColor.BOLD + target);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/op " + target);
			target.sendMessage(ChatColor.GOLD + "You are apart of us now!");
		}
		if (event.getSlot() == 3) {
			// KILL
			player.sendMessage(ChatColor.RED + "Killing " + ChatColor.BOLD + target);
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/kill " + target);
		}
		if (event.getSlot() == 4) {
			player.closeInventory();
		}
		
	}
	
	
	public void createInv() {
		inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "" + ChatColor.BOLD + "Select Mod Option");
		
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = item.getItemMeta();
		
		// Ban User
		meta.setDisplayName(ChatColor.RED + "BAN");
		List<String> lore = new ArrayList<String>();
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(0, item);
		
		// Kick User
		item.setType(Material.IRON_SWORD);
		meta.setDisplayName(ChatColor.RED + "KICK");
		item.setItemMeta(meta);
		inv.setItem(1, item);
		
		
		// OP User
		item.setType(Material.REDSTONE);
		meta.setDisplayName(ChatColor.RED + "OP");
		item.setItemMeta(meta);
		inv.setItem(2, item);
		
		// Kill User
		item.setType(Material.SKELETON_SKULL);
		meta.setDisplayName(ChatColor.RED + "KILL");
		item.setItemMeta(meta);
		inv.setItem(3, item);
		
		// Close GUI
		item.setType(Material.BARRIER);
		meta.setDisplayName(ChatColor.RED + "Close");
		item.setItemMeta(meta);
		inv.setItem(4, item);
		
		
		}
}



