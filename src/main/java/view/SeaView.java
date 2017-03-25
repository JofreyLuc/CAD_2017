package view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import model.Game;
import model.Sea;

@SuppressWarnings("serial")
public class SeaView extends JPanel {

	private Sea sea;
	
	private GridBoxView[][] gridView;
	
	public SeaView(Game game, Sea sea) {
		super();
		this.sea = sea;
		GridLayout layout = new GridLayout(gridView.length, gridView[0].length);
		// Initialisation de la vue de la grille
		gridView = new GridBoxView[sea.getGridWidth()][sea.getGridHeight()];
		this.setLayout(layout);
		for (int i = 0 ; i < gridView.length ; i++)
			for (int j = 0 ; j < gridView[0].length ; j++) {
				gridView[i][j] = new GridBoxView(game, i, j);
				this.add(gridView[i][j]);
			}
	}
}
