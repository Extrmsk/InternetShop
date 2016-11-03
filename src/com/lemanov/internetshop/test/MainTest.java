package com.lemanov.internetshop.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.lemanov.internetshop.dao.CustomerDao;
import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.DaoFactory;
import com.lemanov.internetshop.domain.*;
import com.lemanov.internetshop.domain.exception.AutorizationException;

public class MainTest {

	public static void main(String[] args) throws DAOException, SQLException, AutorizationException {
//		Goods g1 = new Goods("Flower",100 , 5);
//		Goods g2 = new Goods("Telephone", 200, 7);
//		Goods g3 = new Goods("Cap", 300, 2);
//		
//		System.out.println(g1.toString());
//		System.out.println(g2.toString());
//		System.out.println(g3.toString());
		
		ShopManager sh = new ShopManager();
		System.out.println("Login status = " + sh.isLoggedIn());
		sh.authorization("User1", "234");
		System.out.println("Login status = " + sh.isLoggedIn());
		
		Goods g = (sh.findGoodsByName("Nokian")).get(0);
		Goods g2 = (sh.findGoodsByName("Lenovo")).get(0);
		System.out.println("Object name is =" + g.getName());
		System.out.println("Object2 name is =" + g2.getName());
		System.out.println("Amount1 before taken =" + g.getAmount());
		System.out.println("Amount2 before taken =" + g2.getAmount());
		System.out.println("Basket before taken =" + "\n" + "---------------------");
		printBasket(sh);
		
		System.out.println("Amount1 before taken =" + g.getAmount());
		System.out.println("Amount2 before taken =" + g2.getAmount());
		sh.addGoodsToBasket(g, 2);
		sh.addGoodsToBasket(g2, 1);
		System.out.println("Amount1 after 1taken =" + g.getAmount());
		System.out.println("Amount2 after 1taken =" + g2.getAmount());
		System.out.println("Basket after taken =" + "\n" + "---------------------");
		printBasket(sh);
		
		sh.removeGoodsFromBasket(g2);
		printBasket(sh);
		System.out.println("Amount2 after 1taken =" + g2.getAmount());
		
		
		

	}
	
	private static void printBasket(ShopManager sh) {
		List<OrderLine> bl = sh.getBasket().getOrderLines();
		for (OrderLine ol : bl) {
			System.out.println(ol.getItem().getName() + "  amount=" + ol.getAmount());
		}
		System.out.println("\n");
	}

}
