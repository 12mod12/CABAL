package com.mod12.cabal.server.core.person.agent.condition;

public class MentalHealth implements Health {

	private int sanity;
	
	public MentalHealth() {
		sanity = Health.MAX_HEALTH;
	}
	
	@Override
	public void takeDamage(int damage) {
		sanity = sanity - damage;
		if (sanity < Health.MIN_HEALTH) {
			sanity = Health.MIN_HEALTH;
		}
	}

	@Override
	public void receiveHeal(int heal) {
		sanity = sanity + heal;	
		if (sanity > Health.MAX_HEALTH) {
			sanity = Health.MAX_HEALTH;
		}
	}

	@Override
	public int getHealth() {
		return sanity;
	}
	
}
