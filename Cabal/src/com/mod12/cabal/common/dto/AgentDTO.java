package com.mod12.cabal.common.dto;

import static com.mod12.cabal.server.core.person.agent.Agent.BUSY_FALSE;
import static com.mod12.cabal.server.core.person.agent.Agent.BUSY_TRUE;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.util.Tuple;
import com.mod12.cabal.server.core.person.agent.Agent;

public class AgentDTO extends DataTransferObject {

	private static final String SKILL_DELIM = "_skills_";
	
	private String name;
	private List<Tuple<String, Integer>> skills;
	private boolean busy;
	private String info;
	
	public AgentDTO(String name, List<Tuple<String, Integer>> skills, boolean busy, String info) {
		this.name = name;
		this.skills = skills;
		this.busy = busy;
		this.info = info;
	}

	public AgentDTO(Agent agent) {
		this.name = agent.getName().toString();
		skills = agent.createSkillsList();
		this.busy = agent.isBusy();
		this.info = agent.getInfo();
	}

	public AgentDTO(String agent) {
		String[] parts = agent.split(DELIM);
		name = parts[0];
		busy = Boolean.parseBoolean(parts[1]);
		info = parts[2];
		
		skills = new LinkedList<Tuple<String, Integer>>();
		int count = 4;
		while (count != parts.length) {
			String temp = parts[count];
			String[] tuple = temp.split(TUPLE_DELIM);
			skills.add(new Tuple<String, Integer>(tuple[0], Integer.parseInt(tuple[1])));
			count++;
		}
	}

	public String getName() {
		return name;
	}

	public List<Tuple<String, Integer>> getSkills() {
		return skills;
	}

	public boolean isBusy() {
		return busy;
	}
	
	public void busy() {
		busy = true;
		info = info.replaceFirst(BUSY_FALSE, BUSY_TRUE);
	}

	public String getInfo() {
		return info;
	}

	public String marshall() {
		String output = name;
		output += DELIM + busy;
		output += DELIM + info;
		output += DELIM + SKILL_DELIM;
		for (Tuple<String, Integer> skill : skills) {
			output += DELIM + skill.x + TUPLE_DELIM + skill.y;
		}
		return output;
	}
	
	private Tuple<String, Integer> findSkill(String name) {
		for (Tuple<String, Integer> skill : skills) {
			if (skill.x.equals(name)) return skill;
		}
		return null;
	}
	
	public int calculateProbably(String skillName, int successCheck) {
		Tuple<String, Integer> skill = findSkill(skillName);
		if (skill == null) return 0;
		successCheck -= skill.y;
		if (successCheck <= 0) {
			successCheck = 1;
		}
		int probability = 100 - successCheck;
		return probability;
	}

}
