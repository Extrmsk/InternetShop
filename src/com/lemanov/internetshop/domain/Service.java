package com.lemanov.internetshop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.BasketDao;
import com.lemanov.internetshop.dao.CustomerDao;
import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.DaoInit;
import com.lemanov.internetshop.dao.GoodsDao;
import com.lemanov.internetshop.dao.GroupDao;
import com.lemanov.internetshop.dao.OrderDao;
import com.lemanov.internetshop.domain.exception.AutorizationException;
import com.lemanov.internetshop.domain.exception.NotEnoughGoodsException;

public class Service {
	private static Service service;
	private static GoodsDao goodsDao;
	private static CustomerDao customerDao;
	private static BasketDao basketDao;
	private static OrderDao orderDao;
	private static DataSource dataSource;
	private static GroupDao groupDao;
	private static Catalog catalog;
	
	private static Logger log = Logger.getLogger(Service.class.getName());
	
	private Service() {
		log.info("Create Service instance");
		initDataSource();
	}
	
	private static void initDataSource() {
		if (dataSource == null) {
			try {
				InitialContext context = new InitialContext();
				dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Internetshop");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}

// instance classes logic
	public static Service getInstance() {
		if (service == null) {
			service = new Service();
		}
		return service;
	}
	
	public static GoodsDao getGoodsDao() {
		if (goodsDao == null) {
			goodsDao = new GoodsDao();
			goodsDao.setDataSource(dataSource);
		}
		return goodsDao;
	}
	
	public static CustomerDao getCustomerDao() {
		if (customerDao == null) {
			customerDao = new CustomerDao();
			customerDao.setDataSource(dataSource);
		}
		return customerDao;
	}
	
	public static BasketDao getBasketDao() {
		if (basketDao == null) {
			basketDao = new BasketDao();
			basketDao.setDataSource(dataSource);
			basketDao.setGoodsDao(getGoodsDao());
			basketDao.setCustomerDao(getCustomerDao());
		}
		return basketDao;
	}
	
	public static OrderDao getOrderDao() {
		if (orderDao == null) {
			orderDao = new OrderDao();
			orderDao.setDataSource(dataSource);
			orderDao.setCustomerDao(getCustomerDao());
		}
		return orderDao;
	}
	
	public static GroupDao getGroupDao() {
		if (groupDao == null) {
			groupDao = new GroupDao();
//			groupDao.setDataSource(dataSource);
		}
		return groupDao;
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
				goodsDao.changeAmountByDelta(orderLine.getGoodsItem().getId(), orderLine.getAmount());
			}
			basketDao.clearCustomerBasket(customerID);
		}
	}

	//DODO refactor
	public void delOrderLineForCustomer(int goodsItemID, int customerID) throws DAOException {
		log.trace("Delete OrderLine for Customer");
		int goodsAmount = basketDao.getGoodsItemAmount(customerID, goodsItemID);
		basketDao.delOrderLineForCustomer(goodsItemID, customerID);
		goodsDao.changeAmountByDelta(goodsItemID, goodsAmount);
	}

	public void confirmOrder(int customerID, String address, String shipType) throws DAOException {
		log.trace("Confirm order for customerID=" + customerID);
		getOrderDao();
		
		Order order = orderDao.addOrder(customerID, address, ShippingType.valueOf(shipType.toUpperCase()), 
				OrderStatus.valueOf("NEW"));
		int orderID = order.getId();
		System.out.println("orderID=" + orderID);
		log.info("return order obj id=" + orderID);
		
		basketDao.moveBasketToOrderByCustID(customerID, orderID);
		System.out.println("moveBasket is performed! Address=" + address + " ShippingType=" + shipType);
	}
	
	public static Catalog getCatalog() {
		if (catalog == null) {
			getGroupDao();
			try {
				catalog = new Catalog(0, "catalog");
				catalog.setGroupDao(groupDao);
				catalog.loadCatalogTree();
			} catch (DAOException e) {
				log.warn("Cannot create catalog object", e);
				e.printStackTrace();
			}
		}
		return catalog;
	}
	
	
}
