package model;

import java.util.EnumMap;
import java.util.Observable;

import model.Game.GameState;

/**
 * Classe principale du mod�le qui contient tous les autres �l�ments du jeu
 * et qui repr�sente l'�tat d'une partie.
 */
public class Game extends Observable {

	/**
	 * Enum�ration des diff�rens �tats possibles de la partie.
	 */
	public enum GameState { RUNNING, PLAYER_WINS, COMPUTER_WINS }
	
	/**
	 * Etat courant de la partie.
	 */
	private GameState gameState; 
	
	/**
	 * Identifiants des joueurs.
	 */
	public enum PlayerId { PLAYER, COMPUTER }
	
	/**
	 * Repr�sente le joueur dont c'est le tour de jouer.
	 */
	private PlayerId playerTurn;
	
	/**
	 * Les deux joueurs.
	 */
	private EnumMap <PlayerId, Player> players;
	
	/**
	 * L'�poque � laquelle se d�roule la partie.
	 */
	private Epoch epoch;
	
	/**
	 * L'entit� qui contr�le les actions de l'ordinateur.
	 */
	private ComputerController computerController;
	
	/**
	 * Bool�en indiquant si le tour doit se terminer seulement � la fin des animations de tir.
	 */
	private boolean endTurnAfterShotAnimation;
	
	/**
	 * Le nombre de tirs par tour.
	 */
	private int numberOfShotsPerTurn;
	
	/**
	 * Compte du nombre de tirs par tour.
	 */
	private int countNumberOfShots;
	
	/**
	 * Cr�e une partie � partir de l'�poque choisie au pr�alable.
	 * @param epoque L'�poque choisie.
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
		this.endTurnAfterShotAnimation = true;
		this.numberOfShotsPerTurn = 1;
		this.countNumberOfShots = 0;
	}
	
	/**
	 * Retourne le bool�en indiquant si le tour doit se terminer seulement � la fin des animations de tir.
	 * @return
	 */
	public boolean isEndTurnAfterShotAnimation() {
		return endTurnAfterShotAnimation;
	}

	/**
	 * Retourne l'identifiant du joueur dont c'est le tour de jouer.
	 * @return L'identifiant du joueur dont c'est le tour de jouer.
	 */
	public PlayerId getPlayerTurn() {
		return playerTurn;
	}
	
	/**
	 * Retourne le joueur poss�dant cet identifiant.
	 * @param player Identifiant du joueur.
	 * @return Le joueur poss�dant cet identifiant.
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
	 * Retourne l'�tat courant du jeu.
	 * @return L'�tat courant du jeu.
	 */
	public GameState getGameState() {
		return gameState;
	}
	
	/**
	 * Indique si la phase de positionnement est termin�e.
	 * @return Bool�en indiquant si la phase de positionnement est termin�e.
	 */
	public boolean isPositionningPhaseOver() {
		return getPlayerSea().areShipsAllPlaced() && getComputerSea().areShipsAllPlaced();
	}
	
	/**
	 * Indique si tous les tirs du tour ont �t� tir�s.
	 * @return Vrai si tous les tirs du tour ont �t� tir�s, faux sinon.
	 */
	public boolean areAllShotsDone() {
		return countNumberOfShots == numberOfShotsPerTurn;
	}
	
	/**
	 * D�marre la partie.
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
			break;
			default:
				throw new AssertionError("Joueur inconnu " + startingPlayer);
		}
		playerTurn = PlayerId.PLAYER;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Traite l'�v�nement d'un clic sur une certaine case de la grille du joueur.
	 * @param x Abscisse de la case.
	 * @param y Ordonn�e de la case.
	 */
	public void receiveClickEventOnPlayerGrid(int x, int y) {
		// Si la partie est termin�e ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == PlayerId.COMPUTER) {
			return;	// on ne fait rien
		}
		
