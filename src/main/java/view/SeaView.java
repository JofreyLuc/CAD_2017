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

import model.Game;
import model.Sea;

/**
 * Vue abstraite d'une grille de jeu h�ritant de JPanel.
 * Celle-ci poss�de les vues des cases de la grille sous forme de composants
 * et les vues des bateaux qui ne sont pas des composants et d�l�gue ainsi l'affichage
 * de ces donn�es du mod�le � ces vues.
 * Passe les mises � jour de la grille mod�le aux vues de la grille
 * alors que les vues des bateaux re�oivent eux-m�mes les mises � jour des bateaux.
 */
@SuppressWarnings("serial")
public abstract class SeaView extends JPanel implements Observer {

	/**
	 * La grille repr�sent�e (mod�le).
	 */
	protected Sea sea;
	
	/**
	 * Les vues de chaque case de la grille.
	 * H�rite de JComponent.
	 */
	protected GridBoxView[][] gridView;
		
	/**
	 * Les vues des bateaux plac�s sur la grille.
	 * Ne sont pas des composants, sont juste dessiner par-dessus le fond.
	 */
	protected List<ShipView> shipViews;
	
	public SeaView(Game game, Sea sea) {
		super();
		this.sea = sea;
		sea.addObserver(this);
		
		// Initialisation de la vue de la grille (une vue par case)
		gridView = new GridBoxView[sea.getGridWidth()][sea.getGridHeight()];
		GridLayout layout = new GridLayout(gridView.length, gridView[0].length);
		this.setLayout(layout);
		// Parcours ordonn�e puis abscisse pour l'ajout dans le gridlayout
		for (int y = 0 ; y < gridView[0].length ; y++) {
			for (int x = 0 ; x < gridView.length ; x++) {
				gridView[x][y] = new GridBoxView(sea.getGridBoxState(x, y));
				setGridBoxViewInteractability(gridView[x][y], x, y, game);	// on rend la case cliquable ou non
				this.add(gridView[x][y]);
				
			}
		}
		
		// Initialisation de la vue des bateaux plac�s
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
			shipView.draw(g, this.getWidth()/gridView.length);
		}
	}
	
	/**
	 * Rend la case cliquable ou non (ainsi que la gestion du hover)
	 * selon le type de la vue (celle du joueur courant ou de l'adversaire).
	 * @param gridBoxView La vue de la case.
	 * @param x L'abscisse de la case.
	 * @param y L'ordonn�e de la case.
	 * @param game La classe mod�le du jeu (pour faire le lien avec le contr�leur).
	 */
	protected abstract void setGridBoxViewInteractability(GridBoxView gridBoxView, int x, int y, Game game);
	
	/**
	 * Change les conditions de visibilit� du bateau
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
	 * Met � jour la vue.
	 * Cr�e les vues des bateaux qui viennent d'�tre plac�s.
	 */
	@Override
	public void update(Observable o, Object arg) {
		// Met � jour les vues des cases de la grille
		for (int row = 0 ; row < gridView.length ; row++) {
			for (int col = 0 ; col < gridView[0].length ; col++) {
				gridView[row][col].setState(sea.getGridBoxState(row, col));
			}
		}
		
		// Pour chaque bateau plac� dans le mod�le qui n'a pas de vue associ�e
		for (int i = sea.getShips().size()-1 ; sea.getShips().size() != shipViews.size() ; i++) {
			ShipView shipView = new ShipView(sea.getShips().get(i));
			setShipVisiblity(shipView);	// on change les conditions d'affichage du bateau
			shipViews.add(shipView);
		}
	}
	
}
