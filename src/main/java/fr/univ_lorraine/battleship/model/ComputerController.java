package fr.univ_lorraine.battleship.model;

import java.io.Serializable;

import fr.univ_lorraine.battleship.model.Ship.Orientation;


/**
 * Entité contrôlant les actions d'un joueur (l'ordinateur).
 */
public class ComputerController implements Serializable {

	/**
	 * Id pour la serialization.
	 * @serial
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Le joueur contrôlé.
	 * @serial
	 */
	private Player computer;
	
	/**
	 * Stratégie à adopter pour les tirs de l'ordinateur.
	 * @serial
	 */
	private ShootingStrategy shootingStrategy;
	
	/**
	 * La stratégie par défaut.
	 */
	private static final ShootingStrategy DEFAULT_SHOOTING_STRATEGY = new RandomShooting();
		
	/**
	 * Crée un computerController à partir de l'interface du joueur
	 * et avec la stratégie de tir par défaut.
	 * @param iPlayer L'interface du joueur.
	 */
	public ComputerController(Player player) {
		this.computer = player;
		this.shootingStrategy = DEFAULT_SHOOTING_STRATEGY;
	}
	
	/**
	 * Retourne la stratégie de tir de l'ordinateur.
	 * @return La stratégie de tir de l'ordinateur.
	 */
	public ShootingStrategy getShootingStrategy() {
		return shootingStrategy;
	}
	
	/**
	 * Prend la stratégie de tir passé en paramètre.
	 * @param shootingStrategy La nouvelle stratégie de tir de l'ordinateur.
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
			// Tire à la position renvoyée par la stratégie de tir
			computer.shoot(shootingStrategy.playShoot(computer.getOpponentGrid()));
		}
	}
	
}
