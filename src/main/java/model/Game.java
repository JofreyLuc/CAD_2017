package model;

import java.util.EnumMap;
import java.util.Observable;

/**
 * Classe principale du modèle qui contient tous les autres éléments du jeu
 * et qui représente l'état d'une partie.
 */
public class Game extends Observable {

	/**
	 * Enumération des différens états possibles de la partie.
	 */
	public enum GameState { RUNNING, PLAYER_WINS, COMPUTER_WINS}
	
	/**
	 * Etat courant de la partie.
	 */
	private GameState gameState; 
	
	/**
	 * Identifiants des joueurs.
	 */
	public enum PlayerId { PLAYER, COMPUTER }
	
	/**
	 * Représente le joueur dont c'est le tour de jouer.
	 */
	private PlayerId playerTurn;
	
	/**
	 * Les deux joueurs.
	 */
	private EnumMap <PlayerId, Player> players;
	
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
		this.playerTurn = PlayerId.COMPUTER;
		this.epoch = epoch;
		this.players = new EnumMap<PlayerId, Player>(PlayerId.class);
		Sea playerSea = new Sea(epoch);
		Sea computerSea = new Sea(epoch);
		this.players.put(PlayerId.PLAYER, new Player(playerSea, computerSea));
		this.players.put(PlayerId.COMPUTER, new Player(computerSea, playerSea));
		this.computerController = new ComputerController(players.get(PlayerId.COMPUTER));
	}
	
	/**
	 * Retourne l'identifiant du joueur dont c'est le tour de jouer.
	 * @return L'identifiant du joueur dont c'est le tour de jouer.
	 */
	public PlayerId getPlayerTurn() {
		return playerTurn;
	}
	
	/**
	 * Retourne le joueur possédant cet identifiant.
	 * @param player Identifiant du joueur.
	 * @return Le joueur possédant cet identifiant.
	 */
	public Player getPlayer(PlayerId player) {
		return players.get(player);
	}
	
	/**
	 * Retourne la grille du joueur.
	 * @return La grille du joueur.
	 */
	private Sea getPlayerSea() {
		return this.players.get(PlayerId.PLAYER).getSelfGrid();
	}
	
	/**
	 * Retourne la grille du joueur.
	 * @return La grille du joueur.
	 */
	private Sea getComputerSea() {
		return this.players.get(PlayerId.COMPUTER).getSelfGrid();
	}
	
	/**
	 * Indique si la phase de positionnement est terminée.
	 * @return Booléen indiquant si la phase de positionnement est terminée.
	 */
	public boolean isPositionningPhaseOver() {
		return getPlayerSea().areShipsAllPlaced() && getComputerSea().areShipsAllPlaced();
	}
	
	/**
	 * Démarre la partie.
	 * @param startingPlayer Le joueur qui commence.
	 */
	public void startGame(PlayerId startingPlayer) {
		getPlayerSea().putNextShipToPlace();	// On place le premier bateau en phase de positionnement
		getComputerSea().putNextShipToPlace();	// On place le premier bateau en phase de positionnement
		switch(startingPlayer) {
			case PLAYER:
				break;
			case COMPUTER:
				playComputerTurn();
				endTurn();
			break;
			default:
				throw new AssertionError("Joueur inconnu " + startingPlayer);
		}
		playerTurn = PlayerId.PLAYER;
	}
	
	/**
	 * Traite l'événement d'un clic sur une certaine case de la grille du joueur.
	 * @param x Abscisse de la case.
	 * @param y Ordonnée de la case.
	 */
	public void receiveClickEventOnPlayerGrid(int x, int y) {
		// Si la partie est terminée ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == PlayerId.COMPUTER) {
			return;	// on ne fait rien
		}
		
		// Si la phase de positionnement n'est pas terminée
		if(!getPlayerSea().areShipsAllPlaced()) {
			if (players.get(PlayerId.PLAYER).placeShip(new Position(x, y))	// on tente de placer un bateau
					&& getPlayerSea().areShipsAllPlaced()) {				// et si ils sont tous placés,
				endTurn();													// on finit le tour
			}
		}
	}
	
	/**
	 * Traite l'événement d'un clic sur une certaine case de la grille de l'ordinateur.
	 * @param x Abscisse de la case.
	 * @param y Ordonnée de la case.
	 */
	public void receiveClickEventOnComputerGrid(int x, int y) {
		// Si la partie est terminée ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == PlayerId.COMPUTER) {
			return;	// on ne fait rien
		}
		
		// Si la phase de positionnement est terminée
		if(getPlayerSea().areShipsAllPlaced()) {
			if (players.get(PlayerId.PLAYER).shoot(new Position(x, y))) {	// si le tir est validée
				endTurn();													// on termine le tour du joueur
			}
		}
	}
	
	/**
	 * Traite l'événement d'un hover on sur une certaine case de la grille du joueur.
	 * @param x Abscisse de la case.
	 * @param y Ordonnée de la case.
	 */
	public void receiveHoverOnEventOnPlayerGrid(int x, int y) {
		// Si la partie est terminée ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == PlayerId.COMPUTER) {
			return;	// on ne fait rien
		}
		
		// Si la phase de positionnement n'est pas terminée
		if(!getPlayerSea().areShipsAllPlaced()) {
			// On place le bateau en cours de positionnement
			players.get(PlayerId.PLAYER).getSelfGrid().getShipOnPlacing().setPosition(new Position(x, y));
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Traite l'événement d'un hover off sur une certaine case de la grille du joueur.
	 * @param x Abscisse de la case.
	 * @param y Ordonnée de la case.
	 */
	public void receiveHoverOffEventOnPlayerGrid(int x, int y) {
		// Si la partie est terminée ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == PlayerId.COMPUTER) {
			return;	// on ne fait rien
		}
		
		// Si la phase de positionnement n'est pas terminée
		if(!getPlayerSea().areShipsAllPlaced()) {
			// On place le bateau en cours de positionnement
			players.get(PlayerId.PLAYER).getSelfGrid().getShipOnPlacing().setPosition(null);
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Traite l'événement de changement d'orientation du bateau en cours de placement.
	 */
	public void receiveRotateShipEvent() {
		// Si la partie est terminée ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == PlayerId.COMPUTER) {
			return;	// on ne fait rien
		}
		
		// Si la phase de positionnement n'est pas terminée
		if(!getPlayerSea().areShipsAllPlaced()) {
			players.get(PlayerId.PLAYER).rotateShip();	// on fait la rotation
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Termine le tour du joueur courant.
	 */
	private void endTurn() {
		updateGameState();
		if (gameState != GameState.RUNNING) {	// Si la partie est terminée,
			return;		// on ne fait rien
		}
		
		changeTurn();
		if (playerTurn == PlayerId.COMPUTER) {	// Si c'est le tour de l'ordinateur,
			playComputerTurn();				// on le fait jouer
			endTurn();						// et on termine son tour
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Joue le tour de l'ordinateur.
	 */
	private void playComputerTurn() {
		// Si la phase de positionnement n'est pas terminée
		if (!getComputerSea().areShipsAllPlaced()) {
			computerController.placeAllShips();
		}
		else {
			computerController.playShoot();
		}
	}
	
	/**
	 * Vérifie si la partie est terminée.
	 * @return L'état du jeu (selon qui a gagné).
	 */
	private void updateGameState() {
		if (getPlayerSea().areShipsAllDead()) {
			gameState = GameState.COMPUTER_WINS;
		}
		else if (getComputerSea().areShipsAllDead()) {
			gameState = GameState.PLAYER_WINS;
		}
		else {
			gameState = GameState.RUNNING;
		}
	}
	
	/**
	 * Change de tour.
	 */
	private void changeTurn() {
		switch(playerTurn) {
		case COMPUTER:
			playerTurn = PlayerId.PLAYER;
			break;
		case PLAYER:
			playerTurn = PlayerId.COMPUTER;
			break;
		default:
			throw new AssertionError("Joueur inconnu " + playerTurn);
		}
	}

	@Override
	public String toString() {
		return "Game [\ngrille joueur : " + getPlayerSea() + "\n\ngrille ordi : " + getComputerSea() + "]";
	}
	
}
