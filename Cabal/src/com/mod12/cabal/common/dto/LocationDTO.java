package com.mod12.cabal.common.dto;

import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.communication.wireformats.Wireformat;
import com.mod12.cabal.server.core.location.Location;

public class LocationDTO extends DataTransferObject {

	protected static final String CHILDREN_DELIM = "_children_";

	private static final String LOCDELIM = "_endlocation_";
	
	protected String type;
	protected String name;
	protected String description;
	protected String parent;
	protected List<String> children;
	
	public LocationDTO(String type, String name, String description,
			String parent, List<String> children) {
		this.type = type;
		this.name = name;
		this.description = description;
		this.parent = parent;
		this.children = children;
	}
	
	public LocationDTO(Location location) {
		this.type = location.getType();
		this.name = location.getName();
		this.description = location.getDescription();
		if (location.getParent() == null) this.parent = Wireformat.NULL;
		else this.parent = location.getParent().getName();
		this.children = new LinkedList<String>();
		for (Location child : location.getChildren()) {
			this.children.add(child.getName());
		}
	}
	
	public LocationDTO(String location) {
		String[] parts = location.split(DELIM);

//		System.out.println("LocationDTO: " + location);

		type = parts[0];
		name = parts[1];
		description = parts[2];
		parent = parts[3];
		
		children = new LinkedList<String>();
		int count = 5;
		while (!parts[count].equals(LOCDELIM)) {
			children.add(parts[count]);
			count++;
		}
	}
	
	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getParent() {
		return parent;
	}

	public List<String> getChildren() {
		return children;
	}

	public String marshall() {
		String output = type;
		output += DELIM + name;
		output += DELIM + description;
		output += DELIM + parent;
		output += DELIM + CHILDREN_DELIM;
		for (String child : children) {
			output += DELIM + child;
		}
		output += DELIM + LOCDELIM;
		
		return output;
	}
	
}
