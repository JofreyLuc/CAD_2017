package model;

/**
 * Classe représentant un joueur.
 */
public class Player implements IPlayer {

	/**
	 * La grille du joueur.
	 */
	private Sea grid;
	
	public Player(Sea grid) {
		this.grid = grid;
	}
	
	@Override
	public void rotateShip() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean placeShip(Position position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shoot(Position position) {
		// TODO Auto-generated method stub
		return false;
	}

}
