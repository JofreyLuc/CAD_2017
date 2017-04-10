package view;

import java.awt.Graphics;
import javax.swing.JComponent;

import view.AnimationWithCallback.Callback;
import model.Game.PlayerId;
import model.Sea;
import model.Sea.SeaBoxState;

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
	 * Le joueur propri�taire de la grille dont cette case provient.
	 */
	private PlayerId playerOwner;
	
	/**
	 * L'image � dessiner pour cette case.
	 */
	private Animation boxImage;
	
	/**
	 * Bool�en indiquant si la souris est au-dessus de la case.
	 */
	private boolean isHover;
	
	/**
	 * Indique si le viseur peut-�tre affich� si une case est hover.
	 */
	private boolean canDisplayHoverImage;
	
	/**
	 * Callback appel� � la fin de l'animation de tir sur cette case.
	 */
	private Callback animationCallback;
	
	public GridBoxView(PlayerId playerOwner, SeaBoxState state, Callback animationCallback) {
		this.playerOwner = playerOwner;
		this.state = state;
		this.animationCallback = animationCallback;
		this.isHover = false;
		this.canDisplayHoverImage = false;
		attachImage();
	}
	
	/**
	 * Retourne le joueur propri�taire de la grille dont cette case provient.
	 * @return Le joueur propri�taire de la grille dont cette case provient.
	 */
	public PlayerId getPlayerOwner() {
		return playerOwner;
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
	    		boxImage = new AnimationWithCallback(
	    				new Animation(ImageFactory.getInstance().getHitAnimation()), 
	    				animationCallback);
	    		break;
	    	case SHOT:
	    		boxImage = new AnimationWithCallback(
	    				new Animation(ImageFactory.getInstance().getMissAnimation()),
	    				animationCallback);
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
	 * Place le bool�en indiquant si le viseur peut-�tre affich� si une case est hover.
	 * @param displayHoverImage Le bool�en indiquant si le viseur peut-�tre affich� si une case est hover.
	 */
	public void setCanDisplayHoverImage(boolean displayHoverImage) {
		this.canDisplayHoverImage = displayHoverImage;
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
        // Si la souris est hover et que les conditions d'affichage sont remplies
        if (canDisplayHoverImage && isHover && state == SeaBoxState.NORMAL) {
        	g.drawImage(ImageFactory.getInstance().getSightImage(), 0, 0, this);	// on dessine un viseur
        }
	}
	
}
