package com.lemanov.internetshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.domain.Goods;
import com.lemanov.internetshop.domain.Group;

public class GroupDao {
	
	private DaoInit daoFactory = DaoInit.getInstance();
	private static Logger log = Logger.getLogger(GroupDao.class.getName());
	
	public Group addGroup(String name, int parentID) throws DAOException {
		log.trace("Get parameters: name=" + name + ",parentID=" + parentID);
		String sql = "INSERT INTO groups (group_name, group_parent_id) VALUES (?, ?);";
		
		Group tempGroup = null;
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
				prst.setInt(2, parentID);
				prst.execute();
				try {
					log.trace("Get result set");
					resSet = prst.getGeneratedKeys();
					resSet.next();
					log.trace("Create group to return");
					tempGroup = new Group(resSet.getString("group_name"), resSet.getInt("group_parent_id"),
							resSet.getInt("id"));
					log.info("Group item " + name + " is created");
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
			log.warn("Cannot create group", e);
			throw new DAOException("Cannot create group", e);
		} finally {
			try {
				conn.close();
				log.trace("Connection closed");
			} catch (SQLException e) {
				log.warn("Cannot close connection", e);
			}
		}
		log.trace("Group is created: id=" + tempGroup.getID() + ", name=" + tempGroup.getName() + ", parentID=" + tempGroup.getParentID());
		log.trace("Returning group");
		return tempGroup;
	}

}
