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
	 * Numéro du joueur
	 */
	public static final int PLAYER = 0;
	
	/**
	 * Numéro de l'ordinateur.
	 */
	public static final int COMPUTER = 1;
	
	/**
	 * Entier correspondant au tour courant du joueur.
	 * 0 : joueur1 (joueur)
	 * 1 : joueur2 (ordinateur
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
		this.playerTurn = COMPUTER;
		this.epoch = epoch;
		this.grids = new Sea[2];
		this.players = new Player[2];
		this.grids[PLAYER] = new Sea(epoch);
		this.grids[COMPUTER] = new Sea(epoch);
		this.players[PLAYER] = new Player(this.grids[PLAYER], this.grids[COMPUTER]);
		this.players[COMPUTER] = new Player(this.grids[COMPUTER], this.grids[PLAYER]);
		this.computerController = new ComputerController(players[COMPUTER]);
	}
	
	/**
	 * Démarre la partie.
	 * @param startingPlayer Le joueur qui commence.
	 */
	public void startGame(int startingPlayer) {
		this.grids[COMPUTER].putNextShipToPlace();	// On place le premier bateau en phase de positionnement
		this.grids[PLAYER].putNextShipToPlace();	// On place le premier bateau en phase de positionnement
		switch(startingPlayer) {
			case PLAYER:
				break;
			case COMPUTER:
				playComputerTurn();
				endTurn();
			break;
			default:
				// Problème
		}
	}
	
	/**
	 * Traite l'événement d'un clic sur une certaine case.
	 * @param x Abscisse de la case.
	 * @param y Ordonnée de la case.
	 */
	public void receiveClickEvent(int x, int y) {
		// Si la partie est terminée ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == COMPUTER)
			return;	// on ne fait rien
		
		Player player = players[PLAYER]; // le joueur
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
		boolean gameOver = checkGameState() != GameState.RUNNING;
		if (gameOver)	// Si la partie est terminée,
			return;		// on ne fait rien
		
		changeTurn();
		if (playerTurn == COMPUTER) {	// Si c'est le tour de l'ordinateur,
			playComputerTurn();				// on le fait jouer
			endTurn();						// et on termine son tour
		}
	}
	
	/**
	 * Joue le tour de l'ordinateur.
	 */
	private void playComputerTurn() {
		// Si la phase de positionnement n'est pas terminée
		if (!players[COMPUTER].getSelfGrid().areShipsAllPlaced())
			computerController.placeAllShips();
		else
			computerController.playShoot();
	}
	
	/**
	 * Vérifie si la partie est terminée.
	 * @return L'état du jeu (selon qui a gagné).
	 */
	private GameState checkGameState() {
		if (this.grids[PLAYER].areShipsAllDead())
			return GameState.COMPUTER_WINS;
		
		if (this.grids[COMPUTER].areShipsAllDead())
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
		return "Game [\ngrille joueur : " + grids[PLAYER] + "\n\ngrille ordi : " + grids[COMPUTER] + "]";
	}
	
}
