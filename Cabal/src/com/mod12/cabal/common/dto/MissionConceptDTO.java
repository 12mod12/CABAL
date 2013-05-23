package com.mod12.cabal.common.dto;

import com.mod12.cabal.server.core.mission.LogicKeyword;
import com.mod12.cabal.server.core.mission.MissionConcept;
import com.mod12.cabal.server.core.person.agent.Ranking;

public class MissionConceptDTO extends DataTransferObject {

	private String name;
	private String descriptionText;
	private Ranking difficulty;
	private LogicKeyword passLogic;
	private String passSkill;
	private int passSkillCheck;
	private LogicKeyword failLogic;
	private String failSkill;
	private int failSkillCheck;
	private int turnsToComplete;
	private boolean requiresTarget;
	private String info;
	
	public MissionConceptDTO(String name, String descriptionText,
			Ranking difficulty, LogicKeyword passLogic, String passSkill,
			int passSkillCheck, LogicKeyword failLogic, String failSkill,
			int failSkillCheck, int turnsToComplete, boolean requiresTarget, String info) {
		this.name = name;
		this.descriptionText = descriptionText;
		this.difficulty = difficulty;
		this.passLogic = passLogic;
		this.passSkill = passSkill;
		this.passSkillCheck = passSkillCheck;
		this.failLogic = failLogic;
		this.failSkill = failSkill;
		this.failSkillCheck = failSkillCheck;
		this.turnsToComplete = turnsToComplete;
		this.requiresTarget = requiresTarget;
		this.info = info;
	}

	public MissionConceptDTO(MissionConcept mission) {
		this.name = mission.getName();
		this.descriptionText = mission.getDescriptionText();
		this.difficulty = mission.getDifficulty();
		this.passLogic = mission.getSuccessLogic().getKeyword();
		this.passSkill = mission.getSuccessLogic().getAssociatedSkill();
		this.passSkillCheck = mission.getSuccessLogic().getSkillCheckValue();
		this.failLogic = mission.getFailLogic().getKeyword();
		this.failSkill = mission.getFailLogic().getAssociatedSkill();
		this.failSkillCheck = mission.getFailLogic().getSkillCheckValue();
		this.turnsToComplete = mission.turnsToComplete();
		this.requiresTarget = mission.requiresTarget();
		if (mission.requiresTarget()) System.out.println("new mission DTO: " + name + ". requires target: " + requiresTarget);
		else System.out.println("new mission DTO: " + name + " does not need target.");
		this.info = mission.getInfo();
	}
	
	public MissionConceptDTO(String mission) {
		String[] parts = mission.split(DELIM);
		name = parts[0];
		descriptionText = parts[1];
		difficulty = Ranking.valueOf(parts[2]);
		passLogic = LogicKeyword.valueOf(parts[3]);
		passSkill = parts[4];
		passSkillCheck = Integer.parseInt(parts[5]);
		failLogic = LogicKeyword.valueOf(parts[6]);
		failSkill = parts[7];
		failSkillCheck = Integer.parseInt(parts[8]);
		turnsToComplete = Integer.parseInt(parts[9]);
		requiresTarget = Boolean.parseBoolean(parts[10]);
		info = parts[11];
	}

	public String getName() {
		return name;
	}

	public String getDescriptionText() {
		return descriptionText;
	}

	public Ranking getDifficulty() {
		return difficulty;
	}

	public LogicKeyword getPassLogic() {
		return passLogic;
	}

	public String getPassSkill() {
		return passSkill;
	}

	public int getPassSkillCheck() {
		return passSkillCheck;
	}

	public LogicKeyword getFailLogic() {
		return failLogic;
	}

	public String getFailSkill() {
		return failSkill;
	}

	public int getFailSkillCheck() {
		return failSkillCheck;
	}

	public int getTurnsToComplete() {
		return turnsToComplete;
	}
	
	public boolean requiresTarget() {
		return requiresTarget;
	}

	public String getInfo() {
		return info;
	}
	
	@Override
	public String marshall() {
		String output = "";
		output += name + DELIM;
		output += descriptionText + DELIM;
		output += difficulty + DELIM;
		output += passLogic + DELIM;
		output += passSkill + DELIM;
		output += passSkillCheck + DELIM;
		output += failLogic + DELIM;
		output += failSkill + DELIM;
		output += failSkillCheck + DELIM;
		output += turnsToComplete + DELIM;
		output += requiresTarget + DELIM;
		output += info;				
		return output;
	}
	
}
