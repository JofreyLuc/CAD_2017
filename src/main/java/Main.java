import model.EpochXX;
import model.Game;

public class Main {

	public static void main(String[] args) {
		Game game = new Game(new EpochXX());
		game.startGame(Game.COMPUTER);
		for (int i = 0 ; i < 5 ; i++) {
			game.receiveClickEvent(i, 0);
		}
		// tirs
		game.receiveClickEvent(0, 0);
		System.out.println("\n"+game);
		game.receiveClickEvent(0, 1);
		System.out.println("\n"+game);
		game.receiveClickEvent(0, 2);
		System.out.println("\n"+game);
		game.receiveClickEvent(0, 3);
		System.out.println("\n"+game);
		game.receiveClickEvent(0, 4);
		System.out.println("\n"+game);

	}

}
