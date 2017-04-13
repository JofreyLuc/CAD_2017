package view;

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

@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel {

	public MainMenuPanel(final GameFrame gameFrame) {
		this.setLayout(new GridBagLayout());
		JPanel container = new JPanel(new GridLayout(3, 1, 0, 15));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(50, 50, 50, 50);
		this.add(container, gbc);
		
		JButton newGameButton = new JButton("Nouvelle partie");	
		newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		newGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showPanel(GameFrame.CHOOSE_GAME_OPTIONS_PANEL);
			}
		});
		
		JButton loadGameButton = new JButton("Charger partie");
		loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.loadGame();
			}
		});

		JButton exitButton = new JButton("Quitter");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
			}
		});

		container.add(newGameButton);
		container.add(loadGameButton);
		container.add(exitButton);
	}
	
}
