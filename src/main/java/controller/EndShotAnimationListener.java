package controller;

import model.Game;

public class EndShotAnimationListener {
	
	/**
	 * Le jeu.
	 */
	private Game game;
	
	public EndShotAnimationListener(Game game) {
		this.game = game;
	}

	/**
	 * D�clench� � la fin d'un tour
	 */
	public void onEndShotAnimation() {
		// Si on doit attendre la fin des animations avant de finir le tour et que tous les tirs ont �t� effecut�
		if (game.isEndTurnAfterShotAnimation() && game.areAllShotsDone())
			game.endTurn();
	}
	
}
