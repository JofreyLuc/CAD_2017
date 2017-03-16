package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestSea {

	private Sea sea;
	
	private final static Epoch EPOCH = new EpochXVI();
	
	@Before
	public void setUp() {
		sea = new Sea(EPOCH);
		sea.putNextShipToPlace();
	}

	@Test
	public void testIsSeaBoxFreeBoxOutOfBounds() {
		assertFalse("La case devrait être occupée car hors-limites", sea.isSeaBoxFree(new Position(-1, 0)));
	}
	
	@Test
	public void testIsSeaBoxFreeBoxFree() {
		assertTrue("La case devrait être libre", sea.isSeaBoxFree(new Position(0, 0)));
	}

	@Test
	public void testIsSeaBoxFreeBoxOccupied() {
		Ship ship = sea.getShipOnPlacing();
		ship.setPosition(new Position(0, 0));
		sea.validateShipPlacement();
		assertFalse("La case devrait être occupée", sea.isSeaBoxFree(new Position(0, 0)));
	}
	
}
