package model;

public class EpochXX extends Epoch {

	public EpochXX() {
		this.name = EpochName.XX_SIECLE;
	}

	@Override
	protected boolean takeDamage(int size, int hitCount) {
		// Bataille navale classique
		return super.takeDamage(size, hitCount);
	}

}
