package com.lemanov.internetshop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class AddToBasket
 */
@WebServlet("/addToBasket")
public class AddToBasket extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AddToBasket.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToBasket() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is GET running");
		log.info(this.getClass().getSimpleName() + " servlet is running");
		String id = request.getParameter("id");
		System.out.println("added to basket goodItem id=" + id);
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet " + this.getClass().getSimpleName() + " is POST running");
		doGet(request, response);
	}

}
