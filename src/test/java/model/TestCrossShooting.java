package model;

public class TestCrossShooting extends TestShootingStrategy {

	@Override
	protected ShootingStrategy createShootingStrategy() {
		return new CrossShooting();
	}

}
