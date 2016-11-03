package com.lemanov.internetshop.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class ShopManagerHandler {
	
	private static Logger log = Logger.getLogger(ShopManagerHandler.class.getName());
	private static Set<ShopManager> shopManagers = new HashSet<ShopManager>();
	
	public static ShopManager getShopManagerByID(int id) {
		for (ShopManager shman : shopManagers) {
			if (shman.getID() == id) {
				log.trace("Return shopManager by id=" + id);
				return shman;
			}
		}
		log.warn("shopManager by id=" + id + " is not found");
		return null;
	}
	
	public static Set<ShopManager> getShopManagers() {
		return shopManagers;
	}
	
	public static void add(ShopManager shopManager) {
		shopManagers.add(shopManager);
	}

}
