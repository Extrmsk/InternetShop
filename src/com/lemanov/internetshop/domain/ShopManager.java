package com.lemanov.internetshop.domain;

import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.CustomerDao;
import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.DaoFactory;
import com.lemanov.internetshop.domain.exception.AutorizationException;

public class ShopManager {
	private static int counter=0;
	private int id;
	private Customer curCustomer;
	private Basket curBasket;
	private CustomerDao customerDao;
	private DaoFactory daoFactory;
	private GoodsManager goodsManager;
	
	private static Logger log = Logger.getLogger(ShopManager.class.getName());
	
	public ShopManager() {
		id = ++counter;
		log.info("***************** Create new ShopManager id=" + id + " *****************");
//		daoFactory = DaoFactory.getInstance();
		customerDao = new CustomerDao();
		goodsManager = new GoodsManager();
	}
	
	public Customer createAccount(String login, String passwd, String name, String email) throws DAOException {
		log.debug("Create customer " + login);
		return customerDao.create(login, passwd, name, email);
	}
	
	public Customer authorization(String login, String passwd) throws DAOException  {

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
			curCustomer = tempCustomer;
			curBasket = new Basket(curCustomer); 	//initialise new Basket!
			log.info("Customer " + login + " successfully logged");
			return tempCustomer;
		} else {
			log.warn("Authentication for customer=" + login + " failed. Wrong password");
			return null;
		}
	}
	
	public boolean isLoggedIn() {
		if (curCustomer == null) {
			return false;
		} 
		return true;
	}
	
	public void logOut() {
		if (curCustomer != null) {
			log.trace("Logging out customer " + curCustomer.getLogin());
			curCustomer = null;
			curBasket = null;
		} else {
			log.trace("You have already logout");
		}
	}
	
	public void changePasswd(String oldPasswd, String newPasswd) throws AutorizationException, DAOException {
		if (curCustomer == null) {
			log.trace("Not logged in");
			throw new AutorizationException("Need autorisation");
		}
		if ( oldPasswd == null || newPasswd == null || oldPasswd.isEmpty() || newPasswd.isEmpty() ) {
			log.trace("Some field is null or empty");
			throw new IllegalArgumentException("Passwd can't be empty");
		}
		if ( !curCustomer.getPasswd().equals(oldPasswd) ) {
			log.trace("Wrong old password");
			throw new IllegalArgumentException("Wrong old password");
		}
		if (curCustomer.getPasswd().equals(oldPasswd) && oldPasswd.equals(newPasswd)) {
			log.trace("The old and the new passwords is the same");
			throw new IllegalArgumentException("The old and the new passwords is the same");
		}
		log.trace("Updating cutomer " + curCustomer.getLogin() + " info");
		customerDao.update(curCustomer.getLogin(), newPasswd, curCustomer.getName(),
				curCustomer.getAddress(), curCustomer.getPhone(),
				curCustomer.getEmail(), curCustomer.getCreditCardInfo());
		log.trace("Customer " + curCustomer.getLogin() + " changed password.");
		log.trace("Setting new customer info");
		curCustomer = customerDao.read(curCustomer.getLogin());
	}
	
	public void changeAddress(String newAddress) throws AutorizationException, DAOException {
		if (curCustomer == null) {
			log.trace("Not logged in");
			throw new AutorizationException("Need autorisation");
		}
		log.trace("Updating cutomer " + curCustomer.getLogin() + " info");
		customerDao.update(curCustomer.getLogin(), curCustomer.getPasswd(), curCustomer.getName(),
				newAddress, curCustomer.getPhone(), curCustomer.getEmail(), curCustomer.getCreditCardInfo());
		log.trace("Customer " + curCustomer.getLogin() + " changed email.");
		log.trace("Setting new customer info");
		curCustomer = customerDao.read(curCustomer.getLogin());
	}
	
	public Goods addGoodsItem(String name, int price, int groupID, int amount) throws AutorizationException, DAOException {
		if (curCustomer == null) {
			log.trace("Not logged in");
			throw new AutorizationException("Need autorisation");
		}
		if (!curCustomer.getLogin().equals("Admin")) {
			log.warn("Not admin account. Adding goods is forbidden.");
			throw new AutorizationException("Admin autorisation required");
		}
		return goodsManager.addGoodsItem(name, price, groupID, amount);
	}
	
	public Group addGroup(String name, int parentID) throws AutorizationException, DAOException {
		if (curCustomer == null) {
			log.trace("Not logged in");
			throw new AutorizationException("Need autorisation");
		}
		if (!curCustomer.getLogin().equals("Admin")) {
			log.warn("Not admin account. Adding groups is forbidden.");
			throw new AutorizationException("Admin autorisation required");
		}
		return goodsManager.addGroup(name, parentID);
	}
	
	// searching the match of the name expression	
	public List<Goods> findGoodsByName(String name) throws DAOException {
		log.trace("Returning goods by name=" + name);
		return goodsManager.findGoodsByName(name);
	}
	
	public List<Goods> getAllGoods() throws DAOException {
		log.trace("Get all goods");
		return goodsManager.getAllGoods();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void addGoodsToBasket (Goods goods, int amount) throws AutorizationException, DAOException {
		if (curCustomer == null) {
			log.trace("Not logged in");
			throw new AutorizationException("Autorisation required");
		}
		if ( amount == 0 || amount > goods.getAmount() ) {
			log.warn("Not enough items in the store");
			throw new IllegalArgumentException("Not enough items in the store");
		}
		log.trace("Add to basket: goods=" + goods.getName() + " amount=" + amount);
		curBasket.addItem(goods, amount);
	} 
	
	public void removeGoodsFromBasket(Goods goods) throws AutorizationException {
		if (curCustomer == null) {
			log.trace("Not logged in");
			throw new AutorizationException("Autorisation required");
		}
		log.trace("Remove from basket: goods item=" + goods.getName());
		curBasket.removeItem(goods);
		goodsManager.updateGoodsAmountFromDao(goods);
	}
	
	public int getPrice() {
		return curBasket.getPrice();
	}
	
	public String getCurCustomerName() throws AutorizationException {
		if (curCustomer == null) {
			log.trace("Not logged in");
			throw new AutorizationException("Autorisation required");
		}
		log.trace("return customer name=" + curCustomer.getName());
		return curCustomer.getName();
	}
	
	public Basket getBasket() { //TODO delete
		return curBasket;
	}

	public int getID() {
		long treadID = Thread.currentThread().getId();
		log.trace("Return shopManager id=" + id + " / Thread id=" + treadID);
		return id;
	}
	
}
