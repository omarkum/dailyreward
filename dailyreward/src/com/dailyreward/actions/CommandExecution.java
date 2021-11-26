package com.dailyreward.actions;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.dailyreward.utils.gui.RewardGUI;
import com.dailyreward.utils.gui.RewardGUIVIP;

import net.luckperms.api.LuckPermsProvider;
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
		}else if(args[0].equalsIgnoreCase("vip")) {
			if(!Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) { p.sendMessage(ChatColor.GOLD+"This Function Is Not Available Now!"); return true;}
			if(!LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getPrimaryGroup().toString().equalsIgnoreCase("default")) {
				RewardGUIVIP rewardGUIVIP = new RewardGUIVIP(p);
				p.openInventory(rewardGUIVIP.generateGUIVIP());
				return true;
			
			}else {
				p.sendMessage("You have any rank yet.");
			}
		}else {
			p.sendMessage(ChatColor.GOLD+"Usage: /dr or /dailyreward and /dr vip [rank] or /dailyreward vip [rank]");
		}
		
		
		
		
		
		
		
		return true;
	}

}
