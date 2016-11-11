package com.lemanov.internetshop.domain;

import java.util.ArrayList;
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
	
	public Goods addGoodsItem(String name, int price, int groupID, int amount) throws AutorizationException, DAOException {
		log.debug("Adding goods item: name=" + name + ", price=" + price + ", groupId=" + groupID
				+ ", amount=" + amount);
		return goodsDao.addGoodsItem(name, price, groupID, amount);
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
		List<OrderLine> orderLines = new ArrayList<>();
		orderLines = basketDao.getAllOdrerLinesForCustomer(customerID);
		if (!orderLines.isEmpty()) {
			for (OrderLine orderLine : orderLines) {
				goodsDao.increaseAmount(orderLine.getGoodsID(), orderLine.getAmount());
			}
			basketDao.clearCustomerBasket(customerID);
		}
	}
	
}
