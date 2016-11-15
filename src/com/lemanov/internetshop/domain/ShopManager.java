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
	private static GoodsDao goodsDao;
	private static CustomerDao customerDao;
	private static BasketDao basketDao;
	private GoodsManager goodsManager;
	
	private static Logger log = Logger.getLogger(ShopManager.class.getName());
	
	private ShopManager() {
		log.info("*** Create ShopManager ***");
	}

// instance classes logic
	public static ShopManager getInstance() {
		if (shopManager == null) {
			shopManager = new ShopManager();
		}
		return shopManager;
	}
	
	public static GoodsDao getGoodsDao() {
		if (goodsDao == null) {
			goodsDao = new GoodsDao();
		}
		return goodsDao;
	}
	
	public static CustomerDao getCustomerDao() {
		if (customerDao == null) {
			customerDao = new CustomerDao();
		}
		return customerDao;
	}
	
	public static BasketDao getBasketDao() {
		if (basketDao == null) {
			basketDao = new BasketDao();
			basketDao.setGoodsDao(getGoodsDao());
			basketDao.setCustomerDao(getCustomerDao());
		}
		return basketDao;
	}
	
	
	
// business logic
	public Customer authorization(String login, String passwd) throws DAOException  {
		getCustomerDao();

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
			log.info("Customer " + login + " successfully logged");
			return tempCustomer;
		} else {
			log.warn("Authentication for customer=" + login + " failed. Wrong password");
			throw new IllegalArgumentException("Wrong password!");
		}
	}
	
	
	
	
	// searching the match of the names expression
	//TODO to realize!
	public List<Goods> findGoodsByName(String name) throws DAOException {
		log.trace("Returning goods by name=" + name);
		return goodsManager.findGoodsByName(name);
	}
	
	public void addGoodsToBasket (int customerID, int goodsID, int amount) throws NotEnoughGoodsException, DAOException {
		getGoodsDao();
		getBasketDao();
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
	
	//TODO refactor
	public void clearCustomerBasket(int customerID) throws DAOException {
		getBasketDao();
		log.trace("Clear basket for customerID=" + customerID);
		List<OrderLine> orderLines = new ArrayList<>();
		orderLines = basketDao.getAllOdrerLinesForCustomer(customerID);
		if (!orderLines.isEmpty()) {
			for (OrderLine orderLine : orderLines) {
				goodsDao.increaseAmount(orderLine.getGoodsItem().getId(), orderLine.getAmount());
			}
			basketDao.clearCustomerBasket(customerID);
		}
	}

	//DODO refactor
	public void delOrderLineForCustomer(int goodsItemID, int customerID) throws DAOException {
		log.trace("Delete OrderLine for Customer");
		int goodsAmount = basketDao.getGoodsItemAmount(customerID, goodsItemID);
		basketDao.delOrderLineForCustomer(goodsItemID, customerID);
		goodsDao.increaseAmount(goodsItemID, goodsAmount);
	}

	
}
