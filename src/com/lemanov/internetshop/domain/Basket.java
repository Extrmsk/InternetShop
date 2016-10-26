package com.lemanov.internetshop.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Basket {
	private static int counter = 0;
	private int id;
	private List<OrderLine> orderLines;
	private Customer customer;
	
	private static Logger log = Logger.getLogger(Basket.class.getName());
	
	public Basket(Customer customer) {
		this.id = ++counter;
		this.customer = customer;
		this.orderLines = new ArrayList<OrderLine>();
		System.out.println("Basket is created");
	}

	public void addItem(Goods goods, int amount) {
		OrderLine ol = getExistGoodsLine(goods);
		if (ol != null) {
			ol.increaseAmount(amount);
			log.debug("Increasing existing OrderLine on value=" + amount);
		} else {
			orderLines.add(new OrderLine(goods, amount));
			log.debug("Added " + amount + " " + goods.getName() + " to basket " + id);
		}
		goods.reduceAmount(amount);
	}
	
	public void removeItem(Goods goods) {
		if (goods == null) {
			log.debug("goods = null");
			throw new IllegalArgumentException();
		}
		log.trace("Searching for item to delete");
		for (int i=0; i<orderLines.size(); i++) {
			if (orderLines.get(i).getItem().equals(goods)) {
				log.trace("Goods " + goods.getName() + " found");
				orderLines.remove(i);
				log.trace("Goods " + goods.getName() + " removed from basket " + id);
			}
		}
	}
	
	private OrderLine getExistGoodsLine(Goods goods) {
		for (OrderLine od : orderLines) {
			if (od.getItem().equals(goods)) {
				return od;
			}
		}
		return null;
	}
	
	public int getPrice() {
		int result = 0;
		for (OrderLine orderLine : orderLines) {
			result += orderLine.getPrice();
		}
		log.trace("Get overall price for cart " + id + " = " + result);
		return result;
	}
	
	public void clearBasket() {
		orderLines.clear();
		log.trace("Cart " + id + " is clear");
	}
	
	public int getId() {
		return id;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	
	

}
