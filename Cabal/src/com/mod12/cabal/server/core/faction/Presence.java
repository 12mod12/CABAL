package com.mod12.cabal.server.core.faction;

import com.mod12.cabal.common.util.History;

public class Presence {

	private double percentOfWorld;
	private History<Double> history;
	
	public Presence() {
		history = new History<Double>();
	}
	
	public double getPresence() {
		return percentOfWorld;
	}
	
	public void setPresence(double presence) {
		history.addHistory(percentOfWorld);
		this.percentOfWorld = presence;
	}
	
	public History<Double> getHistory() {
		return new History<Double>(history);
	}
	
}
