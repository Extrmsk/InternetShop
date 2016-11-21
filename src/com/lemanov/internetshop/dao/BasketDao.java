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

import com.lemanov.internetshop.domain.Basket;
import com.lemanov.internetshop.domain.Goods;
import com.lemanov.internetshop.domain.OrderLine;

public class BasketDao {
	
	private DaoInit daoInstance = DaoInit.getInstance();
	private static Logger log = Logger.getLogger(BasketDao.class.getName());
	private static GoodsDao goodsDao;
	private static CustomerDao customerDao;
	private static DataSource dataSource;
	
	
	
	public int getGoodsItemAmount(int customerID, int goodsID) throws DAOException {
		log.trace("Prepearing to get goods amount. GoodsID=" + goodsID + ", customerID=" + customerID);
		String sql = "SELECT amount FROM basket WHERE customer_id = ? AND goods_id = ?;";
		
		int tempAmount = 0;
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet resSet = null;
		try {
			log.trace("Open connection");
			conn = daoInstance.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prst.setInt(1, customerID);
				prst.setInt(2, goodsID);
				try {
					log.trace("Get result set");
					resSet = prst.executeQuery();
					while (resSet.next()) {
						log.trace("Create amount to return");
						tempAmount = resSet.getInt("amount");
					}
					log.info("temp amount=" + tempAmount + " is created");
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
			log.warn("Can not return amount", e);
			throw new DAOException("Can not return amount", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning amount");
		return tempAmount;
	}

	public void add(int customerID, int goodsID, int amount) throws DAOException {
		log.trace("Start add item: goodsID=" + goodsID + ", customerID=" + customerID + ", amount=" + amount);
		String sql = "INSERT INTO basket (customer_id, goods_id, amount) VALUES (?, ?, ?);";
		
		Connection conn = null;
		PreparedStatement prst = null;
		try {
			log.trace("Open connection");
			conn = daoInstance.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql);
				prst.setInt(1, customerID);
				prst.setInt(2, goodsID);
				prst.setInt(3, amount);
				prst.executeUpdate();
				log.trace("PrepStatement is executed");
			} finally {
				try {
					prst.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Can not add item", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
	}
	
	public void delOrderLineForCustomer(int goodsID, int customerID) throws DAOException {
		log.trace("Start to remove item: goodsID=" + goodsID + ", customerID=" + customerID);
		String sql = "DELETE FROM basket WHERE customer_id=? AND goods_id=?;";
		
		Connection conn = null;
		PreparedStatement prst = null;
		try {
			log.trace("Open connection");
			conn = daoInstance.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql);
				prst.setInt(1, customerID);
				prst.setInt(2, goodsID);
				prst.executeUpdate();
				log.trace("PrepStatement is executed");
			} finally {
				try {
					prst.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Can not remove item", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot remove connection", e);
			}
		}
		log.trace("Item is removed. GoodsID=" + goodsID);
	}
	
	public void deleteAll() throws DAOException {
		log.trace("Start to delete all raws");
		String sql = "DELETE FROM basket;";
		
		Connection conn = null;
		PreparedStatement prst = null;
		try {
			log.trace("Open connection");
			conn = daoInstance.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql);
				prst.executeUpdate();
				log.trace("PrepStatement is executed");
			} finally {
				try {
					prst.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Can not remove all rows", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot remove connection", e);
			}
		}
		log.trace("Remove all rows is complete");
	}
		
		

