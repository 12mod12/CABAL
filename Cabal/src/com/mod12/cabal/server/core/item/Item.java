package com.mod12.cabal.server.core.item;

import java.util.List;

public class Item {

	protected String name;
	protected List<AssociatedSkill> affectedSkills;

	public Item(String name, List<AssociatedSkill> affectedSkills) {
		this.name = name;
		this.affectedSkills = affectedSkills;
	}

	public Item(Item item) {
		this(item.name, item.affectedSkills);
	}

	public String getName() {
		return name;
	}

	public List<AssociatedSkill> getAffectedSkills() {
		return affectedSkills;
	}
	
	public String toString() {
		String result = this.name;
		return result;
	}
}
