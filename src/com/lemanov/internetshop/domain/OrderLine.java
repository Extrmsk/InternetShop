package com.lemanov.internetshop.domain;

import org.apache.log4j.Logger;

public class OrderLine {
	
	private int goodsID;
	private int amount;
	
	private static Logger log = Logger.getLogger(Goods.class.getName());
	
	public OrderLine(int goodsID, int amount) {
		this.goodsID = goodsID;
		this.amount = amount;
		log.trace("Order line for goodsID=" + goodsID + ", amount=" + amount + " is created");
	}

	public int getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(int goodsID) {
		this.goodsID = goodsID;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
