package com.mod12.cabal.server.core.person;

import com.mod12.cabal.server.core.faction.Faction;

public class Person {

	protected Name name;
	protected int age;
	protected final Sex sex;	
	protected Faction faction; 	
	
	public Person(Name name, int age, Sex sex, Faction faction) {
		this.name = name;
		this.age = age;
		this.sex = sex;		
		this.faction = faction;
	}
	
	public void nameChange(Name newName) {
		name = newName;
	}
	
	// determine algorithm to calculate when they get older
	public void growOlder(int passedYears) {
		age += passedYears;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the sex
	 */
	public Sex getSex() {
		return sex;
	}
	
	/**
	 * 
	 * @param faction
	 * @return true if the person did not already associate with the specified faction
	 *
	public boolean joinFaction(Faction faction) {
		return associatedFactions.add(faction);
	}

	/**
	 * 
	 * @param faction
	 * @return true if the person was associated with the specified faction
	 *
	public boolean leaveFaction(Faction faction) {
		return associatedFactions.remove(faction);
	}*/
	
	public Faction getFaction() {
		return faction;
	}
}
