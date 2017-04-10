package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import view.Sprite.Orientation;

/**
 * Singleton permettant de récupérer les images et les animations
 * nécessaires au jeu.
 */
public class ImageFactory {

	/**
	 * L'instance du singleton.
	 */
    private static ImageFactory instance = new ImageFactory();
	
	// Images
	private BufferedImage gridImage;
	private BufferedImage activeBorderImage;
	private BufferedImage sightImage;

	private BufferedImage shipCarrierImage;
	private BufferedImage shipCarrierRedImage;
	private BufferedImage shipCarrierHilightImage;
	
	private BufferedImage shipBattleshipImage;
	private BufferedImage shipBattleshipRedImage;
	private BufferedImage shipBattleshipHilightImage;
	
	private BufferedImage shipSubmarineImage;
	private BufferedImage shipSubmarineRedImage;
	private BufferedImage shipSubmarineHilightImage;

	private BufferedImage shipPtBoatImage;
	private BufferedImage shipPtBoatRedImage;
	private BufferedImage shipPtBoatHilightImage;
	
	// Animations
	private Animation seaBackgroundAnimation;
	private Animation hitAnimation;
	private Animation missAnimation;
	
	private ImageFactory() {
		try {
			gridImage = loadImage("/images/grid.png");
			activeBorderImage = loadImage("/images/gridActiveBorder.png");
			sightImage = loadImage("/images/sight.png");
			
			shipCarrierImage = loadImage("/images/shipCarrier.png");
			shipCarrierRedImage = loadImage("/images/shipCarrierRed.png");
			shipCarrierHilightImage = loadImage("/images/shipCarrierHilight.png");
			
			shipBattleshipImage = loadImage("/images/shipBattleship.png");
			shipBattleshipRedImage = loadImage("/images/shipBattleshipRed.png");
			shipBattleshipHilightImage = loadImage("/images/shipBattleshipHilight.png");

			shipSubmarineImage = loadImage("/images/shipSubmarine.png");
			shipSubmarineRedImage = loadImage("/images/shipSubmarineRed.png");
			shipSubmarineHilightImage = loadImage("/images/shipSubmarineHilight.png");

			shipPtBoatImage = loadImage("/images/shipPtBoat.png");
			shipPtBoatRedImage = loadImage("/images/shipPtBoatRed.png");
			shipPtBoatHilightImage = loadImage("/images/shipPtBoatHilight.png");
			
			seaBackgroundAnimation = new Animation(Sprite.loadSpriteSheet("/images/seaBackgroundSprite.png", Orientation.HORIZONTAL, 16), 6);
			hitAnimation = new Animation(Sprite.loadSpriteSheet("/images/gridHit.png", Orientation.HORIZONTAL, 24), 1);
			hitAnimation.setLoop(false);
			missAnimation = new Animation(Sprite.loadSpriteSheet("/images/gridMiss.png", Orientation.HORIZONTAL, 16), 1);
			missAnimation.setLoop(false);
		} catch (IOException | ResourceNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	// Getters
	public BufferedImage getGridImage() {
		return gridImage;
	}
	
	public BufferedImage getActiveBorderImage() {
		return activeBorderImage;
	}
	
	public BufferedImage getSightImage() {
		return sightImage;
	}
	
	public BufferedImage getShipCarrierImage() {
		return shipCarrierImage;
	}

	public BufferedImage getShipCarrierRedImage() {
		return shipCarrierRedImage;
	}

	public BufferedImage getShipCarrierHilightImage() {
		return shipCarrierHilightImage;
	}

	public BufferedImage getShipBattleshipImage() {
		return shipBattleshipImage;
	}

	public BufferedImage getShipBattleshipRedImage() {
		return shipBattleshipRedImage;
	}

	public BufferedImage getShipBattleshipHilightImage() {
		return shipBattleshipHilightImage;
	}

	public BufferedImage getShipSubmarineImage() {
		return shipSubmarineImage;
	}

	public BufferedImage getShipSubmarineRedImage() {
		return shipSubmarineRedImage;
	}

	public BufferedImage getShipSubmarineHilightImage() {
		return shipSubmarineHilightImage;
	}

	public BufferedImage getShipPtBoatImage() {
		return shipPtBoatImage;
	}

	public BufferedImage getShipPtBoatRedImage() {
		return shipPtBoatRedImage;
	}

	public BufferedImage getShipPtBoatHilightImage() {
		return shipPtBoatHilightImage;
	}

	public Animation getSeaBackgroundAnimation() {
		return seaBackgroundAnimation;
	}
	
	public Animation getHitAnimation() {
		return hitAnimation;
	}
	
	public Animation getMissAnimation() {
		return missAnimation;
	}

	/**
	 * Retourne l'instance du singleton.
	 * @return L'instance du singleton.
	 */
    public static ImageFactory getInstance() {
        return instance;
    }
	
	/**
	 * Charge une image à partir du nom de son fichier.
	 * @param imgFileName Le nom du fichier de l'image.
	 * @return L'image.
	 * @throws IOException 
	 * @throws ResourceNotFoundException 
	 */
	private BufferedImage loadImage(String imgFileName) throws IOException, ResourceNotFoundException {
		BufferedImage image = null;
		URL imageURL = getClass().getResource(imgFileName);
		if (imageURL == null)
			throw new ResourceNotFoundException(imgFileName);
        image = ImageIO.read(getClass().getResource(imgFileName));
    	return image;
	}
	
}
