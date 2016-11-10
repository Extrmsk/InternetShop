package com.lemanov.internetshop.domain;

import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.BasketDao;
import com.lemanov.internetshop.dao.CustomerDao;
import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.DaoInit;
import com.lemanov.internetshop.dao.GoodsDao;
import com.lemanov.internetshop.domain.exception.AutorizationException;
import com.lemanov.internetshop.domain.exception.NotEnoughGoodsException;

public class ShopManager {
	private static ShopManager shopManager;
	private Customer curCustomer;
	private Basket curBasket;
	private CustomerDao customerDao;
	private GoodsManager goodsManager;
	private GoodsDao goodsDao;
	private BasketDao basketDao;
	
	private static Logger log = Logger.getLogger(ShopManager.class.getName());
	
	private ShopManager() {
		log.info("***************** Create ShopManager *****************");
//		daoFactory = DaoFactory.getInstance();
		customerDao = new CustomerDao();
		goodsManager = new GoodsManager();
		goodsDao = new GoodsDao();
		basketDao = new BasketDao();
	}
	
	public static ShopManager getInstance() {
		if (shopManager == null) {
			shopManager = new ShopManager();
		}
		return shopManager;
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
//			curBasket = new Basket(tempCustomer); 	//initialise new Basket!
			log.info("Customer " + login + " successfully logged");
			return tempCustomer;
		} else {
			log.warn("Authentication for customer=" + login + " failed. Wrong password");
			throw new IllegalArgumentException("Wrong password!");
		}
	}
	
	public boolean isLoggedIn() {
		if (curCustomer == null) {
			return false;
		} 
		return true;
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
		log.debug("Adding goods item: name=" + name + ", price=" + price + ", groupId=" + groupID
				+ ", amount=" + amount);
		return goodsDao.addGoodsItem(name, price, groupID, amount);
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
		return goodsDao.getAll();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void addGoodsToBasket (int customerID, int goodsID, int amount) throws NotEnoughGoodsException, DAOException {
		Goods tempGoods = goodsDao.getGoodsByID(goodsID);
		if (amount > tempGoods.getAmount()) {
			log.warn("Not enough goods= " + tempGoods.getName() + " in storage");
			throw new NotEnoughGoodsException();
		}
		int amountInBasket = basketDao.getGoodsItemAmount(customerID, goodsID);
		System.out.println("amountInBasket=" + amountInBasket);
		System.out.println("goodsID=" + goodsID + " customerID=" + customerID + " amountDelta=" + amount);
		if (amountInBasket == 0) {
			System.out.println("add to basket");
			basketDao.add(customerID, goodsID, amount);
		} else {
			System.out.println("update in basket");
			basketDao.update(customerID, goodsID, amountInBasket + amount );
		}
		goodsDao.updateAmount(goodsID, tempGoods.getAmount() - amount);
		
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

	public void delGoodsItemByID(int delID) throws DAOException {
		log.trace("Delete goodsItem id=" + delID);
		goodsManager.delGoodsItemByID(delID);
	}

	public Goods getGoodsByID(int editID) throws DAOException {
		log.trace("Get goodsItem by id=" + editID);
		return goodsDao.getGoodsByID(editID);
	}

	public void updateGoodsItem(int editID, String name, int price, int amount, int groupID) throws DAOException {
		log.trace("Update goodsItem by id=" + editID);
		goodsManager.updateGoodsItem(editID, name, price, amount, groupID);
	}

	public void clearCustomerBasket(int customerID) throws DAOException {
		log.trace("Clear basket for customerID=" + customerID);
		//TODO create return old amount for all goods in basket!
		basketDao.clearCustomerBasket(customerID);
	}
	
}
