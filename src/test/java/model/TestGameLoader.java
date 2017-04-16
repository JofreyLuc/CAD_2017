package model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGameLoader {

	private static final File INEXISTING_FILE = new File("_inexisting_file_.test");
	
	private static final File VALID_FILE = new File("_valid_file_.test");

	private Game game;
	
	/**
	 * Supprime les fichiers utilisés.
	 */
	private void clean() {
		VALID_FILE.delete();
	}
	
	@Before
	public void setUp() {
		clean();
		INEXISTING_FILE.delete();	// pour éviter un conflit si le fichier existe
		game = new Game(new EpochXX(), new RandomShooting());
	}
	
	@After
	public void tearDown() {
		clean();
	}
	
	// Tests loadGame
	@Test(expected = IOException.class)
	public void testLoadGameFromInexistingFile() throws ClassNotFoundException, IOException {
		GameLoader.loadGame(INEXISTING_FILE);
		fail("Une IOException aurait dû être levée");
	}
	
	@Test
	public void testLoadGameFromValidFile() throws IOException, ClassNotFoundException {
		GameLoader.saveGame(game, VALID_FILE);
		Game testGame = GameLoader.loadGame(VALID_FILE);
		assertNotNull("La partie devrait être ok", testGame);
	}
	
}
