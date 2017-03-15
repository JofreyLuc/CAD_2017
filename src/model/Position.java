package model;

/**
 * Simple classe conteneur pour les coordonnées d'une position (avec des entiers). 
 */
public class Position {
	
	private int x;
	
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
	
	public Position add(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}
}
