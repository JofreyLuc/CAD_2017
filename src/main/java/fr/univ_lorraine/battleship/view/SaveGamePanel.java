package fr.univ_lorraine.battleship.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Panel pour la sauvegarde de partie.
 * Contient un JFileChooser.
 */
@SuppressWarnings("serial")
public class SaveGamePanel extends JPanel {

	/**
	 * Construit le panel.
	 * Utilise la fenêtre principale pour certains listeners.
	 * @param gameFrame La fenêtre principale du jeu.
	 */
	public SaveGamePanel(final GameFrame gameFrame) {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers de sauvegarde (*.sav)", "sav"));
		fileChooser.setApproveButtonText("Sauvegarder");
		fileChooser.setApproveButtonToolTipText("Sauvegarde la partie dans le fichier spécifié.");
		UIManager.put("FileChooser.cancelButtonToolTipText", "Retourne au menu principal.");
		SwingUtilities.updateComponentTreeUI(fileChooser);

		fileChooser.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent evt) {
	        	if (evt.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {
	    	        gameFrame.saveGame(fileChooser.getSelectedFile().getAbsolutePath());
	    	    } else if (evt.getActionCommand().equals(javax.swing.JFileChooser.CANCEL_SELECTION)) {
					backToLastPanel(gameFrame);
	    	    }
	        }
		});
		
        //Key bindings
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "BACK");
        this.getActionMap().put("BACK", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
				backToLastPanel(gameFrame);
            }
        });
		
		this.add(fileChooser);
	}

	/**
	 * Revient au panel précédent.
	 * @param gameFrame La fenêtre principale.
	 */
	private void backToLastPanel(GameFrame gameFrame) {
		gameFrame.showPanel(GameFrame.PanelId.OPTIONS_MENU_PANEL);
	}

}
