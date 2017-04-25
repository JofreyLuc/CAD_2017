package fr.univ_lorraine.battleship.model;

/**
 * Stratégie de tir "Recherche puis destruction" avec une phase de recherche
 * reposant sur des tirs en croix.
 */
public class SeekThenDestroyCrossShooting extends AbstractSeekThenDestroyShooting {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public ShootingStrategyName getShootingStrategyName() {
		return ShootingStrategyName.SEEK_THEN_DESTROY_CROSS;
	}

	@Override
	protected Position playShootInSeekPhase(Sea sea) {
		// TODO
		return null;
	}

}
