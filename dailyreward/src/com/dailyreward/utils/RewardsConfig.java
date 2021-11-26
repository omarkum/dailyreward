package com.dailyreward.utils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class RewardsConfig {
	
	
	private static FileConfiguration dataconfig= null;
	private static File configfile = null;
	private static ArrayList<List<String>> reward_list =  new ArrayList<List<String>>();
	private static ArrayList<List<String>> reward_list_vip = new ArrayList<List<String>>();
	
	
	public static void setup() {
		configfile = new File(Bukkit.getPluginManager().getPlugin("dailyreward").getDataFolder(),"config.yml");
		
		if(!configfile.exists()) {
			try {
				configfile.createNewFile();
			}catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage("Error when creating new config file!");
			}
			dataconfig = YamlConfiguration.loadConfiguration(configfile);
			Map<String,List<String>> rewards = new HashMap<String,List<String>>();
			List<String> day1 = new ArrayList<String>();
			day1.add("OAK_LOG:16");
			rewards.put("Day1",day1);
			
			List<String> day2 = new ArrayList<String>();
			day2.add("COBBLESTONE:64");
			rewards.put("Day2",day2);
			
			List<String> day3 = new ArrayList<String>();
			day3.add("COAL:32");
			rewards.put("Day3",day3);
			
			List<String> day4 = new ArrayList<String>();
			day4.add("SAND:32");
			rewards.put("Day4",day4);
			
			List<String> day5 = new ArrayList<String>();
			day5.add("LAPIS_LAZULI:32");
			rewards.put("Day5",day5);
			
			List<String> day6 = new ArrayList<String>();
			day6.add("COPPER_INGOT:8");
			rewards.put("Day6",day6);
			
			List<String> day7 = new ArrayList<String>();
			day7.add("GOLD_INGOT:8");
			rewards.put("Day7",day7);
			
			dataconfig.createSection("Rewards");
			dataconfig.set("Rewards", rewards);
			save();
		
		}
		
		
		dataconfig = YamlConfiguration.loadConfiguration(configfile);
		Set<String> testList = dataconfig.getConfigurationSection("Rewards").getKeys(true);
		for(String n:testList) {
			reward_list.add(dataconfig.getStringList("Rewards."+n));
			
		}
		
	}
	
	
	public static FileConfiguration getFileConfiguration() {
		return dataconfig;	
	}
	public static ArrayList<List<String>> getReward_List(){
		return reward_list;
	}
	public static ArrayList<List<String>> getReward_List_VIP(String s) {
		reward_list_vip.clear();
		Set<String> reward_list_vip_ranks = dataconfig.getConfigurationSection(s).getKeys(false);
		for(String n:reward_list_vip_ranks) {
			reward_list_vip.add(dataconfig.getStringList(s+"."+n));
		}
		return reward_list_vip;
	}
	public static void save(){
		try {
			dataconfig.save(configfile);
		}catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("Error when saving config file!");
		}
	}
	
	public static void reload(){
		dataconfig = YamlConfiguration.loadConfiguration(configfile);
	}
	
}
