package model;

/**
 * Entité contrôlant les actions d'un joueur (l'ordinateur).
 */
public class ComputerController {

	/**
	 * Le joueur contrôlé.
	 */
	private Player computer;
	
	private final static ShootingStrategy DEFAULT_SHOOTING_STRATEGY = new RandomShooting();
	
	/**
	 * Stratégie à adopter pour les tirs de l'ordinateur.
	 */
	private ShootingStrategy shootingStrategy;
	
	/**
	 * Crée un computerController à partir de l'interface du joueur.
	 * @param iPlayer L'interface du joueur.
	 */
	public ComputerController(Player player) {
		this.computer = player;
		this.shootingStrategy = DEFAULT_SHOOTING_STRATEGY;
	}
	
	/**
	 * Place tous les bateaux du joueur (de l'ordinateur).
	 */
	public void placeAllShips() {
		//TODO
	}

	/**
	 * Effectue un tir pour le joueur (l'ordinateur).
	 */
	public void playShoot() {
		if(shootingStrategy != null)
			// Tire à la position renvoyée par la stratégie de tir
			computer.shoot(shootingStrategy.playShoot(computer.getOpponentGrid()));
	}
	
}
