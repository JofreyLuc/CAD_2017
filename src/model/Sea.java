package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Classe repr�sentant la grille d'un joueur.
 */
public class Sea {

	private final static int GRID_WIDTH = 10;
	
	private final static int GRID_HEIGHT = 10;
	
	private final static int[] SHIPS_SIZES = {5, 4, 3, 3, 2};
	
	/**
	 * Enum�ration des �tats d'une case de la grille.
	 */
	public enum SeaBoxState { NORMAL, SHOT, TOUCHED }
	
	/**
	 * Etats de la grille.
	 */
	private SeaBoxState[][] grid;
	
	/**
	 * Les bateaux qui ne sont pas encore plac� sur la grille.
	 */
	List<Ship> shipsToPlace;
	
	/**
	 * Le bateau en cours de placement.
	 */
	Ship shipOnPlacing;
	
	/**
	 * Les bateaux plac�s sur la grille.
	 */
	List<Ship> ships;
	
	/**
	 * Cr�e une grille � partir de l'�poque associ�e.
	 * @param epoch L'�poque.
	 */
	public Sea(Epoch epoch) {
		// Initialisation de la grille
		this.grid = new SeaBoxState[GRID_WIDTH][GRID_HEIGHT];
		Arrays.fill(this.grid, SeaBoxState.NORMAL);
		// Initialisation des bateaux
		shipsToPlace = new ArrayList<Ship>(SHIPS_SIZES.length);
		ships = new ArrayList<Ship>(SHIPS_SIZES.length);
		for (int i = 0 ; i < SHIPS_SIZES.length ; i++)
			shipsToPlace.add(new Ship(SHIPS_SIZES[i], epoch));
	}
	
	/**
	 * Retourne le bateau en cours de placement.
	 * @return Le bateau en cours de placement.
	 */
	public Ship getShipOnPlacing() {
		return shipOnPlacing;
	}
	
	/**
	 * Regarde si tous les bateaux sont plac�s sur la grille.
	 * @return Vrai si tous les bateaux sont plac�s sur la grille, faux sinon.
	 */
	public boolean areShipsAllPlaced() {
		return shipsToPlace.isEmpty();
	}
	
	/**
	 * Regarde si tous les bateaux de la grille sont d�truits.
	 * @return Vrai si tous les bateaux sont d�truits, faux sinon.
	 */
	public boolean areShipsAllDead() {
		if (!areShipsAllPlaced())	// S'il reste des bateaux � placer,
			return false;			// les bateaux ne peuvent pas �tre tous d�truits
		
		boolean allDead = true;
		Iterator<Ship> iter = ships.iterator();
		while (iter.hasNext() && allDead) {	// On regarde si tous les bateaux sont d�truits
			Ship ship = iter.next();
			allDead = ship.isDead();
		}
		return allDead;
	}
	
	/**
	 * Regarde si la case � cette position est libre.
	 * @param position La position de la case.
	 * @return Vrai si la case � cette position est libre, faux sinon.
	 */
	public boolean isSeaBoxFree(Position position) {
		boolean boxFree = true;
		Iterator<Ship> iter = ships.iterator();
		while (iter.hasNext() && boxFree) {
			Ship ship = iter.next();
			boxFree = Arrays.asList(ship.getSeaBoxesOccupied()).contains(position);
			// On v�rifie si la case est occup�e par le bateau
		}
		return boxFree;
	}
	
	/**
	 * Prend un bateau de la liste des bateaux � placer et le met
	 * en tant que bateau en cours de positionnement s'il n'y en a pas d�j� un.
	 */
	public void putNextShipToPlace() {
		if (shipOnPlacing != null && !shipsToPlace.isEmpty())
			shipOnPlacing = shipsToPlace.get(0);
	}
	
	/**
	 * Valide le placement du bateau en cours de positionnement
	 * en l'ajoutant � la liste des bateaux actif
	 * et le remet � null.
	 */
	public void validateShipPlacement() {
		if (shipOnPlacing != null)
			ships.add(shipOnPlacing);
		shipOnPlacing = null;
	}

	/**
	 * Fait le n�cessaire apr�s le tir d'un joueur.
	 * @param shotPos La position du tir.
	 * @return Vrai si le tir est valide, faux sinon.
	 */
	public boolean receiveShot(Position shotPos) {
		// Coordonn�es du tir non valide
		if (shotPos.getX() < 0 || shotPos.getY() < 0 ||
			shotPos.getX() >= grid.length || shotPos.getY() >= grid[0].length)
			return false;
		// Si un tir � cette position a d�j� �t� effectu�
		if (grid[shotPos.getX()][shotPos.getY()] != SeaBoxState.NORMAL)
			return false;
		
		boolean touched = false;
		Iterator<Ship> iter = ships.iterator();
		while (iter.hasNext() && !touched) {	// Pour chaque bateau,
			Ship ship = iter.next();
			touched = ship.checkShot(shotPos);	// on regarde si il est touch�
		}
		
		updateBoxState(shotPos, touched);		// on met � jour l'�tat de la position du tir
		
		return true;							// on indique que le tir est valide
	}
	
	/**
	 * Met � jour l'�tat de la case de la grille selon si un bateau se trouve
	 * � cette position et donc est touch� ou non.
	 * @param position La position de la case.
	 * @param touched A vrai si un bateau est touch� � cette position, � faux sinon.
	 */
	private void updateBoxState(Position position, boolean touched) {
		grid[position.getX()][position.getY()] = touched ? SeaBoxState.TOUCHED : SeaBoxState.SHOT; 
	}
	
}
