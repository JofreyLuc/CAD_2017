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
				// Touché = coulé
				shipIsDead = hitCount > 0;
				break;
			default :
				// si le bateau est "touché partout", il est détruit 
				shipIsDead = size == hitCount;
		}
		return shipIsDead;
	}

}
