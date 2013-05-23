package com.mod12.cabal.server.core;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.Cabal;
import com.mod12.cabal.common.communication.link.Link;
import com.mod12.cabal.common.communication.link.LinkListener;
import com.mod12.cabal.common.communication.node.Server;
import com.mod12.cabal.common.communication.wireformats.CabalWireformatType;
import com.mod12.cabal.common.communication.wireformats.DeRegisterRequest;
import com.mod12.cabal.common.communication.wireformats.EndGame;
import com.mod12.cabal.common.communication.wireformats.FactionNamesResponse;
import com.mod12.cabal.common.communication.wireformats.NewFactionStepNotification;
import com.mod12.cabal.common.communication.wireformats.PickFactionRequest;
import com.mod12.cabal.common.communication.wireformats.PickFactionResponse;
import com.mod12.cabal.common.communication.wireformats.RegisterRequest;
import com.mod12.cabal.common.communication.wireformats.StartGame;
import com.mod12.cabal.common.communication.wireformats.StartMission;
import com.mod12.cabal.common.communication.wireformats.StartStep;
import com.mod12.cabal.common.communication.wireformats.UpdateWorld;
import com.mod12.cabal.common.communication.wireformats.Wireformat;
import com.mod12.cabal.common.dto.AgentDTO;
import com.mod12.cabal.common.dto.FactionDTO;
import com.mod12.cabal.common.dto.FactionPresenceDTO;
import com.mod12.cabal.common.dto.LocationDTO;
import com.mod12.cabal.common.dto.MissionConceptDTO;
import com.mod12.cabal.common.dto.MissionInstanceDTO;
import com.mod12.cabal.common.util.Constants;
import com.mod12.cabal.common.util.Tuple;
import com.mod12.cabal.server.core.faction.Faction;
import com.mod12.cabal.server.core.manager.AgentManager;
import com.mod12.cabal.server.core.manager.FactionManager;
import com.mod12.cabal.server.core.manager.FileManager;
import com.mod12.cabal.server.core.manager.ItemManager;
import com.mod12.cabal.server.core.manager.LocationManager;
import com.mod12.cabal.server.core.manager.MissionManager;
import com.mod12.cabal.server.core.manager.SkillManager;
import com.mod12.cabal.server.core.manager.TurnManager;
import com.mod12.cabal.server.core.mission.MissionInstance;
import com.mod12.cabal.server.core.victory.VictoryMonitor;


/**        12M0D12.com
 * 
 * 
 * Cabal: A game of power and intrigue
 * by Hamilton Reed & Kevin Kowalski
 * 2011 - 2012
 * 
 * v. 0.9
 */
public class ServerCabal extends Server implements Cabal {

	static boolean isWindows = ServerCabal.isWindows();
	public static final boolean DEBUG = true;
	
	/**************    MANAGERS    **************/
	private AgentManager agentManager;
	private FactionManager factionManager;
	private FileManager fileManager;
	private ItemManager itemManager;	
	private LocationManager worldManager;
	private MissionManager missionManager;
	private SkillManager skillManager;
	private TurnManager turnManager;
	
	private VictoryMonitor victoryMonitor;
	/**************    END MANAGERS    **************/
	
	private static ServerCabal game = null;
	private String scenario;
	private String location;
	private String imageLocation;
	private String timePeriod;	
	//TODO make the server send out time frame and hostname to client lobby
	
	private ServerCabal() {
		factionManager = FactionManager.getFactionManager();
		worldManager = LocationManager.getLocationManager();
		agentManager = AgentManager.getAgentManager();
		missionManager = MissionManager.getMissionManager();
		itemManager = ItemManager.getItemManager();
		skillManager = SkillManager.getSkillManager();
		turnManager = TurnManager.getTurnManager();
		fileManager = FileManager.getFileManager();
		skillManager = SkillManager.getSkillManager();		
		victoryMonitor = VictoryMonitor.getVictoryMonitor();		
	}
	
	// TODO load game constructor
	private ServerCabal(File filename) {
		this();
	}
	
