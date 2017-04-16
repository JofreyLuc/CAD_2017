package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import model.CrossShooting;
import model.Epoch;
import model.EpochXVI;
import model.EpochXX;
import model.RandomShooting;
import model.ShootingStrategy;
import model.Game.PlayerId;

@SuppressWarnings("serial")
public class ChooseGameOptionsPanel extends JPanel {
	
	private static final String EPOCH_XVI_STR = "EPOCH_XVI";

	private static final String EPOCH_XX_STR = "EPOCH_XX";

	private static final Map<String, Epoch> EPOCH_MAP = new HashMap<String, Epoch>();
		
	private static final String SHOOT_RAND_STR = "SHOOT_RAND";
	
	private static final String SHOOT_CROSS_STR = "SHOOT_CROSS";
	
	private static final Map<String, ShootingStrategy> SHOOT_MAP = new HashMap<String, ShootingStrategy>();
	
	private static final String STARTING_RAND_STR = "STARTING_RAND";
	
	private static final String STARTING_PLAYER_STR = "STARTING_PLAYER";
	
	private static final String STARTING_COMPUTER_STR = "STARTING_COMPUTER";
	
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
		
		public abstract PlayerId mapToPlayerId();
	}
	
	private static final Map<String, StartingPlayer> STARTING_MAP = new HashMap<String, StartingPlayer>();
	
	private final ButtonGroup epochGroup = new ButtonGroup();
	
	private final JToggleButton epochXXButton = new JToggleButton("XXème siècle");
	
	private final JToggleButton epochXVIButton  = new JToggleButton("XVIème siècle");
	
	private final ButtonGroup shotGroup = new ButtonGroup();
	
	private final JToggleButton randShotButton = new JToggleButton("Tir aléatoire");
	
	private final JToggleButton crossShotButton = new JToggleButton("Tir en croix");

	private final ButtonGroup startingPlayerGroup = new ButtonGroup();
	
	private final JToggleButton randStartButton = new JToggleButton("Aléatoire");
	
	private final JToggleButton playerStartButton = new JToggleButton("Joueur");
	
	private final JToggleButton computerStartButton = new JToggleButton("Ordinateur");
	
	public ChooseGameOptionsPanel(final GameFrame gameFrame) {
		EPOCH_MAP.put(EPOCH_XVI_STR, new EpochXVI());
		EPOCH_MAP.put(EPOCH_XX_STR, new EpochXX());

		SHOOT_MAP.put(SHOOT_RAND_STR, new RandomShooting());
		SHOOT_MAP.put(SHOOT_CROSS_STR, new CrossShooting());
		
		STARTING_MAP.put(STARTING_RAND_STR, StartingPlayer.RANDOM);
		STARTING_MAP.put(STARTING_PLAYER_STR, StartingPlayer.PLAYER);
		STARTING_MAP.put(STARTING_COMPUTER_STR, StartingPlayer.COMPUTER);
		
		this.setLayout(new GridBagLayout());
		JPanel container = new JPanel(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = new Insets(50, 50, 50, 50);
		this.add(container, gbc1);
		
		// Choix époque
		JLabel epochLabel = new JLabel("Choisissez une époque :");
		epochXVIButton.setActionCommand(EPOCH_XVI_STR);
		epochXXButton.setActionCommand(EPOCH_XX_STR);
		epochGroup.add(epochXVIButton);
		epochGroup.add(epochXXButton);

		// Choix stratégie de tir de l'ordi
		JLabel shotStrategyLabel = new JLabel("Choisissez la technique de tir de l'ordinateur :");
		randShotButton.setActionCommand(SHOOT_RAND_STR);
		crossShotButton.setActionCommand(SHOOT_CROSS_STR);
		shotGroup.add(randShotButton);
		shotGroup.add(crossShotButton);
		
		// Choix joueur qui commence
		JLabel startingPlayerLabel = new JLabel("Choisissez le joueur qui débutera la partie :");
		randStartButton.setActionCommand(STARTING_RAND_STR);
		playerStartButton.setActionCommand(STARTING_PLAYER_STR);
		computerStartButton.setActionCommand(STARTING_COMPUTER_STR);
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
	
		// Bouton retour
		JButton returnButton = new JButton("Retour");
		returnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showPanel(GameFrame.MAIN_MENU_PANEL);
				setToDefaultChoices();
			}
		});
		
		// Epoques
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = gbc.gridy = 0;
		container.add(epochLabel, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1/2;
		gbc.insets = new Insets(0, 10, 0, 0);
		container.add(epochXVIButton, gbc);
		gbc.gridx++;
		gbc.weightx = 1/2;
		container.add(epochXXButton, gbc);
		gbc.gridy++;
		// Stratégies de tir
		gbc.insets = new Insets(30, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy++;
		container.add(shotStrategyLabel, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.weightx = 1/2;
		gbc.insets = new Insets(0, 10, 0, 0);
		container.add(randShotButton, gbc);
		gbc.gridx++;
		gbc.weightx = 1/2;
		container.add(crossShotButton, gbc);
		// Joueur qui commence
		gbc.insets = new Insets(30, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy++;
		container.add(startingPlayerLabel, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.weightx = 1/3;
		gbc.insets = new Insets(0, 10, 0, 0);
		container.add(randStartButton, gbc);
		gbc.gridx++;
		gbc.weightx = 1/3;
		gbc.insets = new Insets(0, 10, 0, 0);
		container.add(playerStartButton, gbc);
		gbc.gridx++;
		gbc.weightx = 1/3;
		container.add(computerStartButton, gbc);	
		
		gbc.insets = new Insets(30, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy++;
		container.add(startGameButton, gbc);
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridy++;
		container.add(returnButton, gbc);
		
		setToDefaultChoices();
	}
	
	private void setToDefaultChoices() {
		epochXXButton.setSelected(true);
		randShotButton.setSelected(true);
		randStartButton.setSelected(true);
	}
	
	private Epoch getChosenEpoch() {
        return EPOCH_MAP.get(epochGroup.getSelection().getActionCommand());
	}
	
	private ShootingStrategy getChosenShootingStrategy() {
        return SHOOT_MAP.get(shotGroup.getSelection().getActionCommand());
	}
	
	private PlayerId getChosenStartingPlayer() {
		return STARTING_MAP.get(startingPlayerGroup.getSelection().getActionCommand()).mapToPlayerId();
	}
	
}
