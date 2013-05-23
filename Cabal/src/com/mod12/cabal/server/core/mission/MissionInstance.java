package com.mod12.cabal.server.core.mission;

import java.util.List;

import com.mod12.cabal.common.util.Formatter;
import com.mod12.cabal.server.core.faction.Faction;
import com.mod12.cabal.server.core.item.UniqueItem;
import com.mod12.cabal.server.core.location.Stage;
import com.mod12.cabal.server.core.manager.AgentManager;
import com.mod12.cabal.server.core.manager.FactionManager;
import com.mod12.cabal.server.core.manager.ItemManager;
import com.mod12.cabal.server.core.manager.MissionManager;
import com.mod12.cabal.server.core.person.agent.Agent;
import com.mod12.cabal.server.core.person.agent.condition.Condition;

public class MissionInstance {

	private MissionConcept mission;
	private Stage stage;
	private Agent agent;
	private int turnsLeft;
	private String target;
	
	public MissionInstance(MissionConcept mission, Stage stage, Agent agent) {
		this.mission = mission;
		this.stage = stage;
		this.agent = agent;
		this.turnsLeft = mission.turnsToComplete();
		this.agent.setBusy(true);
	}
	
	public MissionInstance(MissionConcept mission, Stage stage, Agent agent, String target) {
		this(mission, stage, agent);
		this.target = target;
	}
	
	public void nextTurn() {
		turnsLeft--;
		
		if (turnsLeft == 0) {
			execute();
		}
	}
	
	private void execute() {
		String result = "";
		String message = "";
		boolean success = agent.executeSkill(mission.getSuccessLogic().getAssociatedSkill(), 
				mission.getSuccessLogic().getSkillCheckValue());
		if (success) {
			executeMissionLogic(mission.getSuccessLogic());
			result = Message.SUCCESSFUL;
			message = mission.getSuccessLogic().getMessage();
			message = Formatter.format(message, new Object[] {agent.getName().toString()} );
		}
		else if (agent.executeSkill(mission.getFailLogic().getAssociatedSkill(), mission.getFailLogic().getSkillCheckValue())) {
			result = Message.UNSUCCESSFUL;
		}
		else { // crit fail!
			executeMissionLogic(mission.getFailLogic());
			result = Message.CRIT_FAIL;
			message = mission.getFailLogic().getMessage();
			message = Formatter.format(message, new Object[] {agent.getName().toString()} );
		}
		
		agent.setBusy(false);
		informOfMissionExecution(mission.getName(), result, message);
	}
	
	private void informOfMissionExecution(String missionName, String successful, String flavorMessage) {
		String message = missionName + Message.MISSION_EXECUTED + successful + "\n" + flavorMessage;
		FactionManager.getFactionManager().addMessageForFaction(agent.getFaction().getName(), message);
		MissionManager.getMissionManager().addCompletedMission(this);
	}
	
	// TODO TEST ALL MISSION LOGICS!!!, create move mission??
	private void executeMissionLogic(MissionLogic missionLogic) {
		LogicKeyword keyworld = missionLogic.getKeyword();
//		if (LogicKeyword.MOVE == keyworld) {
//			move(missionLogic);
//		}
		if (LogicKeyword.RECRUIT == keyworld) {
			recruit(missionLogic);
		}
		else if (LogicKeyword.SCOUT == keyworld) {
			scout(missionLogic);
		}
		else if (LogicKeyword.ADJ_PRES == keyworld) {
			adjustPresence(missionLogic);
		}
		else if (LogicKeyword.INC_PHYSICAL_HEALTH == keyworld) {
			adjustHealth(true, true, missionLogic);
		}
		else if (LogicKeyword.DEC_PHYSICAL_HEALTH == keyworld) {
			adjustHealth(true, false, missionLogic);
		}
		else if (LogicKeyword.INC_MENTAL_HEALTH == keyworld) {
			adjustHealth(false, true, missionLogic);
		}
		else if (LogicKeyword.DEC_MENTAL_HEALTH == keyworld) {
			adjustHealth(false, false, missionLogic);
		}
		else if (LogicKeyword.INC_MONEY == keyworld || LogicKeyword.DEC_MONEY == keyworld) {
			adjustMoney(missionLogic);
		}
		else if (LogicKeyword.STEAL_ITEM == keyworld) {
			stealItem(missionLogic);
		}
		else if (LogicKeyword.GEN_ITEM == keyworld) {
			generateItem(missionLogic);
		}
		else if (LogicKeyword.DES_ITEM == keyworld) {
			destroyItem(missionLogic);
		}
		else if (LogicKeyword.INC_INFAMY == keyworld || LogicKeyword.DEC_INFAMY == keyworld) {
			adjustPrestige(true, missionLogic);
		}
		else if (LogicKeyword.INC_CULTURE == keyworld || LogicKeyword.DEC_CULTURE == keyworld) {
			adjustPrestige(false, missionLogic);
		}
		
	}

//	private void move(MissionLogic missionLogic) {
//		Stage destination = (Stage) target;
//		agent.moveLocation(destination);
//	}

