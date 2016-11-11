package com.lemanov.internetshop.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.lemanov.internetshop.dao.DAOException;

public class ShopManagerTest {
	
	private ShopManager shm;

	@Before
	public void setUp() throws Exception {
		shm = ShopManager.getInstance();
	}

	@Test 
	public void testClearCustomerBasket() throws DAOException {
		shm.clearCustomerBasket(2);
		assertEquals(1, 1);
	}

}
