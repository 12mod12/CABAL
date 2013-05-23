package com.mod12.cabal.server.core.person.agent;

/**
 * determine more states and thresholds
 * http://docs.oracle.com/javase/1.5.0/docs/guide/language/enums.html
 *
 * @author kowalski
 *
 */
public enum Ranking {
	
			 	  	// Skill levels determine ranking 
					// Ranking doesn't mean anything, it is just a shorthand for discussing agent progression.
	/* TIER 1*/NEOPHYTE(25,3),      // The base level, all agents have at least this ranking
									// All skills start at 15, except for the three random tagged, with start at 25. 
	/* TIER 2*/ROOKIE(50,1),  	  	// A rookie has either bumped one skill to 50
	/* TIER 3*/VETERAN(50,3), 	 	// A veteran has either three skills >= 50 
	/* TIER 4*/OFFICER(65,4),		// An Officer has four skills >= 65	
	/* TIER 5*/MASTER(80,5);		// A master has five skills at or above 80					
	
	private int startingSkillLevel;
	private int numberOfSkills;
	
	Ranking(int startingSkillLevel, int numberOfSkills) {
		this.startingSkillLevel = startingSkillLevel;
		this.numberOfSkills = numberOfSkills;
	}
	
	public int startingSkillLevel() {
		return startingSkillLevel;
	}
	
	public int numberOfSkills() {
		return numberOfSkills;
	}
}


