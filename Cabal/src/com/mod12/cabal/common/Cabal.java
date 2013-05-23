package com.mod12.cabal.common;

import java.util.List;

import com.mod12.cabal.common.dto.FactionDTO;
import com.mod12.cabal.common.dto.FactionPresenceDTO;
import com.mod12.cabal.common.dto.LocationDTO;
import com.mod12.cabal.common.dto.MissionConceptDTO;
import com.mod12.cabal.common.dto.MissionInstanceDTO;

public interface Cabal {

	public String getImageLocation();

	public boolean startGame();

	public boolean nextStep(boolean distributed);

	public FactionDTO getCurrentStepFaction();

	public Long getCurrentTurnValue();

	public List<String> getCurrentStepFactionMessages();

	public LocationDTO getTopLevel();

	public List<String> getFactionsAtStage(String stageName);

	public List<MissionInstanceDTO> getMyActiveMissions();

	public String getAgentInfo(String factionName, String agentName);

	public int calculateAgentProbability(String faction, String agent, MissionConceptDTO missionConcept);

	public void createMissionInstance(String factionName, MissionConceptDTO missionConcept, 
			String inStage, String agent, String target);

	public void createMissionInstance(String factionName, MissionConceptDTO missionConcept, 
			String inStage, String agent);

	public LocationDTO getLocationDTO(String locationName);

	public FactionDTO getMyFaction();

	public List<MissionConceptDTO> getMissions(List<String> missions);

	public List<FactionPresenceDTO> getFactionPresences(String locationName);

	public void quit();

	public long getMaxTurn();

	public List<String> getScoreborad();

}
