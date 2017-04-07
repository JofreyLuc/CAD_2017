package view;

import java.awt.Graphics;
import javax.swing.JComponent;
import model.Game;
import model.Sea;
import model.Sea.SeaBoxState;

import controller.GridBoxListener;

/**
 * Vue d'une case de la grille.
 * Permet d'afficher l'état de la case (si touché ou manqué).
 */
@SuppressWarnings("serial")
public class GridBoxView extends JComponent {
	
	/**
	 * L'état de cette case de la grille.
	 */
	private Sea.SeaBoxState state;
	
	/**
	 * L'image à dessiner pour cette case.
	 */
	private Animation boxImage;
	
	/**
	 * Booléen indiquant si la souris est au-dessus de la case.
	 */
	private boolean isHover;
	
	public GridBoxView(SeaBoxState state) {
		this.state = state;
		attachImage();
	}
	
	/**
	 * Prend l'image correspondante à l'état de la case.
	 * Place l'image à null si rien ne doit pas être affiché.
	 */
	private void attachImage() {
        switch(state) {
	    	case NORMAL:
	    		boxImage = null;
	    		break;
	    	case TOUCHED:
	    		boxImage = new Animation(ImageFactory.getInstance().getHitAnimation());
	    		break;
	    	case SHOT:
	    		boxImage = new Animation(ImageFactory.getInstance().getMissAnimation());
	    		break;
	    	default:
	    		throw new AssertionError("Etat inconnnu " + state);
        }
	}
	
	/**
	 * Met à jour l'état de la case
	 * et change l'image en conséquence.
	 * @param state Le nouvel état de la case.
	 */
	public void setState(SeaBoxState state) {
		// Si l'état de la case a changé
		if (this.state != state) {
			this.state = state;
			attachImage();
		}
	}
	
	/**
	 * Place le booléen indiquant si la souris est au-dessus de la case.
	 * @param isHover Le booléen indiquant si la souris est au-dessus de la case.
	 */
	public void setIsHover(boolean isHover) {
		this.isHover = isHover;
	}
	
	/**
	 * Crée un gridBoxListener et "l'attache à la vue".
	 * Permet de lier le contrôleur de la grille au jeu.
	 * @param game Le jeu.
	 * @param x L'abscisse de la case.
	 * @param y L'ordonnée de la case.
	 */
	public void attachGridBoxListener(Game game, int x, int y) {
		this.addMouseListener(new GridBoxListener(game, x, y, this));
	}
	
	/**
	 * Dessine l'état de la case si nécessaire
	 * ainsi qu'un viseur si hover et un tir est possible. 
	 */
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    	g.setClip(-100, -100, 200, 200);	// pour pouvoir dessiner en dehors des limites du composant
        if (boxImage != null) {
        	int shift = 25;
        	boxImage.draw(g, -shift, -shift);
        }
        // Si la souris est hover et qu'un tir est encore possible sur cette case
        if (isHover && state == SeaBoxState.NORMAL) {
        	g.drawImage(ImageFactory.getInstance().getSightImage(), 0, 0, this);	// on dessine un viseur
        }
	}
	
}
