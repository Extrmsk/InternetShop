package com.lemanov.internetshop.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class basketDaoTest {

	private BasketDao basketDao;
	int goodsID;
	int customerID;

	@Before
	public void setUp() throws Exception {
		basketDao = new BasketDao();
		goodsID = 51;
		customerID = 2;
	}
	
	@Test 
	public void testBasketGetNull() throws DAOException {
		basketDao.deleteAll();
		assertEquals(0, basketDao.getGoodsItemAmount(customerID, goodsID ));
	}
	
	@Test (expected = DAOException.class) 
	public void testBasketGoodsIDNotExist() throws DAOException {
		basketDao.deleteAll();
		basketDao.add(customerID, goodsID+10000, 1);
	}
	
	@Test 
	public void testBasketAdd() throws DAOException {
		basketDao.deleteAll();
		basketDao.add(customerID, goodsID, 1);
		assertEquals(1, basketDao.getGoodsItemAmount(customerID, goodsID));
	}
	
	@Test
	public void testBasketUpdate() throws DAOException {
		basketDao.deleteAll();
		basketDao.add(customerID, goodsID, 1);
		basketDao.update(customerID, goodsID, 10);
		assertEquals(10, basketDao.getGoodsItemAmount(customerID, goodsID));
	}
	
	@Test 
	public void testClearCustomerBasket() throws DAOException {
		basketDao.deleteAll();
		basketDao.add(customerID, goodsID, 1);
		basketDao.add(customerID+1, goodsID+1, 1);
		basketDao.clearCustomerBasket(customerID);
		int a = basketDao.getGoodsItemAmount(customerID, goodsID);
		int b = basketDao.getGoodsItemAmount(customerID+1, goodsID+1);
		assertEquals(0, a);
		assertEquals(1, b);
	}
	
	
	

}
