package com.lemanov.internetshop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.domain.ShopManager;

/**
 * Servlet implementation class DelOrderLineFromBasket
 */
@WebServlet("/delOrderLineFromBasket")
public class DelOrderLineFromBasket extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(DeleteGoodsItem.class.getName());
       
    public DelOrderLineFromBasket() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		
		int goodsItemID = Integer.parseInt(request.getParameter("deleteID"));
		int customerID = (int) request.getSession().getAttribute("userID");
		try {
			ShopManager.getInstance().delOrderLineForCustomer(goodsItemID, customerID);
		} catch (DAOException e) {
			e.printStackTrace();
		}

		response.sendRedirect("basketPrepare");
	}

}
