package view;

import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Epoch;
import model.Game;
import model.GameLoader;
import model.ShootingStrategy;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	public final static String MAIN_MENU_PANEL = "MAIN_MENU";

	public final static String FILE_CHOOSER_PANEL = "FILE_CHOOSER";
	
	public final static String CHOOSE_GAME_OPTIONS_PANEL = "CHOOSE_GAME_OPTIONS";
	
	public final static String GAME_PANEL = "GAME";
	
	private GameView gamePanel;
	
	private CardLayout cardLayout;
	
	public GameFrame() {
		this.gamePanel = new GameView();
		JPanel menuPanel = new MainMenuPanel(this);
		JPanel fileChooserPanel = new FileChooserPanel(this);
		JPanel chooseGameOptionsPanel = new ChooseGameOptionsPanel(this);
		
		this.cardLayout = new CardLayout();
		this.getContentPane().setLayout(cardLayout);
		this.getContentPane().add(menuPanel, MAIN_MENU_PANEL);
		this.getContentPane().add(fileChooserPanel, FILE_CHOOSER_PANEL);
		this.getContentPane().add(chooseGameOptionsPanel, CHOOSE_GAME_OPTIONS_PANEL);
		this.getContentPane().add(gamePanel, GAME_PANEL);
		this.cardLayout.show(this.getContentPane(), MAIN_MENU_PANEL);
		
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
	}
	
	/**
	 * Change le panel courant.
	 * @param panelId L'id du panel.
	 */
	public void showPanel(String panelId) {
		this.cardLayout.show(this.getContentPane(), panelId);
		this.pack();
	}
	
	public void newGame(Epoch epoch, ShootingStrategy shootingStrategy) {
		Game game = new Game(epoch, shootingStrategy);
		gamePanel.setGame(game);
		showPanel(GAME_PANEL);
		game.start(Game.PlayerId.COMPUTER);
	}

	public void loadGame(String fileName) {
		try {
			Game game = GameLoader.loadGame(new File(fileName));
			gamePanel.setGame(game);
			gamePanel.setNonLoopingAnimationsToEnd();
			showPanel(GAME_PANEL);
			game.resume();
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(this,
				    "Ce fichier n'est pas un fichier de sauvegarde valide.",
				    "Fichier de sauvegarde non valide",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
