package com.mod12.cabal.server.core.person.hero;

import com.mod12.cabal.server.core.faction.Faction;
import com.mod12.cabal.server.core.location.Stage;
import com.mod12.cabal.server.core.person.Name;
import com.mod12.cabal.server.core.person.Sex;
import com.mod12.cabal.server.core.person.agent.Agent;
import com.mod12.cabal.server.core.person.agent.Equipment;
import com.mod12.cabal.server.core.person.agent.Skill;
import com.mod12.cabal.server.core.person.agent.condition.Condition;

public class Hero extends Agent {

	public Hero(Name name, int age, Sex sex, Stage location, Skill[] skills,
			Faction factions, Equipment equipment,
			Condition condition) {
		super(name, age, sex, location, skills, factions, equipment, condition);
		// TODO implement
	}

}
