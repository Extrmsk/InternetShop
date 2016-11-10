package com.lemanov.internetshop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.kohsuke.rngom.binary.ChoicePattern;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.domain.ShopManager;

/**
 * Servlet implementation class DeleteGoodsItem
 */
@WebServlet("/deleteGoodsItem")
public class DeleteGoodsItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(DeleteGoodsItem.class.getName());
       
    public DeleteGoodsItem() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is running");
		log.info(this.getClass().getSimpleName() + " servlet is running");

		int delID = Integer.parseInt(request.getParameter("deleteID"));
		ShopManager shopManager = ShopManager.getInstance();
		try {
			shopManager.delGoodsItemByID(delID);
			System.out.println("Good item id=" + delID + " is deleted!");
		} catch (DAOException e1) {
			e1.printStackTrace();
		}
		response.sendRedirect("catalogPrepare");
		
	}

}
