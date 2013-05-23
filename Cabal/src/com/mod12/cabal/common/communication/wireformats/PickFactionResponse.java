package com.mod12.cabal.common.communication.wireformats;

public class PickFactionResponse extends Wireformat {

	public boolean successful;
	private int success = 1;
	private int failure = 0;
	
	public PickFactionResponse(boolean success) {
		messageType = CabalWireformatType.PICK_FACTION_RESPONSE; 
		this.successful = success;
	}
	
	public PickFactionResponse(byte[] wireformat) {
		messageType = CabalWireformatType.PICK_FACTION_RESPONSE; 
		this.unmarshall(wireformat);
	}

	@Override
	public byte[] marshall() {
		if (successful) {
			return intToBytes(success);
		} else {
			return intToBytes(failure);
		}
	}

	@Override
	public void unmarshall(byte[] bytes) {
		int answer = bytesToInt(bytes);
		successful = answer == success;
	}

}
