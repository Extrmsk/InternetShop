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

/**
 * Servlet implementation class CatalogAdmin
 */
@WebServlet("/catalogPrepare")
public class CatalogPrepare extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(CatalogPrepare.class.getName());
       
    public CatalogPrepare() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		
		ShopManager shopManager = ShopManager.getInstance();
		HttpSession session = request.getSession();
		System.out.println("Session id=" + session.getId());
		try {
			request.setAttribute("goodsList", shopManager.getAllGoods());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		String login = (String) session.getAttribute("login");
		if (login.equals("Admin")) {
			request.getRequestDispatcher("/admin/catalogadmin.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/user/cataloguser.jsp").forward(request, response);
		}
	}

}
