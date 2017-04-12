package model;

public class EpochXVI extends Epoch {

	public EpochXVI() {
		this.name = EpochName.XVI_SIECLE;
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
