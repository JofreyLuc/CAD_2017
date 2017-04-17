package fr.univ_lorraine.battleship.view;

import java.util.Observable;

import fr.univ_lorraine.battleship.model.Game;
import fr.univ_lorraine.battleship.model.Game.PlayerId;


@SuppressWarnings("serial")
public class ComputerSeaView extends SeaView {
	
	@Override
	protected PlayerId getPlayerOwner() {
		return PlayerId.COMPUTER;
	}
	
	@Override
	protected boolean canTilesDisplayHoverImage(Game game) {
		return game.getPlayerTurn() == PlayerId.PLAYER && game.isPositionningPhaseOver() && !game.areAllShotsDone();
	}
	
	@Override
	protected void setShipVisiblity(ShipView shipView) {
		shipView.setShipVisibleOnlyWhenDead(true);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg);
		Game game = (Game) o;
		this.active = game.getPlayerTurn() == PlayerId.PLAYER && game.getPlayer(PlayerId.PLAYER).getSelfGrid().areShipsAllPlaced()
				|| game.getPlayerTurn() == PlayerId.COMPUTER && !game.getPlayer(PlayerId.PLAYER).getSelfGrid().areShipsAllPlaced();
	}
	
}
