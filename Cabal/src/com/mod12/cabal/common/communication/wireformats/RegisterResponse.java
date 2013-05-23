package com.mod12.cabal.common.communication.wireformats;

public class RegisterResponse extends Wireformat {

	public int statusCode;
	public static final int SUCCESS = 1;
	public static final int FAILURE = 2;
	public String sinName;
	public String location;
	public String timePeriod;
	public String hostHandle;
	public String additionalInfo;
	
	public RegisterResponse(int status, String sinName, String location, String timePeriod,
			String hostHandle, String info) {
		messageType = CabalWireformatType.REGISTER_RESPONSE;
		this.statusCode = status;
		this.sinName = sinName;
		this.location = location;
		this.timePeriod = timePeriod;
		this.hostHandle = hostHandle;
		this.additionalInfo = info;
	}
	
	public RegisterResponse(byte[] bytes) {
		messageType = CabalWireformatType.REGISTER_RESPONSE;
		unmarshall(bytes);
	}

	@Override
	public byte[] marshall() {
		String output = "";
		output += statusCode + DELIM;
		output += sinName + DELIM;
		output += location + DELIM;
		output += timePeriod + DELIM;
		output += hostHandle + DELIM;
		output += additionalInfo;
		return output.getBytes();
	}

	@Override
	public void unmarshall(byte[] bytes) {
		String result = bytesToString(bytes);
		String[] parts = result.split(DELIM);
		statusCode = Integer.parseInt(parts[0]);
		sinName = parts[1];
		location = parts[2];
		timePeriod = parts[3];
		hostHandle = parts[4];
		if (parts.length == 6){
			additionalInfo = parts[5];
		}
		else{
			additionalInfo = "";
		}
	}
	
	public String getStatus() {
		String status = "SUCCESS";
		if (statusCode == FAILURE) status = "FAILURE";
		return status;
	}
	
}
