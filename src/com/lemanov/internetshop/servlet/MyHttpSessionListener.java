package com.lemanov.internetshop.servlet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.domain.ShopManager;

public class MyHttpSessionListener implements HttpSessionListener {
	
	private static Logger log = Logger.getLogger(MyHttpSessionListener.class.getName());

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("Listener = session is created. Session id=" + event.getSession().getId());
		log.trace("Session is created id=" + event.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		int customerID = (int) event.getSession().getAttribute("userID");
		System.out.println("Listener = Argument before destroy userID=" + customerID);
		
		if (customerID > 1) {
			try {
				log.trace("Clear basket for cusromer id=" + customerID);
				ShopManager.getInstance().clearCustomerBasket(customerID);
				
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Listener = session is destroyed");
		log.trace("Session is destroy. Id=" + event.getSession().getId());

	}

}
