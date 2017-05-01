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
		Position target = harmed.remove(0); //Cible à viser
		Random rand = new Random();
		ArrayList<Position> shootablePositions = sea.getShootablePositions(target);

		//Si il n'y a qu'une seule cible
		if (harmed.isEmpty()){
			return shootablePositions.get(rand.nextInt(shootablePositions.size())); //Tir au hasard autour de la cible
		}
		
		ArrayList<Position> closeOnes = new ArrayList<>(); //Cibles qui sont contigues à la cible de base
		for (Position p : harmed) {
			if (p.nextTo(target)){
				closeOnes.add(p);
			}			
		}		
		//Si la cible est isolée des autres
		if (closeOnes.isEmpty()){
			return shootablePositions.get(rand.nextInt(shootablePositions.size()));
		}
		
		//Sinon, on cherche à continuer une "ligne" de cibles
		Position lineTail = tail(target, closeOnes.get(0), sea);
		//Si ça n'est pas possible
		if (lineTail == null){
			return shootablePositions.get(rand.nextInt(shootablePositions.size()));
		}
		
		return lineTail;
	}
	
	/**
	 * Retourne une des positions pouvant continuer une ligne de cases touchées entamées, et null si les deux
	 * positions possibles ont été déjà touchées / sont out of bounds.
	 * Ex, grille de 4*4 :
	 * [      ]
	 * [   X  ]
	 * [   X  ]
	 * [      ]
	 * Positions possibles : (0,3) et (3,3)
	 * @param target La position du tir d'origine.
	 * @param nextOne La position du tir touchant à côté de l'origine.
	 * @param sea La mer actuelle.
	 * @return Une deux deux positions possibles, null si aucune possible.
	 */
	private Position tail(Position target, Position nextOne, Sea sea){
		boolean hori = (Math.abs(target.getX() - nextOne.getX()) == 1); //Détermine le sens de la ligne
		
		int x = target.getX(), y = target.getY();
		int xMin = 0, xMax = sea.getGridWidth() - 1, yMin = 0, yMax = sea.getGridHeight()-1;
		
		boolean outOfBounds = false;
		Position aimedAt = new Position(x, y);
		
		if (hori) {
			//Parcours des x vers la droite
			while (sea.isTileTouched(aimedAt)) {
				x++;
				aimedAt = new Position(x, y);
				if (aimedAt.isOutOfBounds(xMin, xMax, yMin, yMax)) {
					outOfBounds = true;
					break;
				}
			}			
			if (!outOfBounds) {
				if (sea.isTileNormal(aimedAt)) return aimedAt;
			}
			
			outOfBounds = false;
			x = target.getX();
			aimedAt = new Position(x, y);
			
			//Parcours des x vers la gauche
			while (sea.isTileTouched(aimedAt)) {
				x--;
				aimedAt = new Position(x, y);
				if (aimedAt.isOutOfBounds(xMin, xMax, yMin, yMax)) {
					outOfBounds = true;
					break;
				}
			}			
			if (!outOfBounds){ 
				if (sea.isTileNormal(aimedAt)) return aimedAt;
			}
		
		} else {
			
			//Parcours des y vers le bas
			while (sea.isTileTouched(aimedAt)) {
				y++;
				aimedAt = new Position(x, y);
				if (aimedAt.isOutOfBounds(xMin, xMax, yMin, yMax)) {
					outOfBounds = true;
					break;
				}
			}			
			if (!outOfBounds) {
				if (sea.isTileNormal(aimedAt)) return aimedAt;
			}
			
			outOfBounds = false;
			y = target.getY();
			aimedAt = new Position(x, y);
			
			//Parcours des y vers le haut
			while (sea.isTileTouched(aimedAt)) {
				y--;
				aimedAt = new Position(x, y);
				if (aimedAt.isOutOfBounds(xMin, xMax, yMin, yMax)) {
					outOfBounds = true;
					break;
				}
			}
			if (!outOfBounds){
				if (sea.isTileNormal(aimedAt)) return aimedAt;
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
