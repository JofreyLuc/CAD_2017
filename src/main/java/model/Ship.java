package model;

import java.util.Arrays;

/**
 * Classe représentant un bateau.
 */
public class Ship {
	
	/**
	 * Position du bateau sur la grille du jeu.
	 */
	private Position position;
	
	/**
	 * Longueur du bateau.
	 */
	private int size;
	
	/**
	 * L'orientation du bateau, à vrai si il est orienté horizontalement.
	 */
	private boolean horizontal;
	
	/**
	 * L'état du bateau, à vrai si il est détruit.
	 */
	private boolean dead;
	
	/**
	 * Les dégâts subis par le bateau ainsi que leurs positions.
	 */
	private boolean[] hits;
	
	/**
	 * L'époque du bateau.
	 */
	private Epoch epoch;
	
	/**
	 * Construit un bateau d'une certaine longueur appartenant à une certaine époque.
	 * @param size Longueur.
	 * @param epoch Epoque.
	 */
	public Ship(int size, Epoch epoch) {
		this.size = size;
		this.epoch = epoch;
		this.hits = new boolean[size];
	}
	
	/**
	 * Retourne la longueur du bateau.
	 * @return La longueur du bateau.
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Retourne la position du bateau.
	 * @return La position du bateau.
	 */
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * Positionne le bateau.
	 * @param x L'abscisse.
	 * @param y L'ordonnée.
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
	
	/**
	 * Change l'orientation du bateau
	 */
	public void changeOrientation() {
		this.horizontal = !this.horizontal;
	}
	
	/**
	 * Renvoie si le bateau est détruit.
	 * @return Vrai si le bateau est détruit, faux sinon.
	 */
	public boolean isDead() {
		return dead;
	}
	
	/**
	 * Retourne les positions occupées par ce bateau sur la grille
	 * sous forme de tableau.
	 * @return Les positions occupées.
	 */
	public Position[] getSeaBoxesOccupied() {
		if (position == null) {
			return new Position[0];
		}
		
		Position[] boxesOccupied = new Position[size];
		for (int i = 0 ; i < size ; i++) {
			if (horizontal) {
				boxesOccupied[i] = new Position(position.getX()+i, position.getY());
			}
			else {
				boxesOccupied[i] = new Position(position.getX(), position.getY()+i);
			}
		}
		return boxesOccupied;
	}
	
	/**
	 * Vérifie si le bateau est touché par le tir et agit en conséquence.
	 * @param shotPosition La position du tir.
	 * @return Vrai si le bateau est touché, faux sinon.
	 */
	public boolean checkShot(Position shotPosition) {
		int i = 0;
		boolean touched = false;
		Position[] boxesOccupied = getSeaBoxesOccupied();
		while (i < size && !touched) {
			touched = boxesOccupied[i].equals(shotPosition);
			i++;
		}
		if(touched) {			// Si le bateau est touché,
			hits[i-1] = true;	// on enregistre les dégâts
			if (!dead) {		// et si le bateau n'est pas déjà détruit,
				this.dead = epoch.takeDamage(size, getHitCount());
			}					// on délègue la gestion de l'état du bateau à l'époque
		}
		return touched;
	}
	
	/**
	 * Compte le nombre de tirs qui ont touchés le bateau.
	 * @return Nombre de "touchés".
	 */
	public int getHitCount() {
		int count = 0;
		for (int i = 0 ; i < hits.length ; i++) {
			if (hits[i]){
				count++;
			}
		}
		return count;
	}

	@Override
	public String toString() {
		String posOcc = " ";
		for (Position pos : this.getSeaBoxesOccupied()) {
			posOcc+= pos + " , ";
		}
		return "\nShip [position=" + position + ", posOcc=" + posOcc + ", size=" + size
				+ ", horizontal=" + horizontal + ", dead=" + dead + ", hits="
				+ Arrays.toString(hits) + ", epoch=" + epoch + "]";
	}
	
}
