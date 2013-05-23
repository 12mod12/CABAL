package com.mod12.cabal.server.core.person.agent.condition;

public enum State {
	
	// TODO could be set in sin file
	
			 	  			// Health Determined States
	HEALTHY(100, 70),      	// >= 70
	PAINED(69, 25),  	  	// >= 25 && < 70 
	UNCOUNSCIOUS(24, 1), 	// < 25 && > 1 
	DEAD(0, 0),		  		// == 0
	
							//Mental Determined States
	SANE(100, 70),			// >= 70
	STRESSED(69, 25),		// >= 25 && < 70 
	SHOCKED(24, 1),			// < 25 && > 1 
	INSANE(0, 0);			// == 0
	
	int top;
	int bottom;
	
	private State(int top, int bottom) {
		this.top = top;
		this.bottom = bottom;
	}
	
	public boolean inRange(int value) {
		return top >= value && value >= bottom;
	}
	
	public static State getState(boolean physicalState, int value) {
		if (physicalState) {
		
			if (HEALTHY.inRange(value)) {
				return HEALTHY;
			}
			else if (PAINED.inRange(value)) {
				return PAINED;
			}
			else if (UNCOUNSCIOUS.inRange(value)) {
				return UNCOUNSCIOUS;
			}
			else if (DEAD.inRange(value)) {
				return DEAD;
			}
		}
		else {
			if (SANE.inRange(value)) {
				return SANE;
			}
			else if (STRESSED.inRange(value)) {
				return STRESSED;
			}
			else if (SHOCKED.inRange(value)) {
				return SHOCKED;
			}
			else if (INSANE.inRange(value)) {
				return INSANE;
			}
		}
		
		return null;
	}
	
}
