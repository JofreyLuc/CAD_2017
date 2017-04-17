package fr.univ_lorraine.battleship.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import fr.univ_lorraine.battleship.model.Game;
import fr.univ_lorraine.battleship.model.Game.PlayerId;
import fr.univ_lorraine.battleship.view.GridTileView;



/**
 * Listener d'une case de la grille.
 * Gére les évenements de la case liée provenant de la souris.
 */
public class GridTileListener extends MouseAdapter {

	/**
	 * Le jeu afin de remonter les événements si nécessaire.
	 */
	private Game game;
	
	/**
	 * Abscisse et ordonnée de la case concernée.
	 */
	private int x, y;
	
	/**
	 * La vue de la case attachée afin de changer son apparence en cas de hover.
	 */
	private GridTileView gridTileView;
	
	/**
	 * Crée le listener d'une case.
	 * @param game Le jeu.
	 * @param x L'abscisse de la case.
	 * @param y L'ordonnée de la case.
	 * @param gridTileView La vue de la case.
	 */
	public GridTileListener(Game game, int x, int y, GridTileView gridTileView) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.gridTileView = gridTileView;
	}

	/**
	 * Gére un clic sur la case.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {			// Clic gauche = click event
			switch(gridTileView.getPlayerOwner()) {
				case COMPUTER:								// grille ordi
					game.receiveClickEventOnComputerGrid(this.x, this.y);
					break;
				case PLAYER:								// grille joueur
					game.receiveClickEventOnPlayerGrid(this.x, this.y);
					break;
				default:
					throw new AssertionError("Joueur inconnu " + gridTileView.getPlayerOwner());		
			}
		}
		else if (SwingUtilities.isRightMouseButton(e)) {	// Clic droit = rotation
			game.receiveRotateShipEvent();
		}
	}

	/**
	 * Gére le hover sur la case.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		gridTileView.setIsHover(true);
		// On remonte les events hover seulement pour la grille du joueur (prévisualisation placement) 
		if (gridTileView.getPlayerOwner() == PlayerId.PLAYER) {
			game.receiveHoverOnEventOnPlayerGrid(x, y);
		}
	}

	/**
	 * Gére le hover sur la case.
	 */
	@Override
	public void mouseExited(MouseEvent e) {	
		gridTileView.setIsHover(false);
		// On remonte les events hover seulement pour la grille du joueur (prévisualisation placement) 
		if (gridTileView.getPlayerOwner() == PlayerId.PLAYER) {
			game.receiveHoverOffEventOnPlayerGrid(x, y);
		}
	}

}
