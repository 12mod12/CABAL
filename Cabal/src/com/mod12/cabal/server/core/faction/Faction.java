package com.mod12.cabal.server.core.faction;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.dto.AgentDTO;
import com.mod12.cabal.common.dto.MissionConceptDTO;
import com.mod12.cabal.common.util.RandomUtil;
import com.mod12.cabal.common.util.Tuple;
import com.mod12.cabal.server.core.item.Item;
import com.mod12.cabal.server.core.item.UniqueItem;
import com.mod12.cabal.server.core.location.Stage;
import com.mod12.cabal.server.core.manager.LocationManager;
import com.mod12.cabal.server.core.person.agent.Agent;
import com.mod12.cabal.server.core.person.leader.Leader;

public class Faction {

	private final String name;
	private Money wealth;
	private Prestige prestige;
	private Presence presence;
	private boolean isHuman;
	private String playerHandle;
	
	private Stage headquarters;
	private Leader leader;
	private List<Agent> members;
	
	// TODO determine the actual use of unique items
	private List<Item> itemStockpile;
	private List<Tuple<String, Integer>> agentsUsingUniqueItems; // agent, item
	
	public Faction(String name, int startingMoney) {
		this.name = name;
		this.wealth = new Money(startingMoney);
		this.prestige = new Prestige();
		this.presence = new Presence();
		this.isHuman = false;
		this.playerHandle = "";
		this.headquarters = null;
		this.members = new LinkedList<Agent>();
		itemStockpile = new LinkedList<Item>();
		agentsUsingUniqueItems = new LinkedList<Tuple<String, Integer>>();
	}
	
	/**
	 * test constructor
	 */
	public Faction(String name) {
		this.name = name;
		this.prestige = new Prestige();
		this.isHuman = false;
	}
	
	public Leader getLeader() {
		return leader;
	}

	public void setLeader(Leader leader) {
		this.leader = leader;
	}

	public void setMembers(List<Agent> members) {
		this.members = members;
	}
	
	public void addMembers(List<Agent> members) {
		this.members.addAll(members);
	}

	public void removeMembers(List<Agent> members) {
		this.members.removeAll(members);
	}
	
	public void setHeadquarters(String headquaters) {
		this.headquarters = (Stage) LocationManager.getLocationManager().getLocation(headquaters);
	}

	public Stage getHeadquarters() {
		return headquarters;
	}
		
	public String getName() {
		return name;
	}

	public Money getWealth() {
		return wealth;
	}

	public Prestige getPrestige() {
		return prestige;
	}
	
	public Presence getPresence() {
		return presence;
	}
	
	public void makePlayable() {
		isHuman = true;
	}
	
	public boolean isHuman() {
		return isHuman;
	}
	
	public void addItemsToStockpile(List<Item> list) {
		for (Item item : list) {
			itemStockpile.add(item);
		}
	}
	
	public void removeItemsFromStockpile(int number) {
		while (number > 0) {
			itemStockpile.remove(RandomUtil.nextInt(itemStockpile.size()));
			number--;
		}
	}
	
	public void setPlayerHandle(String handle) {
		this.playerHandle = handle;
	}
	
	public String getPlayerHandle(){
		return this.playerHandle;
	}
	
	public void addUniqueItem(UniqueItem item) {
		itemStockpile.add(item);
	}
	
	public void assignUniqueItemToAgent(String agentName, int id) {
		UniqueItem item = getUniqueItemFromStockpile(id);
		giveItemToAgent(agentName, item);
		agentsUsingUniqueItems.add(new Tuple<String, Integer>(agentName, id));
	}
	
	public UniqueItem getUniqueItemFromStockpile(int id) {
		for (Item item : itemStockpile) {
			if (item instanceof UniqueItem && ((UniqueItem) item).getID() == id) {
				itemStockpile.remove(item);
				return (UniqueItem) item;
			}
		}
		return null;
	}
	
	// TODO figure out all item logic stuff
	private void giveItemToAgent(String agentName, Item item) {
		giveItemToAgent(getAgent(agentName), item);
	}
	
	private void giveItemToAgent(Agent agent, Item item) {
//		agent.equipItem(item);
	}
	
	public void giveItemToAgent(String agentName, String itemName) {
//		giveItemToAgent(findAgent(agentName), getItemFromStockpile(itemName));
	}
	
	public void tradeItem(String giverAgentName, String receiverAgentName, String itemName) {
//		Agent giverAgent = findAgent(giverAgentName);
//		Agent receiverAgent = findAgent(receiverAgentName);
//		Item item = giverAgent.removeItem(itemName);
//		receiverAgent.equipItem(item);
	}
	
//	private Item getItemFromStockpile(String name) {
//		for (Item item : itemStockpile) {
//			if (item.getName().equals(name)) {
//				itemStockpile.remove(item);
//				return item;
//			}
//		}
//		return null;
//	}
	
	public Agent getAgent(String name) {
		for (Agent agent : members) {
			if (agent.getName().equals(name)) return agent;
		}
		return null;
	}
	
//	public List<Agent> getAgents() {
//		return members;
//	}
	
	public List<AgentDTO> getAgentsDTO() {
		List<AgentDTO> agents = new LinkedList<AgentDTO>();
		for (Agent agent : members) {
			agents.add(new AgentDTO(agent));
		}
		return agents;
	}
	
	public List<Agent> getNonBusyAgents() {
		List<Agent> agents = new LinkedList<Agent>();
		for (Agent agent : members) {
			if (!agent.isBusy()) agents.add(agent);
		}
		return agents;
	}
	
	public void adjustPresence(boolean infamy, int value) {
		if (infamy) {
			prestige.adjustInfamy(value);
		} else {
			prestige.adjustCulture(value);
		}
	}

	public UniqueItem stealUniqueItem(int id) {
		UniqueItem item = getUniqueItemFromStockpile(id);
		if (item == null) {
			item = takeUniqueItemFromAgent(id);
		}
		return item;
	}

	private UniqueItem takeUniqueItemFromAgent(int id) {
		for (Tuple<String, Integer> agentAndItemNames : agentsUsingUniqueItems) {
			if (agentAndItemNames.y == id) {
				Agent agent = getAgent(agentAndItemNames.x);
				return agent.removeUniqueItem(id);
			}
		}
		return null;
	}

	public void decayPrestige() {
		prestige.decayPrestige();
	}

	private List<String> getAgentNames(List<Agent> agents) {
		LinkedList<String> names = new LinkedList<String>();
		for (Agent agent : agents) {
			names.add(agent.getName().toString());
		}
		return names;
	}
	
	public List<String> getAgentNames() {
		return getAgentNames(members);
	}

	public List<String> getNonBusyAgentsNames() {
		return getAgentNames(getNonBusyAgents());
	}

	public String getAgentInfo(String agentName) {
		return getAgent(agentName).getInfo();
	}

	public int calculateAgentSuccessProbability(String agent, MissionConceptDTO missionConcept) {
		return getAgent(agent).calculateProbably(missionConcept.getPassSkill(), missionConcept.getPassSkillCheck());
	}

	public void makeAvailable() {
		isHuman = false;
	}

}
