package com.lemanov.internetshop.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lemanov.internetshop.dao.DAOException;

public class Group extends AbstractGroup {
	
	private static Logger log = Logger.getLogger(Group.class.getName());
	
	public Group(int id, String name) {
		super(id, name);
	}
	
	public Group(int id, String name, Group parent) {
		super(id, name);
		parent.addChildren(this);
	}
	
	public List<Integer> getAllTreeIDs() throws DAOException {
		List<Integer> treeIDs = new ArrayList<>();
		treeIDs.add(this.getId());
		addAllTreeIDsToList(this, treeIDs);
		return treeIDs;
	}
	
	private void addAllTreeIDsToList(Group parent, List<Integer> listIDs) throws DAOException {
		List<Group> childrens = parent.getChildrens();
		if(!childrens.isEmpty()) {
			for (Group child : childrens) {
				listIDs.add(child.getId());
				addAllTreeIDsToList(child, listIDs);
			}
		}
	}

	@Override
	public String toString() {
		String result = "Group " + this.getName() + "(" + this.getId() + ") has a childrens:\n";
		for (Group child : getChildrens()) {
			result += child.getName() + "(" + child.getId() + ") ";
		}
		result += "\n" + "============== \n\n";
		return result;
	}
	
}
