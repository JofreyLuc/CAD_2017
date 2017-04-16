package model;

import static org.junit.Assert.*;

import java.util.Arrays;

import model.Ship.Orientation;

import org.junit.Before;
import org.junit.Test;

public class TestShip {

	private Ship ship;
	
	private static final Epoch EPOCH = new EpochXX();
	
	private static final int SIZE = 4;
	
	@Before
	public void setUp() throws Exception {
		ship = new Ship(SIZE, EPOCH);
	}

	//Tests getSeaTilesOccupied
	@Test
	public void testGetSeaTilesOccupiedVertical() {
		// Position : 0, 0
		// Orientation : vertical
		ship.setPosition(new Position(0, 0));
		ship.setOrientation(Orientation.VERTICAL);
		Position[] positionsExpected = {new Position(0, 0), new Position(0, 1), new Position(0, 2), new Position(0, 3)};
		Position[] results = ship.getSeaTilesOccupied();
		assertTrue("Différents des positions attendues", Arrays.asList(positionsExpected).containsAll(
				Arrays.asList(results))
				&& positionsExpected.length == results.length);
	}
	
	@Test
	public void testGetSeaTilesOccupiedHorizontal() {
		// Position : 0, 0
		// Orientation : horizontal
		ship.setPosition(new Position(0, 0));
		ship.setOrientation(Orientation.HORIZONTAL);
		Position[] positionsExpected = {new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(3, 0)};
		Position[] results = ship.getSeaTilesOccupied();
		assertTrue("Différents des positions attendues", Arrays.asList(positionsExpected).containsAll(
				Arrays.asList(results))
				&& positionsExpected.length == results.length);
	}

	// Tests checkShot
	@Test
	public void testCheckShotNotTouched() {
		ship.setPosition(new Position(1, 2));
		boolean res = ship.checkShot(new Position(0, 0));
		assertFalse("Ne doit pas être touché", res);
		assertEquals("Le nombre de touché doit être nul", 0, ship.getHitCount());
	}
	
	@Test
	public void testCheckShotTouched() {
		Position pos = new Position(0, 0);
		ship.setPosition(pos);
		boolean res = ship.checkShot(pos);
		assertTrue("Doit être touché", res);
		assertEquals("Le nombre de touché doit être de 1", 1, ship.getHitCount());
	}
	
	@Test
	public void testCheckShotMultipleTouchedVertical() {
		// Position : 0, 0
		// Orientation : vertical
		// Taille : 4
		Position pos = new Position(0, 0);
		ship.setOrientation(Orientation.VERTICAL);
		ship.setPosition(pos);
		ship.checkShot(pos);
		boolean res = ship.checkShot(new Position(0, 0+SIZE-1));
		assertTrue("Doit être touché", res);
		assertEquals("Le nombre de touché doit être de 2", 2, ship.getHitCount());
	}
	
	@Test
	public void testCheckShotMultipleTouchedHorizontal() {
		// Position : 0, 0
		// Orientation : horizontal
		// Taille : 4
		Position pos = new Position(0, 0);
		ship.setPosition(pos);
		ship.setOrientation(Orientation.HORIZONTAL);
		ship.checkShot(pos);
		boolean res = ship.checkShot(new Position(0+SIZE-1, 0));
		assertTrue("Doit être touché", res);
		assertEquals("Le nombre de touché doit être de 2", 2, ship.getHitCount());
	}
	
	@Test
	public void testCheckShotDead() {
		Ship ship = new Ship(1, EPOCH);
		Position pos = new Position(0, 0);
		ship.setPosition(pos);
		boolean res = ship.checkShot(pos);
		assertTrue("Doit être touché", res);
		assertEquals("Le nombre de touché doit être de 1", 1, ship.getHitCount());
		assertTrue("Doit être détruit", ship.isDead());
	}
	
}
