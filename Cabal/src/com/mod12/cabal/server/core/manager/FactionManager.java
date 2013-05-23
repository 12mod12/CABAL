package com.mod12.cabal.server.core.manager;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.dto.AgentDTO;
import com.mod12.cabal.common.dto.FactionDTO;
import com.mod12.cabal.common.dto.MissionConceptDTO;
import com.mod12.cabal.common.util.Quad;
import com.mod12.cabal.common.util.RandomUtil;
import com.mod12.cabal.common.util.Tuple;
import com.mod12.cabal.server.core.faction.Faction;
import com.mod12.cabal.server.core.faction.Money;
import com.mod12.cabal.server.core.location.FactionPresence;
import com.mod12.cabal.server.core.person.agent.Ranking;

/**
 * Knows of all Factions in world. Is the main interface to interact with for Faction information.
 * @author kowalski
 *
 */
public class FactionManager {

	private static FactionManager manager = null;
	
	private List<Faction> factions = null;
	private HashMap<String, List<String>> messagesForFactions;
	
	public static FactionManager getFactionManager() {
		if (manager == null) manager = new FactionManager();
		return manager;
	}
	
	private FactionManager() {
		messagesForFactions = new HashMap<String, List<String>>();
	}
	
	public void initializeFactions(List<String> factionNames, List<Integer> startingMonies, 
			List<String> initRanks, List<Integer> sizes) throws Exception {
		
		if (this.factions != null) throw new Exception("Must have starting factions.");

		this.factions = new LinkedList<Faction>();
		for (int i = 0; i < factionNames.size(); i++) {
			initializeFaction(factionNames.get(i), startingMonies.get(i), initRanks.get(i), sizes.get(i));
		}
	}
	
	private void initializeFaction(String name, int startingMoney, String startingAgentsRank, int numberOfAgents) {
		AgentManager agentManager = AgentManager.getAgentManager();
		Faction newFaction = new Faction(name, startingMoney);
		this.factions.add(newFaction);
		newFaction.addMembers(agentManager.generateAgents(name, Ranking.valueOf(startingAgentsRank), numberOfAgents));
	}
	
	public void setFactionHeadquaters(List<Tuple<String, String>> headquarters) {
		for (Tuple<String, String> pair : headquarters) {
			Faction faction = getFaction(pair.x);
			faction.setHeadquarters(pair.y);
		}
	}
	
	/**
	 * @return a new allocated list
	 */
	public List<Faction> getFactions() {
		return new LinkedList<Faction>(factions);
	}
	
	public Faction getFaction(String factionName) {
		for (Faction faction : factions) {			
			if (faction.getName().equals(factionName)) return faction;
		}
		return null;
	}
	
	public String getFactionNameFromHandle(String handleName) {
		for (Faction faction : factions) {			
			if (faction.getPlayerHandle().equals(handleName)) return faction.getName();
		}
		return null;
	}

	public void randomizeOrder() {
		List<Faction> newOrder = new LinkedList<Faction>();
		while (!this.factions.isEmpty()) {
			int index = RandomUtil.nextInt(this.factions.size());
			newOrder.add(this.factions.remove(index));
		}
		this.factions = newOrder;		
	}

	public void updateWorldPresences(List<FactionPresence> worldPresences) {
		for (FactionPresence presence : worldPresences) {
			String factionName = presence.getFaction();
			Faction faction = getFaction(factionName);
			faction.getPresence().setPresence(presence.getPresence());
		}
	}
	
	public List<Tuple<String, Integer>> calculateFactionsWealth() {
		List<Tuple<String, Integer>> wealths = new LinkedList<Tuple<String, Integer>>();
		for (Faction faction : factions) {
			Integer wealth = faction.getWealth().getMoney();
			wealths.add(new Tuple<String, Integer>(faction.getName(), wealth));
		}
		
		return wealths;
	}
	
	public List<Tuple<String, Double>> getFactionsPrestige() {
		List<Tuple<String, Double>> prestiges = new LinkedList<Tuple<String, Double>>();
		for (Faction faction : factions) {
			double prestige = faction.getPrestige().getValue();
			prestiges.add(new Tuple<String, Double>(faction.getName(), prestige));
		}
		
		return prestiges;
	}
	
	public List<Quad<String, Integer, Double, Double>> getFactionsStanding() {
		List<Quad<String, Integer, Double, Double>> standings = new LinkedList<Quad<String, Integer, Double, Double>>();
		for (Faction faction : factions) {
			Integer wealth = faction.getWealth().getMoney();
			Double presence = faction.getPresence().getPresence();
			Double prestige = faction.getPrestige().getValue();
			
			standings.add(new Quad<String, Integer, Double, Double>(faction.getName(), wealth, presence, prestige));
		}
		
		return standings;
	}

	public void setMoneyName(String moneyName) {
		Money.setName(moneyName);
	}

	public void setHumanFaction(List<String> faction) {
		for (String name : faction) {
			Faction temp = this.getFaction(name);
			temp.makePlayable();		
		}
	}

	public List<String> getMessagesForFaction(String faction) {
		List<String> messages = messagesForFactions.remove(faction);
		if (messages == null) messages = new LinkedList<String>();
		return messages;
	}
	
	public void addMessageForFaction(String faction, String message) {
		List<String> messages = messagesForFactions.get(faction);
		if (messages == null) {
			messages = new LinkedList<String>();
			messages.add(message);
			messagesForFactions.put(faction, messages);
		} else {
			messages.add(message);
		}
	}
	
	/**
	 * 
	 * @param playerHandle
	 * @param factionName
	 * @return successful or not (taken already)
	 */
	public synchronized boolean pickFaction(String playerHandle, String factionName) {
		boolean taken = false;
		Faction faction = this.getFaction(factionName);
		if (faction.isHuman() && !faction.getPlayerHandle().isEmpty()) {
			taken = true;
		}
		if (taken) return false;
		
		faction.makePlayable();
		faction.setPlayerHandle(playerHandle);
		return true;
	}

	public void decayPrestige() {
		for (Faction faction : factions) {
			faction.decayPrestige();
		}
	}

	public List<String> getFactionNames() {
		List<String> result = new LinkedList<String>();
		for (Faction faction : factions){
			result.add(faction.getName());
		}
		return result;
	}

	public List<Tuple<String, String>> getFactionsAndPlayerHandles() {
		List<Tuple<String, String>> result = new LinkedList<Tuple<String, String>>();
		for (Faction faction : factions) {
			String name = faction.getName();
			String handle = faction.getPlayerHandle();
			Tuple<String, String> temp = new Tuple<String, String>(name, handle);
			result.add(temp);
		}
		return result;
	}

	public List<AgentDTO> getAgents(String factionName) {
		return getFaction(factionName).getAgentsDTO();
	}

	public String getAgentInfo(String factionName, String agentName) {
		return getFaction(factionName).getAgentInfo(agentName);
	}

	public FactionDTO getFactionDTO(String factionName) {
		return new FactionDTO(getFaction(factionName));
	}

	public int calculateAgentSuccessProbability(String faction, String agent, MissionConceptDTO missionConcept) {
		return getFaction(faction).calculateAgentSuccessProbability(agent, missionConcept);
	}

	public void removePlayerHanlde(String playerHandle) {
		Faction faction = getFaction(getFactionNameFromHandle(playerHandle));
		faction.makeAvailable();
		faction.setPlayerHandle("");
	}
	
}
