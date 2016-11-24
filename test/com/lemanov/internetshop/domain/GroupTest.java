package com.lemanov.internetshop.domain;

import java.util.List;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.GroupDao;

public class GroupTest {
	
	private static GroupDao groupDao = ShopManager.getGroupDao(); 

	public static void main(String[] args) {
		
		Group root = new Group(0, "root");
//		Group a = new Group(1, "a");
//		Group b = new Group(1, "b");
//		root.addChildren(a);
		
		
		
		try {
			loadChildren(root);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		System.out.println("===========================");
		System.out.println(root.getChildrens().isEmpty());
		for (Group g : root.getChildrens()) {
			System.out.println(g.getName());
		}
		

	}
	
	public static void loadChildren(Group parent) throws DAOException {
		List<Group> childrens = groupDao.getChildrensList(parent.getId());
		if(!childrens.isEmpty()) {
			for (Group child : childrens) {
				System.out.println("Parent " + parent.getName() + "(" + parent.getId() + ") add child " + child.getName() + "(" + child.getId() + ")");
				parent.addChildren(child);
				loadChildren(child);
			}
		}
	}

}
