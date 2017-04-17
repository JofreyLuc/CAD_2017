package fr.univ_lorraine.battleship.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import fr.univ_lorraine.battleship.model.ComputerController;
import fr.univ_lorraine.battleship.model.CrossShooting;
import fr.univ_lorraine.battleship.model.Game;
import fr.univ_lorraine.battleship.model.RandomShooting;
import fr.univ_lorraine.battleship.model.ShootingStrategy;
import fr.univ_lorraine.battleship.model.ShootingStrategy.ShootingStrategyName;


/**
 * Panel des options en jeu.
 */
@SuppressWarnings("serial")
public class GameOptionsMenuPanel extends JPanel {
	
	/**
	 * Partie en cours.
	 */
	private Game game;
		
	/**
	 * Map liant les actions des boutons à leur stratégie de tir correspondante.
	 */
	private static final Map<String, ShootingStrategy> ACTION_SHOOT_MAP = new HashMap<String, ShootingStrategy>();

	/**
	 * Groupe de boutons des stratégies de tir.
	 * Permet de rendre seulement un toggleBouton sélectionnable.
	 */
	private final ButtonGroup shotGroup;
	
	/**
	 * Bouton de la stratégie de tir aléatoire.
	 */
	private final JToggleButton randShotButton;
	
	/**
	 * Bouton de la stratégie de tir en croix.
	 */
	private final JToggleButton crossShotButton;
	
	/**
	 * Construit le panel.
	 * Utilise la fenêtre principale pour certains listeners.
	 * @param gameFrame La fenêtre principale du jeu.
	 */
	public GameOptionsMenuPanel(final GameFrame gameFrame) {
		// GridBagLayout afin que les composants ne s'étendent pas
		this.setLayout(new GridBagLayout());
		
		// Contient tous les éléments du panel
		JPanel container = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(50, 50, 50, 50);
		this.add(container, gbc);		
		
		// ActionListener qui met à jour les options de jeu.
		ActionListener optionChangedAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeGameOptions();
			}
		};
		
		// Bouton reprendre partie
		JButton resumeGameButton = new JButton("Reprendre");
		resumeGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resumeGame(gameFrame);
			}
		});
		gbc.insets = new Insets(5, 50, 30, 50);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		container.add(resumeGameButton, gbc);

		// Choix stratégie de tir de l'ordi
		ACTION_SHOOT_MAP.put(ShootingStrategyName.RANDOM.name(), new RandomShooting());
		ACTION_SHOOT_MAP.put(ShootingStrategyName.CROSS.name(), new CrossShooting());
		
		JLabel shotStrategyLabel = new JLabel("Changer la technique de tir de l'ordinateur :");
		gbc.gridy++;
		gbc.insets = new Insets(0, 0, 10, 0);
		container.add(shotStrategyLabel, gbc);

		randShotButton = new JToggleButton("Tir aléatoire");
		randShotButton.setActionCommand(ShootingStrategyName.RANDOM.name());
		randShotButton.addActionListener(optionChangedAction);
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.weightx = 0.5;
		container.add(randShotButton, gbc);
		
		crossShotButton = new JToggleButton("Tir en croix");
		crossShotButton.setActionCommand(ShootingStrategyName.CROSS.name());
		crossShotButton.addActionListener(optionChangedAction);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx++;
		gbc.weightx = 0.5;
		container.add(crossShotButton, gbc);
		
		shotGroup = new ButtonGroup();
		shotGroup.add(randShotButton);
		shotGroup.add(crossShotButton);
		
		// Bouton sauvegarder
		JButton saveGameButton = new JButton("Sauvegarder la partie");
		saveGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showPanel(GameFrame.PanelId.SAVE_GAME_PANEL);
			}
		});
		gbc.insets = new Insets(30, 50, 10, 50);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy++;
		container.add(saveGameButton, gbc);
		
		// Bouton menu principal
		JButton returnButton = new JButton("Retour au menu principal");
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showPanel(GameFrame.PanelId.MAIN_MENU_PANEL);
			}
		});
		gbc.insets = new Insets(0, 50, 5, 50);
		gbc.gridy++;
		container.add(returnButton, gbc);	

        //Key bindings
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "BACK");
        this.getActionMap().put("BACK", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
				resumeGame(gameFrame);
            }
        });
	}
	
	/**
	 * Place le jeu courant afin de pouvoir modifier ses caractéristiques.
	 * @param game Le jeu courant.
	 */
	public void setGame(Game game) {
		this.game = game;
	}
	
	/**
	 * Met à jour l'affichage des options selon les caractéristiques du jeu courant
	 * (les options sélectionnées/désélectionnées).
	 */
	private void updateCurrentOptionsSelected() {
		if (game != null) {
			ShootingStrategy shootingStrategy = game.getComputerController().getShootingStrategy();
			switch (shootingStrategy.getShootingStrategyName()) {
			case CROSS:
				crossShotButton.setSelected(true);
				break;
			case RANDOM:
				randShotButton.setSelected(true);
				break;
			default:
				throw new AssertionError("Stratégie de tir inconnue " + shootingStrategy.getShootingStrategyName());
			}
		}
	}
	
	/**
	 * Change les caractéristiques du jeu courant par rapport aux options choisies.
	 */
	private void changeGameOptions() {
		if (game != null) {
			ComputerController compController = game.getComputerController();
			ShootingStrategy chosenShootingStrategy = ACTION_SHOOT_MAP.get(shotGroup.getSelection().getActionCommand());
			if (compController != null && compController.getShootingStrategy() != chosenShootingStrategy) {
				compController.setShootingStrategy(chosenShootingStrategy);
			}
		}

	}

	/**
	 * Reprend le jeu en affichant la fenêtre de jeu.
	 * @param gameFrame La fenêtre principale.
	 */
	private void resumeGame(GameFrame gameFrame) {
		gameFrame.showPanel(GameFrame.PanelId.GAME_PANEL);
	}
	
	@Override
	public void setVisible(boolean aFlag) {
		// On met à jour les options avant d'afficher le panel
		updateCurrentOptionsSelected();
		super.setVisible(aFlag);
	}
	
}
