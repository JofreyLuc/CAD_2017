package model;

import java.io.Serializable;

import model.Ship.Orientation;

/**
 * Entit� contr�lant les actions d'un joueur (l'ordinateur).
 */
public class ComputerController implements Serializable {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Le joueur contr�l�.
	 * @serial
	 */
	private Player computer;
	
	/**
	 * La strat�gie par d�faut.
	 */
	private final static ShootingStrategy DEFAULT_SHOOTING_STRATEGY = new RandomShooting();
	
	/**
	 * Strat�gie � adopter pour les tirs de l'ordinateur.
	 * @serial
	 */
	private ShootingStrategy shootingStrategy;
	
	/**
	 * Cr�e un computerController � partir de l'interface du joueur.
	 * @param iPlayer L'interface du joueur.
	 */
	public ComputerController(Player player) {
		this.computer = player;
		this.shootingStrategy = DEFAULT_SHOOTING_STRATEGY;
	}
	
	/**
	 * Retourne la strat�gie de tir de l'ordinateur.
	 * @return la strat�gie de tir de l'ordinateur.
	 */
	public ShootingStrategy getShootingStrategy() {
		return shootingStrategy;
	}
	
	/**
	 * Prend la strat�gie de tir pass� en param�tre.
	 * @param shootingStrategy La nouvelle strat�gie de tir de l'ordinateur.
	 */
	public void setShootingStrategy(ShootingStrategy shootingStrategy) {
		this.shootingStrategy = shootingStrategy;
	}

	/**
	 * Place tous les bateaux du joueur (de l'ordinateur).
	 */
	public void placeAllShips() {
		//TODO
		// Temporaire
		Sea sea = this.computer.getSelfGrid();
		int i = 0;
		while (!sea.areShipsAllPlaced()) {
			sea.getShipOnPlacing().setOrientation(Orientation.VERTICAL);
			computer.placeShip(new Position(i, 0));
			i++;
		}
	}

	/**
	 * Effectue un tir pour le joueur (l'ordinateur).
	 */
	public void playShoot() {
		if(shootingStrategy != null) {
			// Tire � la position renvoy�e par la strat�gie de tir
			computer.shoot(shootingStrategy.playShoot(computer.getOpponentGrid()));
		}
	}
	
}
