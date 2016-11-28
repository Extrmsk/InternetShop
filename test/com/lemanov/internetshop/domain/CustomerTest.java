package com.lemanov.internetshop.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CustomerTest {

Customer customer;
	
	@Before
	public void setUp() {
		customer = new Customer("Login", "Password", "Name", "Email");
		customer.setCreditCardInfo("CreditCardInfo");
		customer.setPhone("Phone");
		customer.setAddress("Address");
	}

	@Test
	public void testGetLogin() {
		assertEquals("Login", customer.getLogin());
	}

	@Test
	public void testGetPassword() {
		assertEquals("Password", customer.getPasswd());
	}

	@Test
	public void testGetName() {
		assertEquals("Name", customer.getName());
	}

	@Test
	public void testGetAddress() {
		assertEquals("Address", customer.getAddress());
	}

	@Test
	public void testGetPhone() {
		assertEquals("Phone", customer.getPhone());
	}

	@Test
	public void testGetEmail() {
		assertEquals("Email", customer.getEmail());
	}

	@Test
	public void testGetCreditCardInfo() {
		assertEquals("CreditCardInfo", customer.getCreditCardInfo());
	}

	@Test
	public void testSetPassword() {
		customer.setPasswd("newPass");
		assertEquals("newPass", customer.getPasswd());
	}

	@Test
	public void testSetAddress() {
		customer.setAddress("newAddress");
		assertEquals("newAddress", customer.getAddress());
	}

	@Test
	public void testSetPhone() {
		customer.setPhone("newPhone");
		assertEquals("newPhone", customer.getPhone());
	}

	@Test
	public void testSetEmail() {
		customer.setEmail("newEmail");
		assertEquals("newEmail", customer.getEmail());
	}

	@Test
	public void testSetCreditCardInfo() {
		customer.setCreditCardInfo("1234");
		assertEquals("1234", customer.getCreditCardInfo());
	}

	@Test
	public void testSetGetLogin() {
		customer.setLogin("login");
		assertEquals("login", customer.getLogin());
	}

	@Test
	public void testSetGetName() {
		customer.setName("name");
		assertEquals("name", customer.getName());
	}

	@Test
	public void testSetGetId() {
		customer.setId(5);
		assertEquals(5, customer.getId());
	}

}
