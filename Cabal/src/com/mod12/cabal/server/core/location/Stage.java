package com.mod12.cabal.server.core.location;

import static com.mod12.cabal.server.core.ServerCabal.DEBUG;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.util.RandomUtil;
import com.mod12.cabal.server.core.mission.MissionConcept;
import com.mod12.cabal.server.core.person.agent.Agent;

/**
 * A STAGE represents an area within the WORLD where MISSIONS occur and FACTIONS can be located.
 * Each STAGE has buildings and history associated with them that dictates what sort of MISSIONS
 * can or will occur at that position. For the purpose
 * of this phase of the project we are limited to the city of Paris.
 *
 * STAGES consist of:
 *	- name and position within parent LOCATION
 *	- type of LOCATION
 *	- A value to indicate what MISSIONS might occur here
 *	- Flavor text
 *
 * @author kowalski
 *
 */
public class Stage extends Location {

	private List<Agent> stationedAgents;
	// TODO add missions to a stage based off of name or type of location
	private List<MissionConcept> possibleMissions;
	private List<FactionPresence> factionPresences;

	public Stage(String type, String name, double xCord, double yCord, String description,
			List<FactionPresence> factionPresences) {
		super(type, name, xCord, yCord, description);
		stationedAgents = new LinkedList<Agent>();
		possibleMissions = new LinkedList<MissionConcept>();
		this.factionPresences = factionPresences;
	}

	public Stage(String type, String name, double xCord, double yCord, String description) {
		super(type, name, xCord, yCord, description);
		stationedAgents = new LinkedList<Agent>();
		possibleMissions = new LinkedList<MissionConcept>();
		this.factionPresences = new LinkedList<FactionPresence>();
	}

	public MissionConcept getRandomMission() {
		return possibleMissions.get(RandomUtil.nextInt(possibleMissions.size()));
	}

	public void addMission(MissionConcept mission) {
		possibleMissions.add(mission);
	}

	public void addMissions(MissionConcept... missions) {
		for (MissionConcept mission : missions) {
			possibleMissions.add(mission);
		}
	}

	public void removeMission(MissionConcept mission) {
		possibleMissions.remove(mission);
	}

	public void addAgent(Agent agent) {
		this.stationedAgents.add(agent);
	}

	public void removeAgent(Agent agent) {
		this.stationedAgents.remove(agent);
	}

	public List<MissionConcept> getPossibleMissions() {
		return possibleMissions;
	}

	public List<FactionPresence> getFactionPresences() {
		return factionPresences;
	}

	public void addPresense(FactionPresence presense) {
		factionPresences.add(presense);
	}

	public void removePresense(FactionPresence presense) {
		factionPresences.remove(presense);
	}

	public void changePresence(String gainingFaction, double stolenAmount, String losingFaction) {
		FactionPresence losingPresence = findFactionPresence(losingFaction);
		double actualAdjust = -1 * losingPresence.adjustPresence(-1 * stolenAmount);
		checkPresence(losingPresence);

		FactionPresence gainingPresence = findFactionPresence(gainingFaction);
		if (gainingPresence == null) {
			gainingPresence = new FactionPresence(gainingFaction, actualAdjust);
			factionPresences.add(gainingPresence);
		} else {
			gainingPresence.adjustPresence(actualAdjust);
		}

	}

	private FactionPresence findFactionPresence(String faction) {
		for (FactionPresence presence : factionPresences) {
			if (presence.getFaction().equals(faction)) return presence;
		}
		return null;
	}

	private void checkPresence(FactionPresence presence) {
		if (presence.getPresence() == 0) {
			int oldSize = factionPresences.size();
			boolean success = factionPresences.remove(presence);
			if (DEBUG) {
				System.out.println(presence.getFaction() + " removed: " + success + ". oldSize: " + oldSize
						+ ", newSize: " + factionPresences.size());
			}
		}
	}
}
