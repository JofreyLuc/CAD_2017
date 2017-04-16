package view;

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

import model.ComputerController;
import model.CrossShooting;
import model.Game;
import model.RandomShooting;
import model.ShootingStrategy;

@SuppressWarnings("serial")
public class OptionsMenuPanel extends JPanel {
	
	private Game game;
	
	private static final String SHOOT_RAND_STR = "SHOOT_RAND";
	
	private static final String SHOOT_CROSS_STR = "SHOOT_CROSS";
	
	private static final Map<String, ShootingStrategy> SHOOT_MAP = new HashMap<String, ShootingStrategy>();
		
	private final ButtonGroup shotGroup;
	
	private final JToggleButton randShotButton;
	
	private final JToggleButton crossShotButton;
	
	public OptionsMenuPanel(final GameFrame gameFrame) {
		SHOOT_MAP.put(SHOOT_RAND_STR, new RandomShooting());
		SHOOT_MAP.put(SHOOT_CROSS_STR, new CrossShooting());
		
		this.setLayout(new GridBagLayout());
		JPanel container = new JPanel(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = new Insets(50, 50, 50, 50);
		this.add(container, gbc1);
		
		// Choix stratégie de tir de l'ordi
		JLabel shotStrategyLabel = new JLabel("Changer la technique de tir de l'ordinateur :");

		ActionListener optionChangedAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeGameOptions();
			}
		};
		
		randShotButton = new JToggleButton("Tir aléatoire");
		randShotButton.setActionCommand(SHOOT_RAND_STR);
		randShotButton.addActionListener(optionChangedAction);
		crossShotButton = new JToggleButton("Tir en croix");
		crossShotButton.setActionCommand(SHOOT_CROSS_STR);
		crossShotButton.addActionListener(optionChangedAction);

		shotGroup = new ButtonGroup();
		shotGroup.add(randShotButton);
		shotGroup.add(crossShotButton);

		// Bouton reprendre partie
		JButton resumeGameButton = new JButton("Reprendre");
		resumeGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resumeGame(gameFrame);
			}
		});
		
		// Bouton sauvegarder
		JButton saveGameButton = new JButton("Sauvegarder la partie");
		saveGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showPanel(GameFrame.FILE_SAVE_PANEL);
			}
		});
	
		// Bouton menu principal
		JButton returnButton = new JButton("Retour au menu principal");
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showPanel(GameFrame.MAIN_MENU_PANEL);
			}
		});
		
        //Key bindings
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "BACK");
        this.getActionMap().put("BACK", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
				resumeGame(gameFrame);
            }
        });
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = gbc.gridy = 0;
		container.add(resumeGameButton, gbc);
		
		// Stratégies de tir
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
		container.add(saveGameButton, gbc);
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridy++;
		container.add(returnButton, gbc);	
		
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
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
	
	private void changeGameOptions() {
		if (game != null) {
			ComputerController compController = game.getComputerController();
			ShootingStrategy chosenShootingStrategy = SHOOT_MAP.get(shotGroup.getSelection().getActionCommand());
			if (compController != null && compController.getShootingStrategy() != chosenShootingStrategy) {
				compController.setShootingStrategy(chosenShootingStrategy);
			}
		}

	}

	private void resumeGame(GameFrame gameFrame) {
		gameFrame.showPanel(GameFrame.GAME_PANEL);
	}
	
	@Override
	public void setVisible(boolean aFlag) {
		// On met à jour les options avant d'afficher le panel
		updateCurrentOptionsSelected();
		super.setVisible(aFlag);
	}
}
