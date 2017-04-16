package model;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Classe static permettant de charger et de sauvegarder une partie.
 */
public class GameLoader {

	/**
	 * L'extension des fichiers de sauvegarde de partie.
	 */
	public static final String SAVE_EXTENSION = ".sav";
	
	private GameLoader() {}
	
	/**
	 * Charge une partie depuis un fichier
	 * @param file Le fichier de sauvegarde.
	 * @return La partie à sauvegarder.
	 * @throws ClassNotFoundException, IOException 
	 */
	public static Game loadGame(File file) throws ClassNotFoundException, IOException {
       ObjectInputStream in = null;
       Game game = null;
       try {
	    	in = new ObjectInputStream(new FileInputStream(file));
			game =(Game)in.readObject();
       } catch (ClassNotFoundException | IOException e) {
			throw e;
       } finally {
			closeQuietly(in);
       }
       return game;
	}
	
	/**
	 * Sauvegarde la partie dans un fichier.
	 * @param game La partie à sauvegarder.
	 * @param file Le fichier de sauvegarde.
	 * @throws IOException
	 */
	public static void saveGame(Game game, File file) throws IOException {
		ObjectOutputStream out = null;
	    try {
			out = new ObjectOutputStream(new FileOutputStream(file));  
			out.writeObject(game);
		} catch (IOException e) {
			throw e;
		} finally {
			closeQuietly(out);
		}
	}
	
	/**
	 * Ferme la source sans lever d'exception.
	 * @param c La source à fermer.
	 */
	private static void closeQuietly(Closeable c) {      
		if (c == null) return;
		try {
			c.close();
		} catch (IOException e) { }
	}
	
}
