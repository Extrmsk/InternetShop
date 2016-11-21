package com.lemanov.internetshop.dao;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class OrderLinesDao {
	
	private DaoInit daoInstance = DaoInit.getInstance();
	private static Logger log = Logger.getLogger(OrderLinesDao.class.getName());
	private static DataSource dataSource;
	
	public void setDataSource(DataSource ds) {
		dataSource = ds;
	}
}
