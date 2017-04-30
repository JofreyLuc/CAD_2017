package fr.univ_lorraine.battleship.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

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
	 * Place tous les bateaux du joueur (de l'ordinateur) de manière aléatoire.
	 */
	public void placeAllShips() {
		Sea sea = this.computer.getSelfGrid();
		ArrayList<Position> pos = new ArrayList<>(); //Lisye de toutes les positions, diminuant au fil des essais
		Random randomizer = new Random();
		while (!sea.areShipsAllPlaced()) {	
			
			if (Math.random() < 0.5){
				sea.getShipOnPlacing().setOrientation(Orientation.VERTICAL);
			} else {
				sea.getShipOnPlacing().setOrientation(Orientation.HORIZONTAL);
			}
			
			pos.clear();
			for (int x = 0; x < sea.getGridWidth(); x++){
				for (int y = 0; y < sea.getGridHeight(); y++){
					pos.add(new Position(x, y));
				}
			}
			
			Position random_pos = pos.get(randomizer.nextInt(pos.size()));
			while (!computer.placeShip(random_pos)) {
				pos.remove(random_pos);
				random_pos = pos.get(randomizer.nextInt(pos.size()));
			}
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