	private void recruit(MissionLogic missionLogic) {
		Faction targetFaction = getTargetFaction();
		List<Agent> newRecruits = AgentManager.getAgentManager().generateAgents(targetFaction.getName(),
				mission.getDifficulty(), missionLogic.getKeywordIntValue());
		targetFaction.addMembers(newRecruits);
	}
	
	private void scout(MissionLogic missionLogic) {
		
	}

	private void adjustPresence(MissionLogic missionLogic) {
		stage.changePresence(agent.getFaction().getName(), missionLogic.getKeywordDoubleValue(), target);
	}
	
	private void adjustHealth(boolean physical, boolean heal, MissionLogic missionLogic) {
		int value = missionLogic.getKeywordIntValue();
		Condition condition = agent.getCondition();
		if (physical) {
			if (heal) {
				condition.receivePhysicalHeal(value);
			} else {
				condition.takePhysicalDamage(value);
			}
		} else {
			if (heal) {
				condition.receiveMentalHeal(value);
			} else {
				condition.takePhysicalDamage(value);
			}
		}
	}
	
	private void adjustMoney(MissionLogic missionLogic) {
		int value = missionLogic.getKeywordIntValue();
		Faction targetFaction = getTargetFaction();
		targetFaction.getWealth().updateMoney(value);
	}
	
	private void stealItem(MissionLogic missionLogic) {
		Faction targetFaction = getTargetFaction();
		int id = missionLogic.getKeywordIntValue();
		UniqueItem item = targetFaction.stealUniqueItem(id);
		agent.getFaction().addUniqueItem(item);
	}
	
	private void generateItem(MissionLogic missionLogic) {
		int value = missionLogic.getKeywordIntValue();
		Faction targetFaction = getTargetFaction();
		targetFaction.addItemsToStockpile(ItemManager.getItemManager().getRandomItems(value));
	}
	
	private void destroyItem(MissionLogic missionLogic) {
		int value = missionLogic.getKeywordIntValue();
		Faction targetFaction = getTargetFaction();
		targetFaction.removeItemsFromStockpile(value);
	}
	
	private void adjustPrestige(boolean infamy, MissionLogic missionLogic) {
		int value = missionLogic.getKeywordIntValue();
		Faction targetFaction = getTargetFaction();
		targetFaction.adjustPresence(infamy, value);
	}
	
	private Faction getTargetFaction() {
		Faction faction;
		if (target != null) {
			faction = FactionManager.getFactionManager().getFaction(target);
		} else {
			faction = agent.getFaction();
		}
		return faction;
	}
	
	public String getInfo() {
		String output = "";
		output += mission.getName() + " at " + stage.getName() + " by Agent " + agent.getName() + 
				". Turns left: " + turnsLeft;
		return output;
	}
	
	public MissionConcept getMissionConcept() {
		return mission;
	}

	public Stage getStage() {
		return stage;
	}

	public Agent getAgent() {
		return agent;
	}

	public int turnsLeft() {
		return turnsLeft;
	}
	
}
