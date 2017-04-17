package fr.univ_lorraine.battleship.model;

import java.io.Serializable;

/**
 * Strat�gie de tir de l'ordinateur.
 */
public interface ShootingStrategy extends Serializable {

	/**
	 * Les noms des diff�rentes strat�gies de tir.
	 */
	public enum ShootingStrategyName { RANDOM, CROSS }
		
	/**
	 * Renvoie le nom de la strat�gie.
	 * Utile pour l'UI.
	 * @return Le nom de la strat�gie.
	 */
	public ShootingStrategyName getShootingStrategyName();
	
	/**
	 * Choisit et renvoie une position afin d'y effectuer un tir.
	 * La position de tir doit toujours �tre valide.
	 * @param sea La grille du joueur adverse.
	 * @return La position de tir sur la grille.
	 */
	public Position playShoot(Sea sea);
	
}
