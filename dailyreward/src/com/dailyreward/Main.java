package com.dailyreward;

import org.bukkit.plugin.java.JavaPlugin;

import com.dailyreward.actions.CommandExecution;
import com.dailyreward.utils.PlayerDailyRewardsDataFile;
import com.dailyreward.utils.PlayerDailyRewardsDataProcessing;
import com.dailyreward.utils.RewardsConfig;
import com.dailyreward.utils.TestRewardGUI;

public class Main extends JavaPlugin{
	
	
	@Override
	public void onEnable() {
		RewardsConfig.setup();
		PlayerDailyRewardsDataFile.setup();
		RewardsConfig.save();
		PlayerDailyRewardsDataFile.save();
		//new RewardGUI(this);
		new TestRewardGUI(this);
		test();
		new PlayerDailyRewardsDataProcessing(this);
		
		
	}
	@Override
	public void onDisable() {
		
	}
	
	
public void test() {
		
		getCommand("dailyreward").setExecutor(new CommandExecution());
	}
}

