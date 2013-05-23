package com.mod12.cabal.server.core.item;

public class AssociatedSkill {

	private String associatedSkill;
	private double impact;
	
	public AssociatedSkill(String skill, double impact) {
		this.associatedSkill = skill;
		this.impact = impact;
	}

	public String getAssociatedSkill() {
		return associatedSkill;
	}

	public double getImpact() {
		return impact;
	}

}
