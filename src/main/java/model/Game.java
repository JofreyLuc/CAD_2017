package model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Observable;

/**
 * Classe principale du mod�le qui contient tous les autres �l�ments du jeu
 * et qui repr�sente l'�tat d'une partie.
 */
public class Game extends Observable implements Serializable {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Enum�ration des diff�rens �tats possibles de la partie.
	 */
	public enum GameState { RUNNING, PLAYER_WINS, COMPUTER_WINS }
	
	/**
	 * Etat courant de la partie.
	 * @serial
	 */
	private GameState gameState; 
	
	/**
	 * Identifiants des joueurs.
	 */
	public enum PlayerId { PLAYER, COMPUTER }
	
	/**
	 * Repr�sente le joueur dont c'est le tour de jouer.
	 * @serial
	 */
	private PlayerId playerTurn;
	
	/**
	 * Les deux joueurs.
	 * @serial
	 */
	private EnumMap <PlayerId, Player> players;
	
	/**
	 * Les grilles des deux joueurs.
	 */
	private EnumMap <PlayerId, Sea> seas;
	
	/**
	 * L'�poque � laquelle se d�roule la partie.
	 * @serial
	 */
	private Epoch epoch;
		
	/**
	 * L'entit� qui contr�le les actions de l'ordinateur.
	 * @serial
	 */
	private ComputerController computerController;
	
	/**
	 * Bool�en indiquant si le tour doit se terminer seulement � la fin des animations de tir.
	 * @serial
	 */
	private boolean endTurnAfterShotAnimation;
	
	/**
	 * Le nombre de tirs par tour.
	 * @serial
	 */
	private int numberOfShotsPerTurn;
	
	/**
	 * Compte du nombre de tirs par tour.
	 * @serial
	 */
	private int countNumberOfShots;
	
	/**
	 * Cr�e une partie � partir de l'�poque choisie au pr�alable.
	 * @param epoque L'�poque choisie.
	 */
	public Game(Epoch epoch, ShootingStrategy shootingStrategy) {
		gameState = GameState.RUNNING;
		playerTurn = PlayerId.COMPUTER;
		this.epoch = epoch;
		players = new EnumMap<PlayerId, Player>(PlayerId.class);
		seas = new EnumMap<PlayerId, Sea>(PlayerId.class);
		seas.put(PlayerId.PLAYER, new Sea(epoch));
		seas.put(PlayerId.COMPUTER, new Sea(epoch));
		players.put(PlayerId.PLAYER, new Player(getPlayerSea(), getComputerSea()));
		players.put(PlayerId.COMPUTER, new Player(getComputerSea(), getPlayerSea()));
		computerController = new ComputerController(players.get(PlayerId.COMPUTER));
		computerController.setShootingStrategy(shootingStrategy);
		endTurnAfterShotAnimation = true;
		numberOfShotsPerTurn = 1;
		countNumberOfShots = 0;
	}
	
	/**
	 * Retourne le bool�en indiquant si le tour doit se terminer seulement � la fin des animations de tir.
	 * @return
	 */
	public boolean isEndTurnAfterShotAnimation() {
		return endTurnAfterShotAnimation;
	}

	/**
	 * Retourne l'�poque � laquelle se d�roule cette partie.
	 * @return L'�poque � laquelle se d�roule cette partie.
	 */
	public Epoch getEpoch() {
		return epoch;
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
		return seas.get(PlayerId.PLAYER);
	}
	
	/**
	 * Retourne la grille du joueur.
	 * @return La grille du joueur.
	 */
	private Sea getComputerSea() {
		return seas.get(PlayerId.COMPUTER);
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
	 * Joue le tour de l'ordinateur en pla�ant les bateaux 
	 * si c'est l'ordinateur qui commence.
	 * C'est donc toujours au joueur de jouer apr�s l'appel � cette m�thode.
	 * @param startingPlayer Le joueur qui commence.
	 */
	public void start(PlayerId startingPlayer) {
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
	 * Reprend la partie.
	 * Utile pour reprendre une sauvegarde.
	 */
	public void resume() {
		switch(playerTurn) {
		case PLAYER:
			break;
		case COMPUTER:
			playComputerTurn();
		break;
		default:
			throw new AssertionError("Joueur inconnu " + playerTurn);
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
	 * Termine le tour du joueur courant
	 * et joue le tour de l'ordinateur si c'est � lui de jouer.
	 * V�rifie l'�tat du jeu avant de continuer et de changer de tour.
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
	 * Joue le tour de l'ordinateur
	 * (placements ou tir(s) selon l'avancement de la partie).
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
