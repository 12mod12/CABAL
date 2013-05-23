package com.mod12.cabal.common.dto;

import com.mod12.cabal.server.core.mission.MissionInstance;

public class MissionInstanceDTO extends DataTransferObject {

	private String missionConcept;
	private String stage;
	private String agent;
	private int turnsLeft;
	private String info;
	
	public MissionInstanceDTO(String missionConcept, String stage, String agent, int turnsLeft, String info) {
		this.missionConcept = missionConcept;
		this.stage = stage;
		this.agent = agent;
		this.turnsLeft = turnsLeft;
		this.info = info;
	}
	
	public MissionInstanceDTO(MissionInstance mission) {
		this.missionConcept = mission.getMissionConcept().getName();
		this.stage = mission.getStage().getName();
		this.agent = mission.getAgent().getName().toString();
		this.turnsLeft = mission.turnsLeft();
		this.info = mission.getInfo();
	}
	
	public MissionInstanceDTO(String mission) {
		String[] parts = mission.split(DELIM);
		missionConcept = parts[0];
		stage = parts[1];
		agent = parts[2];
		turnsLeft = Integer.parseInt(parts[3]);
		info = parts[4];
	}

	public String getMissionConcept() {
		return missionConcept;
	}

	public String getStage() {
		return stage;
	}

	public String getAgent() {
		return agent;
	}

	public int getTurnsLeft() {
		return turnsLeft;
	}

	public String getInfo() {
		return info;
	}

	public String marshall() {
		String output = missionConcept;
		output += DELIM + stage;
		output += DELIM + agent;
		output += DELIM + turnsLeft;
		output += DELIM + info;
		return output;
	}
	
	public String toString(){
		String result = "";
		result += missionConcept + " at " + stage + " by " + agent + " Turns left: " + turnsLeft;
		return result;
	}

}
