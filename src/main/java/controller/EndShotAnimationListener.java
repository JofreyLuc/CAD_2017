package controller;

import model.Game;

/**
 * Listener des animations qui permet de changer de tour seulement � la fin des animations de tir.
 */
public class EndShotAnimationListener {
	
	/**
	 * Le jeu afin de changer de tour.
	 */
	private Game game;
	
	/**
	 * Cr�e le listener des animations.
	 * @param game Le jeu.
	 */
	public EndShotAnimationListener(Game game) {
		this.game = game;
	}

	/**
	 * D�clench� � la fin d'une animation de tir
	 * afin de voir si on change de tour.
	 */
	public void onEndShotAnimation() {
		// Si on doit attendre la fin des animations avant de finir le tour et que tous les tirs ont �t� effecut�
		if (game.isEndTurnAfterShotAnimation() && game.areAllShotsDone())
			game.endTurn();
	}
	
}
