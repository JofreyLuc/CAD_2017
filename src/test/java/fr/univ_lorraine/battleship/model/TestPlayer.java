package fr.univ_lorraine.battleship.model;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import fr.univ_lorraine.battleship.model.Epoch;
import fr.univ_lorraine.battleship.model.EpochXX;
import fr.univ_lorraine.battleship.model.Player;
import fr.univ_lorraine.battleship.model.Position;
import fr.univ_lorraine.battleship.model.Sea;
import fr.univ_lorraine.battleship.model.Ship;
import fr.univ_lorraine.battleship.model.Ship.Orientation;

public class TestPlayer {

	private Player player;
	
	@Before
	public void setUp() {
		Epoch epoch = new EpochXX();
		player = new Player(new Sea(epoch), new Sea(epoch));
		player.getSelfGrid().putNextShipToPlace();
	}

	//Tests rotateShip
	@Test
	public void testRotateShipHorizontal() {
		Ship shipOnPlacing = player.getSelfGrid().getShipOnPlacing();
		shipOnPlacing.setOrientation(Orientation.HORIZONTAL);
		player.rotateShip();
		assertEquals("Le bateau devrait être orienté verticalement", Orientation.VERTICAL, shipOnPlacing.getOrientation());
	}

	@Test
	public void testRotateShipVertical() {
		Ship shipOnPlacing = player.getSelfGrid().getShipOnPlacing();
		shipOnPlacing.setOrientation(Orientation.VERTICAL);
		player.rotateShip();
		assertEquals("Le bateau devrait être orienté verticalement", Orientation.HORIZONTAL, shipOnPlacing.getOrientation());
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
