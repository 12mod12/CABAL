package com.mod12.cabal.server.core.manager;

import com.mod12.cabal.server.core.victory.VictoryMonitor;

/**
 * Knows of all items in game. Is the main interface to interact with for Item information.
 * @author kowalski
 *
 */
public class TurnManager {

	private static TurnManager manager = null;
	
	// in seconds
	private long turnLength = 0;
	private long onTurn = 1;
	private boolean gameOver = false;
	private int onStep = 0;
	private long maxTurn;
	private int numberOfHumans = 0;
	private int stepsPerTurn = 0;
	private String currentFaction;
	
	private VictoryMonitor victoryMonitor;
	
	public static TurnManager getTurnManager() {
		if (manager == null) manager = new TurnManager();
		return manager;
	}
	
	private TurnManager() {
		victoryMonitor = VictoryMonitor.getVictoryMonitor();
	}
	
	public void setTurnLength(long turnLength) throws Exception {
		if (this.turnLength != 0) return; 
		
		this.turnLength = turnLength;
		if (this.turnLength <= 0) throw new Exception("Turn length is not valid: " + turnLength);
	}
	
	public void nextStep() {
		onStep = (onStep + 1) % stepsPerTurn;
		currentFaction = FactionManager.getFactionManager().getFactions().get(onStep).getName();
		
		if (onStep == 0) {
			nextTurn();
		}
	}
	
	private void nextTurn() {
		if (victoryMonitor.checkForVictor() || onTurn == maxTurn) {
			gameOver = true;
		} else {
			onTurn++;
			FactionManager.getFactionManager().decayPrestige();
			MissionManager.getMissionManager().nextTurn();
		}
	}
	
	public long getTurnLength() {
		return turnLength;
	}
	
	public long getTurn() {
		return onTurn;
	}
	
	public void setMaxTurn(long maxTurn) {
		this.maxTurn = maxTurn;
	}
	
	public void setCurrentFaction(String temp) {
		this.currentFaction = temp;
	}
	
	public String getCurrentFaction() {
		return currentFaction;
	}
	
	public void setPlayers(int number) {
		if (this.numberOfHumans != 0) return;
		
		this.numberOfHumans = number;
		stepsPerTurn = numberOfHumans;
	}
	
	public void setStep(int number) throws Exception {
		if (this.stepsPerTurn != 0) return;
		
		this.stepsPerTurn = number;
		if (this.stepsPerTurn <= 0) throw new Exception("Invalid steps per turn. Must be at least one step per turn.");
	}

	public boolean gameOver() {
		return gameOver;
	}

	public long getMaxTurn() {
		return maxTurn;
	}

}
