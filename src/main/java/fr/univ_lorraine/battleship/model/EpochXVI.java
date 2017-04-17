package fr.univ_lorraine.battleship.model;

public class EpochXVI extends Epoch {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public EpochName getEpochName() {
		return EpochName.XVI_SIECLE;
	}
	
	@Override
	protected boolean takeDamage(int size, int hitCount) {
		switch(size) {
			case 5:
				return hitCount >= 3;
			case 4:
				return  hitCount >= 2;
			case 3:
				return hitCount >= 2;
			case 2:
				return hitCount >= 1;
			default:
				return super.takeDamage(size, hitCount);
		}
	}

}
