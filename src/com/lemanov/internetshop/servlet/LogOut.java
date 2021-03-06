package com.lemanov.internetshop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.domain.Service;
import com.lemanov.internetshop.domain.exception.AutorizationException;

/**
 * Servlet implementation class LogOut
 */
@WebServlet("/logout")
public class LogOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(LogOut.class.getName());
       
    public LogOut() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		
		HttpSession session = request.getSession();
		try {
//			int customerID = (int)session.getAttribute("userID");
//			ShopManager.getInstance().clearCustomerBasket(customerID);
			System.out.println("invalidate session from logOut servlet");
			session.invalidate();
		} catch (Exception e) {
			log.warn("Can't perform log out and close session", e);
		}
		response.sendRedirect("./logout.jsp");
	}

}
