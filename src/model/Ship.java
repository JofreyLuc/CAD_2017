package model;

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
		Position[] boxesOccupied = new Position[size];
		for (int i = 0 ; i < size ; i++) {
			if (horizontal)
				boxesOccupied[i] = position.add(i, 0);
			else
				boxesOccupied[i] = position.add(0, i);
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
		}
		if(touched) {		// Si le bateau est touch�,
			hits[i] = true;	// on enregistre les d�g�ts
			if (!dead)		// et si le bateau n'est pas d�j� d�truit,
				this.dead = epoch.takeDamage(size, hits);
		}					// on d�l�gue la gestion de l'�tat du bateau � l'�poque
		return touched;
	}
	
}
