package com.dailyreward.utils.rewardsdatavip;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.fusesource.jansi.Ansi.Color;

public class PlayerDailyRewardsVipDataFile {

	private static FileConfiguration player_data_vip= null;
	private static File datafile_vip = null;

	public static void setup() {
		datafile_vip = new File(Bukkit.getPluginManager().getPlugin("dailyreward").getDataFolder(),"data_vip.yml");
		
		if(!datafile_vip.exists()) {
			try {
				datafile_vip.createNewFile();
			}catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage("Error when creating new data file!");
			}
			Bukkit.getConsoleSender().sendMessage(Color.RED+"NO FILE CREATED MAKING NEW ONE");
			player_data_vip = YamlConfiguration.loadConfiguration(datafile_vip);
			save();
			
			
		}
		player_data_vip = YamlConfiguration.loadConfiguration(datafile_vip);
	}
	
	public static FileConfiguration getFileConfiguration() {
		return player_data_vip;	
	}
	public static void save(){
		try {
			player_data_vip.save(datafile_vip);
		}catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("Error when saving config file!");
		}
	}
	
	public static void reload(){
		player_data_vip = YamlConfiguration.loadConfiguration(datafile_vip);
	}
}
