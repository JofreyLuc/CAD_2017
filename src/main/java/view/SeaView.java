package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import view.AnimationWithCallback.Callback;

import controller.EndShotAnimationListener;
import controller.GridTileListener;

import model.Game;
import model.Sea;
import model.Game.PlayerId;
import model.Ship;

/**
 * Vue abstraite d'une grille de jeu h�ritant de JPanel.
 * Celle-ci poss�de les vues des cases de la grille sous forme de composants
 * et les vues des bateaux qui ne sont pas des composants et d�l�gue ainsi l'affichage
 * de ces donn�es du mod�le � ces vues.
 * 
 * Passe les mises � jour de la grille (mod�le) aux vues de la grille
 * alors que les vues des bateaux re�oivent eux-m�mes les mises � jour des bateaux (mod�le).
 */
@SuppressWarnings("serial")
public abstract class SeaView extends JPanel implements Observer {
		
	/**
	 * Les vues de chaque case de la grille.
	 * H�rite de JComponent.
	 */
	protected GridTileView[][] gridTileViews;
		
	/**
	 * Les vues des bateaux plac�s sur la grille.
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
	 * Permet de g�rer les changements de bateau.
	 */
	protected Ship lastShipOnPlacing;
	
	/**
	 * Indique si cette grille est active.
	 * C'est-�-dire si elle attend une action (selon le tour du joueur).
	 */
	protected boolean active;
	
	/**
	 * Animation de fond de la mer.
	 */
	protected Animation backgroundAnim;
	
	/**
	 * La grille a une dimension fixe afin s'adapter aux images.
	 */
	private static final Dimension DIMENSION = new Dimension(263, 263);
	
	/**
	 * Construit la vue d'une grille � partir du jeu et du listener des animations.
	 * @param game Le jeu.
	 * @param endShotAnimationListener Le listener de fin d'animation.
	 */
	public SeaView(Game game, final EndShotAnimationListener endShotAnimationListener) {
		super();
		game.addObserver(this);
		// R�cup�re la grille, diff�rent selon s'il s'agit de la vue de la grille du joueur ou de l'ordinateur
		Sea sea = getSelfSea(game);
		
		// Cr�e le callback pour les animations de tir en les liant au listener de fin d'animation
		Callback tirAnimCallback = new Callback() {
			@Override
			public void completed(Animation source) {
				endShotAnimationListener.onEndShotAnimation();
			}	
		};
		
		// Initialisation de la vue de la grille (une vue par case)
		gridTileViews = new GridTileView[sea.getGridWidth()][sea.getGridHeight()];
		GridLayout layout = new GridLayout(gridTileViews.length, gridTileViews[0].length);
		this.setLayout(layout);
		// Parcours ordonn�e puis abscisse pour l'ajout dans le gridlayout
		for (int y = 0 ; y < gridTileViews[0].length ; y++) {
			for (int x = 0 ; x < gridTileViews.length ; x++) {
				gridTileViews[x][y] = new GridTileView(getPlayerOwner(), sea.getGridTileState(x, y), tirAnimCallback);
				gridTileViews[x][y].addMouseListener(new GridTileListener(game, x, y, gridTileViews[x][y]));		
				this.add(gridTileViews[x][y]);
			}
		}
		
		// Initialisation de la vue des bateaux plac�s
		shipViews = new ArrayList<ShipView>(sea.getShipsToPlace().size());
		
		// Animation de la mer en fond
		switch(game.getEpoch().getEpochName()) {
		case XVI_SIECLE:
			backgroundAnim = ImageFactory.getInstance().getSeaXVIBackgroundAnimation();
			break;
		case XX_SIECLE:
			backgroundAnim = ImageFactory.getInstance().getSeaXXBackgroundAnimation();
			break;
		default:
			throw new AssertionError("Epoque inconnu " + game.getEpoch().getEpochName());
		}
	}
		
	/**
	 * Retourne le joueur propri�taire de la grille.
	 * @return Le joueur propri�taire de la grille.
	 */
	protected abstract PlayerId getPlayerOwner();
	
