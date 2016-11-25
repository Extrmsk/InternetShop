package com.lemanov.internetshop.domain;

import java.util.List;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.GroupDao;

public class Catalog extends AbstractGroup {

	private static GroupDao groupDao;
	private static Logger log = Logger.getLogger(Catalog.class.getName());
	private static Group tempGroup = null;
	
	public Catalog(int id, String name) {
		super(id, name);
		log.trace("Catalog obj is created");
	}
	
	
	public void loadCatalogTree() throws DAOException {
		if (this.getChildrens().isEmpty()) {
			loadChildren(this);
			log.trace("Load childrens is done");
		}
	}
	
	private static void loadChildren(AbstractGroup parent) throws DAOException {
		List<Group> childrens = groupDao.getChildrensList(parent.getId());
		if(!childrens.isEmpty()) {
			for (Group child : childrens) {
				log.trace("Parent " + parent.getName() + "(" + parent.getId() + ") add child " + child.getName() + "(" + child.getId() + ")");
				parent.addChildren(child);
				loadChildren(child);
			}
		}
	}
	
	public Group getGroupByID(int id) {
		getGroupFromTree(this, id);
		return tempGroup;
	}
	
	private void getGroupFromTree(AbstractGroup parent, int id) {
		List<Group> childrens = parent.getChildrens();
		if(!childrens.isEmpty()) {
			for (Group child : childrens) {
				if (child.getId() == id) {
					setTempGroup(child);
				}
				getGroupFromTree(child, id);
			}
		}
	}

	public void setGroupDao(GroupDao groupDao) {
		Catalog.groupDao = groupDao;
	}
	
	private void setTempGroup(Group group) {
		tempGroup = group;
	}
	

}
