package com.mod12.cabal.client;

import static com.mod12.cabal.server.core.ServerCabal.DEBUG;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.dto.FactionPresenceDTO;
import com.mod12.cabal.common.dto.LocationDTO;
import com.mod12.cabal.common.dto.StageDTO;
import com.mod12.cabal.common.util.Tuple;

public class LocationHelper {

	public static List<FactionPresenceDTO> createFactionPresenceDTO(List<LocationDTO> world, String locationName) {
		List<FactionPresenceDTO> factionPresences = new LinkedList<FactionPresenceDTO>();
		
		LocationDTO location = findLocationDTO(world, locationName);
		if (location instanceof StageDTO) {
			convertToDTO((StageDTO) location, factionPresences);
		}
		else {
			calculatePresences(location, world, 0, factionPresences);
		}
		
		return factionPresences;
	}
	
	private static LocationDTO findLocationDTO(List<LocationDTO> locations, String name) {
		for (LocationDTO location : locations) {
			if (location.getName().equals(name)) return location;
		}
		return null;
	}
	
	private static Integer calculatePresences(LocationDTO location, List<LocationDTO> world, 
			Integer stagesSoFar, List<FactionPresenceDTO> presences) {
		if (location instanceof StageDTO) {
			stagesSoFar += 1;
			stagesSoFar = calculatePresence(stagesSoFar, (StageDTO) location, presences);
		}
		
		for (String c : location.getChildren()) {
			LocationDTO child = findLocationDTO(world, c);
			if (child instanceof StageDTO) {
				stagesSoFar += 1;
				stagesSoFar = calculatePresence(stagesSoFar, (StageDTO) child, presences);
			} else {
				stagesSoFar = calculatePresences(child, world, stagesSoFar, presences);
			}
		}

		return stagesSoFar;
	}

	private static void convertToDTO(StageDTO stage, List<FactionPresenceDTO> presences) {
		for (Tuple<String, Double> fp : stage.getFactionPresences()) {
			presences.add(new FactionPresenceDTO(fp.x, fp.y));
		}
	}

	private static Integer calculatePresence(Integer stagesSoFar, StageDTO stage, 
			List<FactionPresenceDTO> presences) {
		if (DEBUG) System.out.println(stage.getName() + ". stages so far: " + stagesSoFar);
		
		List<String> updatedFactions = new LinkedList<String>();
		
		// updated based on existing stage presences
		for (Tuple<String, Double> stagePresence : stage.getFactionPresences()) {

			String factionInStage = stagePresence.x;
			FactionPresenceDTO existingPresence = findFactionPresence(factionInStage, presences);
			if (existingPresence != null) presences.remove(existingPresence);
			
			// faction has not had other presences yet
			if (existingPresence == null) {
				existingPresence = new FactionPresenceDTO(factionInStage, stagePresence.y / stagesSoFar);
				if (DEBUG) System.out.println("1. " + factionInStage + ": " + stagePresence.y / stagesSoFar);
			}
			// faction already had other presences, adjust accordingly
			else {
				existingPresence = calculateNewPresence(stagesSoFar, stagePresence.y, existingPresence);
				if (DEBUG) System.out.println("2. " + factionInStage + ": " + existingPresence.getPresence());
			}
			
			presences.add(existingPresence);
			updatedFactions.add(factionInStage);
		}
		
		// make sure all all presences so far are updated, even if they didnt exist in this stage
		List<Integer> toRemove = new LinkedList<Integer>();
		int oldSize = presences.size();
		for (int i = 0; i < oldSize; i++) {
			FactionPresenceDTO existingPresence = presences.get(i);
			if (!updatedFactions.contains(existingPresence.getFaction())) {
				toRemove.add(i);
				existingPresence = calculateNewPresence(stagesSoFar, 0.0, existingPresence);
				presences.add(existingPresence);
				if (DEBUG) System.out.println("3. " + existingPresence.getFaction() + ": " + existingPresence.getPresence());
			}
		}
		
		for (Integer remove : toRemove) {
			presences.remove(remove.intValue());
		}

		return stagesSoFar;
	}

	private static FactionPresenceDTO calculateNewPresence(Integer stagesSoFar, Double stagePresence, 
			FactionPresenceDTO existingPresence) {
		double newPresence = existingPresence.getPresence() * (stagesSoFar - 1) + stagePresence;
		newPresence = newPresence / stagesSoFar;
		return new FactionPresenceDTO(existingPresence.getFaction(), newPresence);
	}
	
	private static FactionPresenceDTO findFactionPresence(String name, List<FactionPresenceDTO> presences) {
		for (FactionPresenceDTO presence : presences) {
			if (name.equals(presence.getFaction())) return presence;
		}
		return null;
	}
	
}
