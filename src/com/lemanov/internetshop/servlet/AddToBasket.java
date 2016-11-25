package com.lemanov.internetshop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.domain.Service;
import com.lemanov.internetshop.domain.exception.NotEnoughGoodsException;

/**
 * Servlet implementation class AddToBasket
 */
@WebServlet("/addToBasket")
public class AddToBasket extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AddToBasket.class.getName());
       
    public AddToBasket() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		
		int amount = 1;
		int goodsID = Integer.parseInt(request.getParameter("id"));
		Service shopManager = Service.getInstance();
		int customerID = (int) request.getSession().getAttribute("userID");
		try {
		shopManager.addGoodsToBasket(customerID, goodsID, amount);
		} catch (NotEnoughGoodsException | DAOException e) {
			log.warn("Can not add goods to basket");
		}
		response.sendRedirect("catalogPrepare");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is POST running");
		doGet(request, response);
	}

}
