package com.lemanov.internetshop.domain;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private String name;
	private int id;
	private List<Group> childrens = new ArrayList<>();
	
	public Group(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Group(int id, String name, Group parent) {
		this.id = id;
		this.name = name;
		parent.addChildren(this);
	}
	
	public void addChildren(Group child) {
		childrens.add(child);
	}

	@Override
	public String toString() {
		String result = "Group " + name + "(" + id + ") has a childrens:\n";
		for (Group child : childrens) {
			result += child.getName() + "(" + child.getId() + ") ";
		}
		result += "\n" + "============== \n\n";
		return result;
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

	public List<Group> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Group> childrens) {
		this.childrens = childrens;
	}
	
	
	
}
