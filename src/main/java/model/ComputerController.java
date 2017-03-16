package model;

/**
 * Entit� contr�lant les actions d'un joueur (l'ordinateur).
 */
public class ComputerController {

	/**
	 * Le joueur contr�l�.
	 */
	private Player computer;
	
	/**
	 * La strat�gie par d�faut.
	 */
	private final static ShootingStrategy DEFAULT_SHOOTING_STRATEGY = new RandomShooting();
	
	/**
	 * Strat�gie � adopter pour les tirs de l'ordinateur.
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
	 * Place tous les bateaux du joueur (de l'ordinateur).
	 */
	public void placeAllShips() {
		//TODO
		// Temporaire
		Sea sea = this.computer.getSelfGrid();
		int i = 0;
		while (!sea.areShipsAllPlaced()) {
			computer.placeShip(new Position(i, 0));
			sea.putNextShipToPlace();
			i++;
		}
	}

	/**
	 * Effectue un tir pour le joueur (l'ordinateur).
	 */
	public void playShoot() {
		if(shootingStrategy != null)
			// Tire � la position renvoy�e par la strat�gie de tir
			computer.shoot(shootingStrategy.playShoot(computer.getOpponentGrid()));
	}
	
}
