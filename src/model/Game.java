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
		this.grids[1] = new Sea(epoch);
		this.players[0] = new Player(this.grids[0], this.grids[1]);
		this.players[1] = new Player(this.grids[1], this.grids[0]);
		this.computerController = new ComputerController(players[COMPUTER_TURN]);
	}
	
}
