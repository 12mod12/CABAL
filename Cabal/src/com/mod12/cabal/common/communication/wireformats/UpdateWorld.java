package com.mod12.cabal.common.communication.wireformats;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.dto.LocationDTO;
import com.mod12.cabal.common.dto.StageDTO;

public class UpdateWorld extends Wireformat{

	public List<LocationDTO> world;
	
	//Constructor for sender
	public UpdateWorld(List<LocationDTO> locations) {
		messageType = CabalWireformatType.UPDATE_WORLD;
		this.world = locations;
	}
	
	//Constructor for receiver
	public UpdateWorld(byte[] bytes) {
		messageType = CabalWireformatType.UPDATE_WORLD;
		unmarshall(bytes);
	}

	@Override
	public byte[] marshall() {
		String output= "";
		for (LocationDTO location : world) {
			if (location instanceof StageDTO) {
//				System.out.println("marshaling a stage: " + location.marshall());
				output += ((StageDTO) location).marshall() + DELIM;
			}
			else {
//				System.out.println("marshaling a location: " + location.marshall());
				output += location.marshall() + DELIM;
			}
		}
		
		return output.getBytes();
	}

	@Override
	public void unmarshall(byte[] bytes) {
		String result = bytesToString(bytes);
//		System.out.println("world string = " + result);
//		System.out.println("world string byte length = " + result.getBytes().length);
		String[] list = result.split(DELIM);
		world = new LinkedList<LocationDTO>();
		for (String location : list) {
			if (location.startsWith(StageDTO.STAGE_DELIM)) {
				//System.out.println("unmarshaling a stage: " + new StageDTO(location.replace(StageDTO.STAGE_DELIM, "")).getName());
				world.add(new StageDTO(location.replace(StageDTO.STAGE_DELIM, "")));
			}
			else {
				//System.out.println("unmarshaling a location: " + new LocationDTO(location).getName());
				world.add(new LocationDTO(location));
			}
		}
	}
	
}
