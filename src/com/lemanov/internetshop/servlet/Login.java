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
import com.lemanov.internetshop.domain.Customer;
import com.lemanov.internetshop.domain.Service;
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
		
		Service shopManager = null;
		Customer curCustomer = null;
		try {
			shopManager = Service.getInstance();
			curCustomer = shopManager.authorization(login, passwd);
		} catch (IllegalArgumentException | DAOException e) {
			log.warn("Login servlet: authorization deny");
			getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
		
		//warn! After add this string and add "listener" to web.xml sometimes "ide not responding" happening
		MyHttpSessionListener l = new MyHttpSessionListener();
		
		HttpSession session = request.getSession(true);
		log.info("session id=" + session.getId());
		System.out.println("Session id=" + session.getId());
		
		session.setAttribute("userID", curCustomer.getId());
		session.setAttribute("userName", curCustomer.getName());
		session.setAttribute("login", curCustomer.getLogin());
		
		response.sendRedirect("catalogPrepare");
	}

}
