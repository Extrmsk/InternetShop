package com.lemanov.internetshop.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.lemanov.internetshop.dao.DAOException;

public class CatalogTest {
	
	private Catalog mockCatalog;

	@Before
	public void setUp() throws Exception {
		mockCatalog = new Catalog(0, "catalog");
		Group a = new Group(1, "a", mockCatalog);
		Group b = new Group(2, "b", mockCatalog);
		Group c = new Group(3, "c", mockCatalog);
		Group d = new Group(4, "d", c);
		Group e = new Group(5, "e", c);
		Group f = new Group(6, "f", c);
	}

	@Test
	public void testGetGroupByID() {
		assertEquals("f", mockCatalog.getGroupByID(6).getName());
	}
	
	@Test
	public void testGetChildrens() {
		assertEquals(3, mockCatalog.getChildrens().size());
	}
	
	@Test
	public void testGroupGetTreeIDs() throws DAOException {
		assertEquals(4, mockCatalog.getGroupByID(3).getAllTreeIDs().size());
	}
	
	@Test
	public void testGroupGetTreeNoChild() throws DAOException {
		assertEquals(1, mockCatalog.getGroupByID(6).getAllTreeIDs().size());
	}

}
