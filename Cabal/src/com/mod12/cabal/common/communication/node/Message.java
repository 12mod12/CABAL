package com.mod12.cabal.common.communication.node;

public class Message {

	public static final String SERVER_REGISTER_REQUEST = "Register request on client {0} at {1}@{2} on port {3}";
	public static final String SERVER_REGISTER_RESPONSE_DECLINE = "Register Request declined. Sending response.";
	public static final String SERVER_REGISTER_RESPONSE_DECLINE_INFO = "Registration request rejected. " +
			"Request IP address: {0} does not match source IP: {1}.";
	public static final String SERVER_REGISTER_RESPONSE_SUCCESS = "Register Request granted. Sending response. " +
			"The number of connected clients are now {0}.";
	
	public static final String SERVER_DEREGISTER_REQUEST = "DeRegister request on client {0} at {1} on port {2}";
	public static final String SERVER_DEREGISTER_REQUEST_SUCCESS = "DeRegister Request granted. Sending response.";
	public static final String SERVER_DEREGISTER_RESPONSE_SUCCESS = "DeRegistration request successful.";
}
