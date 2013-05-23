package com.mod12.cabal.server.core.faction;

import com.mod12.cabal.common.util.History;
import com.mod12.cabal.common.util.Tuple;

/**
 * TODO spill over functions if verify returns false??
 * @author kowalski
 *
 */
public class Prestige {

	private static final double DECAY_FACTOR = .10;
	private static final int SIZE = 100;
	private Tuple<Integer, Integer> spot;
	private History<Tuple<Integer, Integer>> history;
	
	public Prestige() {			
		spot = new Tuple<Integer, Integer>(0, 0);
		history = new History<Tuple<Integer, Integer>>();
	}
	
	public Tuple<Integer, Integer> getSpot() {
		return spot;
	}
	
//	/**
//	 * set new spot
//	 * @param x
//	 * @param y
//	 */
//	public boolean setSpot(int x, int y) {
//		if (verifyNewSpot(x, y)) {
//			updateSpot(x, y);
//			return true;
//		}
//		return false;
//	}
	
	public void decayPrestige() {
		int decay = (int) Math.round((spot.x + spot.y) / 2 * DECAY_FACTOR);
		moveSpot(-decay, -decay);
	}
	
	// TODO adjustInfamy and culture algorithms
	public boolean adjustInfamy(int value) {
		moveSpot(0, value);
		return true;
	}
	
	public int getInfamy() {
		return spot.y;
	}
	
	public boolean adjustCulture(int value) {
		moveSpot(value, 0);
		return true;
	}
	
	public int getCulture() {
		return spot.x;
	}

	/**
	 * moves spot by x,y
	 * @param x
	 * @param y
	 * @return 
	 */
	public boolean moveSpot(int x, int y) {
		int newX = spot.x + x;
		if (newX > SIZE) newX = SIZE; 
		int newY = spot.y + y;
		if (newY > SIZE) newY = SIZE;
		updateSpot(newX, newY);
		return false;
	}
	
	private void updateSpot(int x, int y) {				
		history.addHistory(spot);
		spot = new Tuple<Integer, Integer>(x, y);
	}
	
//	/**
//	 * verifies new spot is in array size
//	 * @param x
//	 * @param y
//	 * @return
//	 */
//	private boolean verifyNewSpot(int x, int y) {
//		if (x + spot.x >= SIZE && x + spot.x <= 0) return false;
//		if (y + spot.y >= SIZE && y + spot.y <= 0) return false;
//		else return true;
//	}

	/**
	 * will be some type of function to determine how "well" a faction is doing
	 * @return
	 */
	public double getValue() {
		return spot.x + spot.y;
	}
	
}
