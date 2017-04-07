package view;

import model.Game;
import model.Sea;

@SuppressWarnings("serial")
public class OpponentSeaView extends SeaView {

	public OpponentSeaView(Game game, Sea sea) {
		super(game, sea);
	}
	
	@Override
	protected void setGridBoxViewInteractability(GridBoxView gridBoxView, int x, int y, Game game){
		gridBoxView.attachGridBoxListener(game, x, y);		
	}
	
	@Override
	protected void setShipVisiblity(ShipView shipView) {
		shipView.setShipVisibleOnlyWhenDead(true);
	}
	
}
