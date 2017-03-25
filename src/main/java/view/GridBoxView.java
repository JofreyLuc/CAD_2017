package view;

import javax.swing.JLabel;

import model.Game;

import controller.GridBoxListener;

@SuppressWarnings("serial")
public class GridBoxView extends JLabel {
	
	public GridBoxView(Game game, int x, int y) {
		this.addMouseListener(new GridBoxListener(game, x, y));
	}
}
