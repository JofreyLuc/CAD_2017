package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Classe représentant la grille d'un joueur.
 */
public class Sea {

	private final static int GRID_WIDTH = 10;
	
	private final static int GRID_HEIGHT = 10;
	
	private final static int[] SHIPS_SIZES = {5, 4, 3, 3, 2};
	
	/**
	 * Enumération des états d'une case de la grille.
	 */
	public enum SeaBoxState { NORMAL, SHOT, TOUCHED }
	
	/**
	 * Etats de la grille.
	 */
	private SeaBoxState[][] grid;
	
	/**
	 * Les bateaux qui ne sont pas encore placé sur la grille.
	 */
	List<Ship> shipsToPlace;
	
	/**
	 * Le bateau en cours de placement.
	 */
	Ship shipOnPlacing;
	
	/**
	 * Les bateaux placés sur la grille.
	 */
	List<Ship> ships;
	
	/**
	 * Crée une grille à partir de l'époque associée.
	 * @param epoch L'époque.
	 */
	public Sea(Epoch epoch) {
		// Initialisation de la grille
		this.grid = new SeaBoxState[GRID_WIDTH][GRID_HEIGHT];
		for (int i = 0 ; i < grid.length ; i++)
			Arrays.fill(this.grid[i], SeaBoxState.NORMAL);
		// Initialisation des bateaux
		shipsToPlace = new ArrayList<Ship>(SHIPS_SIZES.length);
		ships = new ArrayList<Ship>(SHIPS_SIZES.length);
		for (int i = 0 ; i < SHIPS_SIZES.length ; i++)
			shipsToPlace.add(new Ship(SHIPS_SIZES[i], epoch));
	}
	
	/**
	 * Retourne la largeur de la grille.
	 * @return La largeur de la grille.
	 */
	public int getGridWidth() {
		return grid.length;
	}
	
	/**
	 * Retourne la hauteur de la grille.
	 * @return La hauteur de la grille.
	 */
	public int getGridHeight() {
		return grid[0].length;
	}
	
	/**
	 * Retourne le bateau en cours de placement.
	 * @return Le bateau en cours de placement.
	 */
	public Ship getShipOnPlacing() {
		return shipOnPlacing;
	}
	
	/**
	 * Regarde si tous les bateaux sont placés sur la grille.
	 * @return Vrai si tous les bateaux sont placés sur la grille, faux sinon.
	 */
	public boolean areShipsAllPlaced() {
		return shipsToPlace.isEmpty() && shipOnPlacing == null;
	}
	
	/**
	 * Regarde si tous les bateaux de la grille sont détruits.
	 * @return Vrai si tous les bateaux sont détruits, faux sinon.
	 */
	public boolean areShipsAllDead() {
		if (!areShipsAllPlaced())	// S'il reste des bateaux à placer,
			return false;			// les bateaux ne peuvent pas être tous détruits
		
		boolean allDead = true;
		Iterator<Ship> iter = ships.iterator();
		while (iter.hasNext() && allDead) {	// On regarde si tous les bateaux sont détruits
			Ship ship = iter.next();
			allDead = ship.isDead();
		}
		return allDead;
	}
	
	/**
	 * Regarde si la case à cette position est libre.
	 * Retourne faux si la case est hors des limites de la grille.
	 * @param position La position de la case.
	 * @return Vrai si la case à cette position est libre, faux sinon.
	 */
	public boolean isSeaBoxFree(Position position) {
		if (position.isOutOfBounds(0, grid.length-1, 0, grid[0].length-1))
			return false;
		
		boolean boxFree = true;
		Iterator<Ship> iter = ships.iterator();
		while (iter.hasNext() && boxFree) {
			Ship ship = iter.next();
			boxFree = !Arrays.asList(ship.getSeaBoxesOccupied()).contains(position);
			// On vérifie si la case est occupée par le bateau
		}
		return boxFree;
	}
	
	/**
	 * Renvoie la liste des positions où aucun tir n'a été effectué.
	 * @return La liste des positions où aucun tir n'a été effectué.
	 */
	public List<Position> getAllNormalPositions() {
		List<Position> possibleShots = new ArrayList<Position>(grid.length * grid[0].length);
		for (int i = 0 ; i < grid.length ; i++)
			for (int j = 0 ; j < grid[0].length ; j++)
				if (grid[i][j] == SeaBoxState.NORMAL)
					possibleShots.add(new Position(i, j));
		
		return possibleShots;
	}
	
	/**
	 * Prend un bateau de la liste des bateaux à placer et le met
	 * en tant que bateau en cours de positionnement s'il n'y en a pas déjà un.
	 */
	public void putNextShipToPlace() {
		if (shipOnPlacing == null && !shipsToPlace.isEmpty())
			shipOnPlacing = shipsToPlace.remove(0);
	}
	
	/**
	 * Valide le placement du bateau en cours de positionnement
	 * en l'ajoutant à la liste des bateaux actif
	 * et le remet à null.
	 */
	public void validateShipPlacement() {
		if (shipOnPlacing != null)
			ships.add(shipOnPlacing);
		shipOnPlacing = null;
	}

	/**
	 * Fait le nécessaire après le tir d'un joueur.
	 * @param shotPos La position du tir.
	 * @return Vrai si le tir est valide, faux sinon.
	 */
	public boolean receiveShot(Position shotPos) {
		// Coordonnées du tir non valide
		if (shotPos.isOutOfBounds(0, grid.length-1, 0, grid[0].length-1))
			return false;
		// Si un tir à cette position a déjà été effectué
		if (grid[shotPos.getX()][shotPos.getY()] != SeaBoxState.NORMAL)
			return false;
		
		boolean touched = false;
		Iterator<Ship> iter = ships.iterator();
		while (iter.hasNext() && !touched) {	// Pour chaque bateau,
			Ship ship = iter.next();
			touched = ship.checkShot(shotPos);	// on regarde si il est touché
		}
		
		updateBoxState(shotPos, touched);		// on met à jour l'état de la position du tir
		
		return true;							// on indique que le tir est valide
	}
	
	/**
	 * Met à jour l'état de la case de la grille selon si un bateau se trouve
	 * à cette position et donc est touché ou non.
	 * @param position La position de la case.
	 * @param touched A vrai si un bateau est touché à cette position, à faux sinon.
	 */
	private void updateBoxState(Position position, boolean touched) {
		grid[position.getX()][position.getY()] = touched ? SeaBoxState.TOUCHED : SeaBoxState.SHOT; 
	}

	@Override
	public String toString() {
		String s = "Sea [shipsToPlace : ";
		for (Ship ship : shipsToPlace)
			s += ship + " , ";
		s+= "\nshipOnPlacing : " + shipOnPlacing;
		s+= "\nships : ";
		for (Ship ship : ships)
			s += ship + " , ";
		return s;
	}
	
}
