package com.mod12.cabal.server.core.location;

import java.util.LinkedList;
import java.util.List;

public class Location {

	private String type;
	private String name;	
	private double[] coordinates;
	private String description;
	private Location parent;
	private List<Location> children;
	
	public Location(String type, String name, double xCord, double yCord, String description) {
		this.type = type;
		this.name = name;
		coordinates = new double[2];
		coordinates[0] = xCord;
		coordinates[1] = yCord;		
		this.description = description;		
		children = new LinkedList<Location>();
	}
	
	public String getType() {
		return type;
	}
	
	public boolean hasChildren() {
		if (children.size() == 0) {
			return false;
		}
		else{
			return true;
		}
	}
	
	public String getName() {
		return name;
	}

	public double[] getCoordinates() {
		return coordinates;
	}
	
	public double getXCord() {
		return coordinates[0];
	}
	
	public double getYCord() {
		return coordinates[1];
	}
	
	public String getDescription() {
		return description;
	}

	public Location getParent() {
		return parent;
	}

	public void setParent(Location parent) {
		this.parent = parent;
	}

	public List<Location> getChildren() {
		return children;
	}

	public void addChildren(List<Location> children) {
		this.children.addAll(children);
	}
	
	public void addChild(Location child) {
		this.children.add(child);
	}
	
	public String toString() {
		String result = this.type + ": " + this.name + ": " + this.description;
		return result;
	}

	public boolean simpleEquals(String name) {
		return this.name.equals(name);
	}
	
}
