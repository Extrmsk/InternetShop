package com.lemanov.internetshop.domain;

import java.util.List;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.CustomerDao;
import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.GoodsDao;
import com.lemanov.internetshop.dao.GroupDao;

public class GoodsManager {
	
	private GoodsDao goodsDao;
	private GroupDao groupDao;
	private static Logger log = Logger.getLogger(GoodsDao.class.getName());
	
	public GoodsManager() {
		goodsDao = new GoodsDao();
		groupDao = new GroupDao();
	}

	public Goods addGoodsItem(String name, int price, int groupID, int amount) throws DAOException {
		log.debug("Adding goods item: name=" + name + ", price=" + price + ", groupId=" + groupID
				+ ", amount=" + amount);
		return goodsDao.addGoodsItem(name, price, groupID, amount);
	}

	public Group addGroup(String name, int parentID) throws DAOException {
		log.debug("Adding group: name=" + name + ", parentID=" + parentID);
		return groupDao.addGroup(name, parentID);
	}

	public List<Goods> findGoodsByName(String name) throws DAOException {
		log.debug("Finding goods by name = " + name);
		return goodsDao.findGoodsByName(name);
	}

	//DODO find optimal way to update amount in existing object goods. Is this realy need?
	public void updateGoodsAmountFromDao(Goods goods) {
		//fuckfuckfuck
	}

}
