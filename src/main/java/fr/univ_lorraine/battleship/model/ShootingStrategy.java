package fr.univ_lorraine.battleship.model;

import java.io.Serializable;

/**
 * Stratégie de tir de l'ordinateur.
 */
public interface ShootingStrategy extends Serializable {

	/**
	 * Les noms des différentes stratégies de tir.
	 */
	public enum ShootingStrategyName { RANDOM, CROSS }
		
	/**
	 * Renvoie le nom de la stratégie.
	 * Utile pour l'UI.
	 * @return Le nom de la stratégie.
	 */
	public ShootingStrategyName getShootingStrategyName();
	
	/**
	 * Choisit et renvoie une position afin d'y effectuer un tir.
	 * La position de tir doit toujours être valide.
	 * @param sea La grille du joueur adverse.
	 * @return La position de tir sur la grille.
	 */
	public Position playShoot(Sea sea);
	
}
