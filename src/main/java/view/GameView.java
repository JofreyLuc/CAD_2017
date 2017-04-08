package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Game;

@SuppressWarnings("serial")
public class GameView extends JPanel implements Observer {

	private SeaView playerGridView;
	
	private SeaView computerGridView;
		
	public GameView(Game game) {
		game.addObserver(this);
		this.playerGridView = new PlayerSeaView(game);
		this.computerGridView = new ComputerSeaView(game);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
