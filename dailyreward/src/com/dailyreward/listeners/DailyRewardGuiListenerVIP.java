package com.dailyreward.listeners;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.dailyreward.Main;
import com.dailyreward.utils.RewardsConfig;
import com.dailyreward.utils.inventorystorage.DailyRewardInventoryStorage;
import com.dailyreward.utils.rewardsdatavip.PlayerDailyRewardsVipDataFile;

import net.luckperms.api.LuckPermsProvider;

public class DailyRewardGuiListenerVIP implements Listener {

	private Main plugin;
	private Inventory inv = null;
	private ArrayList<List<String>> reward_list= new ArrayList<List<String>>();
	private static FileConfiguration player_data_vip= null;
	private int reward_day =0;
	private boolean flag = false;

	public DailyRewardGuiListenerVIP (Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		player_data_vip = PlayerDailyRewardsVipDataFile.getFileConfiguration(); 
	}

	@EventHandler
	public void InventoryClickEvent(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		inv = DailyRewardInventoryStorage.getPlayerDailyRewardInventory(p);
		if(!e.getInventory().equals(inv)) return;
		flag=false;
		e.setCancelled(true);
		reward_list = RewardsConfig.getReward_List_VIP(LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getPrimaryGroup().toString());
		reward_day = player_data_vip.getInt("PlayerData."+p.getUniqueId()+".Reward_Days");
		//the following 2 lines are debugging
		//Bukkit.getConsoleSender().sendMessage(""+e.getSlot());
		//Bukkit.getConsoleSender().sendMessage(""+(reward_day-1));
		String last_claim_date = player_data_vip.getString("PlayerData."+p.getUniqueId().toString()+".Last_Claim_Time");
		if(e.getSlot()==(reward_day-1)&&LocalDate.now().compareTo(LocalDate.parse(last_claim_date))>=1){
			List<String> reward=reward_list.get(reward_day-1);
			ItemStack item = new ItemStack(Material.BLACK_WOOL);
			for(String items:reward) {
				String[] tmp= items.split(":");
				Material mat = Material.getMaterial(tmp[0]);
				item.setType(mat);
				item.setAmount(Integer.parseInt(tmp[1]));
				p.getInventory().addItem(item);
			}
			flag=true;
			p.closeInventory();
			player_data_vip = PlayerDailyRewardsVipDataFile.getFileConfiguration();
			player_data_vip.set("PlayerData."+p.getUniqueId().toString()+".Last_Claim_Time", LocalDate.now().toString());
			player_data_vip.set("PlayerData."+p.getUniqueId().toString()+".Reward_Days",reward_day+1);
			if(reward_day==reward_list.size()) player_data_vip.set("PlayerData."+p.getUniqueId().toString()+".Reward_Days",1);
			PlayerDailyRewardsVipDataFile.save();
			PlayerDailyRewardsVipDataFile.reload();
			DailyRewardInventoryStorage.removeInventoryFromStorage(p, inv);
		}
		return;
	}

}
