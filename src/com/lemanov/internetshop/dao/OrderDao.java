package com.lemanov.internetshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lemanov.internetshop.domain.Customer;
import com.lemanov.internetshop.domain.Goods;
import com.lemanov.internetshop.domain.Order;
import com.lemanov.internetshop.domain.OrderStatus;
import com.lemanov.internetshop.domain.ShippingType;

@Component
public class OrderDao {
	
	@Autowired
	private DataSource dataSource;
	
	private CustomerDao customerDao;
	private Logger log = Logger.getLogger(OrderDao.class.getName());
	
	public Order addOrder(int customerID, String address, ShippingType shipType, OrderStatus status) throws DAOException {
		log.trace("Creating new order customerID=" + customerID);
		String sql = "INSERT INTO orders (customer_id, delivery_address, shipping_type, order_status) "
				+ "VALUES (?, ?, ?, ?);";
		
		Order tempOrder = null;
		Customer tempCustomer = null;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resSet = null;
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, customerID);
			statement.setString(2, address);
			statement.setString(3, shipType.name());
			statement.setString(4, status.name());
			statement.execute();
			resSet = statement.getGeneratedKeys();
			resSet.next();
			tempCustomer = customerDao.getCustomerByID(customerID);
			tempOrder = new Order(tempCustomer, resSet.getString("delivery_address"), 
					ShippingType.valueOf(resSet.getString("shipping_type")));
			tempOrder.setId(resSet.getInt("id"));
			tempOrder.setDate(resSet.getString("create_date"));
			tempOrder.setStatus(OrderStatus.valueOf(resSet.getString("order_status")));
			log.info("New order obj is created");
			
		} catch (SQLException e) {
			log.error("Cannot add new order", e);
			throw new DAOException("Cannot add new order", e);
		} finally {
			try {
				if (resSet != null) resSet.close();
				if (statement != null) statement.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				log.warn("Cannot close connection/statement/resultset", e);
			}
		}
		log.trace("Returning new order");
		return tempOrder;
	}

	public List<Order> getOrdersByCustomerID(int customerID) throws DAOException {
		log.trace("Return all orders for customerID=" + customerID);
		String sql = "SELECT * FROM orders WHERE customer_id = ?;";
		
		List<Order> tempOrders = new ArrayList<>();
		Order tempOrder = null;
		Customer tempCustomer = customerDao.getCustomerByID(customerID);
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resSet = null;
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setInt(1, customerID);
			resSet = statement.executeQuery();
			while (resSet.next()) {
				tempOrder = new Order(tempCustomer, resSet.getString("delivery_address"), 
						ShippingType.valueOf(resSet.getString("shipping_type")));
				tempOrder.setId(resSet.getInt("id"));
				tempOrder.setDate(resSet.getString("create_date"));
				tempOrder.setStatus(OrderStatus.valueOf(resSet.getString("order_status")));
				tempOrders.add(tempOrder);
			}
		} catch (SQLException e) {
			log.error("Cannot get all orders", e);
			throw new DAOException("Cannot get all orders", e);
		} finally {
			try {
				if (resSet != null) resSet.close();
				if (statement != null) statement.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				log.warn("Cannot close connection/statement/resultset", e);
			}
		}
		log.info("Return all orders for customerID=" + customerID);
		return tempOrders;
	}
	
//	public void setDataSource(DataSource ds) {
//		dataSource = ds;
//	}
	
	public void setCustomerDao(CustomerDao cd) {
		customerDao = cd;
	}

}
