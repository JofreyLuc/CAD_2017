package view;

import java.util.Observable;

import controller.EndShotAnimationListener;
import model.Game;
import model.Game.PlayerId;

@SuppressWarnings("serial")
public class PlayerSeaView extends SeaView {

	public PlayerSeaView(Game game, EndShotAnimationListener endShotAnimationListener) {
		super(game, endShotAnimationListener);
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

	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg);
		Game game = (Game) o;
		this.active = game.getPlayerTurn() == PlayerId.PLAYER && !game.getPlayer(PlayerId.PLAYER).getSelfGrid().areShipsAllPlaced()
				|| game.getPlayerTurn() == PlayerId.COMPUTER && game.getPlayer(PlayerId.PLAYER).getSelfGrid().areShipsAllPlaced();
	}
	
}
