package com.mod12.cabal.common.communication.link;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.mod12.cabal.client.ClientCabal;
import com.mod12.cabal.common.communication.node.Node;
import com.mod12.cabal.common.communication.wireformats.Wireformat;
import com.mod12.cabal.server.core.ServerCabal;

public class LinkReceiverThread extends Thread {

	private Node node;
	public static final int BUFFER_LENGTH_BYTES = 4000;
	private DataInputStream dataInputStream;
	private byte[] buffer;
	
	private Socket socket;
	private Link link;
	
	public LinkReceiverThread(Node node, Link link) {
		this.node = node;
		this.link = link;
		this.socket = link.getSocket();
	}
	
	public void setLink(Link link) {
		this.link = link;
	}
	
	@Override
	public void run() {
		try {
			waitForAndReadMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * .read is a blocking call
	 */
	private void waitForAndReadMessage() throws IOException {
		try {
			dataInputStream = new DataInputStream(socket.getInputStream());                          
			buffer = new byte[BUFFER_LENGTH_BYTES];
			
			int wireformatType, lengthOfMessage, lengthRead, totalRead;
			while (!isInterrupted()) {
				wireformatType = readNextInt(buffer);
				lengthOfMessage = readNextInt(buffer);
				lengthRead = 0;
				totalRead = 0;
				
				System.out.println("receiving message: " + wireformatType);
				System.out.println("length of message: " + lengthOfMessage);
				
				byte[] totalWireformat = new byte[lengthOfMessage];
				resetToZero(buffer);
				while (totalRead < lengthOfMessage) {
					if (totalRead + BUFFER_LENGTH_BYTES < lengthOfMessage) {
						lengthRead = dataInputStream.read(buffer);
					} else {
						lengthRead = dataInputStream.read(buffer, 0, lengthOfMessage - totalRead);
					}
					

					System.arraycopy(buffer, 0, totalWireformat, totalRead, lengthRead);					

					totalRead += lengthRead;
				}
								
				
				notify(wireformatType, totalWireformat);
			}
			
		} catch (Exception e) {
			System.out.println("receive message error");
			e.printStackTrace();
		}
	}
	
	private void notify(int wireformatType, byte[] totalWireformat) {
		if (totalWireformat == null || totalWireformat.length == 0) return;
		
		if (node instanceof ServerCabal) { // server
			ServerCabal cabal = (ServerCabal) node;
			cabal.notify(wireformatType, totalWireformat, link);
		} else { // client
			ClientCabal cabal = (ClientCabal) node;
			
			cabal.notify(wireformatType, totalWireformat, link);
		}
	}
	
//	private int readInt() {
//		return Wireformat.bytesToInt(wireformat);
//	}
//	
//	private String readString() {
//		return new String(wireformat).trim();
//	}
//	
//	private int read() throws IOException {
//		int length = readLength(wireformat);
//		read(wireformat, length);
//		return length;
//	}
	
	private int readNextInt(byte[] wireformat) throws IOException {
		resetToZero(wireformat);
		dataInputStream.read(wireformat, 0, 4); // read length of int (obviously 4)
		resetToZero(wireformat);
		dataInputStream.read(wireformat, 0, 4); // read actually int
		return Wireformat.bytesToInt(wireformat);
	}
	
//	private int readLength(byte[] wireformat) throws IOException {
//		resetToZero(wireformat);
//		dataInputStream.read(wireformat, 0, 4);
//		return Wireformat.bytesToInt(wireformat);
//	}
//	
//	private void read(byte[] wireformat, int length) throws IOException {
//		resetToZero(wireformat);
//		dataInputStream.read(wireformat, 0, length);
//	}
	
	private void resetToZero(byte[] wireformat) {
		for (int i = 0; i < wireformat.length; i++) wireformat[i] = 0;
	}
	
}
