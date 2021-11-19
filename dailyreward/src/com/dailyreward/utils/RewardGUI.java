package com.dailyreward.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.dailyreward.Main;

import net.md_5.bungee.api.ChatColor;

public class RewardGUI implements Listener {
	private static Main plugin;
	private Inventory inv = null;
	private Player p;
	private ArrayList<List<String>> reward_list= new ArrayList<List<String>>();
	private static FileConfiguration player_data= null;
	private int reward_day =0;
	
	public RewardGUI(Player p){
		reward_list = RewardsConfig.getReward_List();
		player_data = PlayerDailyRewardsDataFile.getFileConfiguration();
		this.p=p;
		//Bukkit.getConsoleSender().sendMessage(""+reward_list.size());
		int size = reward_list.size()/9;
		if(size<1) size =1;
		//Bukkit.getConsoleSender().sendMessage(""+size);
		inv = Bukkit.createInventory(p, size*9, "Daily Rewards");
		//reward_day = player_data.getInt("PlayerData."+p.getUniqueId().toString()+".Reward_Days");
	}
	public RewardGUI (Main plugin) {
		this.plugin = plugin;
		/*reward_list = RewardsConfig.getReward_List();
		player_data = PlayerDailyRewardsDataFile.getFileConfiguration();
		int size = reward_list.size()/9;
		if(size<1) size =1;
		//Bukkit.getConsoleSender().sendMessage(""+size);
		inv = Bukkit.createInventory(p, size*9, "Daily Rewards");*/
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	public Inventory generateGUI() {
	
		
		/*reward_list = RewardsConfig.getReward_List();
		player_data = PlayerDailyRewardsDataFile.getFileConfiguration();
		int size = reward_list.size()/9;
		if(size<1) size =1;
		//Bukkit.getConsoleSender().sendMessage(""+size);
		inv = Bukkit.createInventory(p, size*9, "Daily Rewards");*/
		
		
		
		
		ItemStack item = new ItemStack(Material.BLACK_WOOL);
		ItemMeta meta = null;
		reward_day = player_data.getInt("PlayerData."+p.getUniqueId().toString()+".Reward_Days");
		//Bukkit.getConsoleSender().sendMessage(""+reward_day);
		
		int day=1;
		for(List<String> days:reward_list) {
			String[] tmp=days.get(0).split(":");
			Material mat = Material.getMaterial(tmp[0]);
			item.setType(mat);
			meta=item.getItemMeta();
			item.setAmount(Integer.parseInt(tmp[1]));
			meta.setDisplayName("Day"+day+" Reward");
			List<String> lore = days;
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); 
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			if(day<reward_day) {lore.add(ChatColor.RED+"You Have Claimed This Already.");}else if(day>reward_day) {lore.add(ChatColor.GRAY+"You May Claimed This In Future.");} else {lore.add(ChatColor.GREEN+"Click To Claim!"); }
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(day-1, item);
			day++;
		}
		//p.openInventory(inv);
		return inv;
	}
	@EventHandler
	/*public void InventoryInteractEvent(InventoryInteractEvent e) {
		e.setCancelled(true);
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getHolder().toString()==p.getName()){
			p.sendMessage("yeah");
		}
	}*/
	public void InventoryClickEvent(InventoryClickEvent e) {
		p = (Player) e.getWhoClicked();
		inv = DailyRewardInventoryStorage.getPlayerDailyRewardInventory((Player) e.getWhoClicked());
		if(!e.getInventory().equals(inv)) return;
		e.setCancelled(true);
		//p = (Player) e.getWhoClicked();
		reward_day=player_data.getInt("PlayerData."+p.getUniqueId().toString()+".Reward_Days");
		Bukkit.getConsoleSender().sendMessage(""+e.getSlot());
		Bukkit.getConsoleSender().sendMessage(""+(reward_day-1));
		if(LocalDate.now().compareTo(LocalDate.parse(player_data.getString("PlayerData."+p.getUniqueId().toString()+".Last_Claim_Time")))>=0){return;}
		//Bukkit.getConsoleSender().sendMessage(""+e.getClickedInventory().getHolder().toString().contains(p.getName()));
		if(e.getSlot()==(reward_day-1)){
			List<String> reward=reward_list.get(reward_day-1);
			ItemStack item = new ItemStack(Material.BLACK_WOOL);
			//ItemMeta meta = null;
			for(String items:reward) {
				String[] tmp= items.split(":");
				Material mat = Material.getMaterial(tmp[0]);
				item.setType(mat);
				//meta=item.getItemMeta();
				item.setAmount(Integer.parseInt(tmp[1]));
				//item.setItemMeta(meta);
				p.getInventory().addItem(item);
				player_data.set("PlayerData."+p.getUniqueId().toString()+".Last_Claim_Time", LocalDate.now().toString());
				player_data.set("PlayerData."+p.getUniqueId().toString()+".Reward_Days",reward_day+1);
			}
			//p.sendMessage("yeeeee");
		}
		
		return;
	}
	
	
	
	
}
