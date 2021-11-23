package com.dailyreward.utils.gui;

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
import com.dailyreward.utils.RewardsConfig;
import com.dailyreward.utils.inventorystorage.DailyRewardInventoryStorage;
import com.dailyreward.utils.rewardsdata.PlayerDailyRewardsDataFile;

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
		int size = reward_list.size()/9;
		if(size<1) size =1;
		inv = Bukkit.createInventory(p, size*9, "Daily Rewards");
	}
	
	
	public RewardGUI (Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	public Inventory generateGUI() {
		ItemStack item = new ItemStack(Material.BLACK_WOOL);
		ItemMeta meta = null;
		reward_day = player_data.getInt("PlayerData."+p.getUniqueId()+".Reward_Days");
		String last_claim_date = player_data.getString("PlayerData."+p.getUniqueId().toString()+".Last_Claim_Time");
		
		int day=1;
		for(List<String> days:reward_list) {
			String[] tmp=days.get(0).split(":");
			Material mat = Material.getMaterial(tmp[0]);
			item.setType(mat);
			meta=item.getItemMeta();
			item.setAmount(Integer.parseInt(tmp[1]));
			meta.setDisplayName("Day"+day+" Reward");
			List<String> lore = new ArrayList<String>(days);
			if(LocalDate.now().compareTo(LocalDate.parse(last_claim_date))>=1) {
				if(day<reward_day) {
					lore.add(ChatColor.RED+"You Have Claimed This Already.");
				}else if(day>reward_day) {
					lore.add(ChatColor.GRAY+"You May Claim This In Future.");
				}else {
					lore.add(ChatColor.GREEN+"Click To Claim!"); 
				}	
			}
			if(LocalDate.now().compareTo(LocalDate.parse(last_claim_date))==0) {
				if(day<reward_day-1) {
					lore.add(ChatColor.RED+"You Have Claimed This Already");
				}else if (day>reward_day-1) {
					lore.add(ChatColor.GRAY+"Your May Claim This In Future");
				}else {
					lore.add(ChatColor.GOLD+"You Have Claimed This Today!");
					
				}
			}
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); 
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(day-1, item);
			day++;
		}
		DailyRewardInventoryStorage.addInventoryToStorage(p,inv);
		return inv;
	}
	
	
}

