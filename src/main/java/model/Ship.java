package model;

import java.util.Arrays;
import java.util.Observable;

/**
 * Classe repr�sentant un bateau.
 */
public class Ship extends Observable {
	
	/**
	 * Position du bateau sur la grille du jeu.
	 */
	private Position position;
	
	/**
	 * Longueur du bateau.
	 */
	private int size;
	
	public enum Orientation { HORIZONTAL, VERTICAL }
	
	/**
	 * L'orientation du bateau.
	 */
	private Orientation orientation;
	
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
		this.orientation = Orientation.HORIZONTAL;
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
	 * Retourne l'orientation du bateau.
	 * @return L'orientation du bateau.
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	
	/**
	 * Met l'orientation du bateau � la valeur pass�e en param�tre
	 * @param orientation La nouvelle orientaion du bateau.
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Change l'orientation du bateau
	 */
	public void changeOrientation() {
		switch(orientation) {
			case HORIZONTAL:
				orientation = Orientation.VERTICAL;
				break;
			case VERTICAL:
				orientation = Orientation.HORIZONTAL;
				break;
			default:
				throw new AssertionError("Orientation inconnu " + orientation);
		}
	}
	
	/**
	 * Renvoie si le bateau est d�truit.
	 * @return Vrai si le bateau est d�truit, faux sinon.
	 */
	public boolean isDead() {
		return dead;
	}
	
	/**
	 * Retourne l'�poque du bateau.
	 * @return L'�poque du bateau.
	 */
	public Epoch getEpoch() {
		return epoch;
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
	
	/**
	 * Retourne les positions occup�es par ce bateau sur la grille
	 * sous forme de tableau.
	 * @return Les positions occup�es.
	 */
	public Position[] getSeaTileesOccupied() {
		if (position == null) {
			return new Position[0];
		}
		
		Position[] tileesOccupied = new Position[size];
		for (int i = 0 ; i < size ; i++) {
			switch(orientation) {
				case HORIZONTAL:
					tileesOccupied[i] = new Position(position.getX()+i, position.getY());
					break;
				case VERTICAL:
					tileesOccupied[i] = new Position(position.getX(), position.getY()+i);
					break;
				default:
					throw new AssertionError("Orientation inconnu " + orientation);
			}
		}
		return tileesOccupied;
	}
	
	/**
	 * V�rifie si le bateau est touch� par le tir et agit en cons�quence.
	 * @param shotPosition La position du tir.
	 * @return Vrai si le bateau est touch�, faux sinon.
	 */
	public boolean checkShot(Position shotPosition) {
		int i = 0;
		boolean touched = false;
		Position[] tileesOccupied = getSeaTileesOccupied();
		while (i < size && !touched) {
			touched = tileesOccupied[i].equals(shotPosition);
			i++;
		}
		if(touched) {			// Si le bateau est touch�,
			hits[i-1] = true;	// on enregistre les d�g�ts
			if (!dead) {		// et si le bateau n'est pas d�j� d�truit,
				this.dead = epoch.takeDamage(size, getHitCount());
			}					// on d�l�gue la gestion de l'�tat du bateau � l'�poque
		}
		setChanged();
		notifyObservers();
		return touched;
	}

	@Override
	public String toString() {
		String posOcc = " ";
		for (Position pos : this.getSeaTileesOccupied()) {
			posOcc+= pos + " , ";
		}
		return "\nShip [position=" + position + ", posOcc=" + posOcc + ", size=" + size
				+ ", horizontal=" + (orientation==Orientation.HORIZONTAL) + ", dead=" + dead + ", hits="
				+ Arrays.toString(hits) + ", epoch=" + epoch + "]";
	}
	
}
