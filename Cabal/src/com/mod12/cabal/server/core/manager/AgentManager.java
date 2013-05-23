package com.mod12.cabal.server.core.manager;
import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.util.RandomUtil;
import com.mod12.cabal.server.core.faction.Faction;
import com.mod12.cabal.server.core.person.Name;
import com.mod12.cabal.server.core.person.Sex;
import com.mod12.cabal.server.core.person.agent.Agent;
import com.mod12.cabal.server.core.person.agent.Equipment;
import com.mod12.cabal.server.core.person.agent.Ranking;
import com.mod12.cabal.server.core.person.agent.Skill;
import com.mod12.cabal.server.core.person.agent.condition.Condition;


/**
 * This class functions as the wrapper management class for all Agents available to s specific Faction
 * It maintains the actual instances of Agent classes relevant to the game as well as being responsible 
 * for the utility functions to handle generation and reflection of instances of the Agents available 
 * to the player or computer faction.
 * 
 * @author Hamilton
 *
 */
public class AgentManager {
	
	private static final int MAX_AGE = 61;
	private static final int MINIMUM_AGE = 15;

	private static AgentManager manager;
	
//	private ArrayList<Agent> agents = new ArrayList<Agent>();

	public static AgentManager getAgentManager() {
		if (manager == null) manager = new AgentManager();
		return manager;
	}
	
	private AgentManager() {
	}
	
	public List<Agent> generateAgents(String factionName, Ranking difficulty, int numberOfAgents) {
		Faction faction = FactionManager.getFactionManager().getFaction(factionName);
		List<Agent> fresh = new LinkedList<Agent>();
		for (int i = 0; i < numberOfAgents; i ++ ) {
			if (difficulty == null) fresh.add(this.generateBasicAgent(faction));
			else if (difficulty == Ranking.NEOPHYTE) fresh.add(this.generateNeophyte(faction));
			else if (difficulty == Ranking.ROOKIE) fresh.add(this.generateRookie(faction));
			else if (difficulty == Ranking.VETERAN) fresh.add(this.generateVeteran(faction));
			else if (difficulty == Ranking.OFFICER) fresh.add(this.generateOfficer(faction));
			else if (difficulty == Ranking.MASTER) fresh.add(this.generateMaster(faction));
		}
		return fresh;
	}
	
	private Agent generateBasicAgent(Faction faction) {
		Skill[] skillSet = initializeSkillSet();		
		return generateAgentBasics(skillSet, faction);
	}
	
	private Agent generateNeophyte(Faction faction) {
		Skill[] skillSet = initializeSkillSet(Ranking.NEOPHYTE);		
		return generateAgentBasics(skillSet, faction);
	}
	
	private Agent generateRookie(Faction faction) {
		Skill[] skillSet = initializeSkillSet(Ranking.NEOPHYTE, Ranking.ROOKIE);		
		return generateAgentBasics(skillSet, faction);
	}
	
	private Agent generateVeteran(Faction faction) {
		Skill[] skillSet = initializeSkillSet(Ranking.NEOPHYTE, Ranking.ROOKIE, Ranking.VETERAN);		
		return generateAgentBasics(skillSet, faction);
	}
	
	private Agent generateOfficer(Faction faction) {
		Skill[] skillSet = initializeSkillSet(Ranking.NEOPHYTE, Ranking.ROOKIE, Ranking.VETERAN, Ranking.OFFICER);		
		return generateAgentBasics(skillSet, faction);
	}
	
	private Agent generateMaster(Faction faction) {
		Skill[] skillSet = initializeSkillSet(Ranking.NEOPHYTE, Ranking.ROOKIE, Ranking.VETERAN, Ranking.OFFICER, Ranking.MASTER);		
		return generateAgentBasics(skillSet, faction);
	}

	private Agent generateAgentBasics(Skill[] skillSet, Faction faction) {
		int age = RandomUtil.nextInt(MAX_AGE) + MINIMUM_AGE;  //generates a random age between 15 and 75.
		Equipment equipment = new Equipment();
		Condition condition = new Condition();
		
		boolean isFemale = RandomUtil.nextBoolean();
		Name name;
		Sex sex;
		name = Name.generateName(isFemale);
		if (!isFemale) { 
			sex = Sex.MALE;
		}
		else {
			sex = Sex.FEMALE;
		}							
		
		return new Agent(name, age, sex, null, skillSet, faction, equipment, condition);
	}
	
	
	private Skill[] initializeSkillSet(Ranking... rankingsToGenerate) {
		List<String> names = SkillManager.getSkillManager().getSkillNames();
		Skill[] skillSet = new Skill[names.size()];
		for (int i = 0; i < skillSet.length; i ++) {
			skillSet[i] = new Skill(names.remove(0));
		}
		
		for (Ranking ranking : rankingsToGenerate) {
			int numberOfSkills = ranking.numberOfSkills();
			int startingSkillLevel = ranking.startingSkillLevel();
			
			while (numberOfSkills > 0) {
				int skillIndex = RandomUtil.nextInt(skillSet.length);
				if (skillSet[skillIndex].getValue() == Skill.getDefaultLevel()) {
					skillSet[skillIndex].setSkill(startingSkillLevel);
					numberOfSkills--;
				}
			}
		}
		
		return skillSet;			
	}
	
//	public Agent getAgent(int location) {
//		return agents.get(location);
//	}
//	
//	public String toString() {
//		StringBuffer output = new StringBuffer();
//		for (Agent agent : agents) {
//			output.append(agent.toString());
//			output.append("\n");
//		}
//		return output.toString();
//	}

}
