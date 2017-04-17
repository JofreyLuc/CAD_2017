package fr.univ_lorraine.battleship.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.univ_lorraine.battleship.model.EpochXX;
import fr.univ_lorraine.battleship.model.Position;
import fr.univ_lorraine.battleship.model.Sea;
import fr.univ_lorraine.battleship.model.ShootingStrategy;

/**
 * Classe pour les tests communs � toutes les strat�gies de tir.
 */
public abstract class TestShootingStrategy {

	protected ShootingStrategy shootingStrategy;
		
	protected Sea sea;
	
	/**
	 * Cr�e la strat�gie de tir � tester.
	 * @return La strat�gie de tir � tester.
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
		assertTrue("Le tir devrait �tre valide", sea.receiveShot(shotPos));
	}
	
}
