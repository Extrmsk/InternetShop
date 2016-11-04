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

/**
 * Servlet implementation class CatalogAdmin
 */
@WebServlet("/catalogAdmin")
public class CatalogAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(CatalogAdmin.class.getName());
       
    public CatalogAdmin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		
		ShopManager shopManager = ShopManagerHandler.getShopManagerByID((int) request.getSession().getAttribute("shopManagerID"));
		HttpSession session = request.getSession();
		try {
			session.setAttribute("goodsList", shopManager.getAllGoods());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/admin/catalogadmin.jsp").forward(request, response);
	}

}
