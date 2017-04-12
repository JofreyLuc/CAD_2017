import javax.swing.JFrame;

import view.GameView;

import model.EpochXVI;
import model.EpochXX;
import model.Game;

public class Main {

	public static void main(String[] args) {
		Game game = new Game(new EpochXVI());
		GameView gameView = new GameView(game);
		final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gameView);
        frame.pack();
        frame.setVisible(true);
        
        game.startGame(Game.PlayerId.COMPUTER);
	}

}
