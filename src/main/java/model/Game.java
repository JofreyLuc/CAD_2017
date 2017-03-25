package model;

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
	 * Num�ro du joueur
	 */
	public static final int PLAYER = 0;
	
	/**
	 * Num�ro de l'ordinateur.
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
	 * Retourne la grille du joueur.
	 * @return La grille du joueur.
	 */
	public Sea getPlayerSea() {
		return this.grids[PLAYER];
	}
	
	/**
	 * Retourne la grille du joueur.
	 * @return La grille du joueur.
	 */
	public Sea getComputerSea() {
		return this.grids[COMPUTER];
	}
	
	/**
	 * D�marre la partie.
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
				// Probl�me
		}
	}
	
	/**
	 * Traite l'�v�nement d'un clic sur une certaine case.
	 * @param x Abscisse de la case.
	 * @param y Ordonn�e de la case.
	 */
	public void receiveClickEvent(int x, int y) {
		// Si la partie est termin�e ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == COMPUTER) {
			return;	// on ne fait rien
		}
		
		Player player = players[PLAYER]; // le joueur
		// Si la phase de positionnement n'est pas termin�e
		if(!player.getSelfGrid().areShipsAllPlaced()) {
			player.placeShip(new Position(x, y));			// on tente de placer un bateau
		}	// Si la phase de positionnement est termin�e
		else {
			if (player.shoot(new Position(x, y))) {			// si le tir est valid�e
				endTurn();									// on termine le tour du joueur
			}
		}
	}
	
	/**
	 * Termine le tour du joueur courant.
	 */
	public void endTurn() {
		boolean gameOver = checkGameState() != GameState.RUNNING;
		if (gameOver) {	// Si la partie est termin�e,
			return;		// on ne fait rien
		}
		
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
		// Si la phase de positionnement n'est pas termin�e
		if (!players[COMPUTER].getSelfGrid().areShipsAllPlaced()) {
			computerController.placeAllShips();
		}
		else {
			computerController.playShoot();
		}
	}
	
	/**
	 * V�rifie si la partie est termin�e.
	 * @return L'�tat du jeu (selon qui a gagn�).
	 */
	private GameState checkGameState() {
		if (this.grids[PLAYER].areShipsAllDead()) {
			return GameState.COMPUTER_WINS;
		}
		
		if (this.grids[COMPUTER].areShipsAllDead()) {
			return GameState.PLAYER_WINS;
		}
		
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
