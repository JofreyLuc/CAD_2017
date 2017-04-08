import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import view.ComputerSeaView;
import view.PlayerSeaView;

import model.EpochXX;
import model.Game;

public class Main {

	public static void main(String[] args) {
		Game game = new Game(new EpochXX());
		
		// tirs
		/*game.receiveClickEvent(0, 0);
		System.out.println("\n"+game);
		game.receiveClickEvent(0, 1);
		System.out.println("\n"+game);
		game.receiveClickEvent(0, 2);
		System.out.println("\n"+game);
		game.receiveClickEvent(0, 3);
		System.out.println("\n"+game);
		game.receiveClickEvent(0, 4);
		System.out.println("\n"+game);*/
		
		final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel container = new JPanel();
        frame.add(container);
        container.add(new PlayerSeaView(game));
        container.add(new ComputerSeaView(game));
        frame.pack();
        frame.setVisible(true);
        
        Timer timer = new Timer(17, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.repaint();
			}
        	
        });
        timer.start();
        
        game.startGame(Game.PlayerId.COMPUTER);
		/*for (int i = 0 ; i < 5 ; i++) {
			game.receiveRotateShipEvent();
			game.receiveClickEventOnPlayerGrid(i, 0);
		}*/
		System.out.println(game);
	}

}
