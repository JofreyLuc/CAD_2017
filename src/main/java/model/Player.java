package model;

/**
 * Classe représentant un joueur.
 */
public class Player {

	/**
	 * La grille du joueur.
	 */
	private Sea selfGrid;
	
	/**
	 * La grille de l'adversaire.
	 */
	private Sea opponentGrid;
	
	/**
	 * Crée un joueur à partir de sa grille.
	 * @param selfGrid La grille du joueur.
	 * @param opponentGrid La grille de l'adversaire.
	 */
	public Player(Sea selfGrid, Sea opponentGrid) {
		this.selfGrid = selfGrid;
		this.opponentGrid = opponentGrid;
	}
	
	/**
	 * Renvoie la grille du joueur.
	 * @return La grille du joueur.
	 */
	public Sea getSelfGrid() {
		return selfGrid;
	}

	/**
	 * Renvoie la grille de l'adversaire.
	 * @return La grille de l'adversaire.
	 */
	public Sea getOpponentGrid() {
		return opponentGrid;
	}

	/**
	 * Effectue la rotation du bateau en cours de positionnement du joueur.
	 */
	public void rotateShip() {
		Ship ship = this.selfGrid.getShipOnPlacing();
		if (ship == null)	// Si il n'y a pas de bateau en cours de placement,
			return;			// on ne fait rien
		
		ship.changeOrientation();
	}

	/**
	 * Tente de placer le bateau en cours de positionnement.
	 * @param position La position de placement du bateau.
	 * @return Vrai si le positionnement est valide et donc effectué, faux sinon.
	 */
	public boolean placeShip(Position position) {
		Ship ship = this.selfGrid.getShipOnPlacing();
		if (ship == null)	// Si il n'y a pas de bateau en cours de placement,
			return false;	// on ne fait rien
		
		ship.setPosition(position);								// on place le bateau
		Position[] boxesOccupied = ship.getSeaBoxesOccupied();	// et on récupère les cases qu'il occupe
		boolean validPlace = true;
		int i = 0;										// on regarde pour chaque case occupée par le bateau
		while (i < boxesOccupied.length && validPlace) {
			validPlace = this.selfGrid.isSeaBoxFree(boxesOccupied[i]);	// si elle est libre
			i++;
		}

		if (validPlace)								// Si l'emplacement est valide,
			this.selfGrid.validateShipPlacement();	// on valide le positionnement du bateau
		
		return validPlace;
	}

	/**
	 * Tente de tirer à cette position.
	 * @param position La position du tir.
	 * @return Vrai si le tir est valide et donc effectué, faux sinon.
	 */
	public boolean shoot(Position position) {
		return this.opponentGrid.receiveShot(position);
	}

}
