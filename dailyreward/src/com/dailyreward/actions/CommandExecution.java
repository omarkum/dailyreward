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
import com.dailyreward.utils.RewardGUI;

import net.md_5.bungee.api.ChatColor;

public class CommandExecution implements CommandExecutor{

	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
		// TODO Auto-generated method stub
		if(!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage("This command only available to players");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(args.length<1) {
			RewardGUI rewardGUI = new RewardGUI(p);
			p.openInventory(rewardGUI.generateGUI());
			return true;
		}else {
			p.sendMessage(ChatColor.GOLD+"Usage: /dr or /dailyreward");
		}
		
		
		
		
		
		
		
		return true;
	}

}
