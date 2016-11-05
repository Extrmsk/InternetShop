package com.lemanov.internetshop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.domain.GoodsManager;
import com.lemanov.internetshop.domain.ShopManager;
import com.lemanov.internetshop.domain.ShopManagerHandler;
import com.lemanov.internetshop.domain.exception.AutorizationException;

@WebServlet("/editGoodsSubmit")
public class EditGoodsSubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(Login.class.getName());
       
    public EditGoodsSubmit() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		ShopManager shopManager = ShopManagerHandler.getShopManagerByID((int) request.getSession().getAttribute("shopManagerID"));
		
		String idStr = request.getParameter("editID");
		String name = request.getParameter("goodsname");
		String priceStr = request.getParameter("price");
		String amountStr = request.getParameter("amount");
		String groupIDStr = request.getParameter("groupID");
		
		if ( idStr == null || idStr == "" ) {
			if (name == null || priceStr == null || amountStr == null || groupIDStr == null ) {
				response.sendRedirect("admin/erroraddgoods.jsp");
				return;
			}
			int price = Integer.parseInt(priceStr);
			int groupID = Integer.parseInt(groupIDStr);
			int amount = Integer.parseInt(amountStr);
			try {
				shopManager.addGoodsItem(name, price, groupID, amount);
			} catch (AutorizationException | DAOException e) {
				log.warn("Can not create new goods item");
				e.printStackTrace();
			}
		} else {
			int editID = Integer.parseInt(idStr);
			int price = Integer.parseInt(priceStr);
			int groupID = Integer.parseInt(groupIDStr);
			int amount = Integer.parseInt(amountStr);
			try {
				shopManager.updateGoodsItem(editID, name, price, amount, groupID);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		response.sendRedirect("catalogAdmin");
	}
}
