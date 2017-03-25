package model;

public class TestRandomShooting extends TestShootingStrategy {

	@Override
	protected ShootingStrategy createShootingStrategy() {
		return new RandomShooting();
	}

}
