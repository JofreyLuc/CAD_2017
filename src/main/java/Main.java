import model.EpochXX;
import model.Game;

public class Main {

	public static void main(String[] args) {
		Game game = new Game(new EpochXX());
		System.out.println(game);
		game.startGame(Game.COMPUTER);
		System.out.println("\n"+game);
		for (int i = 0 ; i < 5 ; i++) {
			game.receiveClickEvent(i, 0);
			System.out.println("\n"+game);
		}
		game.receiveClickEvent(0, 0);
		System.out.println("\n"+game);
	}

}
