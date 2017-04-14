package model;

import java.io.Serializable;

/**
 * Stratégie de tir.
 */
public interface ShootingStrategy extends Serializable {

	/**
	 * Choisit et renvoie une position afin d'y effectuer un tir.
	 * @param sea La grille du joueur adv
	 * @return La position de tir sur la grille.
	 */
	public Position playShoot(Sea sea);
	
}
