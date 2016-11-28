package com.lemanov.internetshop.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Matchers;
import org.mockito.AdditionalMatchers;

import com.lemanov.internetshop.dao.BasketDao;
import com.lemanov.internetshop.dao.CustomerDao;
import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.GoodsDao;
import com.lemanov.internetshop.domain.exception.NotEnoughGoodsException;

public class ServiceTest {
	
	@Mock
	private CustomerDao mockCustomerDao;
	@Mock
	private GoodsDao mockGoodsDao;
	@Mock
	private BasketDao mockBasketDao;
	
	private Customer mockCustomer;
	private Service service;
	private Goods mockGoods;
	private int basketGoodsAmount = 0;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockCustomer = new Customer("user1", "123", "username", "user@mail.ru");
		mockCustomer.setId(1);
		Mockito.when(mockCustomerDao.read("user1")).thenReturn(mockCustomer);
		Mockito.when(mockCustomerDao.read(AdditionalMatchers.not(Matchers.eq("user1")))).thenReturn(null);
		service = Service.getInstanceForTest();
		service.setCustomerDao(mockCustomerDao);
		
		mockGoods = new Goods("a", 2, 2, 2);
		Mockito.when(mockGoodsDao.getGoodsByID(2)).thenReturn(mockGoods);
		Mockito.when(mockGoodsDao.getGoodsByID(AdditionalMatchers.not(Matchers.eq(2)))).thenReturn(null);
		service.setGoodsDao(mockGoodsDao);
	}

	@Test
	public void testMockCustomerDao() throws DAOException {
		assertEquals("123", mockCustomerDao.read("user1").getPasswd());
		assertNull(mockCustomerDao.read("user2"));
	}
	
	@Test
	public void testAutorizationOKLoginPasswd() throws DAOException {
		assertEquals("123", service.authorization("user1", "123").getPasswd());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAutorizationWrongPasswd() throws DAOException {
		service.authorization("user1", "");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAutorizationNullArgs() throws DAOException {
		service.authorization(null, null);
	}
	
	@Test (expected = NotEnoughGoodsException.class)
	public void testAddGoodsToBasketTooBigAmount() throws NotEnoughGoodsException, DAOException {
		service.addGoodsToBasket(1, 2, 10);
	}
	
	

	
	
}
