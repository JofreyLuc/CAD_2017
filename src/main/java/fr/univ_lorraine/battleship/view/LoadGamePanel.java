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
 * Panel pour le chargement de partie.
 * Contient un JFileChooser.
 */
@SuppressWarnings("serial")
public class LoadGamePanel extends JPanel {
	
	/**
	 * Construit le panel.
	 * Utilise la fenêtre principale pour certains listeners.
	 * @param gameFrame La fenêtre principale du jeu.
	 */
	public LoadGamePanel(final GameFrame gameFrame) {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers de sauvegarde (*.sav)", "sav"));
		fileChooser.setApproveButtonText("Charger");
		fileChooser.setApproveButtonToolTipText("Charge le fichier de sauvegarde sélectionné.");
		UIManager.put("FileChooser.cancelButtonToolTipText", "Retourne au menu principal.");
		SwingUtilities.updateComponentTreeUI(fileChooser);

		fileChooser.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent evt) {
	        	if (evt.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {
	    	        gameFrame.loadGame(fileChooser.getSelectedFile().getAbsolutePath());
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
        gameFrame.showPanel(GameFrame.PanelId.MAIN_MENU_PANEL);
	}
	
}
