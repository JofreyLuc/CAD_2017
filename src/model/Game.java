package model;

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
		this.grids[1] = new Sea(epoch);
		this.players[0] = new Player(this.grids[0], this.grids[1]);
		this.players[1] = new Player(this.grids[1], this.grids[0]);
		this.computerController = new ComputerController(players[COMPUTER_TURN]);
	}
	
}
