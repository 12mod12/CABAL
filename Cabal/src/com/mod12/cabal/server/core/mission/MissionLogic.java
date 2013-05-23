package com.mod12.cabal.server.core.mission;

/**
 * @author reedh
 *
 */
public class MissionLogic {
	
	private LogicKeyword keyword;
	private String associatedSkill;
	private int skillCheckValue;
	private double keywordValue;
	private String resultMessage;
	
	public MissionLogic(LogicKeyword keyword, String associatedSkill, int skillCheckValue, double keywordValue, String resultMessage) {
		this.keyword = keyword;
		this.associatedSkill = associatedSkill;
		this.skillCheckValue = skillCheckValue;
		this.keywordValue = keywordValue;
		this.resultMessage = resultMessage;
	}
	
	public String getMessage() {
		return resultMessage;
	}
	
	public LogicKeyword getKeyword() {
		return keyword;
	}
	
	public String getAssociatedSkill() {
		return associatedSkill;
	}
	
	public int getSkillCheckValue() {
		return skillCheckValue;
	}
	
	public double getKeywordDoubleValue() {
		return keywordValue;
	}
		
	public int getKeywordIntValue() {
		return (int) keywordValue;
	}
		
}
