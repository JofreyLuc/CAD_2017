package view;

import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.FileChooserPanel.FileChooserType;

import model.Epoch;
import model.Game;
import model.Game.PlayerId;
import model.GameLoader;
import model.ShootingStrategy;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	public static final String MAIN_MENU_PANEL = "MAIN_MENU";

	public static final String FILE_LOAD_PANEL = "FILE_LOAD";
	
	public static final String FILE_SAVE_PANEL = "FILE_SAVE";
	
	public static final String CHOOSE_GAME_OPTIONS_PANEL = "CHOOSE_GAME_OPTIONS";
	
	public static final String GAME_PANEL = "GAME";
	
	public static final String OPTIONS_MENU_PANEL = "OPTIONS_MENU";
	
	private GameView gamePanel;
	
	private OptionsMenuPanel gameOptionsPanel;
	
	private CardLayout cardLayout;
	
	public GameFrame() {
		this.gamePanel = new GameView(this);
		this.gameOptionsPanel = new OptionsMenuPanel(this);
		JPanel menuPanel = new MainMenuPanel(this);
		JPanel fileLoadPanel = new FileChooserPanel(this, FileChooserType.LOAD);
		JPanel fileSavePanel = new FileChooserPanel(this, FileChooserType.SAVE);
		JPanel chooseGameOptionsPanel = new ChooseGameOptionsPanel(this);
		
		this.cardLayout = new CardLayout();
		this.getContentPane().setLayout(cardLayout);
		this.getContentPane().add(menuPanel, MAIN_MENU_PANEL);
		this.getContentPane().add(fileLoadPanel, FILE_LOAD_PANEL);
		this.getContentPane().add(fileSavePanel, FILE_SAVE_PANEL);
		this.getContentPane().add(chooseGameOptionsPanel, CHOOSE_GAME_OPTIONS_PANEL);
		this.getContentPane().add(gamePanel, GAME_PANEL);
		this.getContentPane().add(gameOptionsPanel, OPTIONS_MENU_PANEL);
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
		//this.pack();
	}
	
	public void newGame(Epoch epoch, ShootingStrategy shootingStrategy, PlayerId startingPlayer) {
		Game game = new Game(epoch, shootingStrategy);
		gamePanel.setGame(game);
		gameOptionsPanel.setGame(game);
		showPanel(GAME_PANEL);
		game.start(startingPlayer);
	}

	public void saveGame(String fileName) {
		Game game = gamePanel.getGame();
		if (game == null) {
			return;
		}
		
		File file = new File(fileName);
		boolean save = true;
		// Fichier existant
		if (file.exists()) {
            int result = JOptionPane.showConfirmDialog(this,
            		file.getName() + " existe d�j�.\nVoulez-vous le remplacer ?",
            		"Fichier existant",
            		JOptionPane.YES_NO_OPTION);
            save = result == JOptionPane.YES_OPTION;
		}
		// Fichier inexistant sans extension
		else if (getExtension(fileName).isEmpty()) {
			file = new File(fileName.concat(GameLoader.SAVE_EXTENSION));
			if (file.exists()) {	// Si le fichier avec extension existe d�j�, on reprend celui sans extension
				file = new File(fileName);
			}
		}
		
		if (save) {
			try {
				GameLoader.saveGame(game, file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this,
					    "Une erreur est survenue lors de la sauvegarde de la partie et celle-ci n'a pas pu �tre sauvegard�e correctement.",
					    "Sauvegarde impossible",
					    JOptionPane.ERROR_MESSAGE);
			}
			showPanel(GAME_PANEL);
		}
	}
	
	public void loadGame(String fileName) {
		try {
			Game game = GameLoader.loadGame(new File(fileName));
			gamePanel.setGame(game);
			gameOptionsPanel.setGame(game);
			gamePanel.setNonLoopingAnimationsToEnd();
			showPanel(GAME_PANEL);
			game.resume();
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(this,
				    "Ce fichier n'est pas un fichier de sauvegarde valide ou n'est pas accessible en lecture.",
				    "Fichier de sauvegarde invalide ou inaccessible",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionPos = filename.lastIndexOf('.');
        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);

        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }
	
}
