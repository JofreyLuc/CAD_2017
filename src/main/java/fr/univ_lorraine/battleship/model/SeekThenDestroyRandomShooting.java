package fr.univ_lorraine.battleship.model;

/**
 * Stratégie de tir "Recherche puis destruction" avec une phase de recherche
 * reposant sur des tirs au hasard.
 */
public class SeekThenDestroyRandomShooting extends AbstractSeekThenDestroyShooting {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Utilisation de la stratégie de tir aléatoire pour générer les tirs de la phase de recherche.
	 */
	private static final RandomShooting randomShooting = new RandomShooting();
	
	@Override
	public ShootingStrategyName getShootingStrategyName() {
		return ShootingStrategyName.SEEK_THEN_DESTROY_RANDOM;
	}

	@Override
	protected Position playShootInSeekPhase(Sea sea) {
		return randomShooting.playShoot(sea);
	}

}
