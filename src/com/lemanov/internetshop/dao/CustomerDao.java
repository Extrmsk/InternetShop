package com.lemanov.internetshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import com.lemanov.internetshop.domain.Customer;

public class CustomerDao {
	private DaoFactory daoFactory = DaoFactory.getInstance(); //why static?
	private static Logger log = Logger.getLogger(CustomerDao.class.getName());
	
	public Customer create(String login, String passwd, String name, String email) throws DAOException {
		log.info("Creating new customer with login=" + login);
		String sql = "insert into customers (login, password, name, email) values (?,?,?,?);";
		
		Customer customer = null;
		Connection con = null;
		PreparedStatement prepState = null;
		ResultSet resultSet = null;
		try {
			log.trace("Open connection");
			con = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				prepState = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prepState.setString(1, login);
				prepState.setString(2, passwd);
				prepState.setString(3, name);
				prepState.setString(4, email);
				prepState.execute();
				try {
					log.trace("Get result set");
					resultSet = prepState.getGeneratedKeys();
					resultSet.next();
					log.trace("Create cutsomer to return");
					customer = new Customer(resultSet.getString("login"), resultSet.getString("password"), 
							resultSet.getString("name"), resultSet.getString("email"));
					customer.setAddress(resultSet.getString("address"));
					customer.setPhone(resultSet.getString("phone"));
					customer.setCreditCardInfo(resultSet.getString("credit_card"));
					customer.setId(resultSet.getInt("id"));
					log.info("Customer with login=" + login + " created!");
				} finally {
					try {
						resultSet.close();
						log.trace("result set closed");
					} catch (SQLException e) {
						log.warn("Cannot close result set", e);
					}
				}
			} finally {
				try {
					prepState.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			log.warn("Cannot create user", e);
			throw new DAOException("Cannot create user", e);
		} finally {
			try {
				con.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning customer");
		return customer;
	}
	

}
