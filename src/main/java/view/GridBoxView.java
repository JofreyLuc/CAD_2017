package view;

import java.awt.Graphics;
import javax.swing.JComponent;
import model.Game;
import model.Sea;
import model.Sea.SeaBoxState;

import controller.GridBoxListener;

/**
 * Vue d'une case de la grille.
 * Permet d'afficher l'�tat de la case (si touch� ou manqu�).
 */
@SuppressWarnings("serial")
public class GridBoxView extends JComponent {
	
	/**
	 * L'�tat de cette case de la grille.
	 */
	private Sea.SeaBoxState state;
	
	/**
	 * L'image � dessiner pour cette case.
	 */
	private Animation boxImage;
	
	/**
	 * Bool�en indiquant si la souris est au-dessus de la case.
	 */
	private boolean isHover;
	
	public GridBoxView(SeaBoxState state) {
		this.state = state;
		attachImage();
	}
	
	/**
	 * Prend l'image correspondante � l'�tat de la case.
	 * Place l'image � null si rien ne doit pas �tre affich�.
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
	 * Met � jour l'�tat de la case
	 * et change l'image en cons�quence.
	 * @param state Le nouvel �tat de la case.
	 */
	public void setState(SeaBoxState state) {
		// Si l'�tat de la case a chang�
		if (this.state != state) {
			this.state = state;
			attachImage();
		}
	}
	
	/**
	 * Place le bool�en indiquant si la souris est au-dessus de la case.
	 * @param isHover Le bool�en indiquant si la souris est au-dessus de la case.
	 */
	public void setIsHover(boolean isHover) {
		this.isHover = isHover;
	}
	
	/**
	 * Cr�e un gridBoxListener et "l'attache � la vue".
	 * Permet de lier le contr�leur de la grille au jeu.
	 * @param game Le jeu.
	 * @param x L'abscisse de la case.
	 * @param y L'ordonn�e de la case.
	 */
	public void attachGridBoxListener(Game game, int x, int y) {
		this.addMouseListener(new GridBoxListener(game, x, y, this));
	}
	
	/**
	 * Dessine l'�tat de la case si n�cessaire
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
