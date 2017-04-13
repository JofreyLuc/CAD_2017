package view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Epoch;
import model.Game;
import model.ShootingStrategy;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	public final static String MAIN_MENU_PANEL = "MAIN_MENU";

	public final static String CHOOSE_GAME_OPTIONS_PANEL = "CHOOSE_GAME_OPTIONS";
	
	public final static String GAME_PANEL = "GAME";
	
	private GameView gamePanel;
	
	private CardLayout cardLayout;
	
	public GameFrame() {
		this.gamePanel = new GameView();
		JPanel menuPanel = new MainMenuPanel(this);
		JPanel chooseGameOptionsPanel = new ChooseGameOptionsPanel(this);
		
		this.cardLayout = new CardLayout();
		this.getContentPane().setLayout(cardLayout);
		this.getContentPane().add(menuPanel, MAIN_MENU_PANEL);
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

	public void loadGame() {
		
	}
	
}
