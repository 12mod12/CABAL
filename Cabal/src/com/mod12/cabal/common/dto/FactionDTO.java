package com.mod12.cabal.common.dto;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.server.core.faction.Faction;

public class FactionDTO extends DataTransferObject {

	private static final String AGENTS_DELIM = "_agents_";
	private static final String NON_BUSY_AGENTS_DELIM = "_non_busy_agents_";
	
	private String name;
	private int wealth;
	private int culture;
	private int infamy;
	private double presence;
	private boolean isHuman;
	private String playerHandle;
	
	private String headquarters;
//	private String leader;
	private List<String> agents;
	private List<String> nonBusyAgents;
	
	public FactionDTO(String name, int wealth, double prestige,
			int culture, int infamy, double presence, boolean isHuman, String playerHandle,
			String headquarters, List<String> agents, List<String> nonBusyAgents) {
		this.name = name;
		this.wealth = wealth;
		this.culture = culture;
		this.infamy = infamy;
		this.presence = presence;
		this.isHuman = isHuman;
		this.playerHandle = playerHandle;
		this.headquarters = headquarters;
		this.agents = agents;
		this.nonBusyAgents = nonBusyAgents;
	}
	
	public FactionDTO(Faction faction) {
		this.name = faction.getName();
		this.wealth = faction.getWealth().getMoney();
		this.culture = faction.getPrestige().getCulture();
		this.infamy = faction.getPrestige().getCulture();
		this.presence = faction.getPresence().getPresence();
		this.isHuman = faction.isHuman();
		this.playerHandle = faction.getPlayerHandle();
		this.headquarters = faction.getHeadquarters().getName();
		this.agents = faction.getAgentNames();
		this.nonBusyAgents = faction.getNonBusyAgentsNames();
	}

	public FactionDTO(String faction) {
		String[] parts = faction.split(DELIM);
		name = parts[0];
		wealth = Integer.parseInt(parts[1]);
		culture = Integer.parseInt(parts[2]);
		infamy = Integer.parseInt(parts[3]);
		presence = Double.parseDouble(parts[4]);
		isHuman = Boolean.parseBoolean(parts[5]);
		playerHandle = parts[6];
		headquarters = parts[7];
		
		agents = new LinkedList<String>();
		nonBusyAgents = new LinkedList<String>();
		int count = 9;
		while (!parts[count].equals(NON_BUSY_AGENTS_DELIM)) {
			agents.add(parts[count]);
			count++;
		}
		count++;
		while (count != parts.length) {
			nonBusyAgents.add(parts[count]);
			count++;
		}
	}

	public String getName() {
		return name;
	}

	public int getWealth() {
		return wealth;
	}

	public int getCulture() {
		return culture;
	}
	
	public int getInfamy() {
		return infamy;
	}

	public double getPresence() {
		return presence;
	}

	public boolean isHuman() {
		return isHuman;
	}

	public String getPlayerHandle() {
		return playerHandle;
	}

	public String getHeadquarters() {
		return headquarters;
	}

	public List<String> getAgents() {
		return agents;
	}

	public List<String> getNonBusyAgents() {
		return nonBusyAgents;
	}

	public String marshall() {
		String output = name;
		output += DELIM + wealth;
		output += DELIM + culture;
		output += DELIM + infamy;
		output += DELIM + presence;
		output += DELIM + isHuman;
		output += DELIM + playerHandle;
		output += DELIM + headquarters;
		output += DELIM + AGENTS_DELIM;
		for (String agent : agents) {
			output += DELIM + agent;
		}
		output += DELIM + NON_BUSY_AGENTS_DELIM;
		for (String agent : nonBusyAgents) {
			output += DELIM + agent;
		}
		return output;
	}

	public void addBusyAgent(String agent) {
		nonBusyAgents.remove(agent);
	}

}
