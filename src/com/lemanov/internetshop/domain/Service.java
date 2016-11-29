package com.lemanov.internetshop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.DirStateFactory.Result;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lemanov.internetshop.dao.BasketDao;
import com.lemanov.internetshop.dao.CustomerDao;
import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.GoodsDao;
import com.lemanov.internetshop.dao.GroupDao;
import com.lemanov.internetshop.dao.OrderDao;
import com.lemanov.internetshop.domain.exception.AutorizationException;
import com.lemanov.internetshop.domain.exception.NotEnoughGoodsException;

public class Service {
	private static Service service;
	private static ApplicationContext context;
	private static GoodsDao goodsDao;
	private static CustomerDao customerDao;
	private static BasketDao basketDao;
	private static OrderDao orderDao;
	private static GroupDao groupDao;
	private static Catalog catalog;
	
	private static Logger log = Logger.getLogger(Service.class.getName());
	
	private Service() {
		log.info("Create Service instance");
	}
	
// instance classes logic
	public static Service getInstance() {
		if (service == null) {
			service = new Service();
			context = new ClassPathXmlApplicationContext("Spring-Module.xml");
			goodsDao = (GoodsDao) context.getBean("goodsDaoBean");
			customerDao = (CustomerDao) context.getBean("customerDaoBean");
			basketDao = (BasketDao) context.getBean("basketDaoBean");
			orderDao = (OrderDao) context.getBean("orderDaoBean");
			groupDao = (GroupDao) context.getBean("groupDaoBean");
			basketDao.setGoodsDao(goodsDao);
			basketDao.setCustomerDao(customerDao);
			orderDao.setCustomerDao(customerDao);
		}
		return service;
	}
	
	public static Service getInstanceForTest() {
		return service;
	}
	
	public static GoodsDao getGoodsDao() {
		return goodsDao;
	}
	
	public void setGoodsDao(GoodsDao gd) {
		goodsDao = gd;
	}
	
	public static CustomerDao getCustomerDao() {
		return customerDao;
	}
	
	public void setCustomerDao(CustomerDao cd) {
		customerDao = cd;
	}
	
	public static BasketDao getBasketDao() {
		return basketDao;
	}
	
	public static OrderDao getOrderDao() {
		return orderDao;
	}
	
	public static GroupDao getGroupDao() {
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
			log.trace("add to basket");
			basketDao.add(customerID, goodsID, amount);
		} else {
			log.trace("update in basket");
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
