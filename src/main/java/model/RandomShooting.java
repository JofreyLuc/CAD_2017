package model;

import java.util.List;
import java.util.Random;

/**
 * Stratégie de tir aléatoire.
 */
public class RandomShooting implements ShootingStrategy {

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
