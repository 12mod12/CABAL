package com.mod12.cabal.common.communication.wireformats;

public class DeRegisterRequest extends RegisterRequest {
	
	public String ipAddress;
	public int portNumber;
	public String assignedID;
	
	public DeRegisterRequest(String ip, int port, String id) {
		super(ip, port, id);
		messageType = CabalWireformatType.DE_REGISTER_REQUEST;
	}
	
	public DeRegisterRequest(byte[] bytes) {
		super(bytes);
		messageType = CabalWireformatType.DE_REGISTER_REQUEST;
	}

}
