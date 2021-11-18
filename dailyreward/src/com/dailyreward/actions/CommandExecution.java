package com.dailyreward.actions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.dailyreward.utils.PlayerDailyRewardsDataFile;
import com.dailyreward.utils.TestRewardGUI;

public class CommandExecution implements CommandExecutor{

	private static FileConfiguration player_data= null;
	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
		// TODO Auto-generated method stub
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage("This command only available to players");
			return true;
		}
		player_data =PlayerDailyRewardsDataFile.getFileConfiguration();
		
		Player p = (Player) sender;
		//Inventory reward_gui = RewardGUI.GeneratePlayerRewardGUI();
		
		if(args.length<1) {
			//RewardGUI rewardGUI = new RewardGUI(p);
			//p.openInventory(rewardGUI.generateGUI());
			TestRewardGUI rewardGUI = new TestRewardGUI(p);
			p.openInventory(rewardGUI.generateGUI());
			//rewardGUI.generateGUI(p);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("test")) {
			Inventory inv = Bukkit.createInventory(p, 9, "Daily Rewards");
			ItemStack item = new ItemStack(Material.BLACK_WOOL);
			inv.addItem(item);
			
			p.openInventory(inv);
			Bukkit.getPlayer("Adrian1516").openInventory(inv);
			//p.sendMessage(player_data.get(p.getUniqueId().toString()).toString());
			return true;
		}
		
		
		
		
		p.sendMessage("recieve");
		
		
		
		
		
		
		
		
		return true;
	}

}
