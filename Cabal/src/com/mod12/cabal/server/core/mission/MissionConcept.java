package com.mod12.cabal.server.core.mission;
import com.mod12.cabal.server.core.person.agent.Ranking;

/**
 * MISSIONS are a FACTION’s method to push their agenda upon the environment. Anything that can 
 * affect a FACTION (positive or negative) occurs because a MISSION was attempted by an AGENT 
 * (not necessarily one under the control of the affected FACTION).  At the conclusion of a 
 * MISSION various variables in the system will change and AGENTS will be freed up to be 
 * assigned to other MISSIONS.  These events will also take a certain number of turns based on 
 * the assigned AGENT’S skills and the overall difficulty of the event.  If a MISSION is not 
 * attempted for a certain amount of time it will no longer be available.  MISSIONS also have a 
 * physical LOCATION in the WORLD which affects skill checks and difficulty.
 *
 * MISSIONS consist of:
 *	- name and type
 *	-a time frame for completion, which upon reaching the end causes the mission to expire
 *	-rewards
 *	-a LOCATION
 *	-success conditions/forumulas
 *	-failure paths (failure is not a binary state, and depending on when and where an AGENT fails 
 *     during the mission execution, different parts of the system will be affected.
 *
 */
public class MissionConcept {

	private String name;
	private String descriptionText;
	private Ranking difficulty;
	private MissionLogic pass;
	private MissionLogic fail;
	private int turnsToComplete;
	
	public MissionConcept (String name, String descText, Ranking difficulty, MissionLogic pass, MissionLogic fail, int turnsToComplete) {
		this.name = name;
		this.descriptionText = descText;
		this.difficulty = difficulty;
		this.pass = pass;
		this.fail = fail;
		this.turnsToComplete = turnsToComplete;
	}
	
	
/*	TODO mission ideas
		case 1: text = "A beggar stumbled across a meeting last week.  Make sure word doesn't get out about it."; break;
		case 2: text = "It is time to add to our numbers.  Head out to University campuses and try to recruit some new blood."; break;
		case 3: text = "The Mayor is up for re-election, his policies are unfavorable for us.  Convince him not to run again"; break;
		default: text = "A package needs to be delivered across town as soon as possible.";
*/
	
	public String getName() {
		return name;
	}

	public String getDescriptionText() {
		return descriptionText;
	}

	public Ranking getDifficulty() {
		return difficulty;
	}
	
	public MissionLogic getSuccessLogic() {
		return pass;
	}
	
	public MissionLogic getFailLogic() {
		return fail;
	}
	
	public int turnsToComplete() {
		return turnsToComplete;
	}

	public String getInfo() {
		return "Description: \n    " + descriptionText + "\n" + 
				"Difficulty: \n    " + difficulty + "\n" + 
				"Skill Check: \n   " + pass.getAssociatedSkill() + "\n" + 
				"Success Check: \n    " + pass.getSkillCheckValue();
	}


	public boolean requiresTarget() {
		return pass.getKeyword().requiresTarget();
	}

}
