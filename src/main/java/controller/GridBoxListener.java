package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import view.GridBoxView;

import model.Game;
import model.Game.PlayerId;

/**
 * MouseAdapater d'une case de la grille.
 */
public class GridBoxListener extends MouseAdapter {

	/**
	 * Le jeu.
	 */
	private Game game;
	
	/**
	 * Abscisse et ordonnée de la case concernée.
	 */
	private int x, y;
	
	/**
	 * GridBoxView attaché (la case).
	 */
	private GridBoxView gridBoxView;
	
	public GridBoxListener(Game game, int x, int y, GridBoxView gridBoxView) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.gridBoxView = gridBoxView;
	}

	/**
	 * Gére un clic sur la case.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {			// Clic gauche = placement
			switch(gridBoxView.getPlayerOwner()) {
				case COMPUTER:
					game.receiveClickEventOnComputerGrid(this.x, this.y);
					break;
				case PLAYER:
					game.receiveClickEventOnPlayerGrid(this.x, this.y);
					break;
				default:
					throw new AssertionError("Joueur inconnu " + gridBoxView.getPlayerOwner());		
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
		gridBoxView.setIsHover(true);
		if (gridBoxView.getPlayerOwner() == PlayerId.PLAYER) {
			game.receiveHoverOnEventOnPlayerGrid(x, y);
		}
	}

	/**
	 * Gére le hover sur la case.
	 */
	@Override
	public void mouseExited(MouseEvent e) {	
		gridBoxView.setIsHover(false);
		if (gridBoxView.getPlayerOwner() == PlayerId.PLAYER) {
			game.receiveHoverOffEventOnPlayerGrid(x, y);
		}
	}

}
