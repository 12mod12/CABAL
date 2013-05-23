package com.mod12.cabal.common.communication.node;

import java.net.InetAddress;

import com.mod12.cabal.common.communication.link.Link;

public abstract class Node {

	protected int portNumber;
	protected String ipAddress;
	
	public int getPortNumber() {
		return portNumber;
	}
	
	protected void printHostname() {
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
			String hostname = InetAddress.getLocalHost().getHostName();
			System.out.println("self ip: " + ipAddress + "@" + hostname + "\n");
		} catch (Exception e) {}
	}
	
	public abstract void notify(int wireformatType, byte[] wireformat, Link fromLink);
	
	public String getipAddress() {
		return ipAddress;
	}
	
	public void setPort(int port) {
		portNumber = port;
	}
	
}
