package com.lemanov.internetshop.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TestConnectionPool {
	
	public void check() {
		
		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/Internetshop");
			
			Connection con = ds.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from goods");
			
			while(rs.next()) {
				System.out.println(rs.getString("goods_name"));
			}
			
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
		
	}


}
