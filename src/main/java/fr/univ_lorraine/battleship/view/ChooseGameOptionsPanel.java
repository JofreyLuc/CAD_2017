package fr.univ_lorraine.battleship.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import fr.univ_lorraine.battleship.model.Epoch;
import fr.univ_lorraine.battleship.model.EpochXVI;
import fr.univ_lorraine.battleship.model.EpochXX;
import fr.univ_lorraine.battleship.model.RandomShooting;
import fr.univ_lorraine.battleship.model.SeekThenDestroyCrossShooting;
import fr.univ_lorraine.battleship.model.SeekThenDestroyRandomShooting;
import fr.univ_lorraine.battleship.model.ShootingStrategy;
import fr.univ_lorraine.battleship.model.Epoch.EpochName;
import fr.univ_lorraine.battleship.model.EpochX;
import fr.univ_lorraine.battleship.model.Game.PlayerId;
import fr.univ_lorraine.battleship.model.ShootingStrategy.ShootingStrategyName;


/**
 * Panel du choix des options avant de lancer une partie.
 */
@SuppressWarnings("serial")
public class ChooseGameOptionsPanel extends JPanel {
	
	/**
	 * Map liant les actions des boutons à leur époque correspondante.
	 */
	private static final Map<String, Epoch> ACTION_EPOCH_MAP = new HashMap<String, Epoch>();

	/**
	 * Groupe de boutons des époques.
	 * Permet de rendre seulement un toggleBouton sélectionnable.
	 */
	private final ButtonGroup epochGroup;
	
	/**
	 * Bouton de l'époque XX.
	 */
	private final JToggleButton epochXXButton;
	
	/**
	 * Bouton de l'époque XVI.
	 */
	private final JToggleButton epochXVIButton;
	
	/**
	 * Bouton de l'époque X.
	 */
	private final JToggleButton epochXButton;
	
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
	 * Enumerations des choix possibles du joueur qui débutera la partie.
	 */
	private enum StartingPlayer {
		
		RANDOM {
			@Override
			public PlayerId mapToPlayerId() {
				if(RNG.nextBoolean()) {
					return PlayerId.PLAYER;
				}
				else {
					return PlayerId.COMPUTER;
				}
			}
		},
		
		PLAYER {
			@Override
			public PlayerId mapToPlayerId() {
				return PlayerId.PLAYER;
			}
		},
		
		COMPUTER {
			@Override
			public PlayerId mapToPlayerId() {
				return PlayerId.COMPUTER;
			}
		};
		
		private static Random RNG = new Random();
		
		/**
		 * Retourne l'id du joueur correspondant à cette valeur de l'énumeration.
		 * @return L'id du joueur correspondant.
		 */
		public abstract PlayerId mapToPlayerId();
	}
	
	/**
	 * Map liant les actions des boutons à leur option de début de partie correspondante.
	 */
	private static final Map<String, StartingPlayer> ACTION_STARTING_PLAYER_MAP = new HashMap<String, StartingPlayer>();

	/**
	 * Groupe de boutons des choix possibles du joueur qui débutera la partie.
	 * Permet de rendre seulement un toggleBouton sélectionnable.
	 */
	private final ButtonGroup startingPlayerGroup;
	
	/**
	 * Bouton du choix aléatoire pour le joueur qui débutera la partie.
	 */
	private final JToggleButton randStartButton;
	
	/**
	 * Bouton du choix du joueur pour commencer la partie.
	 */
	private final JToggleButton playerStartButton;
	
	/**
	 * Bouton du choix de l'ordinateur pour commencer la partie.
	 */
	private final JToggleButton computerStartButton;
		
