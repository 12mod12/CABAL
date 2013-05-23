package com.mod12.cabal.common.communication.wireformats;

import java.nio.ByteBuffer;

public abstract class Wireformat {

	protected static String DELIM = com.mod12.cabal.common.util.Constants.DELIM;
	
	public int messageType;
	
	public static final String NULL = "null";
	
	public abstract byte[] marshall();
	
	public abstract void unmarshall(byte[] bytes);
	
	/**
	 * taken from http://snippets.dzone.com/posts/show/93
	 * @param i
	 * @return
	 */
	public static final byte[] intToBytes(int i) {
        return new byte[] {
                (byte)(i >>> 24),
                (byte)(i >>> 16),
                (byte)(i >>> 8),
                (byte)i};
	}
	
	/**
	 * taken from http://snippets.dzone.com/posts/show/93
	 * @param b
	 * @return
	 */
	public static final int bytesToInt(byte[] b) {
        return (b[0] << 24)
                + ((b[1] & 0xFF) << 16)
                + ((b[2] & 0xFF) << 8)
                + (b[3] & 0xFF);
	}
	
	public static final byte[] stringToBytes(String s) {
		return s.getBytes();
	}
	
	public static final String bytesToString(byte[] b) {
		return new String(b).trim();
	}
	
	public static byte[] doubleToByteArray(double d) {
	    byte[] bytes = new byte[8];
	    ByteBuffer.wrap(bytes).putDouble(d);
	    return bytes;
	}

	public static double bytesToDouble(byte[] bytes) {
	    return ByteBuffer.wrap(bytes).getDouble();
	}
	
	public static byte[] stringLengthToBytes(String s) {
		return intToBytes(s.length());
	}
	
	public byte[] simplePacket() {
		String output = "1";
		return output.getBytes();
	}

	public boolean simplePacketResult(byte[] bytes) {
		String result = bytesToString(bytes);
		if (result != "1") {
			return false;
		}
		else{
			return true;
		}
	}
	
}
