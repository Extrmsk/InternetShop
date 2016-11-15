package com.lemanov.internetshop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.domain.Goods;
import com.lemanov.internetshop.domain.ShopManager;

@WebServlet("/editGoodsPrepare")
public class EditGoodsPrepare extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Login.class.getName());
       
    public EditGoodsPrepare() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		
		if (request.getParameter("editID") == null) {
			response.sendRedirect("admin/editgoods.jsp");
			return;
		}
		int editID = Integer.parseInt(request.getParameter("editID"));
		ShopManager shopManager = ShopManager.getInstance();
		
		Goods goodsItem;
		String url = "/admin/editgoods.jsp";
		try {
			goodsItem = ShopManager.getGoodsDao().getGoodsByID(editID);
			//TODO govno code
			url += "?name=" + goodsItem.getName();
			url += "&price=" + goodsItem.getPrice();
			url += "&amount=" + goodsItem.getAmount();
			url += "&groupID=" + goodsItem.getGroupID();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher(url).forward(request, response);
		
	}
}
