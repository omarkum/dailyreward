package com.dailyreward.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DailyRewardInventoryStorage {
	private static Map<Player,Inventory> invs = new HashMap<Player,Inventory>();


	public Map<Player,Inventory> getInventoryMap() {
		return invs;
	}
	
	public static Inventory getPlayerDailyRewardInventory(Player p) {
		return invs.get(p);
	}

	public static void addInventoryToStorage(Player p,Inventory inv) {
		invs.put(p, inv);
		return;
	}
	
	public static void removeInventoryFromStorage(Player p,Inventory inv) {
		invs.remove(p, inv);
		return;
	}
	public static boolean isPlayerInventoryExist(Player p,Inventory inv) {
		if(invs.containsKey(p)&&invs.containsValue(inv)) {
			return true;
		}
		return false;
	}



}
