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
		
		DaoFactory dao = DaoFactory.getInstance();
		Connection con = dao.getConnection();
		
		Statement st = con.createStatement();
		String sql1 = "update customers set login='Bongg' where id=22;";
		String sql2 = "select * from customers;";
		ResultSet rs = st.executeQuery(sql1);
		
		String a = null;
		String b = null;
		
		while (rs.next()) {
			a = rs.getString(1);
			b = rs.getString(2);
		}
		
		System.out.println(a + " " + b);
		

	}

}
