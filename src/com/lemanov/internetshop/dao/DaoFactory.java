package com.lemanov.internetshop.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DaoFactory {
	private static DaoFactory daoFactory;
	private String user;
	private String password;
	private String url;
	private String driver;
	
	private static Logger log = Logger.getLogger(DaoFactory.class.getName());
	
	private DaoFactory() {
		Properties prop = new Properties();
		try {
			prop.load(DaoFactory.class.getResourceAsStream("/db.properties"));
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			url = prop.getProperty("url");
			driver = prop.getProperty("driver");
		} catch (IOException e) {
			throw new NoDBPropertiesException("Can't read file", e);
		}
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("Driver not found\n", e);
		}
	}
	
	public static DaoFactory getInstance() {
		if (daoFactory == null) {
			daoFactory = new DaoFactory();
		}
		return daoFactory;
	}

	public Connection getConnection() throws DAOException {
		log.trace("Driver manager get connection");
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DAOException("No connection to DB", e);
		}
	}

}
