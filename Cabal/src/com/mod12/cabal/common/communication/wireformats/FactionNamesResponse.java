package com.mod12.cabal.common.communication.wireformats;

import java.util.ArrayList;
import java.util.List;

import com.mod12.cabal.common.util.Tuple;

public class FactionNamesResponse extends Wireformat {

	public List<Tuple<String,String>> populateInfo;
	
	//Constructor for sender
	public FactionNamesResponse(List<Tuple<String,String>> populateInfo) {				
		messageType = CabalWireformatType.FACTION_NAMES_RESPONSE;	
		this.populateInfo = populateInfo;
	}
	
	//Constructor for receiver
	public FactionNamesResponse(byte[] bytes) {
		messageType = CabalWireformatType.FACTION_NAMES_RESPONSE;	
		populateInfo = new ArrayList<Tuple<String,String>>();
		unmarshall(bytes);
	}
	
	public List<Tuple<String,String>> getList() {
		return populateInfo;
	}

	@Override
	public byte[] marshall() {
		String output = "";
		for (Tuple<String,String> temp : populateInfo){
			output += temp.x + DELIM;
			output += temp.y + DELIM;
		}						
		return output.getBytes();
	}

	@Override
	public void unmarshall(byte[] bytes) {
		String result = bytesToString(bytes);
		String[] parts = result.split(DELIM);
		for (int i = 0; i < parts.length; i += 2) {
			String name = parts[i];
			String handle = "";
			if (i+1 < parts.length){
				handle = parts[i + 1];
			}			
			Tuple<String, String> temp = new Tuple<String, String>(name, handle);
			if (temp != null){
				populateInfo.add(temp);
			}
		}		
	}
	
}
