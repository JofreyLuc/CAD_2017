package model;

import static org.junit.Assert.*;

import model.Ship.Orientation;

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

	//Tests rotateShip
	@Test
	public void testRotateShipHorizontal() {
		Ship shipOnPlacing = player.getSelfGrid().getShipOnPlacing();
		shipOnPlacing.setOrientation(Orientation.HORIZONTAL);
		player.rotateShip();
		assertEquals("Le bateau devrait �tre orient� verticalement", Orientation.VERTICAL, shipOnPlacing.getOrientation());
	}

	@Test
	public void testRotateShipVertical() {
		Ship shipOnPlacing = player.getSelfGrid().getShipOnPlacing();
		shipOnPlacing.setOrientation(Orientation.VERTICAL);
		player.rotateShip();
		assertEquals("Le bateau devrait �tre orient� verticalement", Orientation.HORIZONTAL, shipOnPlacing.getOrientation());
	}
	
	// Tests placeShip
	@Test
	public void testPlaceShipOutOfBounds() {
		boolean validPos = player.placeShip(new Position(-1, 0));
		assertFalse("La position devrait �tre invalide", validPos);
	}

	@Test
	public void testPlaceShipFirstShip() {
		boolean validPos = player.placeShip(new Position(0, 0));
		assertTrue("La position devrait �tre valide", validPos);
	}
	
	@Test
	public void testPlaceShipShipOnOtherShip() {
		Position pos = new Position(0, 0);
		player.placeShip(pos);
		player.getSelfGrid().putNextShipToPlace();
		boolean validPos = player.placeShip(pos);
		assertFalse("La position devrait �tre invalide", validPos);
	}
	
}
