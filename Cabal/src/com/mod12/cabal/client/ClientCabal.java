package com.mod12.cabal.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.mod12.cabal.common.Cabal;
import com.mod12.cabal.common.communication.link.Link;
import com.mod12.cabal.common.communication.node.Node;
import com.mod12.cabal.common.communication.wireformats.CabalWireformatType;
import com.mod12.cabal.common.communication.wireformats.DeRegisterRequest;
import com.mod12.cabal.common.communication.wireformats.DeRegisterResponse;
import com.mod12.cabal.common.communication.wireformats.EndGame;
import com.mod12.cabal.common.communication.wireformats.EndStep;
import com.mod12.cabal.common.communication.wireformats.FactionNamesRequest;
import com.mod12.cabal.common.communication.wireformats.FactionNamesResponse;
import com.mod12.cabal.common.communication.wireformats.NewFactionStepNotification;
import com.mod12.cabal.common.communication.wireformats.PickFactionRequest;
import com.mod12.cabal.common.communication.wireformats.PickFactionResponse;
import com.mod12.cabal.common.communication.wireformats.RegisterRequest;
import com.mod12.cabal.common.communication.wireformats.RegisterResponse;
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
import com.mod12.cabal.common.dto.StageDTO;
import com.mod12.cabal.common.ui.graphics.GameMenu;
import com.mod12.cabal.common.ui.graphics.Lobby;
import com.mod12.cabal.common.util.Formatter;
import com.mod12.cabal.common.util.Tuple;

public class ClientCabal extends Node implements Cabal {
	
	private String enteredId;
	private String serverIp;
	private int serverPortNumber;
	private Link serverLink;
	private boolean gameHost;
	private Lobby lobbyFrame;
	private GameMenu gameMenu;

	private boolean receivedInitialStartData = false;
	
	/******   GAME DATA   ******/
	private boolean myTurn;
	private String hostHandle = "";
	private String scenario = "";
	private String location = "";
	private String imageLocation;
	private String timePeriod;
	private long currentTurn;
	private long maxTurn;
	private FactionDTO self;
	private List<String> messages;
	private List<LocationDTO> world;
	private List<MissionConceptDTO> missionConcepts;
	private List<MissionInstanceDTO> activeMissions;
	private List<AgentDTO> agents;
	private FactionDTO currentFaction;
	
	public ClientCabal(int selfPort, String enteredId, String serverIp, int serverPortNumber) throws Exception {
		printHostname();
		this.portNumber = selfPort;
		this.enteredId = enteredId;
		this.serverIp = serverIp;
		this.serverPortNumber = serverPortNumber;
		this.portNumber = selfPort;
		this.gameHost = false;
		try {
			registerRequest();
		} catch (UnknownHostException e) {
			System.out.println(Message.SERVER_CONNECTION_FAIL);
			e.printStackTrace();
			throw e;
		}
	}
	
	public ClientCabal(String enteredId, String serverIp, int serverPortNumber) throws Exception {
		this(serverPortNumber + 1, enteredId, serverIp, serverPortNumber);
		this.gameHost = true;
	}
	
	public void setLobby(Lobby frame) {
		lobbyFrame = frame;
		lobbyFrame.drawScenarioInfo(scenario, location, timePeriod, hostHandle);
	}
	
	public void setGameMenu(GameMenu gm){
		gameMenu = gm;
	}
	
	private void prepareForExit() {
		this.serverLink.exit();
		System.exit(0);
	}
	
	@Override
	public void notify(int wireformatType, byte[] wireformat, Link fromLink) {		
		if (wireformatType == CabalWireformatType.REGISTER_RESPONSE) {			
			this.registerResponse( new RegisterResponse(wireformat), fromLink);
		} else if (wireformatType == CabalWireformatType.DE_REGISTER_RESPONSE) {
			this.deRegisterResponse(new DeRegisterResponse(wireformat), fromLink);
		} else if (wireformatType == CabalWireformatType.FACTION_NAMES_RESPONSE) {
			this.factionNamesResponse(new FactionNamesResponse(wireformat), fromLink);
		} else if (wireformatType == CabalWireformatType.PICK_FACTION_RESPONSE) {
			this.pickFactionResponse(new PickFactionResponse(wireformat), fromLink);
		} else if (wireformatType == CabalWireformatType.GAME_START) {
			this.startGameWindow(new StartGame(wireformat));
		} else if (wireformatType == CabalWireformatType.START_STEP) {
			this.startStep(new StartStep(wireformat));
		} else if (wireformatType == CabalWireformatType.UPDATE_WORLD) {
			this.updateWorld(new UpdateWorld(wireformat));
		} else if (wireformatType == CabalWireformatType.NEW_FACTION_STEP_NOTIFICATION) {
			this.updateGameMenu(new NewFactionStepNotification(wireformat));
		} else if (wireformatType == CabalWireformatType.END_GAME) {
			this.endGame(new EndGame(wireformat));
		}
	}
	
