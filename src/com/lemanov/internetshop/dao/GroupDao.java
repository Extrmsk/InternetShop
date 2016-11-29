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

import com.lemanov.internetshop.domain.Goods;
import com.lemanov.internetshop.domain.Group;
import com.lemanov.internetshop.domain.Service;

public class GroupDao {
	
	private static Logger log = Logger.getLogger(GroupDao.class.getName());
	private static DataSource dataSource;
	
	public Group addGroup(String name, int parentID) throws DAOException {
		log.trace("Get parameters: name=" + name + ",parentID=" + parentID);
		String sql = "INSERT INTO groups (group_name, group_parent_id) VALUES (?, ?);";
		
		Group tempGroup = null;
		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet resSet = null;
		try {
			log.trace("Open connection");
			conn = dataSource.getConnection();
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
		log.trace("Returning group");
		return tempGroup;
	}
	
	public List<Group> getChildrensList(int parentID) throws DAOException {
		log.trace("Get childrens for parentID=" + parentID);
		String sql = "SELECT * FROM groups WHERE group_parent_id = ?";
		
		List<Group> tempChildrens = new ArrayList<Group>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resSet = null;
		try {
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(sql); // only read. Without keys
			statement.setInt(1, parentID);
			resSet = statement.executeQuery();
			while (resSet.next()) {
				Group tempGroup = new Group(resSet.getInt("id"), resSet.getString("group_name"));
				tempChildrens.add(tempGroup);
			}
		} catch (SQLException e) {
			log.error("Cannot get childrens", e);
			throw new DAOException("Cannot get childrens", e);
		} finally {
			try {
				if (resSet != null) resSet.close();
				if (statement != null) statement.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				log.warn("Cannot close connection/statement/resultset", e);
			}
		}
		log.trace("Return childrens");
		return tempChildrens;
	}
	
	public static void setDataSource(DataSource dataSource) {
		GroupDao.dataSource = dataSource;
	}
}
