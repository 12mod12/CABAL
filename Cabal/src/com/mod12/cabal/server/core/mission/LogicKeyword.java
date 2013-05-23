package com.mod12.cabal.server.core.mission;

public enum LogicKeyword {

	MOVE(false),
	RECRUIT(false),
	SCOUT(false),
	ADJ_PRES(true),
	INC_PHYSICAL_HEALTH(false),
	DEC_PHYSICAL_HEALTH(false),
	INC_MENTAL_HEALTH(false),
	DEC_MENTAL_HEALTH(false),
	INC_MONEY(false),
	DEC_MONEY(false),
	STEAL_ITEM(true),
	GEN_ITEM(false),
	DES_ITEM(false),
	INC_INFAMY(false),
	DEC_INFAMY(false),
	INC_CULTURE(false),
	DEC_CULTURE(false);
	
	private boolean requiresTarget;
	
	private LogicKeyword(boolean requiresTarget) {
		this.requiresTarget = requiresTarget;
	}
	
	public boolean requiresTarget() {
		return requiresTarget;
	}
}