	/**
	 * Retourne la grille repr�sent�e.
	 * @param game Le jeu.
	 * @return La grille repr�sent�e.
	 */
	protected Sea getSelfSea(Game game) {
		return game.getPlayer(getPlayerOwner()).getSelfGrid();
	}
	
	/**
	 * Indique si le viseur peut-�tre affich� si une case de la grille est hover.
	 * @param game Le jeu.
	 * @return Bool�en indiquant si le viseur peut-�tre affich� si une case de la grille est hover.
	 */
	protected abstract boolean canTilesDisplayHoverImage(Game game);
	
	/**
	 * Change les conditions de visibilit� du bateau
	 * selon le type de la vue.
	 * @param shipView La vue du bateau.
	 */
	protected abstract void setShipVisiblity(ShipView shipView);
	
	/**
	 * Dessine le fond du panel/de la grille.
	 * @param g L'objet Graphics.
	 */
	private void drawBackground(Graphics g) {
		int seaGridSize = this.getWidth();
		// On dessine l'animation de la mer
		backgroundAnim.draw(g, 0, 0, seaGridSize, seaGridSize);
		// On dessine la grille par-dessus
		g.drawImage(ImageFactory.getInstance().getGridImage(), 0, 0, seaGridSize, seaGridSize, null);
	}
	
	/**
	 * Dessine les bateaux.
	 * @param g L'objet Graphics.
	 */
	private void drawShips(Graphics g) {
		for(ShipView shipView : shipViews) {
			shipView.draw(g, this.getWidth()/gridTileViews.length);
		}
		if (shipOnPlacingView != null) {
			shipOnPlacingView.draw(g, this.getWidth()/gridTileViews.length);
		}
	}
		
	/**
	 * Dessine la grille de jeu.
	 */
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawShips(g);
	}
	
	/**
	 * Dessine le cadre indiquant que la grille est active si c'est le cas.
	 * Impl�ment� en surchargant la m�thode paint afin de dessiner au-dessus des composants du panel.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (this.active) {
			int shift = 3;
	    	g.setClip(-shift, -shift, this.getWidth()+shift+2, this.getWidth()+shift+2);	// pour pouvoir dessiner en dehors des limites du composant
			g.drawImage(ImageFactory.getInstance().getActiveBorderImage(), -shift, -shift, this.getWidth()+shift+2, this.getHeight()+shift+2, this);
			System.out.println(this.getSize());
		}
	}

	@Override
	public Dimension getPreferredSize() {
	    return DIMENSION;
	}

	@Override
	public Dimension getMaximumSize() {
	    return DIMENSION;
	}
	
	@Override
	public Dimension getMinimumSize() {
	    return DIMENSION;
	}

	/**
	 * Met � jour la vue.
	 * Cr�e les vues des bateaux qui viennent d'�tre plac�s.
	 */
	@Override
	public void update(Observable o, Object arg) {
		Game game = (Game) o;
		Sea sea = getSelfSea(game);

		// Met � jour les vues des cases de la grille
		for (int row = 0 ; row < gridTileViews.length ; row++) {
			for (int col = 0 ; col < gridTileViews[0].length ; col++) {
				gridTileViews[row][col].setState(sea.getGridTileState(row, col));
				gridTileViews[row][col].setCanDisplayHoverImage(canTilesDisplayHoverImage(game));
			}
		}
		
		// Pour chaque bateau plac� dans le mod�le qui n'a pas de vue associ�e
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

	/**
	 * Termine les animations qui ne bouclent pas.
	 */
	public void setNonLoopingAnimationsToEnd() {
		// On doit uniquement terminer les animations de tir des cases
		// Parcours ordonn�e puis abscisse pour l'ajout dans le gridlayout
		for (int x = 0 ; x < gridTileViews.length ; x++) {
			for (int y = 0 ; y < gridTileViews[0].length ; y++) {
				gridTileViews[x][y].setNonLoopingAnimationsToEnd();
			}
		}
	}
	
}
