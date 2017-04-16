package model;

import java.io.Serializable;

/**
 * Classe repr�sentant une �poque.
 * Permet de changer le comportement des bateaux du jeu ainsi que leur apparence.
 */
public abstract class Epoch implements Serializable {
	
	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Enum�ration de nom d'�poques.
	 * Utile pour changer l'apparence du jeu selon l'�poque.
	 */
	public enum EpochName { XVI_SIECLE, XX_SIECLE }
		
	/**
	 * Retourne le nom de l'�poque.
	 * @return Le nom de l'�poque.
	 */
	public abstract EpochName getEpochName();
	
	/**
	 * M�thode indiquant le comportement � adopter � un bateau
	 * selon le bateau (sa taille) et l'�poque lorsque qu'il subit un tir.
	 * @param size La longueur du bateau, permet de d�terminer de quel type de bateau il s'agit.
	 * @param hitCount Le nombre de "touch�s" du bateau.
	 * @return L'�tat du bateau apr�s avoir subi le tir, � vrai si le bateau est d�truit.
	 */
	protected boolean takeDamage(int size, int hitCount) {
		// Si le bateau est "touch� partout", il est d�truit 
		return size == hitCount;
	}
		
}
