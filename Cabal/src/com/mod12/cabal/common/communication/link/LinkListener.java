package com.mod12.cabal.common.communication.link;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.mod12.cabal.common.communication.node.Server;

public class LinkListener extends Thread {

	private Server node;
	private int portNumber;
	private ServerSocket serverSocket;
	
	public LinkListener(Server node, int portNumber) throws IOException {
		this.node = node;
		this.portNumber = portNumber;
		this.portNumber = node.getPortNumber();
		serverSocket = new ServerSocket(this.portNumber, 1000);
	}
	
	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				Socket socket = serverSocket.accept();
				Link newLink = new Link(node, socket);
				newLink.init();
				node.getLinks().add(newLink); 	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
