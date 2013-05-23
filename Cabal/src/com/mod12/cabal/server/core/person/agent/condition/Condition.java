package com.mod12.cabal.server.core.person.agent.condition;

/**
 * Represents the physical and mental health and state of an {@link com.mod12.cabal.server.core.person.agent.Agent Agent}
 * 
 * @author kowalski
 *
 */
public class Condition {

	private PhysicalHealth physicalHealth;
	private MentalHealth mentalHealth;
	private State physicalState;
	private State mentalState;
	
	public Condition() {
		physicalHealth = new PhysicalHealth();
		mentalHealth = new MentalHealth();
		physicalState = State.HEALTHY;
		mentalState = State.SANE;
	}
	
	public void takePhysicalDamage(int damage) {		
		physicalHealth.takeDamage(damage);		
		checkPhysicalState();
	}
	
	public void takeMentalDamage(int damage) {
		mentalHealth.takeDamage(damage);
		checkMentalState();
	}
	
	public void receivePhysicalHeal(int heal) {				
		physicalHealth.receiveHeal(heal);		
		checkPhysicalState();
	}
	
	public void receiveMentalHeal(int heal) {
		mentalHealth.receiveHeal(heal);
		checkMentalState();
	}
	
	private void checkPhysicalState() {
		physicalState = State.getState(true, physicalHealth.getHealth());
//		if (State.DEAD.inRange(physicalHealth.getHealth())) {
//			physicalState = State.DEAD;
//		}
//		else if (State.UNCOUNSCIOUS.inRange(physicalHealth.getHealth())) {
//			physicalState = State.UNCOUNSCIOUS;
//		}
//		else if (State.PAINED.inRange(physicalHealth.getHealth())) {
//			physicalState = State.PAINED;
//		}
//		else if (State.HEALTHY.inRange(physicalHealth.getHealth())) {
//			physicalState = State.HEALTHY;
//		}
	}
	
	private void checkMentalState() {
		mentalState = State.getState(false, mentalHealth.getHealth());
//		if (State.INSANE.inRange(mentalHealth.getHealth())) {
//			mentalState = State.INSANE;
//		}
//		else if (State.SHOCKED.inRange(mentalHealth.getHealth())) {
//			mentalState =  State.SHOCKED;
//		}
//		else if (State.STRESSED.inRange(mentalHealth.getHealth())) {
//			mentalState = State.STRESSED;
//		}
//		else if (State.SANE.inRange(mentalHealth.getHealth())) {
//			mentalState = State.SANE;
//		}
	}

	public PhysicalHealth getPhysicalHealth() {
		return physicalHealth;
	}

	public MentalHealth getMentalHealth() {
		return mentalHealth;
	}

	public State getPhyiscalState() {
		return physicalState;
	}
	
	public State getMentalState() {
		return mentalState;
	}
	
}
