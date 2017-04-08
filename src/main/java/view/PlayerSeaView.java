package view;

import model.Game;
import model.Game.PlayerId;

@SuppressWarnings("serial")
public class PlayerSeaView extends SeaView {

	public PlayerSeaView(Game game) {
		super(game);
	}

	@Override
	protected PlayerId getPlayerOwner() {
		return PlayerId.PLAYER;
	}
	
	@Override
	protected boolean canBoxesDisplayHoverImage(Game game) {
		return false;	// jamais pour la grille du joueur
	}
	
	@Override
	protected void setShipVisiblity(ShipView shipView) {
		shipView.setShipVisibleOnlyWhenDead(false);
	}

}
