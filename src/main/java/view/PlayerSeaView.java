package view;

import model.Game;
import model.Sea;

@SuppressWarnings("serial")
public class PlayerSeaView extends SeaView {

	public PlayerSeaView(Game game, Sea sea) {
		super(game, sea);
	}

	@Override
	protected void setGridBoxViewInteractability(GridBoxView gridBoxView, int x, int y, Game game) {
		// On ne fait rien car les cases de sa propre grille ne sont pas cliquables
	}
	
	@Override
	protected void setShipVisiblity(ShipView shipView) {
		shipView.setShipVisibleOnlyWhenDead(false);
	}

}
