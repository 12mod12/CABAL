package com.mod12.cabal.common.communication.wireformats;

public class EndStep extends Wireformat {

	public boolean valid;
	
	//Constructor for sender
	public EndStep() {
		messageType = CabalWireformatType.END_STEP;
		valid = true;
	}
	
	//Constructor for receiver
	public EndStep(byte[] bytes) {
		messageType = CabalWireformatType.END_STEP;
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
