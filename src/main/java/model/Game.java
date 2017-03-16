package model;

import java.util.Arrays;

/**
 * Classe principale du mod�le qui contient tous les autres �l�ments du jeu
 * et qui repr�sente l'�tat d'une partie.
 */
public class Game {

	/**
	 * Enum�ration des diff�rens �tats possibles de la partie.
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
	 * L'�poque � laquelle se d�roule la partie.
	 */
	private Epoch epoch;
	
	/**
	 * L'entit� qui contr�le les actions de l'ordinateur.
	 */
	private ComputerController computerController;
	
	/**
	 * Cr�e une partie � partir de l'�poque choisie au pr�alable.
	 * @param epoque L'�poque choisie.
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
	 * Traite l'�v�nement d'un clic sur une certaine case.
	 * @param x Abscisse de la case.
	 * @param y Ordonn�e de la case.
	 */
	public void receiveClickEvent(int x, int y) {
		// Si la partie est termin�e ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == COMPUTER_TURN)
			return;	// on ne fait rien
		
		Player player = players[0]; // le joueur
		// Si la phase de positionnement n'est pas termin�e
		if(!player.getSelfGrid().areShipsAllPlaced()) {
			if (player.placeShip(new Position(x, y)))		// si le positionnement est valid�e
				player.getSelfGrid().putNextShipToPlace();	// on pr�pare le bateau suivant � �tre positionn�
		}	// Si la phase de positionnement est termin�e
		else {
			if (player.shoot(new Position(x, y)))			// si le tir est valid�e
				endTurn();									// on termine le tour du joueur
		}
	}
	
	/**
	 * Termine le tour du joueur courant.
	 */
	public void endTurn() {
		boolean gameOver = checkGameOver() != GameState.RUNNING;
		if (gameOver)	// Si la partie est termin�e,
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
		// Si la phase de positionnement n'est pas termin�e
		if (!players[COMPUTER_TURN].getSelfGrid().areShipsAllPlaced())
			computerController.placeAllShips();
		else
			computerController.playShoot();
	}
	
	/**
	 * V�rifie si la partie est termin�e.
	 * @return L'�tat du jeu (selon qui a gagn�).
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
