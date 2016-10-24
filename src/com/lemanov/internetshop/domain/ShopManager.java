package com.lemanov.internetshop.domain;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.CustomerDao;
import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.DaoFactory;
import com.lemanov.internetshop.domain.exception.AutorizationException;

public class ShopManager {
	private static int counter=0;
	private int id;
	private Customer currentCustomer;
	private Basket currentBasket;
	private CustomerDao customerDao;
	private DaoFactory daoFactory;
	
	private static Logger log = Logger.getLogger(ShopManager.class.getName());
	
	public ShopManager() {
		daoFactory = DaoFactory.getInstance();
		customerDao = new CustomerDao();
		
//		currentCustomer = new Customer("Egor", "Egor", "123456", "extrmsk@gmail.com");
//		currentBasket = new Basket(currentCustomer);
	}
	
	public Customer createAccount(String login, String passwd, String name, String email) throws DAOException {
		log.debug("Create customer " + login);
		return customerDao.create(login, passwd, name, email);
	}
	
	public Customer autorisation(String login, String passwd) {

		log.trace("Trying to authenticate as " + login);
		log.debug("Reading customer from DB");
		
		Customer tempCustomer = null;
		try {
			tempCustomer = customerDao.read(login);
		} catch (DAOException e) {
			log.warn("Customer not found");
		}
		if (null == tempCustomer) {
			log.warn("Trying to authenticate with no existing user login=" + login);
			throw new IllegalArgumentException("Wrong login or password!");
		}
		if (passwd.equals(tempCustomer.getPasswd())) {
			currentCustomer = tempCustomer;
			currentBasket = new Basket(currentCustomer); 	//initialise new Basket!
			log.info("Customer " + login + " successfully logged");
			return tempCustomer;
		} else {
			log.warn("Authentication for customer=" + login + " failed. Wrong password");
			return null;
		}
	}
	
	public boolean isLoggedIn() {
		if (currentCustomer == null) {
			return false;
		} 
		return true;
	}
	
	public void logOut() {
		if (currentCustomer != null) {
			log.trace("Logging out customer " + currentCustomer.getLogin());
			currentCustomer = null;
			currentBasket = null;
		} else {
			log.trace("You have already logout");
		}
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
