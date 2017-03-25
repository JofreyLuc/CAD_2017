package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestPlayer {

	private Player player;
	
	@Before
	public void setUp() {
		Epoch epoch = new EpochXX();
		player = new Player(new Sea(epoch), new Sea(epoch));
		player.getSelfGrid().putNextShipToPlace();
	}

	// Tests placeShip
	@Test
	public void testPlaceShipOutOfBounds() {
		boolean validPos = player.placeShip(new Position(-1, 0));
		assertFalse("La position devrait être invalide", validPos);
	}

	@Test
	public void testPlaceShipFirstShip() {
		boolean validPos = player.placeShip(new Position(0, 0));
		assertTrue("La position devrait être valide", validPos);
	}
	
	@Test
	public void testPlaceShipShipOnOtherShip() {
		Position pos = new Position(0, 0);
		player.placeShip(pos);
		player.getSelfGrid().putNextShipToPlace();
		boolean validPos = player.placeShip(pos);
		assertFalse("La position devrait être invalide", validPos);
	}
	
}
