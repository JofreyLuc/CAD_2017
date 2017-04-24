package fr.univ_lorraine.battleship.model;

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
		//TODO
		return null;
	}
	
	/**
	 * Change de phase si les conditions sont remplies.
	 * @param sea La grille de l'adversaire.
	 */
	private void updatePhase(Sea sea) {
		// TODO
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
