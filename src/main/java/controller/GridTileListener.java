package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import view.GridTileView;

import model.Game;
import model.Game.PlayerId;

/**
 * Listener d'une case de la grille.
 * G�re les �venements de la case li�e provenant de la souris.
 */
public class GridTileListener extends MouseAdapter {

	/**
	 * Le jeu afin de remonter les �v�nements si n�cessaire.
	 */
	private Game game;
	
	/**
	 * Abscisse et ordonn�e de la case concern�e.
	 */
	private int x, y;
	
	/**
	 * La vue de la case attach�e afin de changer son apparence en cas de hover.
	 */
	private GridTileView gridTileView;
	
	/**
	 * Cr�e le listener d'une case.
	 * @param game Le jeu.
	 * @param x L'abscisse de la case.
	 * @param y L'ordonn�e de la case.
	 * @param gridTileView La vue de la case.
	 */
	public GridTileListener(Game game, int x, int y, GridTileView gridTileView) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.gridTileView = gridTileView;
	}

	/**
	 * G�re un clic sur la case.
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
	 * G�re le hover sur la case.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		gridTileView.setIsHover(true);
		// On remonte les events hover seulement pour la grille du joueur (pr�visualisation placement) 
		if (gridTileView.getPlayerOwner() == PlayerId.PLAYER) {
			game.receiveHoverOnEventOnPlayerGrid(x, y);
		}
	}

	/**
	 * G�re le hover sur la case.
	 */
	@Override
	public void mouseExited(MouseEvent e) {	
		gridTileView.setIsHover(false);
		// On remonte les events hover seulement pour la grille du joueur (pr�visualisation placement) 
		if (gridTileView.getPlayerOwner() == PlayerId.PLAYER) {
			game.receiveHoverOffEventOnPlayerGrid(x, y);
		}
	}

}
