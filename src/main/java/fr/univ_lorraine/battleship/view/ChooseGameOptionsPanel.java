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

import fr.univ_lorraine.battleship.model.CrossShooting;
import fr.univ_lorraine.battleship.model.Epoch;
import fr.univ_lorraine.battleship.model.EpochXVI;
import fr.univ_lorraine.battleship.model.EpochXX;
import fr.univ_lorraine.battleship.model.RandomShooting;
import fr.univ_lorraine.battleship.model.ShootingStrategy;
import fr.univ_lorraine.battleship.model.Epoch.EpochName;
import fr.univ_lorraine.battleship.model.Game.PlayerId;
import fr.univ_lorraine.battleship.model.ShootingStrategy.ShootingStrategyName;


/**
 * Panel du choix des options avant de lancer une partie.
 */
@SuppressWarnings("serial")
public class ChooseGameOptionsPanel extends JPanel {
	
	/**
	 * Map liant les actions des boutons � leur �poque correspondante.
	 */
	private static final Map<String, Epoch> ACTION_EPOCH_MAP = new HashMap<String, Epoch>();

	/**
	 * Groupe de boutons des �poques.
	 * Permet de rendre seulement un toggleBouton s�lectionnable.
	 */
	private final ButtonGroup epochGroup;
	
	/**
	 * Bouton de l'�poque XX.
	 */
	private final JToggleButton epochXXButton;
	
	/**
	 * Bouton de l'�poque XVI.
	 */
	private final JToggleButton epochXVIButton;
	
	/**
	 * Map liant les actions des boutons � leur strat�gie de tir correspondante.
	 */
	private static final Map<String, ShootingStrategy> ACTION_SHOOT_MAP = new HashMap<String, ShootingStrategy>();

	/**
	 * Groupe de boutons des strat�gies de tir.
	 * Permet de rendre seulement un toggleBouton s�lectionnable.
	 */
	private final ButtonGroup shotGroup;
	
	/**
	 * Bouton de la strat�gie de tir al�atoire.
	 */
	private final JToggleButton randShotButton;
	
	/**
	 * Bouton de la strat�gie de tir en croix.
	 */
	private final JToggleButton crossShotButton;
	
