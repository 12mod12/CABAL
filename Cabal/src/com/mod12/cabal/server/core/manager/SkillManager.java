package com.mod12.cabal.server.core.manager;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class SkillManager {

	private static SkillManager manager;
	
	private Hashtable<String, String> descriptions;
	private List<String> names = null;
	
	private SkillManager() {
	}

	public static SkillManager getSkillManager() {
		if (manager == null) manager = new SkillManager();
		return manager;
	}
	
	public boolean initializeSkills(Hashtable<String,String> initDescriptions, List<String> initName) {
		if (names != null) return false;
		
		this.descriptions = initDescriptions;
		this.names = initName;
		
		return true;
	}
	
	public String getSkillDescription(String skill) {
		return descriptions.get(skill);
	}
	
	/**
	 * @return new allocated list
	 */
	public List<String> getSkillNames() {
		return new LinkedList<String>(names);
	}
	
}
