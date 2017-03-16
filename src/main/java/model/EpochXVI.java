package model;

public class EpochXVI extends Epoch {

	public EpochXVI() {
		this.name = EpochName.XVI_SIECLE;
	}

	@Override
	protected boolean takeDamage(int size, int hitCount) {
		boolean shipIsDead;
		switch(size) {
			case 4 :	// Galion
				// Touch� = coul�
				shipIsDead = hitCount > 0;
				break;
			default :
				// si le bateau est "touch� partout", il est d�truit 
				shipIsDead = size == hitCount;
		}
		return shipIsDead;
	}

}
