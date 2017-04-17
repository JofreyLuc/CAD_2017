package fr.univ_lorraine.battleship.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.univ_lorraine.battleship.model.EpochXX;
import fr.univ_lorraine.battleship.model.Position;
import fr.univ_lorraine.battleship.model.Sea;
import fr.univ_lorraine.battleship.model.ShootingStrategy;

/**
 * Classe pour les tests communs à toutes les stratégies de tir.
 */
public abstract class TestShootingStrategy {

	protected ShootingStrategy shootingStrategy;
		
	protected Sea sea;
	
	/**
	 * Crée la stratégie de tir à tester.
	 * @return La stratégie de tir à tester.
	 */
	protected abstract ShootingStrategy createShootingStrategy();
	
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
