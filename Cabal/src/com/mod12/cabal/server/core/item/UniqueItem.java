package com.mod12.cabal.server.core.item;

import java.util.List;

public class UniqueItem extends Item {

	private int id;
	private String description;
	
	public UniqueItem(String name, int id, String description, List<AssociatedSkill> affectedSkills) {
		super(name, affectedSkills);
		this.id = id;
		this.description = description;
	}

	public UniqueItem(UniqueItem item) {
		this(item.name, item.id, item.description, item.affectedSkills);
	}

	public int getID() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return name + ": " + description;
	}
	
}
