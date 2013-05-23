package com.mod12.cabal.common.communication.wireformats;

public class PickFactionRequest extends Wireformat {

	public String desiredFaction;
	
	public PickFactionRequest(String faction) {
		messageType = CabalWireformatType.PICK_FACTION_REQUEST;
		this.desiredFaction = faction;
	}
	
	public PickFactionRequest(byte[] wireformat) {
		messageType = CabalWireformatType.PICK_FACTION_REQUEST;
		this.unmarshall(wireformat);
	}

	@Override
	public byte[] marshall() {
		return desiredFaction.getBytes();
	}

	@Override
	public void unmarshall(byte[] bytes) {
		this.desiredFaction = bytesToString(bytes);
	}

}
