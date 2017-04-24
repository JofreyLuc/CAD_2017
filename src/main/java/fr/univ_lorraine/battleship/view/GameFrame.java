package fr.univ_lorraine.battleship.view;

import java.awt.CardLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.univ_lorraine.battleship.Utils;
import fr.univ_lorraine.battleship.model.Epoch;
import fr.univ_lorraine.battleship.model.Game;
import fr.univ_lorraine.battleship.model.GameLoader;
import fr.univ_lorraine.battleship.model.ShootingStrategy;
import fr.univ_lorraine.battleship.model.Game.PlayerId;


/**
 * Fenêtre principale du jeu.
 * Gére les différents panneaux principaux à l'aide d'un CardLayout.
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	
	/**
	 * Identifiants des panels du CardLayout.
	 */
	public enum PanelId {
		MAIN_MENU_PANEL,
		LOAD_GAME_PANEL,
		SAVE_GAME_PANEL,
		CHOOSE_GAME_OPTIONS_PANEL,
		GAME_PANEL,
		OPTIONS_MENU_PANEL
	}
	
	/**
	 * Panel du jeu (partie en cours).
	 */
	private GameView gamePanel;
	
	/**
	 * Panel du menu des options du jeu (partie en cours).
	 */
	private GameOptionsMenuPanel gameOptionsPanel;
	
	/**
	 * Layout du panel principal de la fenêtre.
	 */
	private CardLayout cardLayout;
	
	/**
	 * Crée la fenêtre ainsi que les différents panels du CardLayout.
	 */
	public GameFrame() {
		cardLayout = new CardLayout();
		getContentPane().setLayout(cardLayout);
		
		JPanel menuPanel = new MainMenuPanel(this);
		getContentPane().add(menuPanel, PanelId.MAIN_MENU_PANEL.name());
		gamePanel = new GameView(this);
		getContentPane().add(gamePanel, PanelId.GAME_PANEL.name());
		gameOptionsPanel = new GameOptionsMenuPanel(this);
		getContentPane().add(gameOptionsPanel, PanelId.OPTIONS_MENU_PANEL.name());
		JPanel gameLoadPanel = new LoadGamePanel(this);
		getContentPane().add(gameLoadPanel, PanelId.LOAD_GAME_PANEL.name());
		JPanel gameSavePanel = new SaveGamePanel(this);
		getContentPane().add(gameSavePanel, PanelId.SAVE_GAME_PANEL.name());
		JPanel chooseGameOptionsPanel = new ChooseGameOptionsPanel(this);
		getContentPane().add(chooseGameOptionsPanel, PanelId.CHOOSE_GAME_OPTIONS_PANEL.name());
		
		// Premier panel affiché
		cardLayout.show(this.getContentPane(), PanelId.MAIN_MENU_PANEL.name());
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setMinimumSize(getMinimumSize());	// fixe la taille minimale
        setLocationRelativeTo(null);		// place la frame au centre de l'écran

	}
	
	/**
	 * Change le panel courant.
	 * @param panelId L'id du panel.
	 */
	public void showPanel(PanelId panelId) {
		this.cardLayout.show(this.getContentPane(), panelId.name());
	}
	
	/**
	 * Démarre une nouvelle partie avec les options passées en paramètres.
	 * Initialise les composants graphiques concernés et affiche le panel de jeu.
	 * @param epoch L'époque de la nouvelle partie.
	 * @param shootingStrategy La stratégie de tir de l'ordinateur.
	 * @param startingPlayer Le joueur débutant la partie.
	 */
	public void newGame(Epoch epoch, ShootingStrategy shootingStrategy, PlayerId startingPlayer) {
		Game game = new Game(epoch, shootingStrategy);
		gamePanel.setGame(game);
		gameOptionsPanel.setGame(game);
		showPanel(PanelId.GAME_PANEL);
		game.start(startingPlayer);
	}

	/**
	 * Sauvegarde la partie en cours dans le fichier dont le chemin est passé en paramétre
	 * si il y a une partie en cours
	 * et revient à l'écran de jeu une fois la sauvegarde effectuée.
	 * 
	 * Demande confirmation si le fichier de sauvegarde est un fichier existant.
	 * Ajoute l'extension de sauvegarde si elle est manquante au chemin du fichier.
	 * @param filePath Le chemin du fichier de sauvegarde.
	 */
	public void saveGame(String filePath) {
		Game game = gamePanel.getGame();
		if (game == null) {
			return;
		}
		
		File file = new File(filePath);
		boolean save = true;	// booléen indiquant si la sauvegarde doit est confirmée
		// Fichier existant
		if (file.exists()) {
            int result = JOptionPane.showConfirmDialog(this,
            		file.getName() + " existe déjà.\nVoulez-vous le remplacer ?",
            		"Fichier existant",
            		JOptionPane.YES_NO_OPTION);
            save = result == JOptionPane.YES_OPTION;
		}
		// Fichier inexistant sans extension
		else if (Utils.getExtension(filePath).isEmpty()) {
			file = new File(filePath.concat(GameLoader.SAVE_EXTENSION));
			if (file.exists()) {	// Si le fichier avec extension existe déjà, on reprend celui sans extension
				file = new File(filePath);
			}
		}
		
		if (save) {	// si la sauvegarde est confirmée, on sauvegarde
			try {
				GameLoader.saveGame(game, file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this,
					    "Une erreur est survenue lors de la sauvegarde de la partie et celle-ci n'a pas pu être sauvegardée correctement.",
					    "Sauvegarde impossible",
					    JOptionPane.ERROR_MESSAGE);
			}
			showPanel(PanelId.GAME_PANEL);
		}
	}
	
	/**
	 * Charge la partie depuis le fichier de sauvegarde passé en paramétre
	 * et affiche l'écran de jeu en reprenant la partie.
	 * @param filePath Le chemin du fichier de sauvegarde.
	 */
	public void loadGame(String filePath) {
		try {
			Game game = GameLoader.loadGame(new File(filePath));
			gamePanel.setGame(game);
			gameOptionsPanel.setGame(game);
			gamePanel.setNonLoopingAnimationsToEnd();
			showPanel(PanelId.GAME_PANEL);
			game.resume();
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(this,
				    "Ce fichier n'est pas un fichier de sauvegarde valide ou n'est pas accessible en lecture.",
				    "Fichier de sauvegarde invalide ou inaccessible",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
}
