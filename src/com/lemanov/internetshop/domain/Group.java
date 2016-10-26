package com.lemanov.internetshop.domain;

import org.apache.log4j.Logger;

public class Group {
	
	private String name;
	private int parentID = 0;
	private int ID;
	private static Logger log = Logger.getLogger(Group.class.getName());
	
	public Group(String name, int id, int parentID) {
		this.name = name;
		this.ID = id;
		this.parentID = parentID;
		log.trace("Group " + name + " is created");
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public int getParentID() {
		return parentID;
	}
	

}
