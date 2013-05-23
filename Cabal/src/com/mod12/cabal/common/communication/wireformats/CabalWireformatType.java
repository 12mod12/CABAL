package com.mod12.cabal.common.communication.wireformats;

public class CabalWireformatType {

	public static final int DATA = 1;
	public static final int DE_REGISTER_REQUEST = 2;
	public static final int DE_REGISTER_RESPONSE = 3;
	public static final int NODE_INFO = 4;
	
	
	public static final int REGISTER_REQUEST = 7;
	public static final int REGISTER_RESPONSE = 8;
	
	public static final int EXIT = 10;
	public static final int FACTION_NAMES_REQUEST = 11;
	public static final int FACTION_NAMES_RESPONSE = 12;
	public static final int PICK_FACTION_REQUEST = 13;
	public static final int PICK_FACTION_RESPONSE = 14;
	public static final int GAME_START = 15;
	public static final int START_STEP = 16;
	public static final int END_STEP = 17;
	
	
	public static final int UPDATE_WORLD = 20;
	public static final int START_MISSION = 21;
	public static final int NEW_FACTION_STEP_NOTIFICATION = 22;
	public static final int END_GAME = 23;
	
}
