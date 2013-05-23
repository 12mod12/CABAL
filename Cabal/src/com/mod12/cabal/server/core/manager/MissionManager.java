package com.mod12.cabal.server.core.manager;
import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.dto.MissionConceptDTO;
import com.mod12.cabal.server.core.location.Stage;
import com.mod12.cabal.server.core.mission.MissionConcept;
import com.mod12.cabal.server.core.mission.MissionInstance;

/**
 * Knows of all missions in game. Is the main interface to interact with for missions information.
 * @author kowalski
 *
 */
public class MissionManager {

	private static MissionManager manager;
	
	private LinkedList<MissionConcept> missionConcepts;
	private LinkedList<MissionInstance> activeMissions;
	private LinkedList<MissionInstance> completedMissions;
	
	public static MissionManager getMissionManager() {
		if (manager == null) manager = new MissionManager();
		return manager;
	}
	
	private MissionManager() {
		missionConcepts = new LinkedList<MissionConcept>();
		activeMissions = new LinkedList<MissionInstance>();
		completedMissions = new LinkedList<MissionInstance>();
	}
	
	public void addMissionConcept(MissionConcept missionConcept) {
		missionConcepts.add(missionConcept);
	}
	
	public void createMissionInstance(String factionName, MissionConceptDTO missionConcept, 
			String stageName, String agentName) {
		activeMissions.add(new MissionInstance(findMissionConcept(missionConcept), 
				(Stage) LocationManager.getLocationManager().getLocation(stageName), 
				FactionManager.getFactionManager().getFaction(factionName).getAgent(agentName)));
	}
	
	public void createMissionInstance(String factionName, MissionConceptDTO missionConcept, 
			String stageName, String agentName, String target) {
		activeMissions.add(new MissionInstance(findMissionConcept(missionConcept), 
				(Stage) LocationManager.getLocationManager().getLocation(stageName), 
				FactionManager.getFactionManager().getFaction(factionName).getAgent(agentName),
				target));
	}
	
	private MissionConcept findMissionConcept(MissionConceptDTO missionConcept) {
		for (MissionConcept mission : missionConcepts) {
			if (mission.getName().equals(missionConcept.getName())) return mission;
		}
		return null;
	}

	public List<MissionConcept> getMissions() {
		return missionConcepts;
	}
	
	public MissionConcept getMission(String missionName) {
		for (MissionConcept mission : missionConcepts) {
			if (mission.getName().equals(missionName)) return mission;
		}
		return null;
	}

//	public List<MissionConcept> getPossibleMissions() {
//		return missionConcepts;
//	}

	public void nextTurn() {
		for (MissionInstance mission : activeMissions) {
			mission.nextTurn();
		}
		// missions are added if they execute during nextTurn() above
		for (MissionInstance mission : completedMissions) {
			activeMissions.remove(mission);
		}
	}
	
	public List<MissionInstance> getActiveMissions(String factionName) {
		List <MissionInstance> current = new LinkedList<MissionInstance>();
		for (MissionInstance mission : activeMissions) {
			if (mission.getAgent().getFaction().getName().equals(factionName)) current.add(mission);
		}
		return current;
	}
	
	public List<MissionInstance> getAllActiveMissions() {
		return activeMissions;
	}
	
	public List<MissionConceptDTO> getPossibleMissionsDTO() {
		List<MissionConceptDTO> missions = new LinkedList<MissionConceptDTO>();
		for (MissionConcept mission : missionConcepts) {
			missions.add(new MissionConceptDTO(mission));
		}
		return missions;
	}

	public void addCompletedMission(MissionInstance missionInstance) {
		completedMissions.add(missionInstance);
	}

}

