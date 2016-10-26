package com.lemanov.internetshop.domain;

import org.apache.log4j.Logger;

public class OrderLine {
	
	private Goods goods;
	private int amount;
	
	private static Logger log = Logger.getLogger(Goods.class.getName());
	
	public OrderLine(Goods goods, int amount) {
		this.goods = goods;
		this.amount = amount;
		log.trace("Order line for item=" + goods.getName() + ", amount=" + amount + " is created");
	}
	
	public Goods getItem() {
		return goods;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		if (amount <= 0) {
			log.debug("amount <= 0");
			throw new IllegalArgumentException();
		}
		this.amount = amount;
		log.trace("Amount setted to: " + amount);
	}
	
	public void increaseAmount(int incValue) {
		this.amount += incValue;
	}
	
	public void reduceAmount(int redValue) {
		this.amount -= redValue;
	}
	
	public int getPrice() {
		return goods.getPrice() * amount;
	}

	@Override
	public String toString() {
		return "OrderLine [goods=" + goods.getName() + ", amount=" + amount + "]";
	}
	
	

}
