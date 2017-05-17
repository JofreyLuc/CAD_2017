package fr.univ_lorraine.battleship.model;

import java.io.Serializable;

/**
 * Classe représentant une époque.
 * Permet de changer le comportement des bateaux du jeu ainsi que leur apparence.
 */
public abstract class Epoch implements Serializable {
	
	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Enumération de nom d'époques.
	 * Utile pour changer l'apparence du jeu selon l'époque.
	 */
	public enum EpochName { XVI_SIECLE, XX_SIECLE, X_SIECLE }
		
	/**
	 * Retourne le nom de l'époque.
	 * @return Le nom de l'époque.
	 */
	public abstract EpochName getEpochName();
	
	/**
	 * Méthode indiquant le comportement à adopter à un bateau
	 * selon le bateau (sa taille) et l'époque lorsque qu'il subit un tir.
	 * @param size La longueur du bateau, permet de déterminer de quel type de bateau il s'agit.
	 * @param hitCount Le nombre de "touchés" du bateau.
	 * @return L'état du bateau après avoir subi le tir, à vrai si le bateau est détruit.
	 */
	protected boolean takeDamage(int size, int hitCount) {
		// Si le bateau est "touché partout", il est détruit 
		return size == hitCount;
	}
		
}
