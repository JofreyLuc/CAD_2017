package fr.univ_lorraine.battleship.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * Classe implémentant une stratégie de tir de type "Recherche puis destruction" ou "Chasse/Cible".
 * Avec cette stratégie, l’ordinateur débute en mode Chasse –c’est à dire tire au hasard jusqu’à ce qu’il trouve une cible.
 * Lorsqu’il a touché, il s’acharne sur les cases adjacentes.
 * Une fois le navire coulé, la chasse reprend jusqu’à l’acquisition d’une nouvelle cible.
 * Classe abstraite qui nous permettra d'implémenter deux méthodes différentes pour la phase de chasse/recherche :
 * - tir aléatoire
 * - tir en croix
 */
public abstract class AbstractSeekThenDestroyShooting implements ShootingStrategy {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Booléen indiquant la phase dans laquelle on se trouve (seek ou destroy).
	 */
	private boolean seekPhase = true;
	
	/**
	 * Méthode de tir quand l'ordinateur est en phase seek.
	 * @param sea La grille du joueur adverse.
	 * @return La position de tir choisie.
	 */
	protected abstract Position playShootInSeekPhase(Sea sea);
	
	/**
	 * Méthode de tir quand l'ordinateur est en phase seek.
	 * @param sea La grille du joueur adverse.
	 * @return La position de tir choisie.
	 */
	private Position playShootInDestroyPhase(Sea sea) {
		ArrayList<Position> harmed = sea.harmedShipPositions();
		Position target = harmed.remove(0);
		Random rand = new Random();
		ArrayList<Position> shootablePositions = sea.getShootablePositions(target);

		if (harmed.isEmpty()){
			return shootablePositions.get(rand.nextInt(shootablePositions.size()));
		}
		
		ArrayList<Position> closeOnes = new ArrayList<>();
		for (Position p : harmed) {
			if (p.nextTo(target)){
				closeOnes.add(p);
			}			
		}		
		if (closeOnes.isEmpty()){
			return shootablePositions.get(rand.nextInt(shootablePositions.size()));
		}
		
		Position lineTail = tail(target, closeOnes.get(0), sea); //TODO tail
		if (lineTail == null){
			return shootablePositions.get(rand.nextInt(shootablePositions.size()));
		}
		
		return lineTail;
	}
	
	private Position tail(Position target, Position nextOne, Sea sea){
		int hori = Math.abs(target.getX() - nextOne.getX());
		int x = target.getX(), y = target.getY();
		int xMin = 0, xMax = sea.getGridWidth() - 1, yMin = 0, yMax = sea.getGridHeight()-1;
		
		boolean outOfBounds = false;
		Position aimed = new Position(x, y);
		if (hori == 1) {			
			while (sea.isTileTouched(aimed)) {
				x++;
				aimed = new Position(x, y);
				if (aimed.isOutOfBounds(xMin, xMax, yMin, yMax)) {
					outOfBounds = true;
					break;
				}
			}
			
			if (!outOfBounds) {
				if (sea.isTileNormal(aimed)) return aimed;
			}
			
			outOfBounds = false;
			x = target.getX();
			aimed = new Position(x, y);
			
			while (sea.isTileTouched(aimed)) {
				x--;
				aimed = new Position(x, y);
				if (aimed.isOutOfBounds(xMin, xMax, yMin, yMax)) {
					outOfBounds = true;
					break;
				}
			}
			
			if (!outOfBounds){ 
				if (sea.isTileNormal(aimed)) return aimed;
			}
		
		} else {
			
			while (sea.isTileTouched(aimed)) {
				y++;
				aimed = new Position(x, y);
				if (aimed.isOutOfBounds(xMin, xMax, yMin, yMax)) {
					outOfBounds = true;
					break;
				}
			}
			
			if (!outOfBounds) {
				if (sea.isTileNormal(aimed)) return aimed;
			}
			
			outOfBounds = false;
			y = target.getY();
			aimed = new Position(x, y);
			
			while (sea.isTileTouched(aimed)) {
				y--;
				aimed = new Position(x, y);
				if (aimed.isOutOfBounds(xMin, xMax, yMin, yMax)) {
					outOfBounds = true;
					break;
				}
			}
			
			if (!outOfBounds){
				if (sea.isTileNormal(aimed)) return aimed;
			}
		}
		
		return null;
	}
	
	/**
	 * Change de phase si les conditions sont remplies.
	 * @param sea La grille de l'adversaire.
	 */
	private void updatePhase(Sea sea) {
		if (sea.isAnyShipHarmed()){
			seekPhase = false;
		} else {
			seekPhase = true;
		}
	}
	
	@Override
	public Position playShoot(Sea sea) {
		updatePhase(sea);	// change de phase si nécessaire
		if (seekPhase) {
			return playShootInSeekPhase(sea);
		}
		else {
			return playShootInDestroyPhase(sea);
		}
	}

}
