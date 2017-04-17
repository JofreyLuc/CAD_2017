package fr.univ_lorraine.battleship.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.univ_lorraine.battleship.model.Epoch;

/**
 * Classe pour les tests communs à toutes les époques.
 */
public abstract class TestEpoch {
	
	protected Epoch epoch;
	
	/**
	 * Crée l'époque à tester.
	 * @return L'époque à tester.
	 */
	protected abstract Epoch createEpoch();
	
	@Before
	public void setUp() {
		epoch = createEpoch();
	}
	
	@Test
	public void testTakeDamageNoDamage() {
		int size = 5;
		int hitCount = 0;
		boolean res = epoch.takeDamage(size, hitCount);
		assertFalse("Devrait être en vie", res);
	}
	
	@Test
	public void testTakeDamageFullDamage() {
		int size = 5;
		int hitCount = 5;
		boolean res = epoch.takeDamage(size, hitCount);
		assertTrue("Devrait être détruit", res);
	}
	
}
