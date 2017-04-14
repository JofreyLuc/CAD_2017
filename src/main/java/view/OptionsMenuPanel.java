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

import model.CrossShooting;
import model.RandomShooting;
import model.ShootingStrategy;

@SuppressWarnings("serial")
public class OptionsMenuPanel extends JPanel {

	private final static String SHOOT_RAND_STR = "SHOOT_RAND";
	
	private final static String SHOOT_CROSS_STR = "SHOOT_CROSS";
	
	private final static Map<String, ShootingStrategy> SHOOT_MAP = new HashMap<String, ShootingStrategy>();
		
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

		randShotButton = new JToggleButton("Tir aléatoire");
		randShotButton.setActionCommand(SHOOT_RAND_STR);
		crossShotButton = new JToggleButton("Tir en croix");
		crossShotButton.setActionCommand(SHOOT_CROSS_STR);

		shotGroup = new ButtonGroup();
		shotGroup.add(randShotButton);
		shotGroup.add(crossShotButton);

		randShotButton.setSelected(true);

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
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "OPTIONS");
        this.getActionMap().put("OPTIONS", new AbstractAction(){
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
	
	private ShootingStrategy getChosenShootingStrategy() {
        return SHOOT_MAP.get(shotGroup.getSelection().getActionCommand());
	}

	private void resumeGame(GameFrame gameFrame) {
		gameFrame.changeGameOptions(getChosenShootingStrategy());
		gameFrame.showPanel(GameFrame.GAME_PANEL);
	}
}
