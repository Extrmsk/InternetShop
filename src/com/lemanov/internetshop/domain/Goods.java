package com.lemanov.internetshop.domain;

import org.apache.log4j.Logger;

public class Goods {
	private int id;
	private String name;
	private int price;
	private int groupID;
	private int amount;
	
	Logger log = Logger.getLogger(Goods.class.getName());
	
	public Goods(String name, int price, int amount, int groupID) {
		this.name = name;
		this.price = price;
		this.groupID = groupID;
		this.amount = amount;
		log.trace("Created goods: name=" + name);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id <= 0) {
			log.debug("id <= 0");
			throw new IllegalArgumentException();
		}
		this.id = id;
		log.trace("Set id to " + id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (null == name || name.isEmpty()) {
			log.debug("Name = null or empty");
			throw new IllegalArgumentException();
		}
		this.name = name;
		log.trace("Set name to " + name);
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		if (price <= 0) {
			log.debug("price <= 0");
			throw new IllegalArgumentException();
		}
		this.price = price;
		log.trace("Set price to " + price);
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		if (groupID <= 0) {
			log.debug("groupID <= 0");
			throw new IllegalArgumentException();
		}
		this.groupID = groupID;
		log.trace("Set groupID to " + groupID);
	}

	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", price=" + price + ", groupID=" + groupID + "]";
	}

	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int newAmount) {
		if (newAmount <= 0) {
			log.debug("amount <= 0");
			throw new IllegalArgumentException();
		}
		this.amount = newAmount;
		log.trace("New amount for " + name + " is " + amount);
	}
	
	public void increaseAmount(int value) {
		this.amount += value;
	}
	
	public void reduceAmount(int value) {
		this.amount -= value;
	}
	
	
	
	
	
}
