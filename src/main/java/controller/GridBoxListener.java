package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import view.GridBoxView;

import model.Game;

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
		game.receiveClickEvent(this.x, this.y);
	}

	/**
	 * Gére le hover sur la case.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		gridBoxView.setIsHover(true);
	}

	/**
	 * Gére le hover sur la case.
	 */
	@Override
	public void mouseExited(MouseEvent e) {	
		gridBoxView.setIsHover(false);
	}

}
