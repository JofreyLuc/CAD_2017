package model;

/**
 * Interface exposant les actions pouvant �tre effectu�es par un joueur.
 */
public interface IPlayer {

	/**
	 * Effectue la rotation du bateau en cours de positionnement du joueur.
	 */
	public void rotateShip();
	
	/**
	 * Tente de placer le bateau en cours de positionnement.
	 * @param position La position de placement du bateau.
	 * @return Vrai si le positionnement est valide et donc effectu�, faux sinon.
	 */
	public boolean placeShip(Position position);
	
	/**
	 * Tente de tirer � cette position.
	 * @param position La position du tir.
	 * @return Vrai si le tir est valide et donc effectu�, faux sinon.
	 */
	public boolean shoot(Position position);
	
}
