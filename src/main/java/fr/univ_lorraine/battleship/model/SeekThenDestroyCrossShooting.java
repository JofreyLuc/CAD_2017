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
		Random rand = new Random();
		int xInit = 0;
		Position p;

		for(int y = 0; y < sea.getGridHeight(); y+= 1) {
			for(int x = xInit; x < sea.getGridWidth(); x+=2) {
				p = new Position(x, y);
				if(sea.isTileNormal(p)) shootablePositions.add(p);
			}
			xInit = (xInit+1)%2; // Pour commencer à la case 2 (en x) une ligne sur deux
		}
		
		return shootablePositions.get(rand.nextInt(shootablePositions.size()));
	}
}