	/**
	 * Enumerations des choix possibles du joueur qui d�butera la partie.
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
		 * Retourne l'id du joueur correspondant � cette valeur de l'�numeration.
		 * @return L'id du joueur correspondant.
		 */
		public abstract PlayerId mapToPlayerId();
	}
	
	/**
	 * Map liant les actions des boutons � leur option de d�but de partie correspondante.
	 */
	private static final Map<String, StartingPlayer> ACTION_STARTING_PLAYER_MAP = new HashMap<String, StartingPlayer>();

	/**
	 * Groupe de boutons des choix possibles du joueur qui d�butera la partie.
	 * Permet de rendre seulement un toggleBouton s�lectionnable.
	 */
	private final ButtonGroup startingPlayerGroup;
	
	/**
	 * Bouton du choix al�atoire pour le joueur qui d�butera la partie.
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
	 * Utilise la fen�tre principale pour certains listeners.
	 * @param gameFrame La fen�tre principale du jeu.
	 */
	public ChooseGameOptionsPanel(final GameFrame gameFrame) {
		// GridBagLayout afin que les composants ne s'�tendent pas
		this.setLayout(new GridBagLayout());
		
		// Contient tous les �l�ments du panel
		JPanel container = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(50, 50, 50, 50);
		this.add(container, gbc);

		// Choix �poque
		ACTION_EPOCH_MAP.put(EpochName.XVI_SIECLE.name(), new EpochXVI());
		ACTION_EPOCH_MAP.put(EpochName.XX_SIECLE.name(), new EpochXX());
		
		JLabel epochLabel = new JLabel("Choisissez une �poque :");
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = gbc.gridy = 0;
		container.add(epochLabel, gbc);
		
		epochXVIButton = new JToggleButton("XVI�me si�cle");
		epochXVIButton.setActionCommand(EpochName.XVI_SIECLE.name());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.weightx = 1/3;
		container.add(epochXVIButton, gbc);
		
		epochXXButton = new JToggleButton("XX�me si�cle");
		epochXXButton.setActionCommand(EpochName.XX_SIECLE.name());
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx++;
		gbc.weightx = 1/3;
		container.add(epochXXButton, gbc);
				
		epochGroup = new ButtonGroup();
		epochGroup.add(epochXVIButton);
		epochGroup.add(epochXXButton);
		
		// Choix strat�gie de tir de l'ordi
		ACTION_SHOOT_MAP.put(ShootingStrategyName.RANDOM.name(), new RandomShooting());
		ACTION_SHOOT_MAP.put(ShootingStrategyName.CROSS.name(), new CrossShooting());
		
		JLabel shotStrategyLabel = new JLabel("Choisissez la technique de tir de l'ordinateur :");
		gbc.gridy++;
		gbc.insets = new Insets(30, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy++;
		container.add(shotStrategyLabel, gbc);
		
		randShotButton = new JToggleButton("Tir al�atoire");
		randShotButton.setActionCommand(ShootingStrategyName.RANDOM.name());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 0, 10);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.weightx = 1/3;
		container.add(randShotButton, gbc);
		
		crossShotButton = new JToggleButton("Tir en croix");
		crossShotButton.setActionCommand(ShootingStrategyName.CROSS.name());
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx++;
		gbc.weightx = 1/3;
		container.add(crossShotButton, gbc);
		
		shotGroup = new ButtonGroup();
		shotGroup.add(randShotButton);
		shotGroup.add(crossShotButton);
		
		// Choix joueur qui commence
		ACTION_STARTING_PLAYER_MAP.put(StartingPlayer.RANDOM.name(), StartingPlayer.RANDOM);
		ACTION_STARTING_PLAYER_MAP.put(StartingPlayer.PLAYER.name(), StartingPlayer.PLAYER);
		ACTION_STARTING_PLAYER_MAP.put(StartingPlayer.COMPUTER.name(), StartingPlayer.COMPUTER);
		
		JLabel startingPlayerLabel = new JLabel("Choisissez le joueur qui d�butera la partie :");
		gbc.insets = new Insets(30, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy++;
		container.add(startingPlayerLabel, gbc);
		
		randStartButton = new JToggleButton("Al�atoire");
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
		
		// Bouton d�marrer partie
		JButton startGameButton = new JButton("D�marrer la partie");
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
		
		// S�lectionne les options par d�faut
		setToDefaultChoices();
	}
	
	/**
	 * S�lectionne les options par d�faut.
	 */
	private void setToDefaultChoices() {
		epochXXButton.setSelected(true);
		randShotButton.setSelected(true);
		randStartButton.setSelected(true);
	}
	
	/**
	 * R�cup�re l'�poque s�lectionn�.
	 * @return L'�poque s�lectionn�.
	 */
	private Epoch getChosenEpoch() {
        return ACTION_EPOCH_MAP.get(epochGroup.getSelection().getActionCommand());
	}
	
	/**
	 * R�cup�re la strat�gie de tir de l'ordinateur s�lectionn�e.
	 * @return La strat�gie de tir de l'ordinateur s�lectionn�e.
	 */
	private ShootingStrategy getChosenShootingStrategy() {
        return ACTION_SHOOT_MAP.get(shotGroup.getSelection().getActionCommand());
	}
	
	/**
	 * R�cup�re le choix du joueur qui commencera la partie.
	 * @return Le choix du joueur qui commencera la partie.
	 */
	private PlayerId getChosenStartingPlayer() {
		return ACTION_STARTING_PLAYER_MAP.get(startingPlayerGroup.getSelection().getActionCommand()).mapToPlayerId();
	}
	
}
