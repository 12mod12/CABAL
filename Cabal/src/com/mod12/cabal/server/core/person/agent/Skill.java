package com.mod12.cabal.server.core.person.agent;


/**
 * A class containing the concept of a Skill. A skill is a "the ability to do something well; expertise". 
 * In the context of Cabal, a skill is known by an {@link Agent}. A skill has a name (name) and a percentage value 
 * (value) for how well the {@link Agent} can perform the skill.
 * @author kowalski / hamilton
 *
 */
public class Skill {
	
	//TODO: figure out skill leveling curves. 1-1 all the way or exponential requirements?
	
	private String name;
	private int level; //out of 100
	private int experience; // 0 - 10, resets and increases level at 10;
	private static final int NEEDED_EXP = 10;
	private static final int SUCCESS_EXP = 5;
	private static final int FAIL_EXP = 1;
	private static final int DEFAULT_LEVEL = 15;
	
	public Skill(String name, int initialValue) {
		this.name = name;
		this.level = initialValue;
	}
	
	public Skill(String name) {
		this.name = name;
		this.level = 15;
	}
	
	public void increaseSkill(int increase) {
		level = level + increase;
	}
	
	public void setSkill(int newValue) {
		level = newValue;
	}
	
	public String getName() {
		return name;
	}
	
	public int getValue() {
		return level;
	}

	public static int getDefaultLevel() {
		return DEFAULT_LEVEL;
	}

	public void gainExperience(boolean success) {
		if (success) experience += SUCCESS_EXP;
		else experience += FAIL_EXP;
		
		if (experience >= NEEDED_EXP) {
			levelUp();
		}
	}
	
	private void levelUp() {
		experience = experience % NEEDED_EXP;
		level++;
	}
	
}
