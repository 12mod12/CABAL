package com.mod12.cabal.common.communication.wireformats;

import java.util.LinkedList;
import java.util.List;

public class EndGame extends Wireformat {

	public List<String> scoreboad;
	
	public EndGame(List<String> scoreboad) {
		messageType = CabalWireformatType.END_GAME;
		this.scoreboad = scoreboad;
	}
	
	public EndGame(byte[] bytes) {
		messageType = CabalWireformatType.END_GAME;
		unmarshall(bytes);
	}
	
	@Override
	public byte[] marshall() {
		String output = "";
		for (String score : scoreboad) {
			output += score + DELIM;
		}
		return output.getBytes();
	}

	@Override
	public void unmarshall(byte[] bytes) {
		String result = bytesToString(bytes);
		String[] parts = result.split(DELIM);
		scoreboad = new LinkedList<String>();
		for (String part : parts) {
			scoreboad.add(part);
		}
	}

}
