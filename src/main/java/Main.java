import model.EpochXX;
import model.Game;

public class Main {

	public static void main(String[] args) {
		Game game = new Game(new EpochXX());
		System.out.println(game);
		game.playComputerTurn();
		game.endTurn();
		System.out.println("\n"+game);
	}

}
