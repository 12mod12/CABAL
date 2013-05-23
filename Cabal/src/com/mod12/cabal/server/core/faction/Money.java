package com.mod12.cabal.server.core.faction;

public class Money {

	private static String name;
	
	private int currentFunds;
	
	public Money() {
		this(0);
	}
	
	public Money(int start) {
		currentFunds = start;
	}

	public static void setName(String name) {
		Money.name = name;
	}
	
	public static String getName() {
		return name;
	}
	
	public int updateMoney(int change) {
		currentFunds += change;
		return currentFunds;
	}
	
	public int getMoney() {
		return currentFunds;
	}
	
	public String toString() {
		return "" + currentFunds + " " + name;
	}
}
