package com.lemanov.internetshop.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import com.lemanov.internetshop.domain.Group;
import com.lemanov.internetshop.domain.Service;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		log.info(this.getClass().getSimpleName() + " servlet is running");

		HttpSession session = request.getSession();
		System.out.println("Session id=" + session.getId());
		String login = (String) session.getAttribute("login");
		
		request.setAttribute("node", Service.getCatalog());
		request.setAttribute("level", -1);
		String path = request.getParameter("path"); //get path
		int[] pathItems;
		if (path != null) {
			pathItems = Arrays.stream(path.split(",")).mapToInt(Integer::parseInt).toArray();
		} else {
			pathItems = new int[0];
		}
		request.setAttribute("pathItems", pathItems);
		request.setAttribute("paramsPath", path);
		request.setAttribute("path", "");
		
		try {
			if (pathItems.length == 0) {
				request.setAttribute("goodsList", Service.getGoodsDao().getAll());
			} else {
				int endElement = pathItems[pathItems.length - 1];
				List<Integer> groupIDsList = Service.getCatalog().getGroupByID(endElement).getAllTreeIDs();
				request.setAttribute("goodsList", Service.getGoodsDao().getGoodsByGroupIDs(groupIDsList));
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		if (login.equals("Admin")) {
			request.getRequestDispatcher("/admin/catalogadmin.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/user/cataloguser.jsp").forward(request, response);
		}
	}
	

}
