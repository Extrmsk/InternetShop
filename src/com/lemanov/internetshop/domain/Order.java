package com.lemanov.internetshop.domain;

import org.apache.log4j.Logger;

public class Order {
	
	private int id;
	private Customer customer;
	private String deliveryAddress;
	private ShippingType shipType;
	private OrderStatus status;
	private String createDate;
	
	Logger log = Logger.getLogger(Order.class.getName());
	
	public Order(Customer customer, String address, ShippingType shipType) {
		this.customer = customer;
		this.deliveryAddress = address;
		this.shipType = shipType;
		log.trace("Created order for customer=" + customer.getName());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public ShippingType getShipType() {
		return shipType;
	}

	public void setShipType(ShippingType shipType) {
		this.shipType = shipType;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getDate() {
		return createDate;
	}

	public void setDate(String date) {
		this.createDate = date;
	}
	
	

}
