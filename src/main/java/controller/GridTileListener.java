package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import view.GridTileView;

import model.Game;
import model.Game.PlayerId;

/**
 * MouseAdapater d'une case de la grille.
 */
public class GridTileListener extends MouseAdapter {

	/**
	 * Le jeu.
	 */
	private Game game;
	
	/**
	 * Abscisse et ordonnée de la case concernée.
	 */
	private int x, y;
	
	/**
	 * GridTileView attaché (la case).
	 */
	private GridTileView gridTileView;
	
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
		if (SwingUtilities.isLeftMouseButton(e)) {			// Clic gauche = placement
			switch(gridTileView.getPlayerOwner()) {
				case COMPUTER:
					game.receiveClickEventOnComputerGrid(this.x, this.y);
					break;
				case PLAYER:
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
		if (gridTileView.getPlayerOwner() == PlayerId.PLAYER) {
			game.receiveHoverOffEventOnPlayerGrid(x, y);
		}
	}

}
