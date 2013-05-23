package com.mod12.cabal.server.core.victory;

public class VictoryTracking {

	public static final String RESOURCES = "Resources";
	public static final String PRESENCE = "Presence";
	public static final String PRESTIGE = "Prestige";
	
	public static final double RESOURCE_WEIGHT = 1.0/3.0;
	public static final double PRESENCE_WEIGHT = 1.0/3.0;
	public static final double PRESTIGE_WEIGHT = 1.0/3.0;
	
	private String name;
	private double weight;
	private double rawNumber;
	private double percentage;
	
	private VictoryTracking(String name, double weight) {
		this.name = name;
		this.weight = weight;
	}

	public static VictoryTracking createResourceVictoryTracking() {
		return new VictoryTracking(RESOURCES, RESOURCE_WEIGHT);
	}
	
	public static VictoryTracking createPresenceVictoryTracking() {
		return new VictoryTracking(PRESENCE, PRESENCE_WEIGHT);
	}
	
	public static VictoryTracking createPrestigeVictoryTracking() {
		return new VictoryTracking(PRESTIGE, PRESTIGE_WEIGHT);
	}
	
	public void updateRawNumber(double rawNumber) {
		this.rawNumber = rawNumber;
	}
	
	public void calculatePercentage(double total) {
		if (total != 0) {
			this.percentage = rawNumber / total;
		} else {
			percentage = 0;
		}
	}
	
	public double getValue() {
		return weight * percentage;
	}
	
	public String getName() {
		return name;
	}
	
}
