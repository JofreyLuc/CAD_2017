package model;

import java.util.Arrays;

/**
 * Classe repr�sentant un bateau.
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
	 * L'orientation du bateau, � vrai si il est orient� horizontalement.
	 */
	private boolean horizontal;
	
	/**
	 * L'�tat du bateau, � vrai si il est d�truit.
	 */
	private boolean dead;
	
	/**
	 * Les d�g�ts subis par le bateau ainsi que leurs positions.
	 */
	private boolean[] hits;
	
	/**
	 * L'�poque du bateau.
	 */
	private Epoch epoch;
	
	/**
	 * Construit un bateau d'une certaine longueur appartenant � une certaine �poque.
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
	 * @param y L'ordonn�e.
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
	 * Renvoie si le bateau est d�truit.
	 * @return Vrai si le bateau est d�truit, faux sinon.
	 */
	public boolean isDead() {
		return dead;
	}
	
	/**
	 * Retourne les positions occup�es par ce bateau sur la grille
	 * sous forme de tableau.
	 * @return Les positions occup�es.
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
	 * V�rifie si le bateau est touch� par le tir et agit en cons�quence.
	 * @param shotPosition La position du tir.
	 * @return Vrai si le bateau est touch�, faux sinon.
	 */
	public boolean checkShot(Position shotPosition) {
		int i = 0;
		boolean touched = false;
		Position[] boxesOccupied = getSeaBoxesOccupied();
		while (i < size && !touched) {
			touched = boxesOccupied[i].equals(shotPosition);
			i++;
		}
		if(touched) {			// Si le bateau est touch�,
			hits[i-1] = true;	// on enregistre les d�g�ts
			if (!dead) {		// et si le bateau n'est pas d�j� d�truit,
				this.dead = epoch.takeDamage(size, getHitCount());
			}					// on d�l�gue la gestion de l'�tat du bateau � l'�poque
		}
		return touched;
	}
	
	/**
	 * Compte le nombre de tirs qui ont touch�s le bateau.
	 * @return Nombre de "touch�s".
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
