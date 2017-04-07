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
	 * Abscisse et ordonn�e de la case concern�e.
	 */
	private int x, y;
	
	/**
	 * GridBoxView attach� (la case).
	 */
	private GridBoxView gridBoxView;
	
	public GridBoxListener(Game game, int x, int y, GridBoxView gridBoxView) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.gridBoxView = gridBoxView;
	}

	/**
	 * G�re un clic sur la case.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		game.receiveClickEvent(this.x, this.y);
	}

	/**
	 * G�re le hover sur la case.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		gridBoxView.setIsHover(true);
	}

	/**
	 * G�re le hover sur la case.
	 */
	@Override
	public void mouseExited(MouseEvent e) {	
		gridBoxView.setIsHover(false);
	}

}
