package model;

/**
 * Classe représentant une époque.
 * Permet de changer le comportement des bateaux du jeu ainsi que leur apparence.
 */
public abstract class Epoch {
	
	/**
	 * Enumération de nom d'époques.
	 */
	public enum EpochName { XVI_SIECLE, XX_SIECLE }
	
	/**
	 * Le nom de l'époque.
	 */
	protected EpochName name;
	
	/**
	 * Méthode indiquant le comportement à adopter à un bateau
	 * selon le bateau (sa taille) et l'époque lorsque qu'il subit un tir.
	 * @param size La longueur du bateau, permet de déterminer de quel type de bateau il s'agit.
	 * @param hits Les dégâts déjà subis par le bateau.
	 * @return L'état du bateau après avoir subi le tir, à vrai si le bateau est détruit.
	 */
	protected abstract boolean takeDamage(int size, boolean[] hits);
	
}
