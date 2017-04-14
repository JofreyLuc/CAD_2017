package model;

import java.util.List;
import java.util.Random;

/**
 * Strat�gie de tir al�atoire.
 */
public class RandomShooting implements ShootingStrategy {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
    private Random RNG;
	
    public RandomShooting() {
    	super();
    	RNG = new Random();
    }
    
	@Override
	public Position playShoot(Sea sea) {
		List<Position> possibleShots = sea.getAllNormalPositions();
		int randomIndex = RNG.nextInt(possibleShots.size());
		return possibleShots.get(randomIndex);
	}

}
