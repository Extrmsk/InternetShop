package com.lemanov.internetshop.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lemanov.internetshop.domain.OrderLine;
import com.lemanov.internetshop.domain.ShopManager;

public class OrderDaoTest {

	private BasketDao basketDao;
//	private int goodsID;
	private int customerID;
	private int orderID;

	@Before
	public void setUp() throws Exception {
		ShopManager shop = ShopManager.getInstance();
		basketDao = shop.getBasketDao();
//		goodsID = 70;
		customerID = 2;
		orderID = 1;
	}
	
	@Test
	public void testMoveBasketToOrder() throws DAOException {
		basketDao.moveBasketToOrderByCustID(customerID, orderID);
		assertTrue(basketDao.getAllOdrerLinesForCustomer(customerID).isEmpty());
	}
	

}
