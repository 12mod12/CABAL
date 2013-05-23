package com.mod12.cabal.server.core.location;

import com.mod12.cabal.server.core.faction.Faction;

public class FactionPresence {

	private static final int MAX_PRESENCE = 100;
	private static final int MIN_PRESENCE = 0;

	private String faction;
	private double presence; // percentage value out of 100

	public FactionPresence(Faction faction, double presence) {
		this.faction = faction.getName();
		this.presence = presence;
	}

	public FactionPresence(String faction, double presence) {
		this.faction = faction;
		this.presence = presence;
	}

	public String getFaction() {
		return faction;
	}

	public double getPresence() {
		return presence;
	}

	public void setPresence(double presence) {
		this.presence = presence;
	}

	/**
	 * actual amount adjusted by
	 * @param adjust
	 * @return
	 */
	public double adjustPresence(double adjust) {
		double oldPresence = presence;
		this.presence += adjust;

		if (presence < MIN_PRESENCE) presence = MIN_PRESENCE;
		else if (presence > MAX_PRESENCE) presence = MAX_PRESENCE;
		return presence - oldPresence;
	}

    public String toString() {
        String result = faction + ": " + presence;
        return result;
    }

}
