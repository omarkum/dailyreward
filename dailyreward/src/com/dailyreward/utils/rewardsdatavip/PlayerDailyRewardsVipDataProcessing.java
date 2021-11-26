package com.dailyreward.utils.rewardsdatavip;

import java.time.LocalDate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.dailyreward.Main;

import net.luckperms.api.LuckPermsProvider;

public class PlayerDailyRewardsVipDataProcessing implements Listener {

	private static Main plugin;
	private static FileConfiguration player_data_vip= null;
	
	public PlayerDailyRewardsVipDataProcessing(Main plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) return;
		if(LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getPrimaryGroup().toString()=="default") return;
		player_data_vip=PlayerDailyRewardsVipDataFile.getFileConfiguration();
		if(player_data_vip.get("PlayerData."+p.getUniqueId().toString())==null) {
			addNewPlayer(p.getUniqueId().toString(),p);
		}
		
		//display notification to player who haven't claim their daily reward when player log-in
		LocalDate last_claim_date = LocalDate.parse(player_data_vip.get("PlayerData."+p.getUniqueId()+".Last_Claim_Time").toString());
		LocalDate current_date = LocalDate.now();
		if(current_date.compareTo(last_claim_date)>0) {
			new BukkitRunnable() {
				@Override
				public void run() {
					p.sendMessage(ChatColor.GOLD+"Oh! You have Unclaimed Daily Reward! Use /dr vip or /dailyreward vip to claim your rewards!");
					p.playSound(p.getLocation(), "entity.player.levelup", (float) 0.5, 0);
				}
			}.runTaskLater(plugin, 20L);
			
		}
		
	}

	private void addNewPlayer(String uuid,Player p) {
		player_data_vip.set("PlayerData."+uuid+".Name",p.getName());
		player_data_vip.set("PlayerData."+uuid+".UUID",uuid);
		player_data_vip.set("PlayerData."+uuid+".Login_Days",1);
		player_data_vip.set("PlayerData."+uuid+".Reward_Days",1);
		player_data_vip.set("PlayerData."+uuid+".Last_Claim_Time","2000-01-01");
		PlayerDailyRewardsVipDataFile.save();
		PlayerDailyRewardsVipDataFile.reload();
	}
}
