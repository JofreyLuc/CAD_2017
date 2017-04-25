package fr.univ_lorraine.battleship.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Panel du menu principal du jeu.
 */
@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel {

	/**
	 * Construit le panel.
	 * Utilise la fenêtre principale pour certains listeners.
	 * @param gameFrame La fenêtre principale du jeu.
	 */
	public MainMenuPanel(final GameFrame gameFrame) {
		// GridBagLayout afin que les composants ne s'étendent pas
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// Contient tous les éléments du panel
		JPanel container = new JPanel(new GridLayout(3, 1, 0, 15));
		gbc.insets = new Insets(50, 50, 50, 50);
		this.add(container, gbc);
		
		JButton newGameButton = new JButton("Nouvelle partie");	
		newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showPanel(GameFrame.PanelId.CHOOSE_GAME_OPTIONS_PANEL);
			}
		});
		container.add(newGameButton);

		JButton loadGameButton = new JButton("Charger partie");
		loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showPanel(GameFrame.PanelId.LOAD_GAME_PANEL);
			}
		});
		container.add(loadGameButton);

		JButton exitButton = new JButton("Quitter");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
			}
		});
		container.add(exitButton);
	}
	
}
