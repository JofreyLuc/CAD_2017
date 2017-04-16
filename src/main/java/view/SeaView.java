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
 * Vue abstraite d'une grille de jeu héritant de JPanel.
 * Celle-ci possède les vues des cases de la grille sous forme de composants
 * et les vues des bateaux qui ne sont pas des composants et délègue ainsi l'affichage
 * de ces données du modèle à ces vues.
 * 
 * Passe les mises à jour de la grille (modèle) aux vues de la grille
 * alors que les vues des bateaux reçoivent eux-mêmes les mises à jour des bateaux (modèle).
 */
@SuppressWarnings("serial")
public abstract class SeaView extends JPanel implements Observer {
		
	/**
	 * Les vues de chaque case de la grille.
	 * Hérite de JComponent.
	 */
	protected GridTileView[][] gridTileViews;
		
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
	
	/**
	 * Indique si cette grille est active.
	 * C'est-à-dire si elle attend une action (selon le tour du joueur).
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
	 * Construit la vue d'une grille à partir du jeu et du listener des animations.
	 * @param game Le jeu.
	 * @param endShotAnimationListener Le listener de fin d'animation.
	 */
	public SeaView(Game game, final EndShotAnimationListener endShotAnimationListener) {
		super();
		game.addObserver(this);
		// Récupère la grille, différent selon s'il s'agit de la vue de la grille du joueur ou de l'ordinateur
		Sea sea = getSelfSea(game);
		
		// Crée le callback pour les animations de tir en les liant au listener de fin d'animation
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
		// Parcours ordonnée puis abscisse pour l'ajout dans le gridlayout
		for (int y = 0 ; y < gridTileViews[0].length ; y++) {
			for (int x = 0 ; x < gridTileViews.length ; x++) {
				gridTileViews[x][y] = new GridTileView(getPlayerOwner(), sea.getGridTileState(x, y), tirAnimCallback);
				gridTileViews[x][y].addMouseListener(new GridTileListener(game, x, y, gridTileViews[x][y]));		
				this.add(gridTileViews[x][y]);
			}
		}
		
		// Initialisation de la vue des bateaux placés
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
	protected abstract boolean canTilesDisplayHoverImage(Game game);
	
	/**
	 * Change les conditions de visibilité du bateau
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
	 * Implémenté en surchargant la méthode paint afin de dessiner au-dessus des composants du panel.
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
	 * Met à jour la vue.
	 * Crée les vues des bateaux qui viennent d'être placés.
	 */
	@Override
	public void update(Observable o, Object arg) {
		Game game = (Game) o;
		Sea sea = getSelfSea(game);

		// Met à jour les vues des cases de la grille
		for (int row = 0 ; row < gridTileViews.length ; row++) {
			for (int col = 0 ; col < gridTileViews[0].length ; col++) {
				gridTileViews[row][col].setState(sea.getGridTileState(row, col));
				gridTileViews[row][col].setCanDisplayHoverImage(canTilesDisplayHoverImage(game));
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

	/**
	 * Termine les animations qui ne bouclent pas.
	 */
	public void setNonLoopingAnimationsToEnd() {
		// On doit uniquement terminer les animations de tir des cases
		// Parcours ordonnée puis abscisse pour l'ajout dans le gridlayout
		for (int x = 0 ; x < gridTileViews.length ; x++) {
			for (int y = 0 ; y < gridTileViews[0].length ; y++) {
				gridTileViews[x][y].setNonLoopingAnimationsToEnd();
			}
		}
	}
	
}
