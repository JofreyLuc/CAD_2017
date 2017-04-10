package view;

import controller.EndShotAnimationListener;
import model.Game;
import model.Game.PlayerId;

@SuppressWarnings("serial")
public class ComputerSeaView extends SeaView {

	public ComputerSeaView(Game game, EndShotAnimationListener endShotAnimationListener) {
		super(game, endShotAnimationListener);
	}
	
	@Override
	protected PlayerId getPlayerOwner() {
		return PlayerId.COMPUTER;
	}
	
	@Override
	protected boolean canBoxesDisplayHoverImage(Game game) {
		return game.getPlayerTurn() == PlayerId.PLAYER && game.isPositionningPhaseOver() && !game.areAllShotsDone();
	}
	
	@Override
	protected void setShipVisiblity(ShipView shipView) {
		shipView.setShipVisibleOnlyWhenDead(true);
	}
	
}
