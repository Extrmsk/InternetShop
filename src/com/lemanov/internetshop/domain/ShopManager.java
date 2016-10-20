package com.lemanov.internetshop.domain;

import javax.security.sasl.AuthorizeCallback;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.domain.exception.AutorizationException;

public class ShopManager {
	private static int counter=0;
	private int id;
	private Customer currentCustomer;
	private Basket currentBasket;
	
	private static Logger log = Logger.getLogger(ShopManager.class.getName());
	
	public ShopManager() {
		currentCustomer = new Customer("Egor", "Egor", "123456", "extrmsk@gmail.com");
		currentBasket = new Basket(currentCustomer);
	}
	
	public Customer createAccount(String login, String passwd, String name, String email) {
		log.debug("Create customer " + login);
		return currentCustomer; //TODO change!!!
	}
	
	public void addGoodsToBasket (Goods goods, int amount) throws AutorizationException {
		if (currentCustomer == null) {
			log.trace("Not logged in");
			throw new AutorizationException("Autorisation required");
		}
		log.trace("Add to basket: goods=" + goods.getName() + " amount=" + amount);
		currentBasket.addItem(goods, amount);
	}
	
	public void removeGoodsFromBasket(Goods goods) throws AutorizationException {
		if (currentCustomer == null) {
			log.trace("Not logged in");
			throw new AutorizationException("Autorisation required");
		}
		log.trace("Remove from basket: goods=" + goods.getName());
		currentBasket.removeItem(goods);
	}
	
	public int getPrice() {
		return currentBasket.getPrice();
	}

}
