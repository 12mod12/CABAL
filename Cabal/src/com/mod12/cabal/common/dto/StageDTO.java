package com.mod12.cabal.common.dto;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.util.Tuple;
import com.mod12.cabal.server.core.location.FactionPresence;
import com.mod12.cabal.server.core.location.Stage;
import com.mod12.cabal.server.core.mission.MissionConcept;

public class StageDTO extends LocationDTO {
	
	public static final String STAGE_DELIM = "!stage!";
	protected static final String POSSIBLE_MISSIONS_DELIM = "_possible_missions_";
	protected static final String FACTION_PRESENCES_DELIM = "_faction_presences_";
	
	private List<String> possibleMissions;
	private List<Tuple<String, Double>> factionPresences;

	public StageDTO(String type, String name, String description,
			String parent, List<String> children,
			List<String> possibleMissions,
			List<Tuple<String, Double>> factionPresences) {
		super(type, name, description, parent, children);
		this.possibleMissions = possibleMissions;
		this.factionPresences = factionPresences;
	}
	
	public StageDTO(Stage stage) {
		super(stage);
		this.factionPresences = new LinkedList<Tuple<String, Double>>();
		for (FactionPresence presence : stage.getFactionPresences()) {
			factionPresences.add(new Tuple<String, Double>(presence.getFaction(), presence.getPresence()));
		}
		this.possibleMissions = new LinkedList<String>();
		for (MissionConcept mc : stage.getPossibleMissions()){
			possibleMissions.add(mc.getName());
		}
	}
	
	public StageDTO(String stage) {
		super(stage);

//		System.out.println("StageDTO: " + stage);

		String[] parts = stage.split(DELIM);

		possibleMissions = new LinkedList<String>();
		factionPresences = new LinkedList<Tuple<String, Double>>();
		int count = 0;
		while (!parts[count].equals(POSSIBLE_MISSIONS_DELIM)) count++;
		count++;
		while (!parts[count].equals(FACTION_PRESENCES_DELIM)) {
			possibleMissions.add(parts[count]);
			count++;
		}
		count++;
		while (count != parts.length) {
			String[] tuple = parts[count].split(TUPLE_DELIM);
			factionPresences.add(new Tuple<String, Double>(tuple[0], Double.parseDouble(tuple[1])));
			count++;
		}
	}

	public List<String> getPossibleMissions() {
		return possibleMissions;
	}

	public List<Tuple<String, Double>> getFactionPresences() {
		return factionPresences;
	}
	
	@Override
	public String marshall() {
		String output = super.marshall();
		output += DELIM + POSSIBLE_MISSIONS_DELIM;
		for (String mission : possibleMissions) {
			output += DELIM + mission;
		}
		output += DELIM + FACTION_PRESENCES_DELIM;
		for (Tuple<String, Double> fp : factionPresences) {
			output += DELIM + fp.x + TUPLE_DELIM + fp.y;
		}
		
		// add stage note at front
		output = STAGE_DELIM + output;
		
		return output;
	}
	
}