	public boolean serverInit() {
		try {
			linkConnectionFactory = new LinkListener(this, portNumber);
			linkConnectionFactory.start();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static ServerCabal getNewCabal(File file) throws Exception {
		if (game == null) {
			game = new ServerCabal();
			game.fileManager.initalizeScenario(file);			
			game.formatForImageFile(file.getAbsolutePath());
		}
		
		return game;
	}
	
	public static ServerCabal loadCabal(File file) {
		if (game == null) {
			game = new ServerCabal(file);
		}
		
		return game;
	}
	
	public static ServerCabal getCabalInstance() {
		return game;
	}
	
	private void formatForImageFile(String absoluteLocation) {
		int lastIndex = absoluteLocation.lastIndexOf(File.separatorChar);
		String path = absoluteLocation.substring(0, lastIndex + 1);
		String name = absoluteLocation.substring(lastIndex + 1);
		name = name.substring(0, name.indexOf('.'));
		path = path + name + File.separatorChar;
		this.imageLocation = path;
	}
	
	public String getImageLocation() {
		return imageLocation;
	}
	
	public FactionDTO getCurrentStepFaction() {
		FactionDTO currentFaction = new FactionDTO(factionManager.getFaction(this.turnManager.getCurrentFaction()));
		return currentFaction;
	}
	
	public void setHumanPlayers(int value) {
		this.turnManager.setPlayers(value);
	}
		
	public void setHumanFaction(List<String> faction) {
		this.factionManager.setHumanFaction(faction);
	}
	
	public String getScenarioInfo(String choice) {
		String result = "";
		try{
			int select = Integer.parseInt(choice);
			if ((select > 0) && (select <= Constants.scenarios.length)) { //checks to see if the number corresponds to a valid scenario
				String scenario = "";
				if (isWindows == true) {
					scenario = Constants.SCENARIO_PATH + Constants.scenarios[select-1] + ".sin";  // this might cause headaches later on, keep on eye on this, if nothing else we need to make it none hardcoded
				}
				else{
					scenario = Constants.SCENPATH_LINUX + Constants.scenarios[select-1] + ".sin";
				}
				
														
				try {
					FileReader fin = new FileReader(scenario);
					BufferedReader bin = new BufferedReader(fin);
					String info = bin.readLine();						
					bin.close();
					fin.close();
					return Constants.formatInfo(info);					
					
				} catch (FileNotFoundException e) {
					//Catches an error when the scenario file is not found
					result = Constants.ERROR_FILE_LOAD_SCENARIO + scenario;
				}
				catch (IOException e) {
					// Catches an error when the scenario file is not properly formatted
					result = Constants.ERROR_FILE_SCENARIO_FORMAT + scenario;
				}
			}
			else{
				result = Constants.ERROR_INVALID_INPUT + choice;
			}
		}
		catch(NumberFormatException notInt) {
			result = Constants.ERROR_INVALID_INPUT + choice;
		}
		return result;
	}
	
	@Override
	public void deRegisterRequest(DeRegisterRequest request, Link link) {
		super.deRegisterRequest(request, link);
		factionManager.removePlayerHanlde(link.getLinkTo());
		if (true) { // always successful
			List<Tuple<String, String>> temp = this.getFactionsAndPlayerHandles();
			FactionNamesResponse broadcast = new FactionNamesResponse(temp);
			broadcastMessage(broadcast, link);
		}
	}
	
	public void factionNameRequest(Link link) {
		List<Tuple<String, String>> temp = this.getFactionsAndPlayerHandles();
		FactionNamesResponse message = new FactionNamesResponse(temp);
		link.sendMessage(message);
	}
	
	
	public void pickFactionRequest(PickFactionRequest message, Link link) {
		String desiredFaction = message.desiredFaction;
		String playerHandle = link.getLinkTo();
		boolean successful = factionManager.pickFaction(playerHandle, desiredFaction);
		
		PickFactionResponse response = new PickFactionResponse(successful);
		link.sendMessage(response);
		if (successful) {
			List<Tuple<String, String>> temp = this.getFactionsAndPlayerHandles();
			FactionNamesResponse broadcast = new FactionNamesResponse(temp);
			broadcastMessage(broadcast, link);
		} 
	}
	
	public void broadcastMessage(Wireformat message, Link... linksToIgnore) {
		List<Link> ignore = new LinkedList<Link>();
		for (Link link : linksToIgnore) {
			ignore.add(link);
		}
		
		for (Link link : getLinks()) {
			if (!ignore.contains(link)) {
				link.sendMessage(message);
			}
		}
	}
	
	public Link getLink(String handle){
		Link link = this.findLink(handle);
		return link;
	}
	
	public void notifyNextPlayer() {
		String name = turnManager.getCurrentFaction();
		Faction temp = factionManager.getFaction(name);
		Link link = getLink(temp.getPlayerHandle());
		
		UpdateWorld updateWorld = new UpdateWorld(worldManager.getAllLocationDTO());
		link.sendMessage(updateWorld);
		
		StartStep startStep = new StartStep(turnManager.getTurn(), new FactionDTO(temp), 
				this.getCurrentStepFactionMessages(), getMyActiveMissions(), 
				getMyAgents());
		link.sendMessage(startStep);
	}
	
	public void notifyEndStep() {
		String name = turnManager.getCurrentFaction();
		Faction temp = factionManager.getFaction(name);
		Link link = getLink(temp.getPlayerHandle());			
		
		FactionDTO currFaction = new FactionDTO(temp);
		NewFactionStepNotification message = new NewFactionStepNotification(turnManager.getTurn(), currFaction);
		
		broadcastMessage(message, link);
	}
	
	@Override
	public boolean startGame() {
		factionManager.randomizeOrder(); //the order the factions are in is randomized
		try {
			turnManager.setStep(factionManager.getFactions().size());
		} catch (Exception e) {
			return false;
		}		//the number of factions is used to set the number of stages in a turn
		turnManager.setCurrentFaction(factionManager.getFactions().get(0).getName());
		victoryMonitor.checkCurrentStandings();
		return true;
	}
	
	public void startDistributedGame() {
		boolean result = this.startGame();
		if (result == true) {
			// all update world
			UpdateWorld updateWorld = new UpdateWorld(worldManager.getAllLocationDTO());
			broadcastMessage(updateWorld);

			FactionDTO startingFaction = new FactionDTO(factionManager.getFaction(turnManager.getCurrentFaction()));
			
			// all start game
			StartGame broadcast = new StartGame(scenario, location, timePeriod, turnManager.getTurn(), 
					turnManager.getMaxTurn(), startingFaction, missionManager.getPossibleMissionsDTO());
			broadcastMessage(broadcast);
			
			// all get self initial starting data
			for (Link link : connectedNodes) {
				String factionName = factionManager.getFactionNameFromHandle(link.getLinkTo());
				StartStep startStep = new StartStep(turnManager.getTurn(), 
						factionManager.getFactionDTO(factionName), 
						this.getCurrentStepFactionMessages(), getMyActiveMissions(), 
						factionManager.getAgents(factionName));
				link.sendMessage(startStep);
			}
			
			// all get current step faction
			NewFactionStepNotification notify = new NewFactionStepNotification(turnManager.getTurn(), startingFaction);
			broadcastMessage(notify);

			// notify starting player
			notifyNextPlayer();
		}
	}
	
	public void notify(int wireformatType, byte[] wireformat, Link fromLink) {
		super.notify(wireformatType, wireformat, fromLink);
		
		if (wireformatType == CabalWireformatType.REGISTER_REQUEST) {						
			this.registerRequest(new RegisterRequest(wireformat), fromLink, scenario, location, timePeriod);
		} else if (wireformatType == CabalWireformatType.FACTION_NAMES_REQUEST) {
			this.factionNameRequest(fromLink);
		} else if (wireformatType == CabalWireformatType.PICK_FACTION_REQUEST) {
			this.pickFactionRequest(new PickFactionRequest(wireformat), fromLink);
		} else if (wireformatType == CabalWireformatType.GAME_START) {
			this.startDistributedGame();
		} else if (wireformatType == CabalWireformatType.END_STEP) {
			this.nextStep(true);
		} else if (wireformatType == CabalWireformatType.START_MISSION) {
			this.startMission(new StartMission(wireformat));
		}
	}
	
	
	
	//*****GETTERS AND SETTERS*****\\			
	

	

	public void setScenario(String name) {
		this.scenario = name;
	}
	
	protected String getScenario() {
		return this.scenario;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	protected String getLocation() {
		return this.location;
	}
	
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}
	
	public List<MissionInstanceDTO> getMyActiveMissions() {
		List<MissionInstanceDTO> missions = new LinkedList<MissionInstanceDTO>();
		for (MissionInstance mission : missionManager.getActiveMissions(turnManager.getCurrentFaction())) {
			missions.add(new MissionInstanceDTO(mission));
		}
		return missions;
	}
	
	protected String getTime() {
		return this.timePeriod;
	}
	
	protected static boolean isWindows() {
		return System.getProperty("os.name").startsWith("Windows");
	}

//	public List<Quad<String, Double, Double, Double>> getCurrentStandingsPercentages() {
//		victoryMonitor.checkCurrentStandings();
//		return victoryMonitor.getCurrentStandingsPercentages();
//	}
//	
//	public List<Quad<String, Integer, Double, Double>> getCurrentStandingsRawNumbers() {
//		victoryMonitor.checkCurrentStandings();
//		return victoryMonitor.getCurrentStandingsRawNumbers();
//	}

	public List<String> getFactionNames() {
		List<String> result = factionManager.getFactionNames();
		return result;
	}
	
	public List<Tuple<String, String>> getFactionsAndPlayerHandles() {
		return factionManager.getFactionsAndPlayerHandles();
	}

	private List<AgentDTO> getMyAgents() {
		return factionManager.getAgents(turnManager.getCurrentFaction());
	}
	
//	public List<MissionConcept> getPossibleMissions() {
//		List<MissionConcept> possibleMissions = missionManager.getPossibleMissions();
//		return possibleMissions;
//	}

	public boolean nextStep(boolean distributed) {
		turnManager.nextStep();
		boolean result = true;
		if (turnManager.gameOver()) {
			victoryMonitor.checkCurrentStandings();
			List<String> scoreboard = victoryMonitor.scoreboard();
			if (distributed){
				EndGame message = new EndGame(scoreboard);
				broadcastMessage(message);
			}
			else{
				return false; 
			}
			
		} else {
			if (distributed) {
				notifyEndStep();
				notifyNextPlayer();
			}
		}
		return true;
	}

    public Long getCurrentTurnValue() {
        return turnManager.getTurn();
    }

    public LocationDTO getTopLevel() {
        return new LocationDTO(worldManager.getTopLevel());
    }

	public List<String> getCurrentStepFactionMessages() {
		String faction = turnManager.getCurrentFaction();
		List<String> messages = factionManager.getMessagesForFaction(faction);
		return messages;
	}

	public List<String> getFactionsAtStage(String location) {
		return worldManager.getFactionsAtStage(location);
	}

	@Override
	public String getAgentInfo(String factionName, String agentName) {
		return factionManager.getAgentInfo(factionName, agentName);
	}

	@Override
	public LocationDTO getLocationDTO(String locationName) {
		return worldManager.getLocationDTO(locationName);
	}

	@Override
	public FactionDTO getMyFaction() {
		return factionManager.getFactionDTO(turnManager.getCurrentFaction());
	}

	@Override
	public int calculateAgentProbability(String faction, String agent, MissionConceptDTO missionConcept) {
		return factionManager.calculateAgentSuccessProbability(faction, agent, missionConcept);
	}

	@Override
	public void createMissionInstance(String factionName, MissionConceptDTO missionConcept, 
			String inStage, String agent, String target) {
		missionManager.createMissionInstance(factionName, missionConcept, inStage, agent, target);
	}

	@Override
	public void createMissionInstance(String factionName, MissionConceptDTO missionConcept, 
			String inStage, String agent) {
		missionManager.createMissionInstance(factionName, missionConcept, inStage, agent);
	}
	
	@Override
	public List<MissionConceptDTO> getMissions(List<String> missions) {
		return missionManager.getPossibleMissionsDTO();
	}
	
	private void startMission(StartMission message) {
		String factionName = turnManager.getCurrentFaction();
		String missionConceptName = message.missionConceptName;
		String inStage = message.inStage;
		String agent = message.agent;
		String target = message.target;
		
		if (target.equals(Wireformat.NULL)) {createMissionInstance(
				factionName, new MissionConceptDTO(
						missionManager.getMission(missionConceptName)), inStage, agent);
		}
		else {
			createMissionInstance(factionName, new MissionConceptDTO(
					missionManager.getMission(missionConceptName)), inStage, agent, target);
		}
	}

	@Override
	public List<FactionPresenceDTO> getFactionPresences(String locationName) {
		return worldManager.calculatePresences(locationName);
	}

	@Override
	public void quit() {
		prepareForExit();
	}

	@Override
	public long getMaxTurn() {
		return turnManager.getMaxTurn();
	}

	@Override
	public List<String> getScoreborad() {		
		return victoryMonitor.scoreboard();
	}

}
