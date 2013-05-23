package com.mod12.cabal.common.communication.wireformats;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.dto.AgentDTO;
import com.mod12.cabal.common.dto.FactionDTO;
import com.mod12.cabal.common.dto.MissionInstanceDTO;

public class StartStep extends Wireformat {

	protected static final String MESSAGES_DELIM = "_messages_";
	protected static final String MISSIONS_DELIM = "_mission_instance_dto_";
	protected static final String AGENTS_DELIM = "_agents_";

	public long currentTurn;
	public FactionDTO faction;
	public List<String> messages;
	public List<MissionInstanceDTO> activeMissions;
	public List<AgentDTO> agents;
	
	//Constructor for sender
	public StartStep(long currentTurn, FactionDTO faction, List<String> messages, 
			List<MissionInstanceDTO> activeMissions, List<AgentDTO> agents) {
		messageType = CabalWireformatType.START_STEP;
		this.currentTurn = currentTurn;
		this.faction = faction;
		this.messages = messages;
		this.activeMissions = activeMissions;
		this.agents = agents;
	}
	
	//Constructor for receiver
	public StartStep(byte[] bytes) {
		messageType = CabalWireformatType.START_STEP;
		unmarshall(bytes);
	}

	@Override
	public byte[] marshall() {
		String output = currentTurn + "";
		output += DELIM + faction.marshall();
		output += DELIM + MESSAGES_DELIM;
		for (String message : messages) {
			output += DELIM + message;
		}
		output += DELIM + MISSIONS_DELIM;
		for (MissionInstanceDTO mission : activeMissions) {
			output += DELIM + mission.marshall();
		}
		output += DELIM + AGENTS_DELIM;
		for (AgentDTO agent : agents) {
			output += DELIM + agent.marshall();
		}
		
		return output.getBytes();
	}

	@Override
	public void unmarshall(byte[] bytes) {
		String result = Wireformat.bytesToString(bytes);
		String[] parts = result.split(Wireformat.DELIM);
		currentTurn = Integer.parseInt(parts[0]);
		faction = new FactionDTO(parts[1]);
		
		messages = new LinkedList<String>();
		activeMissions = new LinkedList<MissionInstanceDTO>();
		agents = new LinkedList<AgentDTO>();
		
		int count = 3;
		while (!parts[count].equals(MISSIONS_DELIM)) {
			messages.add(parts[count]);
			count++;
		}
		count++;
		while (!parts[count].equals(AGENTS_DELIM)) {
			activeMissions.add(new MissionInstanceDTO(parts[count]));
			count++;
		}
		count++;
		while (count != parts.length) {
			agents.add(new AgentDTO(parts[count]));
			count++;
		}
	}

}
