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
import com.lemanov.internetshop.domain.Order;
import com.lemanov.internetshop.domain.OrderLine;
import com.lemanov.internetshop.domain.Service;

@WebServlet("/ordersPrepare")
public class OrdersPrepare extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(OrdersPrepare.class.getName());
       
    public OrdersPrepare() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		
		int customerID = (int) request.getSession().getAttribute("userID");
		List<Order> orders = null;
		try {
			orders = Service.getOrderDao().getOrdersByCustomerID(customerID);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		request.setAttribute("orders", orders);
		
		request.getRequestDispatcher("/user/orders.jsp").forward(request, response);
	}

}
