package com.lemanov.internetshop.domain;

import org.apache.log4j.Logger;

public class Customer {
	private int id;
	private String name;
	private String login;
	private String passwd;
	private String email;
	
	private static Logger log = Logger.getLogger(Customer.class.getName());
	
	public Customer(String name, String login, String passwd, String email) {
		this.name = name;
		this.login = login;
		this.passwd = passwd;
		this.email = email;
		System.out.println("customer is created");
	}
	
	
}
