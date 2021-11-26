package com.dailyreward;

import org.bukkit.plugin.java.JavaPlugin;

import com.dailyreward.actions.CommandExecution;
import com.dailyreward.listeners.DailyRewardGuiListener;
import com.dailyreward.listeners.DailyRewardGuiListenerVIP;
import com.dailyreward.utils.RewardsConfig;
import com.dailyreward.utils.rewardsdata.PlayerDailyRewardsDataFile;
import com.dailyreward.utils.rewardsdata.PlayerDailyRewardsDataProcessing;
import com.dailyreward.utils.rewardsdatavip.PlayerDailyRewardsVipDataFile;
import com.dailyreward.utils.rewardsdatavip.PlayerDailyRewardsVipDataProcessing;

public class Main extends JavaPlugin{
	
	
	@Override
	public void onEnable() {
		RewardsConfig.setup();
		PlayerDailyRewardsDataFile.setup();
		PlayerDailyRewardsVipDataFile.setup();
		RewardsConfig.save();
		PlayerDailyRewardsDataFile.save();
		PlayerDailyRewardsVipDataFile.save();
		
		//new RewardGUI(this);
		//new TestRewardGUI(this);
		new DailyRewardGuiListener(this);
		new DailyRewardGuiListenerVIP(this);
		command_dr();
		new PlayerDailyRewardsDataProcessing(this);
		new PlayerDailyRewardsVipDataProcessing(this);
		
		
	}
	@Override
	public void onDisable() {
		
	}
	
	
public void command_dr() {
		
		getCommand("dailyreward").setExecutor(new CommandExecution());
	}
}

