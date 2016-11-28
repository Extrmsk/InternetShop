package com.lemanov.internetshop.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class GoodsTest {

	@Mock
	private Group mockGroup;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(mockGroup.getId()).thenReturn(1);
	}

	@Test
	public final void testGetName() {
		Goods goods = new Goods("SomeName", 500, 5, mockGroup.getId());
		assertEquals("SomeName", goods.getName());
	}

	@Test
	public final void testSetName() {
		Goods goods = new Goods("SomeName", 500, 5, mockGroup.getId());
		goods.setName("NewName");
		assertEquals("NewName", goods.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetNullName() {
		Goods goods = new Goods("SomeName", 500, 5, mockGroup.getId());
		goods.setName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetBlankName() {
		Goods goods = new Goods("SomeName", 500, 5, mockGroup.getId());
		goods.setName("");
	}

	@Test
	public final void testGetPrice() {
		Goods goods = new Goods("SomeName", 500, 5, mockGroup.getId());
		assertEquals(500, goods.getPrice());
	}

	@Test
	public final void testSetPrice() {
		Goods goods = new Goods("SomeName", 500, 5, mockGroup.getId());
		goods.setPrice(1000);
		assertEquals(1000, goods.getPrice());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetZeroPrice() {
		Goods goods = new Goods("SomeName", 500, 5, mockGroup.getId());
		goods.setPrice(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetnegativePrice() {
		Goods goods = new Goods("SomeName", 500, 5, mockGroup.getId());
		goods.setPrice(-56);
	}

	@Test
	public final void testSetGroup() {
		Group tempGroup = Mockito.mock(Group.class);
		Mockito.when(tempGroup.getId()).thenReturn(1);
		Goods goods = new Goods("SomeName", 500, 5, mockGroup.getId());
		goods.setGroupID(tempGroup.getId());
		assertEquals(tempGroup.getId(), goods.getGroupID());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetNegativeGroupId() {
		Goods goods = new Goods("SomeName", 500, 5, mockGroup.getId());
		goods.setGroupID(-5);
	}
	
	@Test
	public final void testGoodsGetAmount() {
		Goods goods = new Goods("SomeName", 1234, 5, mockGroup.getId());
		assertEquals(5, goods.getAmount());
	}
	
	@Test
	public final void testGoodsSetZeroAmount() {
		Goods goods = new Goods("SomeName", 1234, 0, mockGroup.getId());
		assertEquals(0, goods.getAmount());
	}
	
	@Test
	public final void testGoodsSetAmount() {
		Goods goods = new Goods("SomeName", 1234, 5, mockGroup.getId());
		goods.setAmount(10);
		assertEquals(10, goods.getAmount());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testGoodsSetNegativeAmount() {
		Goods goods = new Goods("SomeName", 1234, 5, mockGroup.getId());
		goods.setAmount(-10);
	}
	
	@Test
	public final void testGoodsGetId() {
		Goods goods = Mockito.mock(Goods.class);
		Mockito.when(goods.getId()).thenReturn(1);
		assertEquals(1, goods.getId());
	}
	
	@Test
	public final void testGoodsSetId() {
		Goods goods = new Goods("SomeName", 1234, 5, mockGroup.getId());
		goods.setId(1);
		assertEquals(1, goods.getId());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testGoodsSetnegativeId() {
		Goods goods = new Goods("SomeName", 1234, 5, mockGroup.getId());
		goods.setId(-1);
	}

}
