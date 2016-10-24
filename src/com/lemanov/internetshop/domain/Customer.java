package com.lemanov.internetshop.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Customer {
	private int id;
	private String name;
	private String login;
	private String passwd;
	private String email;
	private String address;
	private String phone;
	private String creditCardInfo = "";
	
	private static Logger log = Logger.getLogger(Customer.class.getName());
	
	public Customer(String login, String passwd, String name, String email) {
		this.name = name;
		this.login = login;
		this.passwd = passwd;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreditCardInfo() {
		return creditCardInfo;
	}

	public void setCreditCardInfo(String creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", login=" + login + ", passwd=" + passwd + ", email=" + email
				+ "]";
	}
	
	
	
	
	
	
}
