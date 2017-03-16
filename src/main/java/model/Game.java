package model;

import java.util.Arrays;

/**
 * Classe principale du modèle qui contient tous les autres éléments du jeu
 * et qui représente l'état d'une partie.
 */
public class Game {

	/**
	 * Enumération des différens états possibles de la partie.
	 */
	public enum GameState { RUNNING, PLAYER_WINS, COMPUTER_WINS}
	
	/**
	 * Etat courant de la partie.
	 */
	private GameState gameState; 
	
	/**
	 * Tour de l'ordinateur.
	 */
	private static final int COMPUTER_TURN = 1;
	
	/**
	 * Entier correspondant au tour courant du joueur.
	 * 0 : joueur1
	 * 1 : joueur2
	 */
	private int playerTurn;
	
	/**
	 * Les grilles de la partie.
	 */
	private Sea[] grids;
	
	/**
	 * Les deux joueurs.
	 */
	private Player[] players;
	
	/**
	 * L'époque à laquelle se déroule la partie.
	 */
	private Epoch epoch;
	
	/**
	 * L'entité qui contrôle les actions de l'ordinateur.
	 */
	private ComputerController computerController;
	
	/**
	 * Crée une partie à partir de l'époque choisie au préalable.
	 * @param epoque L'époque choisie.
	 */
	public Game(Epoch epoch) {
		this.gameState = GameState.RUNNING;
		this.playerTurn = COMPUTER_TURN;
		this.epoch = epoch;
		this.grids = new Sea[2];
		this.players = new Player[2];
		this.grids[0] = new Sea(epoch);
		this.grids[0].putNextShipToPlace();	// On place le premier bateau en phase de positionnement
		this.grids[1] = new Sea(epoch);
		this.grids[1].putNextShipToPlace();	// On place le premier bateau en phase de positionnement
		this.players[0] = new Player(this.grids[0], this.grids[1]);
		this.players[1] = new Player(this.grids[1], this.grids[0]);
		this.computerController = new ComputerController(players[COMPUTER_TURN]);
	}
	
	/**
	 * Traite l'événement d'un clic sur une certaine case.
	 * @param x Abscisse de la case.
	 * @param y Ordonnée de la case.
	 */
	public void receiveClickEvent(int x, int y) {
		// Si la partie est terminée ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == COMPUTER_TURN)
			return;	// on ne fait rien
		
		Player player = players[0]; // le joueur
		// Si la phase de positionnement n'est pas terminée
		if(!player.getSelfGrid().areShipsAllPlaced()) {
			if (player.placeShip(new Position(x, y)))		// si le positionnement est validée
				player.getSelfGrid().putNextShipToPlace();	// on prépare le bateau suivant à être positionné
		}	// Si la phase de positionnement est terminée
		else {
			if (player.shoot(new Position(x, y)))			// si le tir est validée
				endTurn();									// on termine le tour du joueur
		}
	}
	
	/**
	 * Termine le tour du joueur courant.
	 */
	public void endTurn() {
		boolean gameOver = checkGameOver() != GameState.RUNNING;
		if (gameOver)	// Si la partie est terminée,
			return;		// on ne fait rien
		
		changeTurn();
		if (playerTurn == COMPUTER_TURN) {	// Si c'est le tour de l'ordinateur,
			playComputerTurn();				// on le fait jouer
			endTurn();						// et on termine son tour
		}
	}
	
	/**
	 * Joue le tour de l'ordinateur.
	 */
	public void playComputerTurn() {
		// Si la phase de positionnement n'est pas terminée
		if (!players[COMPUTER_TURN].getSelfGrid().areShipsAllPlaced())
			computerController.placeAllShips();
		else
			computerController.playShoot();
	}
	
	/**
	 * Vérifie si la partie est terminée.
	 * @return L'état du jeu (selon qui a gagné).
	 */
	private GameState checkGameOver() {
		if (this.grids[0].areShipsAllDead())
			return GameState.COMPUTER_WINS;
		
		if (this.grids[1].areShipsAllDead())
			return GameState.PLAYER_WINS;
		
		return GameState.RUNNING;
	}
	
	/**
	 * Change de tour.
	 */
	private void changeTurn() {
		this.playerTurn = 1 - playerTurn;
	}

	@Override
	public String toString() {
		return "Game [\ngrille joueur : " + grids[0] + "\n\ngrille ordi : " + grids[1] + "]";
	}
	
}
