package model;

/**
 * Classe repr�sentant une �poque.
 * Permet de changer le comportement des bateaux du jeu ainsi que leur apparence.
 */
public abstract class Epoch {
	
	/**
	 * Enum�ration de nom d'�poques.
	 */
	public enum EpochName { XVI_SIECLE, XX_SIECLE }
	
	/**
	 * Le nom de l'�poque.
	 */
	protected EpochName name;
	
	/**
	 * M�thode indiquant le comportement � adopter � un bateau
	 * selon le bateau (sa taille) et l'�poque lorsque qu'il subit un tir.
	 * @param size La longueur du bateau, permet de d�terminer de quel type de bateau il s'agit.
	 * @param hitCount Le nombre de "touch�s" du bateau.
	 * @return L'�tat du bateau apr�s avoir subi le tir, � vrai si le bateau est d�truit.
	 */
	protected abstract boolean takeDamage(int size, int hitCount);
	
	/**
	 * Retourne le nom de l'�poque.
	 * @return Le nom de l'�poque.
	 */
	public EpochName getEpochName() {
		return name;
	}
}
