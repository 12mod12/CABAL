package com.mod12.cabal.server.core.victory;

import static com.mod12.cabal.common.util.Constants.DELIM_KEY_VALUE_PAIR;

public class VictoryPosition {

	private String playerHandle;
	private String faction;
	private double total;
	private int rank;
	
	private VictoryTracking resources;
	private VictoryTracking presence;
	private VictoryTracking prestige;
	
	public VictoryPosition(String playerHandle, String faction) {
		this.playerHandle = playerHandle;
		this.faction = faction;
		resources = VictoryTracking.createResourceVictoryTracking();
		presence = VictoryTracking.createPresenceVictoryTracking();
		prestige = VictoryTracking.createPrestigeVictoryTracking();
	}
	
	public void assignRank(int rank) {
		this.rank = rank;
	}
	
	public void updateTracking(String name, double value, double total) {
		VictoryTracking update = null;
		if (name.equals(resources.getName())) {
			update = resources;
		} else if (name.equals(presence.getName())) {
			update = presence;
		} else if (name.equals(prestige.getName())) {
			update = prestige;
		}
		
		if (update != null) {
			update.updateRawNumber(value);
			update.calculatePercentage(total);
		}
	}
	
	public double getTotal() {
		total = resources.getValue() + presence.getValue() + prestige.getValue(); 
		return total;
	}
	
	public String getFaction() {
		return faction;
	}
	
	public int getRank() {
		return rank;
	}
	
	public String toString() {
		return faction + DELIM_KEY_VALUE_PAIR + rank + DELIM_KEY_VALUE_PAIR + total + DELIM_KEY_VALUE_PAIR + 
				presence.getValue() + DELIM_KEY_VALUE_PAIR + prestige.getValue() + DELIM_KEY_VALUE_PAIR + 
				resources.getValue() + DELIM_KEY_VALUE_PAIR + playerHandle;
	}
	
}
