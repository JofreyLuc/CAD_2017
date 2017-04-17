package fr.univ_lorraine.battleship.model;

import fr.univ_lorraine.battleship.model.RandomShooting;
import fr.univ_lorraine.battleship.model.ShootingStrategy;

public class TestRandomShooting extends TestShootingStrategy {

	@Override
	protected ShootingStrategy createShootingStrategy() {
		return new RandomShooting();
	}

}
