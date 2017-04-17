package fr.univ_lorraine.battleship.model;

import java.io.Serializable;

/**
 * Classe représentant un joueur.
 */
public class Player implements Serializable {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * La grille du joueur.
	 * @serial
	 */
	private Sea selfGrid;
	
	/**
	 * La grille de l'adversaire.
	 * @serial
	 */
	private Sea opponentGrid;
	
	/**
	 * Crée un joueur à partir de sa grille et de la grille adverse.
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
		if (ship != null) {
			ship.changeOrientation();
		}
	}

	/**
	 * Tente de placer le bateau en cours de positionnement.
	 * @param position La position de placement du bateau.
	 * @return Vrai si le positionnement est valide et donc effectué, faux sinon.
	 */
	public boolean placeShip(Position position) {
		Ship ship = this.selfGrid.getShipOnPlacing();
		if (ship == null) {	// Si il n'y a pas de bateau en cours de placement,
			return false;	// on ne fait rien
		}
		
		ship.setPosition(position);						// on place le bateau
		// on regarde si son positionnement est ok
		boolean validPlace = this.selfGrid.isShipOnPlacingInValidPosition();
		if (validPlace) {								// Si l'emplacement est valide,
			this.selfGrid.validateShipPlacement();		// on valide le positionnement du bateau
		}
		
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