	public void update(int customerID, int goodsID, int amount) throws DAOException {
		log.trace("Start update item: goodsID=" + goodsID + ", customerID=" + customerID + ", amount=" + amount);
		String sql = "UPDATE basket SET amount = ? WHERE customer_id = ? AND goods_id = ?;";
		
		Connection conn = null;
		PreparedStatement prst = null;
		try {
			log.trace("Open connection");
			conn = daoInstance.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql);
				prst.setInt(1, amount);
				prst.setInt(2, customerID);
				prst.setInt(3, goodsID);
				prst.executeUpdate();
				log.trace("PrepStatement is executed");
			} finally {
				try {
					prst.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Can not update item", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
	}

	public void clearCustomerBasket(int customerID) throws DAOException {
		log.trace("Start to remove basket for customer=" + customerID);
		String sql = "DELETE FROM basket WHERE customer_id = ?;";
		
		Connection conn = null;
		PreparedStatement prst = null;
		try {
			log.trace("Open connection");
			conn = daoInstance.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql);
				prst.setInt(1, customerID);
				prst.executeUpdate();
				log.trace("PrepStatement is executed");
			} finally {
				try {
					prst.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Can not remove item", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot remove connection", e);
			}
		}
		log.trace("Basket is removed for customerID=" + customerID);
	}

	public List<OrderLine> getAllOdrerLinesForCustomer(int customerID) throws DAOException {
		log.trace("Prepearing to get all OrderLines for customerID=" + customerID);
		String sql = "SELECT goods_id, amount FROM basket WHERE customer_id = ? ORDER BY goods_id;";

		List<OrderLine> orderLines = new ArrayList<>();
		OrderLine tempOrderLine = null;
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet resSet = null;
		try {
			log.trace("Open connection");
			conn = daoInstance.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prst.setInt(1, customerID);
				try {
					log.trace("Get result set");
					resSet = prst.executeQuery();
					while (resSet.next()) {
						log.trace("Create OrderLine to return");
						Goods tempGoods = goodsDao.getGoodsByID(resSet.getInt("goods_id"));
						tempOrderLine = new OrderLine(tempGoods, resSet.getInt("amount"));
						orderLines.add(tempOrderLine);
					}
					log.info("List of orderLines is created");
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
			log.warn("Can not return OrderLine list", e);
			throw new DAOException("Can not return OrderLine list", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning list OrderLines");
		return orderLines;
	}

	public Basket getBasketByCustomerID(int customerID) throws DAOException {
		log.trace("Prepearing to get basket of customerID=" + customerID);
		String sql = "SELECT * FROM basket WHERE customer_id = ?;";
		
		
		Basket tempBasket = new Basket(customerDao.getCustomerByID(customerID));
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet resSet = null;
		
		try {
			log.trace("Open connection");
			conn = daoInstance.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prst.setInt(1, customerID);
				try {
					log.trace("Get result set");
					resSet = prst.executeQuery();
					while (resSet.next()) {
						Goods goodsItem = goodsDao.getGoodsByID(resSet.getInt("goods_id"));
						tempBasket.addGoodsItem(goodsItem, resSet.getInt("amount"));
					}
					log.info("basket is created. Added goods");
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
			log.warn("Can not return Basket", e);
			throw new DAOException("Can not return Basket", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		return tempBasket;
	}
	
	public void moveBasketToOrderByCustID(int customerID, int orderID) throws DAOException {
		log.trace("Move all basket to OrderLines by customerID=" + customerID);
		String sql = "WITH output AS ("
				+ "DELETE FROM basket WHERE customer_id = ?"
				+ "RETURNING (?) AS order_id, goods_id, amount)"
				+ "INSERT INTO order_lines (order_id, goods_id, amount)"
				+ "SELECT order_id, goods_id, amount FROM output;";
		
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setInt(2, orderID);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Cannot move basket to orderLines", e);
			throw new DAOException("Cannot move basket to orderLines", e);
		} finally {
			try {
				if (statement != null) statement.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				log.warn("Cannot close connection/statement/resultset", e);
			}
		}
		log.trace("Basket for customerID=" + customerID + " is moved to orderLines. OrderID=" + orderID);
	}
	
	public void setDataSource(DataSource ds) {
		dataSource = ds;
	}
	
	public void setGoodsDao(GoodsDao gd) {
		goodsDao = gd;
	}
	
	public void setCustomerDao(CustomerDao cd) {
		customerDao = cd;
	}

}
