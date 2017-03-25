package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public abstract class TestShootingStrategy {

	protected ShootingStrategy shootingStrategy;
	
	protected abstract ShootingStrategy createShootingStrategy();
	
	protected Sea sea;
	
	@Before
	public void setUp() {
		shootingStrategy = createShootingStrategy();
		sea = new Sea(new EpochXX());
	}
	
	@Test
	public void testPlayShootValidPosition() {
		Position shotPos = shootingStrategy.playShoot(sea);
		assertTrue("Le tir devrait être valide", sea.receiveShot(shotPos));
	}
}
