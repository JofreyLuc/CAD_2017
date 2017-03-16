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
				// Coul� au 2�me tir
				shipIsDead = hitCount > 1;
				break;
			default :
				// si le bateau est "touch� partout", il est d�truit 
				shipIsDead = size == hitCount;
		}
		return shipIsDead;
	}

}
