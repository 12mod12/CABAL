package com.mod12.cabal.server.core.victory;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.util.Quad;
import com.mod12.cabal.server.core.faction.Faction;
import com.mod12.cabal.server.core.location.FactionPresence;
import com.mod12.cabal.server.core.manager.FactionManager;
import com.mod12.cabal.server.core.manager.LocationManager;

public class VictoryMonitor {
	
	private static VictoryMonitor monitor = null;
	
	public static VictoryMonitor getVictoryMonitor() {
		if (monitor == null) monitor = new VictoryMonitor();
		return monitor;
	}
	
	private LocationManager locationManager = LocationManager.getLocationManager();
	private FactionManager factionManager = FactionManager.getFactionManager();
	
	private List<VictoryPosition> scoreboard;
	
	private VictoryMonitor() {
		scoreboard = new LinkedList<VictoryPosition>();
	}
	
	private void resetScoreboard() {
		scoreboard.clear();
		for (Faction faction : FactionManager.getFactionManager().getFactions()) {
			scoreboard.add(new VictoryPosition(faction.getPlayerHandle(), faction.getName()));
		}
	}
	
	public void checkCurrentStandings() {
		updateWorldPresences();
		resetScoreboard();
		calculateStandings(factionManager.getFactionsStanding());
	}

	// TODO for now, you must play through the entire game to determine the victor 
	public boolean checkForVictor() {
		checkCurrentStandings();
//		List<Tuple<String, Double>> finalStandings = new LinkedList<Tuple<String, Double>>();
//		for (Quad<String, Double, Double, Double> faction : percentages) {
//			finalStandings 
//		}
		return false;
	}
	
	/**
	 * finds the current game leader, if multiple factions have the same total,
	 * whoever got their first
	 * TODO add test cases to check logic
	 */
	private void calculateStandings(List<Quad<String, Integer, Double, Double>> rawNumbers) {
		double totalResources = calculateTotal(1, rawNumbers);
		double totalPresence = calculateTotal(2, rawNumbers);
		double totalPrestige = calculateTotal(3, rawNumbers);
		for (Quad<String, Integer, Double, Double> faction : rawNumbers) {
			VictoryPosition position = findFactionPosition(faction.a);
			position.updateTracking(VictoryTracking.RESOURCES, faction.b, totalResources);
			position.updateTracking(VictoryTracking.PRESENCE, faction.c, totalPresence);
			position.updateTracking(VictoryTracking.PRESTIGE, faction.d, totalPrestige);
		}
		
		LinkedList<VictoryPosition> standings = new LinkedList<VictoryPosition>();
		int currentRank = 0;
		while (scoreboard.size() > 0) {
			VictoryPosition leader = findLeader(scoreboard);
			if (standings.size() == 0 || standings.get(standings.size() - 1).getTotal() > leader.getTotal()) {
				currentRank++;
			}
			
			leader.assignRank(currentRank);
			scoreboard.remove(leader);
			standings.add(leader);
		}
		scoreboard = standings;
	}
	
	private VictoryPosition findLeader(List<VictoryPosition> list) {
		VictoryPosition leader = list.get(0);
		for (VictoryPosition faction : list) {
			if (faction.getTotal() > leader.getTotal()) {
				leader = faction;
			}
		}
		return leader;
	}
	
	private VictoryPosition findFactionPosition(String factionName) {
		for (VictoryPosition faction : scoreboard) {
			if (faction.getFaction().equals(factionName)) return faction;
		}
		return null;
	}
	
	public VictoryPosition findPosition(int rank) {
		for (VictoryPosition faction : scoreboard) {
			if (faction.getRank() == rank) return faction;
		}
		return null;
	}
	
	public String getVictor() {
		return findLeader(scoreboard).getFaction();
	}
	
	private void updateWorldPresences() {
		List<FactionPresence> currentWorldPresences = locationManager.calculatePresences(locationManager.getTopLevel());
		factionManager.updateWorldPresences(currentWorldPresences);
	}
	
	private double calculateTotal(int index, List<Quad<String, Integer, Double, Double>> rawNumbers) {
		double total = 0;
		for (Quad<String, Integer, Double, Double> faction : rawNumbers) {
			if (index == 1) total += faction.b;
			if (index == 2) total += faction.c;
			if (index == 3) total += faction.d;
		}
		
		return total;
	}
	
	public List<String> scoreboard() {
		return convertScoreBoard();
	}

	private List<String> convertScoreBoard() {
		List<String> list = new LinkedList<String>();
		for (VictoryPosition position : scoreboard) {
			list.add(position.toString());
		}
		System.out.println("size of scoreboard " + scoreboard.size() + ", " + list.size());
		return list;
	}

}
