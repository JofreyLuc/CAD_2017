package fr.univ_lorraine.battleship;
import java.io.Closeable;
import java.io.IOException;

/**
 * Classe static contenant des méthodes utilitaires.
 */
public class Utils {

	private Utils() {}
	
	/**
	 * Ferme la source sans lever d'exception.
	 * @param c La source à fermer.
	 */
	public static void closeQuietly(Closeable c) {      
		if (c == null) return;
		try {
			c.close();
		} catch (IOException e) { }
	}
	
	/**
	 * Méthode provenant de la libraire Apache :
	 * https://commons.apache.org/proper/commons-io/javadocs/api-1.4/org/apache/commons/io/FilenameUtils.html#getExtension(java.lang.String)
	 * 
	 * Retourne l'extension d'un fichier.
	 * @param filePath Le chemin du fichier.
	 * @return L'extension du fichier.
	 */
	public static String getExtension(String filePath) {
        if (filePath == null) {
            return null;
        }
        int extensionPos = filePath.lastIndexOf('.');
        int lastUnixPos = filePath.lastIndexOf('/');
        int lastWindowsPos = filePath.lastIndexOf('\\');
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);

        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) {
            return "";
        } else {
            return filePath.substring(index + 1);
        }
    }
	
}
