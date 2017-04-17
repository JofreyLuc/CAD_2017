package fr.univ_lorraine.battleship.view;

import java.util.Observable;

import fr.univ_lorraine.battleship.model.Game;
import fr.univ_lorraine.battleship.model.Game.PlayerId;


@SuppressWarnings("serial")
public class PlayerSeaView extends SeaView {

	@Override
	protected PlayerId getPlayerOwner() {
		return PlayerId.PLAYER;
	}
	
	@Override
	protected boolean canTilesDisplayHoverImage(Game game) {
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
