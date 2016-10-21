package com.lemanov.internetshop.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.DaoFactory;
import com.lemanov.internetshop.domain.*;

public class Main {

	public static void main(String[] args) throws DAOException, SQLException {
//		Goods g1 = new Goods("Flower",100 , 5);
//		Goods g2 = new Goods("Telephone", 200, 7);
//		Goods g3 = new Goods("Cap", 300, 2);
//		
//		System.out.println(g1.toString());
//		System.out.println(g2.toString());
//		System.out.println(g3.toString());
		
		ShopManager sh = new ShopManager();
		sh.createAccount("Ara", "111d", "Arara", "ara@gmail.com");
		
		System.out.println(Customer.customers.toString());

	}

}
