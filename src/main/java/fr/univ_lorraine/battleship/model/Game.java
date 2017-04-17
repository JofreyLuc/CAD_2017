package fr.univ_lorraine.battleship.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Observable;

/**
 * Classe principale du modèle qui contient tous les autres éléments du jeu
 * et qui représente l'état d'une partie.
 */
public class Game extends Observable implements Serializable {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Enumération des différens états possibles de la partie.
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
	 * Le joueur dont c'est le tour de jouer.
	 * @serial
	 */
	private PlayerId playerTurn;
	
	/**
	 * Les deux joueurs.
	 * @serial
	 */
	private EnumMap <PlayerId, Player> players;
		
	/**
	 * L'époque à laquelle se déroule la partie.
	 * @serial
	 */
	private Epoch epoch;
		
	/**
	 * L'entité qui contrôle les actions de l'ordinateur.
	 * @serial
	 */
	private ComputerController computerController;
	
	/**
	 * Booléen indiquant si le tour doit se terminer seulement à la fin des animations de tir.
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
	 * Crée une partie à partir de l'époque
	 * et de la stratégie de tir de l'ordinateur choisies au préalable.
	 * @param epoque L'époque choisie.
	 * @param shootingStrategy La stratégie de tir de l'ordinateur choisie.
	 */
	public Game(Epoch epoch, ShootingStrategy shootingStrategy) {
		gameState = GameState.RUNNING;
		this.epoch = epoch;
		
		players = new EnumMap<PlayerId, Player>(PlayerId.class);
		Sea playerSea = new Sea(epoch);
		Sea computerSea = new Sea(epoch);
		players.put(PlayerId.PLAYER, new Player(playerSea, computerSea));
		players.put(PlayerId.COMPUTER, new Player(computerSea, playerSea));
		
		computerController = new ComputerController(players.get(PlayerId.COMPUTER));
		computerController.setShootingStrategy(shootingStrategy);
		
		endTurnAfterShotAnimation = true;
		numberOfShotsPerTurn = 1;
		countNumberOfShots = 0;
	}
	
	/**
	 * Retourne la grille du joueur.
	 * @return La grille du joueur.
	 */
	private Sea getPlayerSea() {
		return getPlayer(PlayerId.PLAYER).getSelfGrid();
	}
	
	/**
	 * Retourne la grille du joueur.
	 * @return La grille du joueur.
	 */
	private Sea getComputerSea() {
		return getPlayer(PlayerId.COMPUTER).getSelfGrid();
	}
	
	/**
	 * Retourne le booléen indiquant si le tour doit se terminer seulement à la fin des animations de tir.
	 * @return
	 */
	public boolean isEndTurnAfterShotAnimation() {
		return endTurnAfterShotAnimation;
	}

	/**
	 * Retourne l'époque à laquelle se déroule cette partie.
	 * @return L'époque à laquelle se déroule cette partie.
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
	 * Retourne le joueur possédant cet identifiant.
	 * @param player Identifiant du joueur.
	 * @return Le joueur possédant cet identifiant.
	 */
	public Player getPlayer(PlayerId player) {
		return players.get(player);
	}
	
	/**
	 * Retourne l'état courant du jeu.
	 * @return L'état courant du jeu.
	 */
	public GameState getGameState() {
		return gameState;
	}
	
	/**
	 * Indique si la phase de positionnement est terminée.
	 * @return Booléen indiquant si la phase de positionnement est terminée.
	 */
	public boolean isPositionningPhaseOver() {
		return getPlayerSea().areShipsAllPlaced() && getComputerSea().areShipsAllPlaced();
	}
	
	/**
	 * Indique si tous les tirs du tour ont été tirés.
	 * @return Vrai si tous les tirs du tour ont été tirés, faux sinon.
	 */
	public boolean areAllShotsDone() {
		return countNumberOfShots == numberOfShotsPerTurn;
	}
	
	/**
	 * Retourne le controleur de l'ordinateur.
	 * @return Le controleur de l'ordinateur.
	 */
	public ComputerController getComputerController() {
		return computerController;
	}
	
	/**
	 * Démarre la partie.
	 * Joue le tour de l'ordinateur en plaçant les bateaux 
	 * si c'est l'ordinateur qui commence.
	 * C'est donc toujours au joueur de jouer après l'appel à cette méthode.
	 * @param startingPlayer Le joueur qui commence.
	 */
	public void start(PlayerId startingPlayer) {
		getPlayerSea().putNextShipToPlace();	// On place le premier bateau en phase de positionnement
		getComputerSea().putNextShipToPlace();	// On place le premier bateau en phase de positionnement
		switch(startingPlayer) {
			case PLAYER:
				break;
			case COMPUTER:
				playerTurn = PlayerId.COMPUTER;
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
		updateGameState();
		if (gameState == GameState.RUNNING) {
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
		}
		setChanged();
		notifyObservers();
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
		setChanged();
		notifyObservers();
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
			// si tous les tirs n'ont pas été effectué
			if (!areAllShotsDone() && players.get(PlayerId.PLAYER).shoot(new Position(x, y))) {	// si le tir est validée
				countNumberOfShots++;
				// Si tous les tirs ont été effectué et la fin du tour ne se déclenche pas à la fin des animations
				if (areAllShotsDone() && !endTurnAfterShotAnimation) {
					endTurn();												// on termine le tour du joueur
				}
			}
		}
		setChanged();
		notifyObservers();
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
	 * Termine le tour du joueur courant
	 * et joue le tour de l'ordinateur si c'est à lui de jouer.
	 * Vérifie l'état du jeu avant de continuer et de changer de tour.
	 */
	public void endTurn() {
		updateGameState();
		if (gameState != GameState.RUNNING) {	// Si la partie est terminée,
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
	 * (placements ou tir(s) selon l'avancement de la partie)
	 * et termine son tour immédiatement en phase de positionnement.
	 * Termine son tour en phase de tir seulement si le nombre de tir par tour
	 * est atteint et la fin du tour n'est pas déclenché
	 * à la fin des animations {@link #endTurnAfterShotAnimation}.
	 */
	private void playComputerTurn() {
		// Si la phase de positionnement n'est pas terminée
		if (!getComputerSea().areShipsAllPlaced()) {
			computerController.placeAllShips();
			endTurn();
		}
		else {
			while(!areAllShotsDone()) {	// tant que tous les tirs n'ont pas été effectués
				computerController.playShoot();
				countNumberOfShots++;
			}
			// Si tous les tirs ont été effectué et la fin du tour ne se déclenche pas à la fin des animations
			if (!endTurnAfterShotAnimation) {
				endTurn();												// on termine le tour du joueur
			}
		}
	}
	
	/**
	 * Met à jour l'état du jeu.
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
	
}
