package com.mod12.cabal.common.util;

public class Constants {
    // TODO move messages that don't matter to util out
    public final static String OPENING = "Welcome to CABAL: The game of power and intrigue.";
    public final static String OPEN_PROMPT = "Would you like to start a NEW game, LOAD a game, or QUIT?";

    public final static String NOT_IMP = "Feature not implemented yet.";
    public final static String EXIT = "Exiting program.  Thanks for playing.  Come back soon.";
    public final static String MENU_OUTPUT = " 1) MAP \n 2) MISSIONS \n 3) FACTION \n 4) OPTIONS \n 5) SAVE \n 6) QUIT \n";
    public final static String SCENARIO_PROMPT = "Please enter the number of a scenario from the list below to learn more or type BACK to return to the main menu";
    public final static String[] scenarios = {"FRENCH_REV","USA_WWII"};
    public final static String[] scenarioName = {"French Revolution","U.S.A. homeland during WWII"};
    public final static String PLAYER_PROMPT = "Please enter the number of players.";
    public final static String PLAYER_FACTION_PROMPT =  "Please Select faction by number Player ";


    public final static String SCENARIO_CONFIRM = "If you wish to play this SCENARIO, type YES.  Otherwise enter anything else to go back to selection.";
    public final static String SCENARIO_PATH = System.getProperty("user.dir") + "\\Scenarios\\";
    public final static String SCENPATH_LINUX = System.getProperty("user.dir") + "/Scenarios/";
    public final static String SCENARIO_FILE_EX = ".sin";


    /////* Delimiters */////
    public final static String DELIM = "~~~";
    public final static String END_DELIM = "***";
    public final static String DELIM_LIST = ",,,";
    public final static String DELIM_KEY_VALUE_PAIR = ":::";
    public final static String DELIM_NESTED_START = "{";
    public final static String DELIM_NESTED_END = "}";

    public static final String COMMENT = "//";
    public static final String EOL = System.getProperty("line.separator");

    /////* Error Messages */////
    public final static String ERROR_FILE_LOAD_SCENARIO = "##Could not find given scenario file in location: ";
    public final static String ERROR_FILE_SCENARIO_FORMAT = "##Scenario file format error: Check that format of file is correct: ";
    public final static String ERROR_INVALID_INPUT = "##Not a valid input: ";




    public final static String formatInfo(String firstLine) {
        String result = "";
        result = firstLine.replace('|', '\n');
        return result;
    }
}
