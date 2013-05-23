package com.mod12.cabal.server.core.person.agent.condition;

public interface Health {

	public static final int MENTAL_ID = 1;
	public static final int PHYSICAL_ID = 2;
	
	public static final int MAX_HEALTH = 100;
	public static final int MIN_HEALTH = 0;
	
	public void takeDamage(int damage);
	
	public void receiveHeal(int heal);
	
	public int getHealth();
	
}
