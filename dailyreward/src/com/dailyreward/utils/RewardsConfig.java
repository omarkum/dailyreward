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
			//dataconfig.createSection("Rewards.Day1");
			//dataconfig.set("Rewards.Day1", item);
			dataconfig.set("Rewards", rewards);
			save();
			//Bukkit.getConsoleSender().sendMessage(rewards.toString());
		
		}
		dataconfig = YamlConfiguration.loadConfiguration(configfile);
		Set<String> testList = dataconfig.getConfigurationSection("Rewards").getKeys(true);
		
		//Bukkit.getConsoleSender().sendMessage(testList.toString());
		
		
		
		
		
		for(String n:testList) {
			reward_list.add(dataconfig.getStringList("Rewards."+n));
			
		}
		//Bukkit.getConsoleSender().sendMessage(reward_list.toString());
		
		//Bukkit.getConsoleSender().sendMessage(reward_items);
	}
	
	
	public static FileConfiguration getFileConfiguration() {
		return dataconfig;	
	}
	public static ArrayList<List<String>> getReward_List(){
		return reward_list;
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
	/*public static void test() {
		dataconfig = YamlConfiguration.loadConfiguration(configfile);
		List<String> test = dataconfig.getStringList("Rewards.Day1");
		//List<String> test = new ArrayList<String>();
		test.add("test1");
		Bukkit.getConsoleSender().sendMessage(test.toString());
	}*/
}
