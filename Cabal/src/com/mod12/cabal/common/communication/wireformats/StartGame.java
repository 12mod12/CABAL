package com.mod12.cabal.common.communication.wireformats;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.dto.FactionDTO;
import com.mod12.cabal.common.dto.MissionConceptDTO;


public class StartGame extends Wireformat {

	public String scenario;
	public String location;
	public String timePeriod;
	public long turn;
	public long maxTurn;
	public FactionDTO startingFaction;
	public List<MissionConceptDTO> allMissions;

	//Constructor for sender
	public StartGame(String scenario, String location, String time, long turn, long maxTurn, 
			FactionDTO startingFaction, List<MissionConceptDTO> allMissions) {
		messageType = CabalWireformatType.GAME_START;
		this.scenario = scenario;
		this.location = location;
		this.timePeriod = time;
		this.turn = turn;
		this.maxTurn = maxTurn;
		this.startingFaction = startingFaction;
		this.allMissions = allMissions;
	}
	
	//Constructor for receiver
	public StartGame(byte[] bytes) {
		messageType = CabalWireformatType.GAME_START;
		unmarshall(bytes);
	}

	// constructor for initializing game by client host
	public StartGame() {
		messageType = CabalWireformatType.GAME_START;
		scenario = location = timePeriod = NULL; 
		turn = maxTurn = 0;
		// startingFaction is null!!
		allMissions = new LinkedList<MissionConceptDTO>();
	}

	@Override
	public byte[] marshall() {
		String output = scenario + DELIM;
		output += location + DELIM;
		output += timePeriod + DELIM;
		output += turn + DELIM;
		output += maxTurn + DELIM;
		if (startingFaction != null) {
			output += startingFaction.marshall() + DELIM;
		}
		for (MissionConceptDTO missionConcept : allMissions) {
			output += missionConcept.marshall() + DELIM;
		}
		return output.getBytes();
	}

	@Override
	public void unmarshall(byte[] bytes) {
		String result = bytesToString(bytes);
		String[] parts = result.split(DELIM);
		scenario = parts[0];
		location = parts[1];
		timePeriod = parts[2];
		turn = Long.parseLong(parts[3]);
		maxTurn = Long.parseLong(parts[4]); 
		
		int index = 5;
		if (!scenario.equals(NULL)) {
			startingFaction = new FactionDTO(parts[5]);
			index++;
		}
		
		allMissions = new ArrayList<MissionConceptDTO>();
		while (index < parts.length){
			allMissions.add(new MissionConceptDTO(parts[index]));
			index++;
		}
	}

}
