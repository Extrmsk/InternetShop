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
import com.lemanov.internetshop.domain.ShopManagerHandler;
import com.lemanov.internetshop.domain.exception.AutorizationException;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Login.class.getName());

    public Login() {
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		if (request.getParameter("login") == null || request.getParameter("passwd") == null 
				|| request.getParameter("login").isEmpty() || request.getParameter("passwd").isEmpty() ) {
			System.out.println("null redirect");
			//TODO add red alert message in index.jsp!
			getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
		
		String login = request.getParameter("login");
		String passwd = request.getParameter("passwd");
		
		ShopManager shopManager = null;
		try {
			shopManager = new ShopManager();
			shopManager.authorization(login, passwd);
		} catch (IllegalArgumentException | DAOException e) {
			getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			System.out.println("Login servlet: autorisation deny");
			return;
		}
		
		ShopManagerHandler.add(shopManager);
		HttpSession session = request.getSession();
		session.setAttribute("shopManagerID", shopManager.getID());
		log.info("Open session id=" + session.getId());
		
		try {
			session.setAttribute("userName", shopManager.getCurCustomerName());
		} catch (AutorizationException e) {
			e.printStackTrace();
		}
		
		if (login.equals("Admin")) {
//			getServletContext().getRequestDispatcher("/admin/catalogadmin.jsp").forward(request, response);
			response.sendRedirect("catalogAdmin");
		} else {
			getServletContext().getRequestDispatcher("/user/catalog.jsp").forward(request, response);
		}
	}

}
