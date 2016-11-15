package com.lemanov.internetshop.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.domain.Basket;
import com.lemanov.internetshop.domain.Goods;
import com.lemanov.internetshop.domain.OrderLine;
import com.lemanov.internetshop.domain.ShopManager;

/**
 * Servlet implementation class BasketPrepare
 */
@WebServlet("/basketPrepare")
public class BasketPrepare extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(BasketPrepare.class.getName());
       
    public BasketPrepare() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		
		int customerID = (int) request.getSession().getAttribute("userID");
		
		if ( request.getParameter("action") != null ) {
				try {
					ShopManager.getInstance().clearCustomerBasket(customerID);
					System.out.println("Basket prepare - Basket is clear");
				} catch (DAOException e) {
					e.printStackTrace();
			}
			System.out.println("Basket prepare - action=" + request.getAttribute("action"));
		}
		
		try {
			Basket basket = ShopManager.getBasketDao().getBasketByCustomerID(customerID);
			request.setAttribute("orderLines", basket.getBasketItems());
			request.setAttribute("basketPrice", basket.getPrice());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/user/basket.jsp").forward(request, response);
	}

}
