package com.mod12.cabal.common.communication.wireformats;

import com.mod12.cabal.common.dto.FactionDTO;

public class NewFactionStepNotification extends Wireformat {

	public long currentTurn;
	public FactionDTO newFactionStep;
	
	//Constructor for sender
	public NewFactionStepNotification(long currentTurn, FactionDTO newFactionTurn) {
		messageType = CabalWireformatType.NEW_FACTION_STEP_NOTIFICATION;
		this.currentTurn = currentTurn;
		this.newFactionStep = newFactionTurn;
	}
	
	//Constructor for receiver
	public NewFactionStepNotification(byte[] bytes) {
		messageType = CabalWireformatType.NEW_FACTION_STEP_NOTIFICATION;
		unmarshall(bytes);
	}

	@Override
	public byte[] marshall() {
		String output = currentTurn + "";
		output += DELIM + newFactionStep.marshall();
		return output.getBytes();
	}

	@Override
	public void unmarshall(byte[] bytes) {
		String result = Wireformat.bytesToString(bytes);
		String[] parts = result.split(DELIM);
		currentTurn = Long.parseLong(parts[0]);
		newFactionStep = new FactionDTO(parts[1]);
	}

}
