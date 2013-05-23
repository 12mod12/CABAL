package com.mod12.cabal.common.communication.wireformats;

public class RegisterRequest extends Wireformat {

	public String ipAddress;
	public int portNumber;
	public String requestedID;
	
	//Constructor for sender
	public RegisterRequest(String ip, int port, String id) {
		messageType = CabalWireformatType.REGISTER_REQUEST;
		this.ipAddress = ip;
		this.portNumber = port;
		this.requestedID = id;
	}
	
	//Constructor for receiver
	public RegisterRequest(byte[] bytes) {
		messageType = CabalWireformatType.REGISTER_REQUEST;
		unmarshall(bytes);
	}

	@Override
	public byte[] marshall() {
		String output = "";
		output += ipAddress + DELIM;
		output += portNumber + DELIM;
		output += requestedID;
		return output.getBytes();
	}

	@Override
	public void unmarshall(byte[] bytes) {
		String result = bytesToString(bytes);
		String[] parts = result.split(DELIM);
		ipAddress = parts[0];
		portNumber = Integer.parseInt(parts[1]);
		requestedID = parts[2];		
	}
	
}
