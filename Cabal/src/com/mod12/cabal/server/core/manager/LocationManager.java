package com.mod12.cabal.server.core.manager;

import static com.mod12.cabal.server.core.ServerCabal.DEBUG;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.dto.FactionPresenceDTO;
import com.mod12.cabal.common.dto.LocationDTO;
import com.mod12.cabal.common.dto.StageDTO;
import com.mod12.cabal.common.util.ListUtil;
import com.mod12.cabal.common.util.Tuple;
import com.mod12.cabal.server.core.faction.Faction;
import com.mod12.cabal.server.core.location.FactionPresence;
import com.mod12.cabal.server.core.location.Location;
import com.mod12.cabal.server.core.location.Stage;
import com.mod12.cabal.server.core.mission.MissionConcept;

/**
 * Knows of all locations in game. Is the main interface to interact with for Location information.
 * @author kowalski
 *
 */
public class LocationManager {

	private static boolean PRIVATE_DEBUG = false;
	
	private static LocationManager manager = null;

	private List<Location> worldLocations = null;
	private Location topLevel = null;

	public static LocationManager getLocationManager() {
		if (manager == null) manager = new LocationManager();
		return manager;
	}

	private LocationManager() {
		worldLocations = new LinkedList<Location>();
	}

//	public int move(String from, String to) {
//		int numberOfMoves = 0;
//
//		int fromDepth = getDepth(topLevel, from, 0);
//		int toDepth = getDepth(topLevel, to, 0);
//
//		return numberOfMoves;
//	}
//
//	private int getDepth(Location level, String lookingFor, int depth) {
//		if (level.getName().equals(lookingFor)) return depth;
//		else {
//			for (Location child : level.getChildren()) {
//				depth = getDepth(child, lookingFor, depth++);
//			}
//		}
//	}

	public List<FactionPresence> createFactionPresences(List<Tuple<String, Double>> factionPresences) {
		FactionManager factionManager = FactionManager.getFactionManager();
		List <FactionPresence> presences = new LinkedList<FactionPresence>();
		for (int i = 0; i < factionPresences.size(); i++) {
			Faction faction = factionManager.getFaction(factionPresences.get(i).x);
			double presence = factionPresences.get(i).y;
			presences.add(new FactionPresence(faction.getName(), presence));
		}

		return presences;
	}

//	public List<Tuple<Stage, MissionConcept>> getActiveMissions() {
//		List<Tuple<Stage,MissionConcept>> results = new ArrayList<Tuple<Stage,MissionConcept>> ();
//		findActiveMissions(results, topLevel);
//		return results;
//	}
//
//	private void findActiveMissions(List<Tuple<Stage, MissionConcept>> results, Location location) {
//		//if the location has children, and thus is not a stage, go deeper
//		if (location.hasChildren() == true) {
//			findActiveMissions(results,location);
//		}
//		else{
//			//if the bottom location is in fact a stage, it should have missions.
//			if (location.getClass() == Stage.class) {
//				Stage stage = (Stage) location;
//				for (MissionConcept mc : stage.getPossibleMissions()) {
//					Tuple<Stage, MissionConcept> temp = new Tuple<Stage,MissionConcept>(stage,mc);
//					results.add(temp);
//				}
//			}
//		}
//
//	}

	public Location createLocation(String type, String name, double xCord, double yCord, String description) {
		Location location = new Location(type, name, xCord, yCord, description);
		worldLocations.add(location);
		return location;
	}

	public Stage createStage(String type, String name, double xCord, double yCord, String description, List<FactionPresence> factionPresences) {
		Stage stage = new Stage(type, name, xCord, yCord, description, factionPresences);
		worldLocations.add(stage);
		return stage;
	}

	public Stage createStage(String type, String name, double xCord, double yCord, String description) {
		Stage stage = new Stage(type, name, xCord, yCord, description);
		worldLocations.add(stage);
		return stage;
	}

	public void setParentAndChildren(Location location, Location parent, Location... children) {
		location.setParent(parent);
		location.addChildren(ListUtil.arrayToList(children));
	}

	public void addMissions(Stage stage, MissionConcept... missions) {
		stage.addMissions(missions);
	}

	public Location getTopLevel() {
		return topLevel;
	}

	public List<Location> getLocationList() {
		return worldLocations;
	}

	public void setTopLevel(Location topLevel) {
		this.topLevel = topLevel;
	}

	public Location getLocation(String locationName) {
		return findLocation(locationName, topLevel);
	}

	private Location findLocation(String locationName, Location level) {
		if (level.getName().equals(locationName)){			
			if (level.getClass() == Stage.class){
				Stage temp = (Stage) level;				
				return temp;
			}
			return level;
		}
		for (Location child : level.getChildren()) {
			Location temp = findLocation(locationName, child);
			if (temp != null) return temp;
		}
		return null;
	}

	public List<String> getFactionsAtStage(String stageName) {
		Stage stage = (Stage) getLocation(stageName);
		List<String> factions = new LinkedList<String>();
		for (FactionPresence presences : stage.getFactionPresences()) {
			factions.add(presences.getFaction());
		}
		return factions;
	}

