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
        
        game.startGame(Game.PlayerId.PLAYER);
		System.out.println(game);
	}

}
