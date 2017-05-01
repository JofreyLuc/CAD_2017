package fr.univ_lorraine.battleship.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import fr.univ_lorraine.battleship.model.Game;
import fr.univ_lorraine.battleship.model.Game.GameState;



/**
 * Panel étant la vue principale du jeu contenant les grilles des deux joueurs
 * ainsi que d'autres informations (tour courant, etc.).
 * 
 * Observer de Game.
 */
@SuppressWarnings("serial")
public class GameView extends JPanel implements Observer {

	/**
	 * Label indiquant le tour courant.
	 */
	private JLabel turnLabel;
	
	/**
	 * Label affichant des instructions/une aide quand nécessaire.
	 */
	private JLabel instructionLabel;
	
	/**
	 * Panel de la grille du joueur.
	 */
	private SeaView playerGridView;
	
	/**
	 * Panel de la grille de l'ordinateur.
	 */
	private SeaView computerGridView;
	
	/**
	 * Le jeu (modèle).
	 */
	private Game game;
	
	/**
	 * Panel à afficher quand la partie est terminée
	 * positionné par dessus le panel principal.
	 */
	private JPanel gameOverPanel;
	
	/**
	 * Label indiquant le résultat de la partie.
	 */
	private JLabel gameOverLabel;
	
	private static final String WIN_MESSAGE = "Vous avez gagné !";
	
	private static final String LOSE_MESSAGE = "Vous avez perdu !";
	
	/**
	 * Construit le panel.
	 * Utilise la fenêtre principale pour certains listeners.
	 * @param gameFrame La fenêtre principale du jeu.
	 */
	public GameView(final GameFrame gameFrame) {
		// Layout afin de pouvoir afficher le panel de game over quand la partie est terminée
		this.setLayout(new OverlayLayout(this));
		GridBagConstraints gbc = new GridBagConstraints();

		// Panel venant par dessus le panel principal
		gameOverPanel = new JPanel();
		this.add(gameOverPanel, 0);
		gameOverPanel.setLayout(new GridBagLayout());
		gameOverPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		gameOverLabel = new JLabel();
		gbc.gridx = gbc.gridy = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(30, 10, 30, 10);
		gameOverPanel.add(gameOverLabel, gbc);
		
		JButton returnButton = new JButton("Revenir au menu principal");
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.showPanel(GameFrame.PanelId.MAIN_MENU_PANEL);
			}
		});
		gbc.gridy++;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 10, 30, 10);
		gameOverPanel.add(returnButton, gbc);
		
		// Restreint la taille du panel de fin de partie pour qu'il ne recouvre pas le panel principal
		gameOverPanel.setMaximumSize(gameOverPanel.getPreferredSize());
		
		// Panel principal du jeu contenant tous les éléments
		JPanel mainPanel = new JPanel();
		this.add(mainPanel, 1);
		mainPanel.setLayout(new GridBagLayout());		
		turnLabel = new JLabel("TOUR DE L'ORDINATEUR");
		turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		turnLabel.setVerticalAlignment(SwingConstants.CENTER);
		turnLabel.setMinimumSize(turnLabel.getPreferredSize());
		turnLabel.setPreferredSize(turnLabel.getPreferredSize());
		turnLabel.setMaximumSize(turnLabel.getPreferredSize());
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = gbc.gridy = 0;
		gbc.insets = new Insets(15, 0, 15, 0);
		mainPanel.add(turnLabel, gbc);

		playerGridView = new PlayerSeaView();
		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.insets = new Insets(0, 15, 15, 15);
		mainPanel.add(playerGridView, gbc);
		
		computerGridView = new ComputerSeaView();
		gbc.gridx++;
		gbc.insets = new Insets(0, 15, 15, 15);
		mainPanel.add(computerGridView, gbc);
		
		instructionLabel = new JLabel("<html>Veuillez placer vos bateaux.<br>Clic gauche pour placer.<br>Clic droit pour pivoter.");
		instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		instructionLabel.setVerticalAlignment(SwingConstants.CENTER);
		instructionLabel.setMinimumSize(instructionLabel.getPreferredSize());
		instructionLabel.setPreferredSize(instructionLabel.getPreferredSize());
		instructionLabel.setMaximumSize(instructionLabel.getPreferredSize());
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.insets = new Insets(0, 15, 15, 15);
		mainPanel.add(instructionLabel, gbc);
		
		Box escapeKeyBox = Box.createHorizontalBox();
		JLabel escapeKeyImage = new JLabel();
		escapeKeyImage.setIcon(new ImageIcon(ImageFactory.getInstance().getEscapeKeyImage()));
		escapeKeyBox.add(escapeKeyImage);
		escapeKeyBox.add(new JLabel(" pour afficher les options."));
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridy++;
		gbc.insets = new Insets(0, 15, 15, 15);
		mainPanel.add(escapeKeyBox, gbc);
		
		// Raffraîchit l'affichage toutes les 17ms ~ 60 fps
		Timer timer = new Timer(17, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
        	
        });
        timer.start();
        
        //Key bindings
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "OPTIONS");
        this.getActionMap().put("OPTIONS", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                gameFrame.showPanel(GameFrame.PanelId.OPTIONS_MENU_PANEL);
            }
        });
	}

	@Override
	public boolean isOptimizedDrawingEnabled() {
		return false;
    }
	
	/**
	 * Retourne le jeu courant.
	 * @return Le jeu courant.
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Place le jeu courant et initialise les vues.
	 * @param game Le jeu.
	 */
	public void setGame(Game game) {
		if (this.game != null) {
			this.game.deleteObservers();
		}
		this.game = game;
		game.addObserver(this);
		playerGridView.initialize(game);
		computerGridView.initialize(game);
		// Cache le panel de fin de partie
		gameOverPanel.setVisible(false);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Game game = (Game) o;
		
		// Si la partie est terminée
		if (game.getGameState() == GameState.COMPUTER_WINS) {
			gameOverLabel.setText(LOSE_MESSAGE);
			gameOverPanel.setVisible(true);
		}
		else if (game.getGameState() == GameState.PLAYER_WINS) {
			gameOverLabel.setText(WIN_MESSAGE);
			gameOverPanel.setVisible(true);
		}
		
		// Selon le tour du joueur
		switch(game.getPlayerTurn()) {
			case COMPUTER:
				turnLabel.setText("TOUR DE L'ORDINATEUR");
				instructionLabel.setText("");
				break;
			case PLAYER:
				turnLabel.setText("TOUR DU JOUEUR");
				if (!game.isPositionningPhaseOver()) {
					instructionLabel.setText("<html>Veuillez placer vos bateaux.<br>Clic gauche pour placer.<br>Clic droit pour pivoter.");
				}
				else {
					instructionLabel.setText("");
				}
				break;
			default:
				throw new AssertionError("Joueur inconnu " + game.getPlayerTurn());		
		}
	}

	/**
	 * Termine les animations qui ne bouclent pas.
	 */
	public void setNonLoopingAnimationsToEnd() {
		playerGridView.setNonLoopingAnimationsToEnd();
		computerGridView.setNonLoopingAnimationsToEnd();
	}
	
}
