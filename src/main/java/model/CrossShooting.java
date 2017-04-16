package model;

/**
 * Stratégie de tir en croix.
 */
public class CrossShooting implements ShootingStrategy {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public ShootingStrategyName getShootingStrategyName() {
		return ShootingStrategyName.CROSS;
	}
	
	@Override
	public Position playShoot(Sea sea) {
		// TODO Auto-generated method stub
		return null;
	}

}