	/**
	 * Construit le panel.
	 * Utilise la fenêtre principale pour certains listeners.
	 * @param gameFrame La fenêtre principale du jeu.
	 */
	public ChooseGameOptionsPanel(final GameFrame gameFrame) {
		// GridBagLayout afin que les composants ne s'étendent pas
		this.setLayout(new GridBagLayout());
		
		// Contient tous les éléments du panel
		JPanel container = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(50, 50, 50, 50);
		this.add(container, gbc);

		// Choix époque
		ACTION_EPOCH_MAP.put(EpochName.X_SIECLE.name(), new EpochX());
		ACTION_EPOCH_MAP.put(EpochName.XVI_SIECLE.name(), new EpochXVI());
		ACTION_EPOCH_MAP.put(EpochName.XX_SIECLE.name(), new EpochXX());
		
		JLabel epochLabel = new JLabel("Choisissez une époque :", SwingConstants.CENTER);
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = gbc.gridy = 0;
		container.add(epochLabel, gbc);
		
		epochXButton = new JToggleButton("Xème siècle");
		epochXButton.setToolTipText("Les bateaux sont peu résistants.");
		epochXButton.setActionCommand(EpochName.X_SIECLE.name());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.weightx = 1/3;
		container.add(epochXButton, gbc);
		
		epochXVIButton = new JToggleButton("XVIème siècle");
		epochXVIButton.setToolTipText("A cette époque, les bateaux sont moins résistants et coulent en moins de tirs.");
		epochXVIButton.setActionCommand(EpochName.XVI_SIECLE.name());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.gridwidth = 1;
		gbc.gridx++;
		gbc.weightx = 1/3;
		container.add(epochXVIButton, gbc);
		
		epochXXButton = new JToggleButton("XXème siècle");
		epochXXButton.setToolTipText("Bataille navale classique.");
		epochXXButton.setActionCommand(EpochName.XX_SIECLE.name());
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx++;
		gbc.weightx = 1/3;
		container.add(epochXXButton, gbc);
				
		epochGroup = new ButtonGroup();
		epochGroup.add(epochXButton);
		epochGroup.add(epochXVIButton);
		epochGroup.add(epochXXButton);
		
		// Choix stratégie de tir de l'ordi
		ACTION_SHOOT_MAP.put(ShootingStrategyName.RANDOM.name(), new RandomShooting());
		ACTION_SHOOT_MAP.put(ShootingStrategyName.SEEK_THEN_DESTROY_RANDOM.name(), new SeekThenDestroyRandomShooting());
		ACTION_SHOOT_MAP.put(ShootingStrategyName.SEEK_THEN_DESTROY_CROSS.name(), new SeekThenDestroyCrossShooting());
		
		JLabel shotStrategyLabel = new JLabel("Choisissez la technique de tir de l'ordinateur :", SwingConstants.CENTER);
		gbc.gridy++;
		gbc.insets = new Insets(30, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy++;
		container.add(shotStrategyLabel, gbc);
		
		randShotButton = new JToggleButton("Tir aléatoire");
		randShotButton.setToolTipText("Tirs entièrement aléatoires.");
		randShotButton.setActionCommand(ShootingStrategyName.RANDOM.name());
		gbc.fill = GridBagConstraints.BOTH;
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
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.gridx++;
		gbc.weightx = 1/3;
		container.add(SAndDRandShotButton, gbc);

		SAndDCrossShotButton = new JToggleButton("<html><center>Chasse/Cible<br>avec tir en croix</center></html>");
		SAndDCrossShotButton.setToolTipText("<html>Avec cette stratégie, l’ordinateur débute en mode Chasse et tire en croix jusqu’à ce qu’il trouve une cible.<br>" +
				"Lorsqu’il a touché, il s’acharne sur les cases adjacentes.<br>" +
				"Une fois le navire coulé, la chasse reprend jusqu’à l’acquisition d’une nouvelle cible.</html>");
		SAndDCrossShotButton.setActionCommand(ShootingStrategyName.SEEK_THEN_DESTROY_CROSS.name());
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx++;
		gbc.weightx = 1/3;
		container.add(SAndDCrossShotButton, gbc);

		shotGroup = new ButtonGroup();
		shotGroup.add(randShotButton);
		shotGroup.add(SAndDRandShotButton);
		shotGroup.add(SAndDCrossShotButton);
		
		// Choix joueur qui commence
		ACTION_STARTING_PLAYER_MAP.put(StartingPlayer.RANDOM.name(), StartingPlayer.RANDOM);
		ACTION_STARTING_PLAYER_MAP.put(StartingPlayer.PLAYER.name(), StartingPlayer.PLAYER);
		ACTION_STARTING_PLAYER_MAP.put(StartingPlayer.COMPUTER.name(), StartingPlayer.COMPUTER);
		
		JLabel startingPlayerLabel = new JLabel("Choisissez le joueur qui débutera la partie :", SwingConstants.CENTER);
		gbc.insets = new Insets(30, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy++;
		container.add(startingPlayerLabel, gbc);
		
		randStartButton = new JToggleButton("Aléatoire");
		randStartButton.setActionCommand(StartingPlayer.RANDOM.name());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.weightx = 1/3;
		container.add(randStartButton, gbc);
		
		playerStartButton = new JToggleButton("Joueur");
		playerStartButton.setActionCommand(StartingPlayer.PLAYER.name());
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.gridx++;
		gbc.weightx = 1/3;
		container.add(playerStartButton, gbc);
		
		computerStartButton = new JToggleButton("Ordinateur");
		computerStartButton.setActionCommand(StartingPlayer.COMPUTER.name());
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx++;
		gbc.weightx = 1/3;
		container.add(computerStartButton, gbc);	

		startingPlayerGroup = new ButtonGroup();
		startingPlayerGroup.add(randStartButton);
		startingPlayerGroup.add(playerStartButton);
		startingPlayerGroup.add(computerStartButton);
		
		// Bouton démarrer partie
		JButton startGameButton = new JButton("Démarrer la partie");
		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.newGame(getChosenEpoch(), getChosenShootingStrategy(), getChosenStartingPlayer());
				setToDefaultChoices();
			}
		});
		gbc.insets = new Insets(30, 100, 10, 100);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy++;
		container.add(startGameButton, gbc);

		// Bouton retour
		JButton returnButton = new JButton("Retour");
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showPanel(GameFrame.PanelId.MAIN_MENU_PANEL);
				setToDefaultChoices();
			}
		});
		gbc.insets = new Insets(0, 100, 5, 100);
		gbc.gridy++;
		container.add(returnButton, gbc);
		
		// Sélectionne les options par défaut
		setToDefaultChoices();
	}
	
	/**
	 * Sélectionne les options par défaut.
	 */
	private void setToDefaultChoices() {
		epochXXButton.setSelected(true);
		randShotButton.setSelected(true);
		randStartButton.setSelected(true);
	}
	
	/**
	 * Récupère l'époque sélectionné.
	 * @return L'époque sélectionné.
	 */
	private Epoch getChosenEpoch() {
        return ACTION_EPOCH_MAP.get(epochGroup.getSelection().getActionCommand());
	}
	
	/**
	 * Récupère la stratégie de tir de l'ordinateur sélectionnée.
	 * @return La stratégie de tir de l'ordinateur sélectionnée.
	 */
	private ShootingStrategy getChosenShootingStrategy() {
        return ACTION_SHOOT_MAP.get(shotGroup.getSelection().getActionCommand());
	}
	
	/**
	 * Récupère le choix du joueur qui commencera la partie.
	 * @return Le choix du joueur qui commencera la partie.
	 */
	private PlayerId getChosenStartingPlayer() {
		return ACTION_STARTING_PLAYER_MAP.get(startingPlayerGroup.getSelection().getActionCommand()).mapToPlayerId();
	}
	
}