		// Si la phase de positionnement n'est pas termin�e
		if(!getPlayerSea().areShipsAllPlaced()) {
			if (players.get(PlayerId.PLAYER).placeShip(new Position(x, y))	// on tente de placer un bateau
					&& getPlayerSea().areShipsAllPlaced()) {				// et si ils sont tous plac�s,
				endTurn();													// on finit le tour
			}
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Traite l'�v�nement d'un clic sur une certaine case de la grille de l'ordinateur.
	 * @param x Abscisse de la case.
	 * @param y Ordonn�e de la case.
	 */
	public void receiveClickEventOnComputerGrid(int x, int y) {
		// Si la partie est termin�e ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == PlayerId.COMPUTER) {
			return;	// on ne fait rien
		}
		
		// Si la phase de positionnement est termin�e
		if(getPlayerSea().areShipsAllPlaced()) {
			// si tous les tirs n'ont pas �t� effectu�
			if (!areAllShotsDone() && players.get(PlayerId.PLAYER).shoot(new Position(x, y))) {	// si le tir est valid�e
				countNumberOfShots++;
				// Si tous les tirs ont �t� effectu� et la fin du tour ne se d�clenche pas � la fin des animations
				if (areAllShotsDone() && !endTurnAfterShotAnimation) {
					endTurn();												// on termine le tour du joueur
				}
			}
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Traite l'�v�nement d'un hover on sur une certaine case de la grille du joueur.
	 * @param x Abscisse de la case.
	 * @param y Ordonn�e de la case.
	 */
	public void receiveHoverOnEventOnPlayerGrid(int x, int y) {
		// Si la partie est termin�e ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == PlayerId.COMPUTER) {
			return;	// on ne fait rien
		}
		
		// Si la phase de positionnement n'est pas termin�e
		if(!getPlayerSea().areShipsAllPlaced()) {
			// On place le bateau en cours de positionnement
			players.get(PlayerId.PLAYER).getSelfGrid().getShipOnPlacing().setPosition(new Position(x, y));
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Traite l'�v�nement d'un hover off sur une certaine case de la grille du joueur.
	 * @param x Abscisse de la case.
	 * @param y Ordonn�e de la case.
	 */
	public void receiveHoverOffEventOnPlayerGrid(int x, int y) {
		// Si la partie est termin�e ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == PlayerId.COMPUTER) {
			return;	// on ne fait rien
		}
		
		// Si la phase de positionnement n'est pas termin�e
		if(!getPlayerSea().areShipsAllPlaced()) {
			// On place le bateau en cours de positionnement
			players.get(PlayerId.PLAYER).getSelfGrid().getShipOnPlacing().setPosition(null);
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Traite l'�v�nement de changement d'orientation du bateau en cours de placement.
	 */
	public void receiveRotateShipEvent() {
		// Si la partie est termin�e ou si c'est le tour de l'ordinateur,
		if (gameState != GameState.RUNNING || playerTurn == PlayerId.COMPUTER) {
			return;	// on ne fait rien
		}
		
		// Si la phase de positionnement n'est pas termin�e
		if(!getPlayerSea().areShipsAllPlaced()) {
			players.get(PlayerId.PLAYER).rotateShip();	// on fait la rotation
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Termine le tour du joueur courant.
	 */
	public void endTurn() {
		updateGameState();
		if (gameState != GameState.RUNNING) {	// Si la partie est termin�e,
			return;		// on ne fait rien
		}
		
		changeTurn();
		if (playerTurn == PlayerId.COMPUTER) {	// Si c'est le tour de l'ordinateur,
			playComputerTurn();					// on le fait jouer
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Joue le tour de l'ordinateur.
	 */
	private void playComputerTurn() {
		// Si la phase de positionnement n'est pas termin�e
		if (!getComputerSea().areShipsAllPlaced()) {
			computerController.placeAllShips();
			endTurn();
		}
		else {
			while(!areAllShotsDone()) {	// tant que tous les tirs n'ont pas �t� effectu�s
				computerController.playShoot();
				countNumberOfShots++;
			}
			// Si tous les tirs ont �t� effectu� et la fin du tour ne se d�clenche pas � la fin des animations
			if (!endTurnAfterShotAnimation) {
				endTurn();												// on termine le tour du joueur
			}
		}
	}
	
	/**
	 * V�rifie si la partie est termin�e.
	 * @return L'�tat du jeu (selon qui a gagn�).
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
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Change de tour.
	 */
	private void changeTurn() {
		this.countNumberOfShots = 0;
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
