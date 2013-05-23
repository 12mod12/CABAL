package com.mod12.cabal.common.communication.link;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.mod12.cabal.common.communication.node.Node;
import com.mod12.cabal.common.communication.wireformats.Wireformat;

public class Link {

	private LinkReceiverThread receiver;
	private LinkSender sender;
	
	private Socket socket;
	private String linkTo;
	private int remotePort;
	private String remoteIp;
	
	public Link(Node node, Socket socket) {
		this(node, socket, null);
	}
	
	public Link(Node node, Socket socket, String linkTo) {
		this.socket = socket;
		this.linkTo = linkTo;
		this.remoteIp = socket.getInetAddress().getHostAddress();
		this.remotePort = socket.getPort();
		receiver = new LinkReceiverThread(node, this);
		sender = new LinkSender(this);
	}
	
	public Link(Node node, String remoteIp, int port, String linkTo) {
		try {
			socket = new Socket(remoteIp, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.linkTo = linkTo;
		this.remoteIp = socket.getInetAddress().getHostAddress();
		this.remotePort = socket.getPort();
		receiver = new LinkReceiverThread(node, this);
		sender = new LinkSender(this);
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public LinkReceiverThread getReceiverThread() {
		return receiver;
	}
	
	public String getLinkTo() {
		return linkTo;
	}
	
	public void init() {
		receiver.start();
	}
	
	public int sendMessage(Wireformat message) {
		return sender.sendMessage(message);
	}

	public void exit() {
		receiver.interrupt();
		try {
			if (!socket.isClosed()) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("receiver is still alive: " + receiver.isAlive());
	}
	
	public void setLinkTo(String assignedID) {
		this.linkTo = assignedID;
	}
	
	public int getRemotePort() {
		return remotePort;
	}
	
	public String toString() {
		return linkTo + " at " + remoteIp + " on port " + remotePort;
	}

	public String getRemoteIP() {
		return remoteIp;
	}
}
