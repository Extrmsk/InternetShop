package com.lemanov.internetshop.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Basket {
	
	private static Logger log = Logger.getLogger(Basket.class.getName());
	private Customer customer;
	private List<OrderLine> basketItems;
	private int price = 0;
	
	public Basket (Customer customer) {
		this.customer = customer;
		basketItems = new ArrayList<OrderLine>();
	}
	
	public void addGoodsItem(Goods goods, int amount) {
		basketItems.add(new OrderLine(goods, amount));
	}
	
	public int getPrice() {
		if (basketItems != null) {
			for (OrderLine orderLine : basketItems) {
				price += orderLine.getPrice();
			}
		}
		return price;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderLine> getBasketItems() {
		return basketItems;
	}

	public void setBasketItems(List<OrderLine> basketItems) {
		this.basketItems = basketItems;
	}
	
}
