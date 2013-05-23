package com.mod12.cabal.common.communication.node;

import java.util.ArrayList;

import com.mod12.cabal.common.communication.link.Link;
import com.mod12.cabal.common.communication.link.LinkListener;
import com.mod12.cabal.common.communication.wireformats.CabalWireformatType;
import com.mod12.cabal.common.communication.wireformats.DeRegisterRequest;
import com.mod12.cabal.common.communication.wireformats.DeRegisterResponse;
import com.mod12.cabal.common.communication.wireformats.RegisterRequest;
import com.mod12.cabal.common.communication.wireformats.RegisterResponse;
import com.mod12.cabal.common.util.Formatter;

public class Server extends Node {
	
	protected ArrayList<Link> connectedNodes;
	protected LinkListener linkConnectionFactory;
	protected int counter;
	protected String hostHandle;
	
	public Server() {
		connectedNodes = new ArrayList<Link>();
		printHostname();
		counter = 1;
		hostHandle = "";
	}
	
	public ArrayList<Link> getLinks() {
		return connectedNodes;
	}
	
	protected Link findLink(String node) {
		for (int i = 0; i < connectedNodes.size(); i++) {
			if (connectedNodes.get(i).getLinkTo().equals(node)) return connectedNodes.get(i);
		}
		return null;
	}

	public void check() {
		for (int i = 0; i < connectedNodes.size(); i++) {
			Link link = connectedNodes.get(i);
			System.out.println("checking: " + link.getLinkTo() + ": " + link.getRemotePort() + " ?= " + link.getSocket().getPort());
		}
	}

	public void prepareForExit() {
		this.linkConnectionFactory.interrupt();
		for (int i = 0; i < connectedNodes.size(); i++) {
			connectedNodes.get(i).exit();
		}
		System.exit(0);
	}
	
	public void listNodes() {
		System.out.println("Connected Nodes:");
		for (int i = 0; i < connectedNodes.size(); i++) {
			System.out.println(connectedNodes.get(i));
		}
	}
	
	@Override
	public void notify(int wireformatType, byte[] wireformat, Link fromLink) {
		if (wireformatType == CabalWireformatType.DE_REGISTER_REQUEST) {
			this.deRegisterRequest( new DeRegisterRequest(wireformat), fromLink);
		}
	}
	
	// TODO put messages in Message.java and write code to add param's to messages (kevin)
	public void registerRequest(RegisterRequest request, Link link, String scenario, 
			String location, String timePeriod) {
		String remoteIpAddress = link.getRemoteIP();
		int remotePort = link.getRemotePort();
		
		String linkIP = link.getSocket().getInetAddress().getHostAddress();
		System.out.println(Formatter.format(Message.SERVER_REGISTER_REQUEST, 
				new Object[] {request.requestedID, remoteIpAddress, linkIP, remotePort}));
		
		//int linkPort = link.getSocket().getPort();
		//System.out.println(linkIP + ", " + linkPort);
		String info;
		RegisterResponse response;
		if (!linkIP.equals(remoteIpAddress)) {
			System.out.println(Message.SERVER_REGISTER_RESPONSE_DECLINE);
			info = Formatter.format(Message.SERVER_REGISTER_REQUEST, 
					new Object[] {request.ipAddress, remoteIpAddress});
			response = new RegisterResponse(RegisterResponse.FAILURE, scenario, location, 
					timePeriod, hostHandle, info);
			connectedNodes.remove(link);
		}
		else {
			if (hostHandle.isEmpty()){
				hostHandle = request.requestedID;
			}
			info = "";
			if (alreadyRegistered(request)) {
				request.requestedID = request.requestedID + counter;
				counter++;
				info = request.requestedID;
			} 
			System.out.println(Formatter.format(Message.SERVER_REGISTER_RESPONSE_SUCCESS, 
					new Object[] {connectedNodes.size()}));			
			response = new RegisterResponse(RegisterResponse.SUCCESS, scenario, location, 
					timePeriod, hostHandle, info);			
			link.setLinkTo(request.requestedID);
		}
		
		System.out.println("\n");
		
		link.sendMessage(response);
	}
	
	protected boolean alreadyRegistered(RegisterRequest request) {
		for (int i = 0; i < connectedNodes.size(); i++) {
			Link link = connectedNodes.get(i);
			if (link.getLinkTo() != null && link.getLinkTo().equals(request.requestedID)) return true;
		}
		return false;
	}
	
	public void deRegisterRequest(DeRegisterRequest request, Link link) {
		System.out.println(Formatter.format(Message.SERVER_REGISTER_RESPONSE_SUCCESS, 
				new Object[] {request.assignedID, request.ipAddress, request.portNumber}));
		System.out.println(Message.SERVER_DEREGISTER_REQUEST_SUCCESS);
		String info = Message.SERVER_DEREGISTER_RESPONSE_SUCCESS;
		DeRegisterResponse response = new DeRegisterResponse(DeRegisterResponse.SUCCESS, info);
		link.sendMessage(response);
		connectedNodes.remove(link);
		link.exit();
	}

}
