package com.mod12.cabal.common.communication.link;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.mod12.cabal.common.communication.wireformats.Wireformat;

public class LinkSender {

	private static final int INT_BYTE_LENGTH = 4;
	private Link link;
	private OutputStream outStream;
	
	public LinkSender(Link link) {
		this.link = link;
	}
	
	public int sendMessage(Wireformat message) {
		try {
			Socket socket = link.getSocket();
			
			outStream = socket.getOutputStream();
			
			byte[] bytes = message.marshall();
			sendAcross(message.messageType);
			sendAcross(bytes.length);

			System.out.println("sent messagetype: " + message.messageType + " to " +
					link.getLinkTo() + ", local port: " + socket.getLocalPort() + 
					", remote port: " + socket.getPort() + ". length of bytes=" + bytes.length);
			
			
			outStream.write(bytes);
			outStream.flush();
			return 1;
		} catch (Exception e) {
			System.out.println("send message error");
			e.printStackTrace();
			
			return -1;
		}
	}
	
//	private void writeBuffer(byte[] bytes){
//		int lengthSent = 0;
//		int totalSent = 0;
//		int lengthOfMessage = bytes.length;
//				
//		System.out.println("length of message" + lengthOfMessage);
//		int buffersize = LinkReceiverThread.BUFFER_LENGTH_BYTES;
//		
//		byte[] buffer = new byte[buffersize];
//		//resetToZero(buffer);
//		while (totalSent < lengthOfMessage) {
//			
//			if (totalSent + buffersize < lengthOfMessage) {
//				buffer = extractBytes(lengthSent, buffer, buffersize, buffersize);
//			} else {
//				buffer = extractbytes(lengthSent, buffer, lengthOfMessage - totalSent);
//			}
//			
//			for (int i = totalSent; i < lengthSent; i++) {
//				totalWireformat[i] = buffer[i];
//			}
//			totalSent += lengthSent;
//		}
//	}
//	
//	private byte[] extractBytes(int lengthSent,byte[] bytes, int length, int bufferSize){
//		byte[] buffer = new byte[bufferSize];
//		System.arraycopy(bytes, lengthSent, buffer, 0, length);
//		return buffer;
//	}
	
//	private void sendAcross(String send) throws IOException {
//		outStream.write(Wireformat.intToBytes(send.length())); //Integer.toString(send.length()).getBytes());
//		outStream.write(send.getBytes());
//	}
	
	private void sendAcross(int send) throws IOException {
		outStream.write(Wireformat.intToBytes(INT_BYTE_LENGTH));
		outStream.write(Wireformat.intToBytes(send));
	}
	
	
}
