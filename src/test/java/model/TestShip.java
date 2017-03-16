package model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class TestShip {

	private Ship ship;
	
	private static final int SIZE = 4;
	
	@Before
	public void setUp() throws Exception {
		ship = new Ship(SIZE, new EpochXX());
	}

	@Test
	public void testGetSeaBoxesOccupiedVertical() {
		ship.setPosition(new Position(0, 0));
		// Position : 0, 0
		// Orientation : vertical
		Position[] positionsExpected = {new Position(0, 0), new Position(0, 1), new Position(0, 2), new Position(0, 3)};
		Position[] results = ship.getSeaBoxesOccupied();
		assertTrue("Différents des positions attendues", Arrays.asList(positionsExpected).containsAll(
				Arrays.asList(results))
				&& positionsExpected.length == results.length);
	}
	
	@Test
	public void testGetSeaBoxesOccupiedHorizontal() {
		ship.setPosition(new Position(0, 0));
		ship.changeOrientation();
		// Position : 0, 0
		// Orientation : horizontal
		Position[] positionsExpected = {new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(3, 0)};
		Position[] results = ship.getSeaBoxesOccupied();
		assertTrue("Différents des positions attendues", Arrays.asList(positionsExpected).containsAll(
				Arrays.asList(results))
				&& positionsExpected.length == results.length);
	}

}
