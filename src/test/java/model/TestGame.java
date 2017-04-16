package model;

import static org.junit.Assert.*;

import model.Game.PlayerId;

import org.junit.Before;
import org.junit.Test;

public class TestGame {

	private Game game;
	
	@Before
	public void setUp() {
		game = new Game(new EpochXX(), new RandomShooting());
	}

	// Tests start
	@Test
	public void testStartPlayer() {
		game.start(PlayerId.PLAYER);
		Ship shipOnPlacing = game.getPlayer(PlayerId.PLAYER).getSelfGrid().getShipOnPlacing();
		assertNotNull("Il devrait y avoir un bateau en cours de positionnement", shipOnPlacing);
		assertEquals("Cela devait être le tour du joueur",PlayerId.PLAYER, game.getPlayerTurn());
	}

	@Test
	public void testStartComputer() {
		game.start(PlayerId.COMPUTER);
		Ship shipOnPlacing = game.getPlayer(PlayerId.PLAYER).getSelfGrid().getShipOnPlacing();
		boolean computerShipsAllPlaced = game.getPlayer(PlayerId.COMPUTER).getSelfGrid().areShipsAllPlaced();
		assertTrue("Les bateaux de l'ordinateur devraient être placés", computerShipsAllPlaced);
		assertNotNull("Il devrait y avoir un bateau en cours de positionnement", shipOnPlacing);
		assertEquals("Cela devait être le tour du joueur",PlayerId.PLAYER, game.getPlayerTurn());
	}
	
}
