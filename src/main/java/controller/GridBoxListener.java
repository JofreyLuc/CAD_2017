package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Game;

public class GridBoxListener implements MouseListener {

	private Game game;
	
	private int x;
	
	private int y;
	
	public GridBoxListener(Game game, int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		game.receiveClickEvent(this.x, this.y);
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}

}
