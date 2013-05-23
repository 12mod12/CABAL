package com.mod12.cabal.server.core.person.agent;

public enum ItemArea {

	 HEAD(0),
	 CHEST(1),
	 LEGS(2),
	 FEET(3),
	 HANDS(4),
	 WEAPON(5),
	 TOTEM(6);
	
	 private int index;
	 
	 private ItemArea(int index) {
		 this.index = index;
	 }
	 
	 public int getIndex() {
		 return this.index;
	 }
	 
}
