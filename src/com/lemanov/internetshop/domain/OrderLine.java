package com.lemanov.internetshop.domain;

import org.apache.log4j.Logger;

public class OrderLine {
	
	private Goods goodsItem;
	private int amountsss;
	
	private static Logger log = Logger.getLogger(OrderLine.class.getName());
	
	public OrderLine(Goods goodsItem, int amount) {
		this.goodsItem = goodsItem;
		this.amountsss = amount;
		log.trace("Order line for goodsName=" + goodsItem.getName() + ", amount=" + amount + " is created");
	}

	public Goods getGoodsItem() {
		return goodsItem;
	}

	public void setGoodsItem(Goods goodsItem) {
		this.goodsItem = goodsItem;
	}

	public int getAmount() {
		return amountsss;
	}

	public void setAmount(int amount) {
		this.amountsss = amount;
	}
	
	public int getPrice() {
		return goodsItem.getPrice() * amountsss;
	}
	
	public String getGoodsName() {
		return goodsItem.getName();
	}
	
	public int getGoodsID() {
		return goodsItem.getId();
	}
	
}
