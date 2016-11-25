package com.lemanov.internetshop.domain;

import java.util.ArrayList;
import java.util.List;

import com.lemanov.internetshop.dao.DAOException;
import com.lemanov.internetshop.dao.GoodsDao;
import com.lemanov.internetshop.dao.GroupDao;

public class GroupTest {
	
	private static GroupDao groupDao = Service.getGroupDao(); 
	private static GoodsDao goodsDao = Service.getGoodsDao();
	

	public static void main(String[] args) {
		
//		List<Integer> groupIDs = new ArrayList<Integer>();
//		groupIDs.add(8);
//		groupIDs.add(12);
//		
//		int[] arr = {9};
		
		Group a = new Group(1, "a");
		Group b = new Group(2, "b", a);
		Group c = new Group(3, "c", a);
		Group d = new Group(4, "d", c);
		Group e = new Group(5, "e", c);
		Group f = new Group(6, "f", c);
		
		try {
			System.out.println(a.getAllTreeIDs().toString());
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
//		try {
//			List<Goods> goods = goodsDao.getGoodsByGroupIDs(arr);
//			System.out.println("is empty: " + goods.isEmpty());
//			System.out.println(goods.toString());
//			
//			
//		} catch (DAOException e) {
//			e.printStackTrace();
//		}
//		

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
