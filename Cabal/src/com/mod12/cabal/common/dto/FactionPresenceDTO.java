package com.mod12.cabal.common.dto;

import com.mod12.cabal.server.core.location.FactionPresence;

public class FactionPresenceDTO extends DataTransferObject {

	private String faction;
	private double presence;
	
	public FactionPresenceDTO(String faction, double presence) {
		this.faction = faction;
		this.presence = presence;
	}

	public FactionPresenceDTO(FactionPresence fp) {
		this.faction = fp.getFaction();
		this.presence = fp.getPresence();
	}

	public String getFaction() {
		return faction;
	}

	public double getPresence() {
		return presence;
	}

	@Override
	public String marshall() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
