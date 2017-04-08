package view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controller.GridBoxListener;

import model.Game;
import model.Sea;
import model.Game.PlayerId;
import model.Ship;

/**
 * Vue abstraite d'une grille de jeu héritant de JPanel.
 * Celle-ci possède les vues des cases de la grille sous forme de composants
 * et les vues des bateaux qui ne sont pas des composants et délègue ainsi l'affichage
 * de ces données du modèle à ces vues.
 * Passe les mises à jour de la grille modèle aux vues de la grille
 * alors que les vues des bateaux reçoivent eux-mêmes les mises à jour des bateaux.
 */
@SuppressWarnings("serial")
public abstract class SeaView extends JPanel implements Observer {
		
	/**
	 * Les vues de chaque case de la grille.
	 * Hérite de JComponent.
	 */
	protected GridBoxView[][] gridBoxViews;
		
	/**
	 * Les vues des bateaux placés sur la grille.
	 * Ne sont pas des composants, sont juste dessiner par-dessus le fond.
	 */
	protected List<ShipView> shipViews;
	
	/**
	 * La vue du bateau en cours de positionnement.
	 * Pareil que ShipView.
	 */
	protected ShipOnPlacingView shipOnPlacingView;
	
	/**
	 * Le dernier bateau en cours de positionnement.
	 * Permet de gérer les changements de bateau.
	 */
	protected Ship lastShipOnPlacing;
	
	public SeaView(Game game) {
		super();
		game.addObserver(this);
		Sea sea = getSelfSea(game);
		
		// Initialisation de la vue de la grille (une vue par case)
		gridBoxViews = new GridBoxView[sea.getGridWidth()][sea.getGridHeight()];
		GridLayout layout = new GridLayout(gridBoxViews.length, gridBoxViews[0].length);
		this.setLayout(layout);
		// Parcours ordonnée puis abscisse pour l'ajout dans le gridlayout
		for (int y = 0 ; y < gridBoxViews[0].length ; y++) {
			for (int x = 0 ; x < gridBoxViews.length ; x++) {
				gridBoxViews[x][y] = new GridBoxView(getPlayerOwner(), sea.getGridBoxState(x, y));
				gridBoxViews[x][y].addMouseListener(new GridBoxListener(game, x, y, gridBoxViews[x][y]));		
				this.add(gridBoxViews[x][y]);
				
			}
		}
		
		// Initialisation de la vue des bateaux placés
		shipViews = new ArrayList<ShipView>(sea.getShipsToPlace().size());
	}
		
	/**
	 * Dessine le fond du panel/de la grille.
	 * @param g L'objet Graphics.
	 */
	private void drawBackground(Graphics g) {
		int seaGridSize = this.getWidth();
		// On dessine l'animation de la mer
		ImageFactory.getInstance().getSeaBackgroundAnimation().draw(g, 0, 0, seaGridSize, seaGridSize);
		// On dessine la grille par-dessus
		g.drawImage(ImageFactory.getInstance().getGridImage(), 0, 0, seaGridSize, seaGridSize, null);
	}
	
	/**
	 * Dessine les bateaux.
	 * @param g L'objet Graphics.
	 */
	private void drawShips(Graphics g) {
		for(ShipView shipView : shipViews) {
			shipView.draw(g, this.getWidth()/gridBoxViews.length);
		}
		if (shipOnPlacingView != null) {
			shipOnPlacingView.draw(g, this.getWidth()/gridBoxViews.length);
		}
	}
	
	/**
	 * Retourne le joueur propriétaire de la grille.
	 * @return Le joueur propriétaire de la grille.
	 */
	protected abstract PlayerId getPlayerOwner();
	
	/**
	 * Retourne la grille représentée.
	 * @param game Le jeu.
	 * @return La grille représentée.
	 */
	protected Sea getSelfSea(Game game) {
		return game.getPlayer(getPlayerOwner()).getSelfGrid();
	}
	
	/**
	 * Indique si le viseur peut-être affiché si une case de la grille est hover.
	 * @param game Le jeu.
	 * @return Booléen indiquant si le viseur peut-être affiché si une case de la grille est hover.
	 */
	protected abstract boolean canBoxesDisplayHoverImage(Game game);
	
	/**
	 * Change les conditions de visibilité du bateau
	 * selon le type de la vue.
	 * @param shipView La vue du bateau.
	 */
	protected abstract void setShipVisiblity(ShipView shipView);
	
	/**
	 * Dessine la grille de jeu.
	 */
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawShips(g);
	}
	
	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(263, 263);
	}
	
	/*
	@Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        Container c = getParent();
        if (c != null) {
            d = c.getSize();
        } else {
            return new Dimension(10, 10);
        }
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();
        int s = (w < h ? w : h);
        return new Dimension(s, s);
    }*/

	/**
	 * Met à jour la vue.
	 * Crée les vues des bateaux qui viennent d'être placés.
	 */
	@Override
	public void update(Observable o, Object arg) {
		Game game = (Game) o;
		Sea sea = getSelfSea(game);

		// Met à jour les vues des cases de la grille
		for (int row = 0 ; row < gridBoxViews.length ; row++) {
			for (int col = 0 ; col < gridBoxViews[0].length ; col++) {
				gridBoxViews[row][col].setState(sea.getGridBoxState(row, col));
				gridBoxViews[row][col].setCanDisplayHoverImage(canBoxesDisplayHoverImage(game));
			}
		}
		
		// Pour chaque bateau placé dans le modèle qui n'a pas de vue associée
		for (int i = shipViews.size() ; sea.getShips().size() != shipViews.size() ; i++) {
			sea.getShips().get(i).deleteObserver(shipOnPlacingView);
			ShipView shipView = new ShipView(sea.getShips().get(i));
			setShipVisiblity(shipView);	// on change les conditions d'affichage du bateau
			shipViews.add(shipView);
		}
		
		if (sea.getShipOnPlacing() == null) {
			shipOnPlacingView = null;
		}
		else if (sea.getShipOnPlacing() != lastShipOnPlacing) {
				shipOnPlacingView = new ShipOnPlacingView(sea.getShipOnPlacing());
		}
		
		if (shipOnPlacingView != null) {
			shipOnPlacingView.setValidPlacement(sea.isShipOnPlacingInValidPosition());
		}
	}
	
}
