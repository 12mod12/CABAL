package com.mod12.cabal.server.core.person.agent.condition;

public class PhysicalHealth implements Health {
	
	private int health;
	
	public PhysicalHealth() {
		health = Health.MAX_HEALTH;
	}
	
	@Override
	public void takeDamage(int damage) {
		health = health - damage;
		if (health < Health.MIN_HEALTH) {
			health = Health.MIN_HEALTH;
		}
	}

	@Override
	public void receiveHeal(int heal) {
		health = health + heal;	
		if (health > Health.MAX_HEALTH) {
			health = Health.MAX_HEALTH;
		}
	}
	
	@Override
	public int getHealth() {
		return health;
	}
	
}
