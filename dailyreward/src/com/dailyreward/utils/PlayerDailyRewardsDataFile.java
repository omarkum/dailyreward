package com.dailyreward.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.fusesource.jansi.Ansi.Color;

public class PlayerDailyRewardsDataFile {
	
	
	
	private static FileConfiguration player_data= null;
	private static File datafile = null;

	public static void setup() {
		datafile = new File(Bukkit.getPluginManager().getPlugin("dailyreward").getDataFolder(),"data.yml");
		
		if(!datafile.exists()) {
			try {
				datafile.createNewFile();
			}catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage("Error when creating new data file!");
			}
			Bukkit.getConsoleSender().sendMessage(Color.RED+"NO FILE CREATED MAKING NEW ONE");
			player_data = YamlConfiguration.loadConfiguration(datafile);
			//player_data.createSection("PlayersData");
			save();
			
			
		}
		player_data = YamlConfiguration.loadConfiguration(datafile);
	}
	
	public static FileConfiguration getFileConfiguration() {
		return player_data;	
	}
	public static void save(){
		try {
			player_data.save(datafile);
		}catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("Error when saving config file!");
		}
	}
	
	public static void reload(){
		player_data = YamlConfiguration.loadConfiguration(datafile);
	}
	
	
	
	
	
	
	
	
}

