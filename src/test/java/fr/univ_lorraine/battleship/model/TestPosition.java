package fr.univ_lorraine.battleship.model;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.univ_lorraine.battleship.model.Position;

public class TestPosition {

	// Tests isOutOfBounds
	@Test
	public void testIsOutOfBoundsStrictInsideBounds() {
		Position pos = new Position(0, 0);
		assertFalse("Doit être dans les limites", pos.isOutOfBounds(0, 5, 0, 5));
	}
	
	@Test
	public void testIsOutOfBoundsInsideBounds() {
		Position pos = new Position(2, 3);
		assertFalse("Doit être dans les limites", pos.isOutOfBounds(0, 5, 0, 5));
	}
	
	@Test
	public void testIsOutOfBoundsOutOfBounds() {
		Position pos = new Position(6, 0);
		assertTrue("Doit être hors-limites", pos.isOutOfBounds(0, 5, 0, 5));
	}
	
	// Tests equals
	@Test
	public void testEqualsTrue() {
		Position pos1 = new Position(0, 0);
		Position pos2 = new Position(0, 0);
		assertEquals(pos1, pos2);
	}
	
	@Test
	public void testEqualsFalse() {
		Position pos1 = new Position(0, 0);
		Position pos2 = new Position(2, 0);
		assertFalse(pos1.equals(pos2));
	}

}
