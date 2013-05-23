package com.mod12.cabal.common.communication.wireformats;

public class FactionNamesRequest extends Wireformat {

	public boolean valid;
	
	//Constructor for sender
	public FactionNamesRequest() {
		messageType = CabalWireformatType.FACTION_NAMES_REQUEST;
		valid = true;
	}
	
	//Constructor for receiver
	public FactionNamesRequest(byte[] bytes) {
		messageType = CabalWireformatType.FACTION_NAMES_REQUEST;
		unmarshall(bytes);
	}

	@Override
	public byte[] marshall() {
		return simplePacket();
	}

	@Override
	public void unmarshall(byte[] bytes) {
		valid = simplePacketResult(bytes);
	}
	
}
