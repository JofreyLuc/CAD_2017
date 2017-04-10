package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import controller.EndShotAnimationListener;

import model.Game;
import model.Game.PlayerId;

@SuppressWarnings("serial")
public class GameView extends JPanel implements Observer {

	private SeaView playerGridView;
	
	private SeaView computerGridView;

	private JLabel turnLabel;
	
	private JLabel instructionLabel;
	
	public GameView(Game game) {
		game.addObserver(this);
		
		// Listener de fin d'animation de tir
		EndShotAnimationListener endShotAnimListener = new EndShotAnimationListener(game);

		this.playerGridView = new PlayerSeaView(game, endShotAnimListener);
		this.computerGridView = new ComputerSeaView(game, endShotAnimListener);
		
		this.turnLabel = new JLabel("VICTOIRE DE L'ORDINATEUR");
		this.turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.turnLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.turnLabel.setMinimumSize(turnLabel.getPreferredSize());
		this.turnLabel.setPreferredSize(turnLabel.getPreferredSize());
		this.turnLabel.setMaximumSize(turnLabel.getPreferredSize());
		
		this.instructionLabel = new JLabel("<html>Veuillez placer vos bateaux.<br>Clic gauche pour placer.<br>Clic droit pour pivoter.");
		this.instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.instructionLabel.setVerticalAlignment(SwingConstants.CENTER);
		this.instructionLabel.setMinimumSize(instructionLabel.getPreferredSize());
		this.instructionLabel.setPreferredSize(instructionLabel.getPreferredSize());
		this.instructionLabel.setMaximumSize(instructionLabel.getPreferredSize());

		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = gbc.gridy = 0;
		gbc.insets = new Insets(15, 0, 15, 0);
		this.add(turnLabel, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 15, 15, 15);
		this.add(playerGridView, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 15, 15, 15);
		this.add(computerGridView, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		this.add(instructionLabel, gbc);
				
		// Raffraîchit l'affichage toutes les 17ms ~ 60 fps
		Timer timer = new Timer(17, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
        	
        });
        timer.start();
	}

	@Override
	public void update(Observable o, Object arg) {
		Game game = (Game) o;
		switch(game.getPlayerTurn()) {
			case COMPUTER:
				turnLabel.setText("TOUR DE L'ORDINATEUR");
				instructionLabel.setText("");
				break;
			case PLAYER:
				turnLabel.setText("TOUR DU JOUEUR");
				if (!game.isPositionningPhaseOver()) {
					instructionLabel.setText("<html>Veuillez placer vos bateaux.<br>Clic gauche pour placer.<br>Clic droit pour pivoter.");
				}
				else {
					instructionLabel.setText("");
				}
				break;
			default:
				throw new AssertionError("Joueur inconnu " + game.getPlayerTurn());		
		}
	}
}
