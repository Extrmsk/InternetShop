package com.lemanov.internetshop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    public Login() {
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		
		if (request.getParameter("login") == null || request.getParameter("passwd") == null 
				|| request.getParameter("login").isEmpty() || request.getParameter("passwd").isEmpty() ) {
			System.out.println("null redirect");
			//TODO add red alert message in index.jsp!
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
		String login = request.getParameter("login");
		String passwd = request.getParameter("passwd");
		
		ShopManager shopManager = null;
		try {
			shopManager = new ShopManager();
			shopManager.autorisation(login, passwd);
		} catch (IllegalArgumentException | DAOException e) {
			request.setAttribute("loginStatus", new String("AutorisationDeny"));
			//TODO add red alert message in index.jsp!
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
			System.out.println("autorisation deny redirect");
			return;
		}
		
		ShopManagerHandler.add(shopManager);
		HttpSession session = request.getSession();
		session.setAttribute("shopManagerID", shopManager.getID());
		try {
			session.setAttribute("username", shopManager.getCurCustomerName());
		} catch (AutorizationException e) {
			e.printStackTrace();
		}
		
		if (login.equals("Admin")) {
			getServletContext().getRequestDispatcher("/admin/adminlogin.jsp").forward(request, response);
		} else {
			getServletContext().getRequestDispatcher("/user/customerlogin.jsp").forward(request, response);
		}
	}

}
