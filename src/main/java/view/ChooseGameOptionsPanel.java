package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

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

@SuppressWarnings("serial")
public class ChooseGameOptionsPanel extends JPanel {
		
	private final static String EPOCH_XVI_STR = "EPOCH_XVI";

	private final static String EPOCH_XX_STR = "EPOCH_XX";

	private final static Map<String, Epoch> EPOCH_MAP = new HashMap<String, Epoch>();
		
	private final static String SHOOT_RAND_STR = "SHOOT_RAND";
	
	private final static String SHOOT_CROSS_STR = "SHOOT_CROSS";
	
	private final static Map<String, ShootingStrategy> SHOOT_MAP = new HashMap<String, ShootingStrategy>();
	
	private final JToggleButton epochXXButton;
	
	private final JToggleButton epochXVIButton;
	
	private final JToggleButton randShotButton;
	
	private final JToggleButton crossShotButton;

	public ChooseGameOptionsPanel(final GameFrame gameFrame) {
		EPOCH_MAP.put(EPOCH_XVI_STR, new EpochXVI());
		EPOCH_MAP.put(EPOCH_XX_STR, new EpochXX());

		SHOOT_MAP.put(SHOOT_RAND_STR, new RandomShooting());
		SHOOT_MAP.put(SHOOT_CROSS_STR, new CrossShooting());
		
		this.setLayout(new GridBagLayout());
		JPanel container = new JPanel(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = new Insets(50, 50, 50, 50);
		this.add(container, gbc1);
		
		// Choix époque
		JLabel epochLabel = new JLabel("Choisissez une époque :");
		
		epochXVIButton = new JToggleButton("XVIème siècle");
		epochXVIButton.setActionCommand(EPOCH_XVI_STR);
		epochXXButton = new JToggleButton("XXème siècle");
		epochXXButton.setActionCommand(EPOCH_XX_STR);

		final ButtonGroup epochGroup = new ButtonGroup();
		epochGroup.add(epochXVIButton);
		epochGroup.add(epochXXButton);

		epochXXButton.setSelected(true);

		// Choix stratégie de tir de l'ordi
		JLabel shotStrategyLabel = new JLabel("Choisissez la technique de tir de l'ordinateur :");

		randShotButton = new JToggleButton("Tir aléatoire");
		randShotButton.setActionCommand(SHOOT_RAND_STR);
		crossShotButton = new JToggleButton("Tir en croix");
		crossShotButton.setActionCommand(SHOOT_CROSS_STR);

		final ButtonGroup shotGroup = new ButtonGroup();
		shotGroup.add(randShotButton);
		shotGroup.add(crossShotButton);

		randShotButton.setSelected(true);

		JButton startGameButton = new JButton("Démarrer la partie");
		startGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.newGame(getChosenEpoch(epochGroup), getChosenShootingStrategy(shotGroup));
				setToDefaultChoices();
			}
		});
	
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
		container.add(epochXVIButton, gbc);
		gbc.gridx++;
		container.add(epochXXButton, gbc);
		// Stratégies de tir
		gbc.insets = new Insets(30, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.gridy++;
		container.add(shotStrategyLabel, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridwidth = 1;
		gbc.gridy++;
		container.add(randShotButton, gbc);
		gbc.gridx++;
		container.add(crossShotButton, gbc);

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
	}
	
	private Epoch getChosenEpoch(ButtonGroup group) {
        return EPOCH_MAP.get(group.getSelection().getActionCommand());
	}
	
	private ShootingStrategy getChosenShootingStrategy(ButtonGroup group) {
        return SHOOT_MAP.get(group.getSelection().getActionCommand());
	}
	
}
