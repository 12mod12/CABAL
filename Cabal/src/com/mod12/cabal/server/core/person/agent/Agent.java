package com.mod12.cabal.server.core.person.agent;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.util.RandomUtil;
import com.mod12.cabal.common.util.Tuple;
import com.mod12.cabal.server.core.faction.Faction;
import com.mod12.cabal.server.core.item.Item;
import com.mod12.cabal.server.core.item.UniqueItem;
import com.mod12.cabal.server.core.location.Stage;
import com.mod12.cabal.server.core.manager.ItemManager;
import com.mod12.cabal.server.core.person.Name;
import com.mod12.cabal.server.core.person.Person;
import com.mod12.cabal.server.core.person.Sex;
import com.mod12.cabal.server.core.person.agent.condition.Condition;
import com.mod12.cabal.server.core.person.agent.condition.MentalHealth;
import com.mod12.cabal.server.core.person.agent.condition.PhysicalHealth;

/**
 * Class: Agent
 * An agent exists as the basic interaction unit in
 * the world of cabal, taking missions, executing them,
 * and changing the structure of the world in the process
 *
 * @author Hamilton
 *
 */
public class Agent extends Person {

	private static final String BUSY = "Busy: ";
	public static final String BUSY_TRUE = "Busy: true";
	public static final String BUSY_FALSE = "Busy: false";
	
	// TODO enum tiers for agent generation: think about skill lvls/ratios for tier declarations
	private Stage location; 			//the current location of the agent
	private Skill[] skills;  			//as big as total number of skills for an era
	private Equipment equipment; 			//the currently equipped items of an agent
	private Condition condition;
    private boolean busy;
	// TODO implement  loyalties later
	//private int[] loyalties; 			//an array of all the loyalties to all the groups an agent belongs to


	// TODO implement beliefs later
	//beliefs							an as yet undefined element


	public Agent(Name name, int age, Sex sex, Stage location, Skill[] skills, Faction faction, 
			Equipment equipment, Condition condition) {
		super(name, age, sex, faction);
		this.location = location;
		this.skills = skills;
		this.equipment = equipment;
		this.condition = condition;
	}

	/**
	 * Moves the agent to a new Location, may or may not have a formula
	 * to determine success or may just auto-move.  Still not sure.
	 *
	 * @param dest the destination the target wishes to move to
	 * @return whether or not the move was successful
	 *
	 */
	public boolean moveLocation(Stage destination) {
		return false;
	}

	/**
	 * Indicates a skill to be raised in training.
	 * Needs more params to indicate how well/much training is
	 *
	 * @param goal the skill to be trained
	 * @return whether or not the training was successful
	 */
	public boolean trainSkill(Skill goal) {
		return false;
	}

	public Stage getLocation() {
		return location;
	}

	public Condition getCondition() {
		return condition;
	}

	public PhysicalHealth getPhysicalHealth() {
		return condition.getPhysicalHealth();
	}

	public MentalHealth getMentalHealth() {
		return condition.getMentalHealth();
	}

	public boolean executeSkill(String skillName, int successCheck) {
		Skill skill = findSkill(skillName);
		successCheck -= skill.getValue();
		if (successCheck <= 0) {
			successCheck = 1;
		}
		int roll = RandomUtil.roll();
		boolean success = roll > successCheck;
		skill.gainExperience(success);
		return success;
	}

	public int calculateProbably(String skillName, int successCheck) {
		Skill skill = findSkill(skillName);
		successCheck -= skill.getValue();
		if (successCheck <= 0) {
			successCheck = 1;
		}
		int probability = 100 - successCheck;
		return probability;
	}

	private Skill findSkill(String skillName) {
		for (Skill skill : skills) {
			if (skill.getName().equals(skillName)) return skill;
		}
		return null;
	}

	public void equipItem(ItemArea area, Item item) {
		equipment.equipItem(area, item);
	}

	public Item removeItem(String itemName) {
		if (equipment.hasItem(itemName)) {
			Item item = equipment.removeItem(itemName);
			return item;
		} else {
			return null;
		}
	}

	public UniqueItem removeUniqueItem(int id) {
		String itemName = ItemManager.getItemManager().getUniqueItemName(id);
		if (equipment.hasItem(itemName)) {
			UniqueItem uniqueItem = (UniqueItem) equipment.removeItem(itemName);
			return uniqueItem;
		}
		else {
			return null;
		}
	}

	public String printSkills() {
		String result = "";
		for (Skill skill : skills) {
			result += "   " + skill.getName() + ":" + skill.getValue() + "\n";
		}
		return result;
	}

	public String toString() {
		return name.toString();
	}
	
	public boolean isBusy() {
		return busy;
	}
	
	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public String getInfo() {
		String output = "";
        output += "Age: " + age;
        output += "     Sex: " + sex + "\n";
        output += "Physical Health:" + condition.getPhyiscalState() + "\n";
        output += "Mental   Health:" + condition.getMentalState() + "\n";
        //TODO fix agent location init
//        output += "Location: " + agent.getLocation().getName() + "\n";
        output += BUSY + busy + "\n"; 
        // TODO get active mission is busy
        output += "Skills:\n";
        output += printSkills();
        
        return output;
	}
	
	public List<Tuple<String, Integer>> createSkillsList() {
		LinkedList<Tuple<String, Integer>> skillList = new LinkedList<Tuple<String, Integer>>();
		for (Skill skill : skills) {
			skillList.add(new Tuple<String, Integer>(skill.getName(), skill.getValue()));
		}
		return skillList;
	}
	
}
