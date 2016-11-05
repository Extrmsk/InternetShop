package com.lemanov.internetshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.domain.Customer;
import com.lemanov.internetshop.domain.Goods;

public class GoodsDao {
	
	private DaoFactory daoFactory = DaoFactory.getInstance();
	private static Logger log = Logger.getLogger(GoodsDao.class.getName());
	
	public Goods addGoodsItem(String name, int price, int groupID, int amount) throws DAOException {
		log.trace("Get parameters: name=" + name + ", price=" + price + ", groupID=" + groupID + ", amount=" + amount);
		String sql = "INSERT INTO goods (goods_name, price, amount, group_id) VALUES (?, ?, ?, ?);";

		Goods tempGoods = null;
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet resSet = null;
		try {
			log.trace("Open connection");
			conn = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prst.setString(1, name);
				prst.setInt(2, price);
				prst.setInt(3, groupID);
				prst.setInt(4, amount);
				prst.execute();
				try {
					log.trace("Get result set");
					resSet = prst.getGeneratedKeys();
					resSet.next();
					log.trace("Create goods item to return");
					tempGoods = new Goods(resSet.getString("goods_name"), resSet.getInt("price"),
							resSet.getInt("amount"), resSet.getInt("group_id"));
					tempGoods.setId(resSet.getInt("id"));
					log.info("Goods item " + name + " is created");
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
			log.warn("Cannot create goods item", e);
			throw new DAOException("Cannot create goods item", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Goods item is created: id=" + tempGoods.getId() + ", name=" + tempGoods.getName() + ", price="
				+ tempGoods.getPrice() + ", groupId=" + tempGoods.getGroupID() + ", amount=" + tempGoods.getAmount());
		log.trace("Returning goods item");
		return tempGoods;
	}
	
// searching the match of the name expression
	public List<Goods> findGoodsByName(String nameSearchExp) throws DAOException {
		List<Goods> goodsList = new ArrayList<Goods>();
		String sql = "SELECT * FROM goods WHERE goods_name LIKE ?;";
		
		Goods tempGoods = null;
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet resSet = null;
		try {
			log.trace("Open connection");
			conn = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prst.setString(1, "%" + nameSearchExp + "%");
				try {
					log.trace("Get result set");
//					System.out.println(prst.toString()); //print full sql statement
					resSet = prst.executeQuery();
					while (resSet.next()) {
						log.trace("Create goods to add to the set");
						tempGoods = new Goods(resSet.getString("goods_name"),
								resSet.getInt("price"), resSet.getInt("amount"),
								resSet.getInt("group_id"));
						tempGoods.setId(resSet.getInt("id"));
						goodsList.add(tempGoods);
						log.trace("Goods item " + tempGoods.getName() + " added to set");
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
		} catch (SQLException e) {
			throw new DAOException("Cannot get goods by name=" + nameSearchExp, e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning all goods with match name=" + nameSearchExp);
		return goodsList;
	}

	public void updateAmount(int goodsID, int newAmount) throws DAOException {
		String sql = "UPDATE goods SET amount = ? where id = ?;";
		
		Connection conn = null;
		PreparedStatement prst = null;
		try {
			log.trace("Open connection");
			conn = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql);
				prst.setInt(1, newAmount);
				prst.setInt(2, goodsID);
				prst.executeUpdate();
				log.trace("Amount of goods id=" + goodsID + ", changed to " + newAmount);
			} finally {
				try {
					prst.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot update goods amount", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
	}
	
	public Goods getGoodsByID(int id) throws DAOException {
		log.info("Searching goodsItem id=" + id);
		String sql = "select * from goods where id = ?;";
		
		Goods tempGoods = null;
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet resSet = null;
		try {
			log.trace("Open connection");
			conn = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql);
				prst.setInt(1, id);
				try {
					log.trace("Get result set");
					resSet = prst.executeQuery();
					while (resSet.next()) {
						log.trace("Create goodsItem to return");
						tempGoods = new Goods(resSet.getString("goods_name"),
								resSet.getInt("price"), resSet.getInt("amount"),
								resSet.getInt("group_id"));
						tempGoods.setId(resSet.getInt("id"));
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
		if (tempGoods == null) {
			log.debug("GOODS ITEM NOT FOUND");
		} else {
			log.trace("Goods item id=" + tempGoods.getId() + " is found");
		}
		log.trace("Returning goodsItem");
		return tempGoods;
	}

	public List<Goods> getAll() throws DAOException {
		List<Goods> goodsList = new ArrayList<>();
		String sql = "select * from goods;";
		
		Goods tempGoods = null;
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet resSet = null;
		try {
			log.trace("Open connection");
			conn = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql); //only read. Without keys
				try {
					log.trace("Get result set");
					resSet = prst.executeQuery();
					while (resSet.next()) {
						log.trace("Create goods to add to the set");
						tempGoods = new Goods(resSet.getString("goods_name"),
								resSet.getInt("price"), resSet.getInt("amount"),
								resSet.getInt("group_id"));
						tempGoods.setId(resSet.getInt("id"));
						goodsList.add(tempGoods);
						log.trace("Goods item " + tempGoods.getName() + " added to set");
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
		} catch (SQLException e) {
			throw new DAOException("Cannot get all goods", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Returning all goods");
		return goodsList;
	}

	public void delGoodsItemByID(int delID) throws DAOException {
		String sql = "delete from goods where id = ?;";
		
		Connection conn = null;
		PreparedStatement prst = null;
		try {
			log.trace("Open connection");
			conn = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql);
				prst.setInt(1, delID);
				prst.executeUpdate();
				log.trace("Ttem id=" + delID + " is deleted");
			} finally {
				try {
					prst.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Can not delete item", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
	}

	public void update(int editID, String name, int price, int amount, int groupID) throws DAOException {
		String sql = "UPDATE goods SET goods_name = ?, price = ?, amount = ?, group_id = ? WHERE id = ?;";
		
		Connection conn = null;
		PreparedStatement prst = null;
		try {
			log.trace("Open connection");
			conn = daoFactory.getConnection();
			try {
				log.trace("Create prepared statement");
				prst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				prst.setString(1, name);
				prst.setInt(2, price);
				prst.setInt(3, amount);
				prst.setInt(4, groupID);
				prst.setInt(5, editID);
				prst.executeUpdate();
				log.trace("Goods item id=" + editID + " is fully updated");
			} finally {
				try {
					prst.close();
					log.trace("statement closed");
				} catch (SQLException e) {
					log.warn("Cannot close statement", e);
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot update goods item", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Goods item id=" + editID + " has fully updated");
	}

}
