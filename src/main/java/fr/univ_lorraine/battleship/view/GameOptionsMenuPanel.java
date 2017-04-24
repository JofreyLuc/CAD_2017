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
import javax.swing.SwingConstants;

import fr.univ_lorraine.battleship.model.ComputerController;
import fr.univ_lorraine.battleship.model.Game;
import fr.univ_lorraine.battleship.model.RandomShooting;
import fr.univ_lorraine.battleship.model.SeekThenDestroyCrossShooting;
import fr.univ_lorraine.battleship.model.SeekThenDestroyRandomShooting;
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
	 * Bouton de la stratégie de tir en seek then destroy aléatoire.
	 */
	private final JToggleButton SAndDRandShotButton;

	/**
	 * Bouton de la stratégie de tir en seek then destroy en croix.
	 */
	private final JToggleButton SAndDCrossShotButton;
	
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
		ACTION_SHOOT_MAP.put(ShootingStrategyName.SEEK_THEN_DESTROY_RANDOM.name(), new SeekThenDestroyRandomShooting());
		ACTION_SHOOT_MAP.put(ShootingStrategyName.SEEK_THEN_DESTROY_CROSS.name(), new SeekThenDestroyCrossShooting());
		
		JLabel shotStrategyLabel = new JLabel("Changer la technique de tir de l'ordinateur :", SwingConstants.CENTER);
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 10, 0);
		container.add(shotStrategyLabel, gbc);

		randShotButton = new JToggleButton("Tir aléatoire");
		randShotButton.setToolTipText("Tirs entièrement aléatoires.");
		randShotButton.setActionCommand(ShootingStrategyName.RANDOM.name());
		randShotButton.addActionListener(optionChangedAction);
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.weightx = 1/3;
		container.add(randShotButton, gbc);
				
		SAndDRandShotButton = new JToggleButton("<html><center>Chasse/Cible<br>avec tir aléatoire</center></html>");
		SAndDRandShotButton.setToolTipText("<html>Avec cette stratégie, l’ordinateur débute en mode Chasse et tire au hasard jusqu’à ce qu’il trouve une cible.<br>" +
								"Lorsqu’il a touché, il s’acharne sur les cases adjacentes.<br>" +
								"Une fois le navire coulé, la chasse reprend jusqu’à l’acquisition d’une nouvelle cible.</html>");
		SAndDRandShotButton.setActionCommand(ShootingStrategyName.SEEK_THEN_DESTROY_RANDOM.name());
		SAndDRandShotButton.addActionListener(optionChangedAction);
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.gridx++;
		gbc.weightx = 1/3;
		container.add(SAndDRandShotButton, gbc);

		SAndDCrossShotButton = new JToggleButton("<html><center>Chasse/Cible<br>avec tir en croix</center></html>");
		SAndDCrossShotButton.setToolTipText("<html>Avec cette stratégie, l’ordinateur débute en mode Chasse et tire en croix jusqu’à ce qu’il trouve une cible.<br>" +
				"Lorsqu’il a touché, il s’acharne sur les cases adjacentes.<br>" +
				"Une fois le navire coulé, la chasse reprend jusqu’à l’acquisition d’une nouvelle cible.</html>");
		SAndDCrossShotButton.setActionCommand(ShootingStrategyName.SEEK_THEN_DESTROY_CROSS.name());
		SAndDCrossShotButton.addActionListener(optionChangedAction);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx++;
		gbc.weightx = 1/3;
		container.add(SAndDCrossShotButton, gbc);
		
		shotGroup = new ButtonGroup();
		shotGroup.add(randShotButton);
		shotGroup.add(SAndDRandShotButton);
		shotGroup.add(SAndDCrossShotButton);
		
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
			case RANDOM:
				randShotButton.setSelected(true);
				break;
			case SEEK_THEN_DESTROY_CROSS:
				SAndDCrossShotButton.setSelected(true);
				break;
			case SEEK_THEN_DESTROY_RANDOM:
				SAndDRandShotButton.setSelected(true);
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
