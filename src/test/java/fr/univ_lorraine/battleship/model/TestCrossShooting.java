package fr.univ_lorraine.battleship.model;

import fr.univ_lorraine.battleship.model.CrossShooting;
import fr.univ_lorraine.battleship.model.ShootingStrategy;

public class TestCrossShooting extends TestShootingStrategy {

	@Override
	protected ShootingStrategy createShootingStrategy() {
		return new CrossShooting();
	}

}
