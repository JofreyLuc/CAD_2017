package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class FileChooserPanel extends JPanel {

	public enum FileChooserType { SAVE, LOAD }
		
	public FileChooserPanel(final GameFrame gameFrame, FileChooserType type) {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers de sauvegarde (*.sav)", "sav"));
		switch(type) {
			case LOAD:
				fileChooser.setApproveButtonText("Charger");
				fileChooser.setApproveButtonToolTipText("Charge le fichier de sauvegarde sélectionné.");
				UIManager.put("FileChooser.cancelButtonToolTipText", "Retourne au menu principal.");
				fileChooser.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent evt) {
			        	if (evt.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {
			    	        gameFrame.loadGame(fileChooser.getSelectedFile().getAbsolutePath());
			    	    } else if (evt.getActionCommand().equals(javax.swing.JFileChooser.CANCEL_SELECTION)) {
			    	        gameFrame.showPanel(GameFrame.MAIN_MENU_PANEL);
			    	    }
			        }
				});
				break;
			case SAVE:
				fileChooser.setApproveButtonText("Sauvegarder");
				fileChooser.setApproveButtonToolTipText("Sauvegarde la partie dans le fichier spécifié.");
				UIManager.put("FileChooser.cancelButtonToolTipText", "Retourne au menu principal.");
				fileChooser.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent evt) {
			        	if (evt.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {
			    	        gameFrame.saveGame(fileChooser.getSelectedFile().getAbsolutePath());
			    	    } else if (evt.getActionCommand().equals(javax.swing.JFileChooser.CANCEL_SELECTION)) {
			    	        gameFrame.showPanel(GameFrame.MAIN_MENU_PANEL);
			    	    }
			        }
				});
				break;
			default:
	    		throw new AssertionError("Type inconnnu " + type);
		}
		SwingUtilities.updateComponentTreeUI(fileChooser);
		
		
		
		this.add(fileChooser);
	}

}
