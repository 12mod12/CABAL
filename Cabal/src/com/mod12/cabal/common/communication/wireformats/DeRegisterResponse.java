package com.mod12.cabal.common.communication.wireformats;

public class DeRegisterResponse extends RegisterResponse {

	public DeRegisterResponse(int status, String info) {
		super(status, NULL, NULL, NULL, NULL, info);
		messageType = CabalWireformatType.DE_REGISTER_RESPONSE;
	}
	
	public DeRegisterResponse(byte[] bytes) {
		super(1, NULL, NULL, NULL, NULL, NULL);
		messageType = CabalWireformatType.DE_REGISTER_RESPONSE;
		unmarshall(bytes);
	}
	
}
