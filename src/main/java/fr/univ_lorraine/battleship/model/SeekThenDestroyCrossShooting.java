package fr.univ_lorraine.battleship.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Stratégie de tir "Recherche puis destruction" avec une phase de recherche
 * reposant sur des tirs en croix.
 */
public class SeekThenDestroyCrossShooting extends AbstractSeekThenDestroyShooting {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public ShootingStrategyName getShootingStrategyName() {
		return ShootingStrategyName.SEEK_THEN_DESTROY_CROSS;
	}

	@Override
	protected Position playShootInSeekPhase(Sea sea) {

		ArrayList<Position> shootablePositions = new ArrayList<>();

		int x, y;
		int width = sea.getGridWidth();
		int height = sea.getGridHeight();

		for(int i = 0; i < width*height; i += 2) {
			x = i%width;
			y = (int)Math.floor(i/width);

			Position p = new Position(x, y);

			if(sea.isTileNormal(p)) shootablePositions.add(p);
		}

		Random rand = new Random();

		return shootablePositions.get(rand.nextInt(shootablePositions.size()));
	}
}
