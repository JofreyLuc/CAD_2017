package model;

import java.io.Serializable;

/**
 * Simple classe conteneur pour les coordonnées d'une position (avec des entiers). 
 */
public class Position implements Serializable {
	
	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Abscisse.
	 * @serial
	 */
	private int x;
	
	/**
	 * Ordonnée.
	 * @serial
	 */
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * Regarde si la position est en-dehors des limites.
	 * Les limites sont inclusives.
	 * 
	 * @param xMin Abscisse min.
	 * @param xMax Abscisse max.
	 * @param yMin Ordonnée min.
	 * @param yMax Ordonnée max.
	 * @return Vrai si la position est en-dehors des limites, faux sinon.
	 */
	public boolean isOutOfBounds(int xMin, int xMax, int yMin, int yMax) {
		return this.x < xMin || this.x > xMax || this.y < yMin || this.y > yMax;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.x == ((Position) obj).x && this.y == ((Position) obj).y;
	}
	
}
