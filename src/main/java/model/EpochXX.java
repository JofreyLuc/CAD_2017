package model;

public class EpochXX extends Epoch {

	public EpochXX() {
		this.name = EpochName.XX_SIECLE;
	}

	@Override
	protected boolean takeDamage(int size, int hitCount) {
		boolean shipIsDead;
		switch(size) {
			case 4 :	// Galion
				// Coulé au 2ème tir
				shipIsDead = hitCount > 1;
				break;
			default :
				// si le bateau est "touché partout", il est détruit 
				shipIsDead = size == hitCount;
		}
		return shipIsDead;
	}

}
