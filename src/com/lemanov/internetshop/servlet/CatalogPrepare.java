package com.lemanov.internetshop.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
		
		HttpSession session = request.getSession();
		System.out.println("Session id=" + session.getId());
		try {
			request.setAttribute("goodsList", ShopManager.getGoodsDao().getAll());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		String login = (String) session.getAttribute("login");
		request.setAttribute("node", ShopManager.getCatalog());
		request.setAttribute("level", -1);
		String path = request.getParameter("path");
		int[] pathItems;
		if (path != null) {
			pathItems = Arrays.stream(path.split(",")).mapToInt(Integer::parseInt).toArray();
		} else {
			pathItems = new int[0];
		}
		request.setAttribute("pathItems", pathItems);
		request.setAttribute("paramsPath", path);
		request.setAttribute("path", "");
		if (login.equals("Admin")) {
			request.getRequestDispatcher("/admin/catalogadmin.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/user/cataloguser.jsp").forward(request, response);
		}
	}

}
