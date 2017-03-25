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

	// Tests isSeaBoxFree
	@Test
	public void testIsSeaBoxFreeBoxOutOfBounds() {
		assertFalse("La case devrait �tre occup�e car hors-limites", sea.isSeaBoxFree(new Position(-1, 0)));
	}
	
	@Test
	public void testIsSeaBoxFreeBoxFree() {
		assertTrue("La case devrait �tre libre", sea.isSeaBoxFree(new Position(0, 0)));
	}

	@Test
	public void testIsSeaBoxFreeBoxOccupied() {
		Ship ship = sea.getShipOnPlacing();
		ship.setPosition(new Position(0, 0));
		sea.validateShipPlacement();
		assertFalse("La case devrait �tre occup�e", sea.isSeaBoxFree(new Position(0, 0)));
	}
	
	// Tests receiveShot
	public void testReceiveShotOutOfBounds() {
		boolean res = sea.receiveShot(new Position(100, 200));
		assertFalse("Doit �tre d�clar� invalide", res);
	}
	
	public void testReceiveShotAlreadyShot() {
		Position pos = new Position(0, 0);
		sea.receiveShot(pos);
		boolean res = sea.receiveShot(pos);
		assertFalse("Doit �tre d�clar� invalide", res);
	}
	
	public void testReceiveShotValid() {
		boolean res = sea.receiveShot(new Position(0, 0));
		assertTrue("Doit �tre d�clar� valide", res);
	}
	
	// Tests getAllNormalPositions
	@Test
	public void testGetAllNormalPositionsAtBeginning() {
		assertEquals("Devrait contenir toutes les positions", sea.getGridHeight()*sea.getGridWidth(), sea.getAllNormalPositions().size());
	}
	
	@Test
	public void testGetAllNormalPositionsWithOneTouched() {
		Position touchedPos = new Position(0, 0);
		sea.getShipOnPlacing().setPosition(touchedPos);
		sea.receiveShot(touchedPos);
		assertFalse("Cette position ne devrait pas �tre normale", sea.getAllNormalPositions().contains(touchedPos));
		assertEquals("Le nombre de positions normales est incorrect", sea.getGridHeight()*sea.getGridWidth()-1, sea.getAllNormalPositions().size());
	}
	
	@Test
	public void testGetAllNormalPositionsWithOneShot() {
		Position shotPos = new Position(0, 0);
		sea.receiveShot(shotPos);
		assertFalse("Cette position ne devrait pas �tre normale", sea.getAllNormalPositions().contains(shotPos));
		assertEquals("Le nombre de positions normales est incorrect", sea.getGridHeight()*sea.getGridWidth()-1, sea.getAllNormalPositions().size());
	}
		
	@Test
	public void testGetAllNormalPositionsWithMultipleShot() {
		Position[] positions = { new Position(0, 0), new Position(5, 6), new Position(3, 4), new Position(7, 0)};
		for (Position pos : positions)
			sea.receiveShot(pos);
		// On regarde si une des positions des tirs est contenue dans les positions normales
		boolean containsOne = false;
		int i = 0;
		while (i < positions.length && !containsOne){
			containsOne = sea.getAllNormalPositions().contains(positions[i]);
			i++;
		}
		assertFalse("Ces positions ne devraient pas �tre normale", containsOne);
		assertEquals("Le nombre de positions normales est incorrect", sea.getGridHeight()*sea.getGridWidth()-positions.length, sea.getAllNormalPositions().size());
	}
	
	// Tests isShipOnPlacingInValidPosition
	@Test
	public void testisShipOnPlacingInValidPositionOutOfBounds() {
		sea.getShipOnPlacing().setPosition(new Position(-1, 0));
		boolean validPos = sea.isShipOnPlacingInValidPosition();
		assertFalse("La position devrait �tre invalide", validPos);
	}

	@Test
	public void testPlaceShipFirstShip() {
		sea.getShipOnPlacing().setPosition(new Position(0, 0));
		boolean validPos = sea.isShipOnPlacingInValidPosition();
		assertTrue("La position devrait �tre valide", validPos);
	}
	
	@Test
	public void testPlaceShipShipOnOtherShip() {
		Position pos = new Position(0, 0);
		sea.getShipOnPlacing().setPosition(pos);
		sea.validateShipPlacement();
		sea.putNextShipToPlace();
		sea.getShipOnPlacing().setPosition(pos);
		boolean validPos = sea.isShipOnPlacingInValidPosition();
		assertFalse("La position devrait �tre invalide", validPos);
	}

}
