package fr.univ_lorraine.battleship.model;

/**
 * Classe impl�mentant une strat�gie de tir de type "Recherche puis destruction" ou "Chasse/Cible".
 * Avec cette strat�gie, l�ordinateur d�bute en mode Chasse �c�est � dire tire au hasard jusqu�� ce qu�il trouve une cible.
 * Lorsqu�il a touch�, il s�acharne sur les cases adjacentes.
 * Une fois le navire coul�, la chasse reprend jusqu�� l�acquisition d�une nouvelle cible.
 * Classe abstraite qui nous permettra d'impl�menter deux m�thodes diff�rentes pour la phase de chasse/recherche :
 * - tir al�atoire
 * - tir en croix
 */
public abstract class AbstractSeekThenDestroyShooting implements ShootingStrategy {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Bool�en indiquant la phase dans laquelle on se trouve (seek ou destroy).
	 */
	private boolean seekPhase = true;
	
	/**
	 * M�thode de tir quand l'ordinateur est en phase seek.
	 * @param sea La grille du joueur adverse.
	 * @return La position de tir choisie.
	 */
	protected abstract Position playShootInSeekPhase(Sea sea);
	
	/**
	 * M�thode de tir quand l'ordinateur est en phase seek.
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
		updatePhase(sea);	// change de phase si n�cessaire
		if (seekPhase) {
			return playShootInSeekPhase(sea);
		}
		else {
			return playShootInDestroyPhase(sea);
		}
	}

}
