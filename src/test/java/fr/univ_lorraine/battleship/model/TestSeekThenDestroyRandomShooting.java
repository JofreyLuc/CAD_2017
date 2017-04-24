package fr.univ_lorraine.battleship.model;

public class TestSeekThenDestroyRandomShooting extends TestShootingStrategy {

	@Override
	protected ShootingStrategy createShootingStrategy() {
		return new SeekThenDestroyRandomShooting();
	}

}
