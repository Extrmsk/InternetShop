package com.lemanov.internetshop.domain;

import java.util.HashSet;
import java.util.Set;

public class ShopManagerHandler {
	
	private static Set<ShopManager> shopManagers = new HashSet<ShopManager>();
	
	public static ShopManager getShopManagerByID(int id) {
		for (ShopManager shman : shopManagers) {
			if (shman.getID() == id) {
				return shman;
			}
		}
		return null;
	}
	
	public static Set<ShopManager> getShopManagers() {
		return shopManagers;
	}
	
	public static void add(ShopManager shopManager) {
		shopManagers.add(shopManager);
	}

}
