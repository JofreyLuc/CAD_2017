package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Classe permettant de charger des sprites et d'obtenir les images correspondantes.
 */
public class Sprite {

	/**
	 * L'orientation du sprite.
	 */
	public enum Orientation { HORIZONTAL, VERTICAL }
	
    /**
     * Charge une feuille de sprite et renvoie le tableau d'images correspondant.
     * @param fileName Le nom du fichier image.
     * @param orientation L'orientation du sprite.
     * @param numberOfFrames Le nombre d'images que contient le sprite.
     * @return Le tableau d'images correspondant au sprite.
     * @throws ResourceNotFoundException 
     * @throws IOException 
     */
    public static BufferedImage[] loadSpriteSheet(String fileName, Orientation orientation, int numberOfFrames) throws ResourceNotFoundException, IOException {
    	BufferedImage spriteSheet = null;
		URL imageURL = Sprite.class.getResource(fileName);
		if (imageURL == null)
			throw new ResourceNotFoundException(fileName);
        spriteSheet = ImageIO.read(Sprite.class.getResource(fileName));
    	
    	BufferedImage frames[] = new BufferedImage[numberOfFrames];
    	int frameWidth = spriteSheet.getWidth()/numberOfFrames;
    	int frameHeight = frameWidth;
    	for (int i = 0 ; i < numberOfFrames ; i++) {
    		switch(orientation) {
				case HORIZONTAL:
					frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
					break;
				case VERTICAL:
					frames[i] = spriteSheet.getSubimage(0, i * frameHeight, frameWidth, frameHeight);
					break;
				default:
					throw new AssertionError("Orientation inconnu " + orientation);
    		}
    	}
    	return frames;
    }

    /**
     * Charge une feuille de sprite et renvoie le tableau d'images correspondant.
     * @param fileName Le nom du fichier image.
     * @param orientation L'orientation du sprite.
     * @param frameWidth La largeur d'une image du sprite.
     * @param frameHeight La hauteur d'une image du sprite.
     * @return Le tableau d'images correspondant au sprite.
     */
    public static BufferedImage[] loadSpriteSheet(String fileName, Orientation orientation, int frameWidth, int frameHeight) {
    	BufferedImage spriteSheet = null;
    	try {
            spriteSheet = ImageIO.read(Sprite.class.getResource(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    	int numberOfFrames = 0;
    	switch(orientation) {
	    	case HORIZONTAL:
	        	numberOfFrames = spriteSheet.getWidth()/frameWidth;
	    		break;
	    	case VERTICAL:
	        	numberOfFrames = spriteSheet.getHeight()/frameHeight;
	    		break;
			default:
				throw new AssertionError("Orientation inconnu " + orientation);
    	}
    	
    	BufferedImage frames[] = new BufferedImage[numberOfFrames];
    	for (int i = 0 ; i < numberOfFrames ; i++) {
    		switch(orientation) {
				case HORIZONTAL:
					frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
					break;
				case VERTICAL:
					frames[i] = spriteSheet.getSubimage(0, i * frameHeight, frameWidth, frameHeight);
					break;
				default:
					throw new AssertionError("Orientation inconnu " + orientation);
    		}
    	}
    	return frames;
    }
    
}
