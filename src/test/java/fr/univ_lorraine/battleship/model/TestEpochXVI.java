package fr.univ_lorraine.battleship.model;

import fr.univ_lorraine.battleship.model.Epoch;
import fr.univ_lorraine.battleship.model.EpochXVI;

public class TestEpochXVI extends TestEpoch {

	@Override
	protected Epoch createEpoch() {
		return new EpochXVI();
	}

}