	private void endGame(EndGame message) {		
		List<String> scoreboard = message.scoreboad;
		gameMenu.createVictory(scoreboard);
		
	}
	
	private void updateGameMenu(NewFactionStepNotification message) {
		this.currentTurn = message.currentTurn;
		this.currentFaction = message.newFactionStep;
		gameMenu.updateLabels();
		enableScreen(false);
	}

	private void registerRequest() throws UnknownHostException {
		serverLink = new Link(this, serverIp, serverPortNumber, serverIp);
		serverLink.init();
		ipAddress = InetAddress.getLocalHost().getHostAddress();
		RegisterRequest register = new RegisterRequest(ipAddress, portNumber, enteredId);
		serverLink.sendMessage(register);
	}
	
	public void deRegisterRequest() {
		DeRegisterRequest request = new DeRegisterRequest(ipAddress, portNumber, enteredId);
		serverLink.sendMessage(request);
	}

	public void registerResponse(RegisterResponse response, Link link) {
		System.out.println(Formatter.format(Message.REGISTER_RESPONSE, 
				new Object[] {response.getStatus(), response.additionalInfo}));
		this.scenario = response.sinName;
		this.location = response.location;
		this.timePeriod = response.timePeriod;
		this.hostHandle = response.hostHandle;
		if (response.additionalInfo != null && !response.additionalInfo.isEmpty()){
			this.enteredId = response.additionalInfo;
		}		
		if (response.statusCode == RegisterResponse.FAILURE) {
			prepareForExit();
		}
	}
	
	public void deRegisterResponse(DeRegisterResponse response, Link link) {
		System.out.println(Formatter.format(Message.DEREGISTER_RESPONSE, 
				new Object[] {response.getStatus(), response.additionalInfo}));
		
		if (response.statusCode == DeRegisterResponse.SUCCESS) {
			prepareForExit();
			lobbyFrame.quitGame();
		}
	}
	
	public void requestFactionNames() {
		FactionNamesRequest message = new FactionNamesRequest();
		serverLink.sendMessage(message);
	}

	public void factionNamesResponse(FactionNamesResponse message, Link link) {		
		List<String> unchosen = new ArrayList<String>();
		List<String> chosen = new ArrayList<String>();
		for (Tuple<String, String> tuple : message.getList()){
			//this is an unchosen faction
			if (tuple.y.equals("")){
				unchosen.add(tuple.x);
			}
			else{
				String print = tuple.x + com.mod12.cabal.common.ui.graphics.Message.FACTION_HANDLE_SEPARATOR + tuple.y;
				chosen.add(print);
			}
		}
		lobbyFrame.setAvailableFactions(unchosen);
		lobbyFrame.setChosenFactions(chosen);
		lobbyFrame.populateList();
	}

	public void pickFactionRequest(String selectedFaction) {
		PickFactionRequest message = new PickFactionRequest(selectedFaction);
		serverLink.sendMessage(message);
	}
	
	public void pickFactionResponse(PickFactionResponse message, Link link) {
		boolean success = message.successful;
		
		lobbyFrame.ownFaction(success);
	}
	
	public String getID() {
		return enteredId;
	}
	
	public String getIP() {
		return ipAddress;
	}
	
	public boolean gameHost() {
		return gameHost;
	}
	
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
	
	@Override
	public String getImageLocation() {
		return imageLocation;
	}

	@Override
	public boolean startGame() {
		StartGame message = new StartGame();
		serverLink.sendMessage(message);
		return true;
	}
	
	public void updateWorld(UpdateWorld message) {
		world = message.world;
	}
	
	public void startGameWindow(StartGame message) {
		scenario = message.scenario;
		location = message.location;
		timePeriod = message.timePeriod;
		currentTurn = message.turn;
		maxTurn = message.maxTurn;
		currentFaction = message.startingFaction;
		missionConcepts = message.allMissions;
		
		for (MissionConceptDTO dto : missionConcepts) {
			System.out.println("MissionDTO " + dto.getName() + " requires target " + dto.requiresTarget());
		}
		
		Lobby lobby = (Lobby) lobbyFrame;
		lobby.gameStarted();
		enableScreen(false);
		
		// a fail safe if the time between the start game message and first star step is human noticeable
		gameMenu.setEnabled(false);
	}
	
	// TODO possibly notify user (in a fun way!) that it is there turn
	private void startStep(StartStep message) {
		self = message.faction;
		currentTurn = message.currentTurn;
		messages = message.messages;
		activeMissions = message.activeMissions;
		agents = message.agents;
		currentFaction = self;
		
		enableScreen(true);
		gameMenu.displayMessages();
		
		// a fail safe if the time between the start game message and first star step is human noticeable 
		if (!receivedInitialStartData) {
			gameMenu.setEnabled(true);
			receivedInitialStartData = true;
		}
	}
	
