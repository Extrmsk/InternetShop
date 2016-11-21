package com.lemanov.internetshop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.domain.ShopManager;

@WebServlet("/confirmOrder")
public class ConfirmOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ConfirmOrder.class.getName());
	
       
    public ConfirmOrder() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		
		String address = request.getParameter("address");
		String shipType = request.getParameter("shippingType");
		
		HttpSession session = request.getSession();
		int customerID = (int) session.getAttribute("userID");
		try {
			ShopManager.getInstance().confirmOrder(customerID, address, shipType);
		} catch (DAOException e) {
			e.printStackTrace();
		}
//		response.getWriter().println("shipType=" + shipType);
		
		response.sendRedirect("ordersPrepare");
	}

}
