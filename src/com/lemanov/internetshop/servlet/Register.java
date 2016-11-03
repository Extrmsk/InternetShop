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
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Register.class.getName());
       
    public Register() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		if ((request.getParameter("login") == null) || request.getParameter("passwd") == null
				|| request.getParameter("name") == null || request.getParameter("email") == null
				|| request.getParameter("login").isEmpty() || request.getParameter("passwd").isEmpty()
				|| request.getParameter("name").isEmpty() || request.getParameter("email").isEmpty()) {
			getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		String login = request.getParameter("login");
		String passwd = request.getParameter("passwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		try {
			ShopManager shopManager = new ShopManager();
			shopManager.createAccount(login, passwd, name, email);
		} catch (DAOException e) {
			e.printStackTrace();
		}

		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
