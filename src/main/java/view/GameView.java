package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Game;

@SuppressWarnings("serial")
public class GameView extends JPanel implements Observer {

	private SeaView playerGridView;
	
	private SeaView opponentGridView;
	
	private Game game;
	
	public GameView(Game game) {
		this.game = game;
		this.playerGridView = new SeaView(game, game.getPlayerSea());
		this.opponentGridView = new SeaView(game, game.getComputerSea());
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
