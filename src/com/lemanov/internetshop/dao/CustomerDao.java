package com.lemanov.internetshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import com.lemanov.internetshop.domain.Customer;

public class CustomerDao {
	private DaoFactory daoFactory = DaoFactory.getInstance();
	private static Logger log = Logger.getLogger(CustomerDao.class.getName());
	
	public Customer create(String login, String passwd, String name, String email) throws DAOException {
		log.info("Creating new customer with login=" + login);
		String sql = "INSERT INTO customers (login, password, name, email) VALUES (?,?,?,?);";
		
		Customer customer = null;
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet resSet = null;
		try {
			log.trace("Open connection");
			conn = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prst.setString(1, login);
				prst.setString(2, passwd);
				prst.setString(3, name);
				prst.setString(4, email);
				prst.execute();
				try {
					log.trace("Get result set");
					resSet = prst.getGeneratedKeys();
					resSet.next();
					log.trace("Create cutsomer to return");
					customer = new Customer(resSet.getString("login"), resSet.getString("password"), 
							resSet.getString("name"), resSet.getString("email"));
					customer.setAddress(resSet.getString("address"));
					customer.setPhone(resSet.getString("phone"));
					customer.setCreditCardInfo(resSet.getString("credit_card"));
					customer.setId(resSet.getInt("id"));
					log.info("Customer with login=" + login + " created!");
				} finally {
					try {
						resSet.close();
						log.trace("result set closed");
					} catch (SQLException e) {
						log.warn("Cannot close result set", e);
					}
				}
			} finally {
				try {
					prst.close();
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
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning customer");
		return customer;
	}
	
	public Customer read (String login) throws DAOException {
		log.info("Searching customer with login = " + login);
		String sql = "SELECT * FROM customers WHERE login=?;";
		
		Customer customer = null;
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet resSet = null;
		try {
			log.trace("Open connection");
			conn = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql);
				prst.setString(1, login);
				try {
					log.trace("Get result set");
					resSet = prst.executeQuery();
					while (resSet.next()) {
						log.trace("Create customer to return");
						customer = new Customer(resSet.getString("login"), resSet.getString("password"),
								resSet.getString("name"), resSet.getString("email"));
						customer.setAddress(resSet.getString("address"));
						customer.setPhone(resSet.getString("phone"));
						customer.setCreditCardInfo(resSet.getString("credit_card"));
						customer.setId(resSet.getInt("id"));
					}
				} finally {
					try {
						resSet.close();
						log.trace("result set closed");
					} catch (SQLException e) {
						log.warn("Cannot close result set", e);
					}
				}
			} finally {
				try {
					prst.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch ( SQLException e) {
			throw new DAOException("Cannot read user", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		if (customer == null) {
			log.debug("CUSTOMER NOT FOUND");
		} else {
			log.trace("Customer " + login + " found");
		}
		log.trace("Returning customer");
		return customer;
	}
	
	public Customer update(String login, String password, String name, String address, String phone, String email,
			String creditCardInfo) throws DAOException {
		log.info("Update data for login=" + login);
		String sql = "UPDATE customers SET password = ?, name = ?, address = ?, phone = ?, email = ?, credit_card = ? WHERE login = ?;";
		
		Customer customer = null;
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet resSet = null;
		try {
			log.trace("Open connection");
			conn = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prst.setString(1, password);
				prst.setString(2, name);
				prst.setString(3, address);
				prst.setString(4, phone);
				prst.setString(5, email);
				prst.setString(6, creditCardInfo);
				prst.setString(7, login);
				prst.execute();
				try {
					log.trace("Get result set");
					resSet = prst.getGeneratedKeys();
					resSet.next();
					log.trace("Create customer to return");
					customer = new Customer(resSet.getString("login"), resSet.getString("password"),
							resSet.getString("name"), resSet.getString("email"));
					log.trace("Coustructor is done");
					customer.setAddress(resSet.getString("address"));
					customer.setPhone(resSet.getString("phone"));
					customer.setCreditCardInfo(resSet.getString("credit_card"));
					customer.setId(resSet.getInt("id"));
					log.trace("Return object for " + login + " is created");
				} finally {
					try {
						resSet.close();
						log.trace("result set closed");
					} catch (SQLException e) {
						log.warn("Cannot close result set", e);
					}
				}
			} finally {
				try {
					prst.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}

		} catch (SQLException e) {
			throw new DAOException("Cannot update user", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Customer " + login + " has updated info");
		log.trace("Returning updated customer");
		return customer;
	}

}