	public LocationDTO getLocationDTO(String locationName) {		
		Location location = getLocation(locationName);
		LocationDTO result;
		if (location.getClass() == Stage.class){
			result = new StageDTO((Stage) location);
		}
		else{
			result = new LocationDTO(location);
		}
		return result;
	}
	
	public List<LocationDTO> getAllLocationDTO() {
		List<LocationDTO> locations = new LinkedList<LocationDTO>();
		convertToDTO(locations, topLevel);
		return locations;
	}

	private void convertToDTO(List<LocationDTO> locations, Location location) {
		LocationDTO dto;
		if (location instanceof Stage) {
			dto = new StageDTO((Stage) location);
		} else {
			dto = new LocationDTO(location);
		}
		locations.add(dto);
		
		for (Location child : location.getChildren()) {
			convertToDTO(locations, child);
		}
	}
	
	/**
	 * Calculates the overall faction presence of all its stages located within a specific area
	 * @param specificArea
	 * @return
	 */
	public List<FactionPresence> calculatePresences(Location specificArea) {
		List<FactionPresence> presences = new LinkedList<FactionPresence>();

		Integer numberOfStages = 0;
		if (DEBUG && PRIVATE_DEBUG) System.out.println();
		calculatePresences(numberOfStages, specificArea, presences);

		return presences;
	}

	private Integer calculatePresences(Integer stagesSoFar, Location level, List<FactionPresence> presences) {
		if (level instanceof Stage) {
			stagesSoFar += 1;
			stagesSoFar = calculatePresence(stagesSoFar, (Stage) level, presences);
		}
		
		for (Location location : level.getChildren()) {
			if (location instanceof Stage) {
				stagesSoFar += 1;
				stagesSoFar = calculatePresence(stagesSoFar, (Stage) location, presences);
			} else {
				stagesSoFar = calculatePresences(stagesSoFar, location, presences);
			}
		}

		return stagesSoFar;
	}

	private Integer calculatePresence(Integer stagesSoFar, Stage stage, List<FactionPresence> existingPresences) {
		if (DEBUG && PRIVATE_DEBUG) System.out.println(stage.getName() + ". stages so far: " + stagesSoFar);
		
		List<String> updatedFactions = new LinkedList<String>();
		
		// updated based on existing stage presences
		for (FactionPresence stagePresence : stage.getFactionPresences()) {

			String factionInStage = stagePresence.getFaction();
			FactionPresence existingPresence = findFactionPresence(factionInStage, existingPresences);

			// faction has not had other presences yet
			if (existingPresence == null) {
				existingPresences.add(new FactionPresence(factionInStage, stagePresence.getPresence() / stagesSoFar));
				if (DEBUG && PRIVATE_DEBUG) System.out.println("1. " + factionInStage + ": " + stagePresence.getPresence() / stagesSoFar);
			}
			// faction already had other presences, adjust accordingly
			else {
				calculateNewPresence(stagesSoFar, stagePresence.getPresence(), existingPresence);
				if (DEBUG && PRIVATE_DEBUG) System.out.println("2. " + factionInStage + ": " + existingPresence.getPresence());
			}
			updatedFactions.add(factionInStage);
		}
		
		// make sure all all presences so far are updated, even if they didnt exist in this stage
		for (FactionPresence existingPresence : existingPresences) {
			if (!updatedFactions.contains(existingPresence.getFaction())) {
				calculateNewPresence(stagesSoFar, 0.0, existingPresence);
				if (DEBUG && PRIVATE_DEBUG) System.out.println("3. " + existingPresence.getFaction() + ": " + existingPresence.getPresence());
			}
		}

		return stagesSoFar;
	}

	/**
	 * calculates the new overall presence as the stages have increased
	 * @param stagesSoFar
	 * @param stagePresence
	 * @param existingPresence
	 */
	private void calculateNewPresence(Integer stagesSoFar, double stagePresence, FactionPresence existingPresence) {
		double newPresence = existingPresence.getPresence() * (stagesSoFar - 1) + stagePresence;
		newPresence = newPresence / stagesSoFar;
		existingPresence.setPresence(newPresence);
	}

	private FactionPresence findFactionPresence(String name, List<FactionPresence> presences) {
		for (FactionPresence presence : presences) {
			if (name.equals(presence.getFaction())) return presence;
		}
		return null;
	}

	public List<FactionPresenceDTO> calculatePresences(String locationName) {
		List<FactionPresence> fp = this.calculatePresences(findLocation(locationName, topLevel));
		return convertToDTO(fp);
	}

	private List<FactionPresenceDTO> convertToDTO(List<FactionPresence> fp) {
		List<FactionPresenceDTO> presences = new LinkedList<FactionPresenceDTO>();
		for (FactionPresence presence : fp) {
			presences.add(new FactionPresenceDTO(presence));
		}
		return presences;
	}

}
