package fr.univ_lorraine.battleship.model;

import fr.univ_lorraine.battleship.model.Epoch;
import fr.univ_lorraine.battleship.model.EpochXX;

public class TestEpochXX extends TestEpoch {

	@Override
	protected Epoch createEpoch() {
		return new EpochXX();
	}

}
