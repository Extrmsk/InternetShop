package com.lemanov.internetshop.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGroup {
	
	private int id;
	private String name;
	private List<Group> childrens = new ArrayList<>();
	
	public AbstractGroup(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	public List<Group> getChildrens() {
		return childrens;
	}
	
	public void addChildren(Group child) {
		childrens.add(child);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
