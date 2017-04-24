package fr.univ_lorraine.battleship.model;

public class TestSeekThenDestroyCrossShooting extends TestShootingStrategy {

	@Override
	protected ShootingStrategy createShootingStrategy() {
		return new SeekThenDestroyCrossShooting();
	}

}