	@Override
	public boolean nextStep(boolean distributed) {
		serverLink.sendMessage(new EndStep());
		enableScreen(false);
		return true;
	}
	
	private void enableScreen(boolean myTurn) {
		this.myTurn = myTurn;
		gameMenu.enableScreens(myTurn);
		gameMenu.updateLabels();
	}

	@Override
	public FactionDTO getCurrentStepFaction() {
		return currentFaction;
		
	}

	@Override
	public Long getCurrentTurnValue() {
		return currentTurn;
	}

	@Override
	public List<String> getCurrentStepFactionMessages() {
		return messages;
	}

	@Override
	public LocationDTO getTopLevel() {
		for (LocationDTO location : world) {
			if (location.getParent().equals(Wireformat.NULL)) return location;
		}
		return null;
	}

	@Override
	public List<String> getFactionsAtStage(String name) {
		StageDTO stage = (StageDTO) getLocationDTO(name);
		List<String> factions = new LinkedList<String>();
		List<Tuple<String, Double>> factionPresences = stage.getFactionPresences();
		for (Tuple<String, Double> fp : factionPresences) {
			factions.add(fp.x);
		}
		return factions;
	}

	@Override
	public List<MissionInstanceDTO> getMyActiveMissions() {
		return activeMissions;
	}

	@Override
	public String getAgentInfo(String factionName, String agentName) {
		return getAgentDTO(agentName).getInfo();
	}
	
	public AgentDTO getAgentDTO(String name) {
		for (AgentDTO agent : agents) {
			if (agent.getName().equals(name)) return agent;
		}
		return null;
	}

	@Override
	public LocationDTO getLocationDTO(String name) {
		for (LocationDTO location : world) {
			if (location.getName().equals(name)) return location;
		}
		return null;
	}
	
	@Override
	public FactionDTO getMyFaction() {
		return self;
	}

	public boolean myTurn() {
		return myTurn;
	}

	@Override
	public int calculateAgentProbability(String faction, String agentName, MissionConceptDTO missionConcept) {
		AgentDTO agent = getAgentDTO(agentName);
		return agent.calculateProbably(missionConcept.getPassSkill(), missionConcept.getPassSkillCheck());
	}

	@Override
	public void createMissionInstance(String factionName, MissionConceptDTO missionConcept, 
			String inStage, String agent, String target) {
		AgentDTO agentDTO = getAgentDTO(agent);
		agentDTO.busy();
		self.addBusyAgent(agent);
		
		StartMission message = new StartMission(missionConcept.getName(), inStage, agent, target);
		serverLink.sendMessage(message);		
		this.addInstanceToMenu(missionConcept.getName(), inStage, agent, missionConcept.getTurnsToComplete(), missionConcept.getInfo());
	}

	@Override
	public void createMissionInstance(String factionName, MissionConceptDTO missionConcept,
			String inStage, String agent) {
		AgentDTO agentDTO = getAgentDTO(agent);
		agentDTO.busy();
		self.addBusyAgent(agent);
		
		StartMission message = new StartMission(missionConcept.getName(), inStage, agent);
		serverLink.sendMessage(message);
		this.addInstanceToMenu(missionConcept.getName(), inStage, agent, missionConcept.getTurnsToComplete(), missionConcept.getInfo());
	}
	
	private void addInstanceToMenu(String missionConcept, String stage, String agent, int turnsLeft, String info){
		System.out.println("MC: " + missionConcept);
		System.out.println("Stage: " + stage);
		System.out.println("Agent:" + agent);
		System.out.println("TL: " + turnsLeft);
		System.out.println("Info: " + info);
		MissionInstanceDTO temp = new MissionInstanceDTO(missionConcept, stage, agent, turnsLeft, info);
		this.activeMissions.add(temp);
	}

	@Override
	public List<MissionConceptDTO> getMissions(List<String> missions) {
		List<MissionConceptDTO> missonConcepts = new LinkedList<MissionConceptDTO>();
		for (String mission : missions) {
			MissionConceptDTO dto = getMissionConceptDTO(mission);
			if (dto != null) missonConcepts.add(dto);
		}
		return missonConcepts;
	}
	
	public MissionConceptDTO getMissionConceptDTO(String name) {
		for (MissionConceptDTO mission : missionConcepts) {
			if (mission.getName().equals(name)) return mission;
		}
		return null;
	}

	@Override
	public List<FactionPresenceDTO> getFactionPresences(String locationName) {
		return LocationHelper.createFactionPresenceDTO(world, locationName);
	}

	public String getScenario() {		
		return this.scenario;
	}
	
	public String getLocation(){
		return this.location;
	}

	@Override
	public long getMaxTurn() {
		return maxTurn;
	}
	
	@Override
	public void quit() {
		prepareForExit();
	}

	@Override
	public List<String> getScoreborad() {
		// TODO Auto-generated method stub
		return null;
	}
}
