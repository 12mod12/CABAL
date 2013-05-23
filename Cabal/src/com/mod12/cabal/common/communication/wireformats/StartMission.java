package com.mod12.cabal.common.communication.wireformats;

public class StartMission extends Wireformat {

	public String missionConceptName;
	public String inStage;
	public String agent;
	public String target;
	
	// target sender constructor
	public StartMission(String name, String inStage, String agent, String target) {
		messageType = CabalWireformatType.START_MISSION;
		this.missionConceptName = name;
		this.inStage = inStage;
		this.agent = agent;
		this.target = target;
	}
	
	// no target sender constructor
	public StartMission(String name, String inStage, String agent) {
		messageType = CabalWireformatType.START_MISSION;
		this.missionConceptName = name;
		this.inStage = inStage;
		this.agent = agent;
		this.target = NULL;
	}

	// receiver constructor
	public StartMission(byte[] bytes) {
		messageType = CabalWireformatType.START_MISSION;
		unmarshall(bytes);
	}

	@Override
	public byte[] marshall() {
		String output = missionConceptName;
		output += DELIM + inStage;
		output += DELIM + agent;
		output += DELIM + target;
		return output.getBytes();
	}

	@Override
	public void unmarshall(byte[] bytes) {
		String result = bytesToString(bytes);
		String[] parts = result.split(DELIM);
		missionConceptName = parts[0];
		inStage = parts[1];
		agent = parts[2];
		target = parts[3];
	}

}
