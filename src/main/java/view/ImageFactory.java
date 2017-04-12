package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import view.Sprite.Orientation;

/**
 * Singleton permettant de r�cup�rer les images et les animations
 * n�cessaires au jeu.
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
	
	private BufferedImage oldShip2Image;
	private BufferedImage oldShip2RedImage;
	private BufferedImage oldShip2HilightImage;

	private BufferedImage oldShip3Image;
	private BufferedImage oldShip3RedImage;
	private BufferedImage oldShip3HilightImage;

	private BufferedImage oldShip4Image;
	private BufferedImage oldShip4RedImage;
	private BufferedImage oldShip4HilightImage;
	
	private BufferedImage oldShip5Image;
	private BufferedImage oldShip5RedImage;
	private BufferedImage oldShip5HilightImage;
	
	// Animations
	private Animation seaXXBackgroundAnimation;
	private Animation hitAnimation;
	private Animation missAnimation;
	
	private Animation seaXVIBackgroundAnimation;

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
			
			oldShip2Image = loadImage("/images/oldShip2.png");
			oldShip2RedImage = loadImage("/images/oldShip2Red.png");
			oldShip2HilightImage = loadImage("/images/oldShip2Hilight.png");

			oldShip3Image = loadImage("/images/oldShip3.png");
			oldShip3RedImage = loadImage("/images/oldShip3Red.png");
			oldShip3HilightImage = loadImage("/images/oldShip3Hilight.png");

			oldShip4Image = loadImage("/images/oldShip4.png");
			oldShip4RedImage = loadImage("/images/oldShip4Red.png");
			oldShip4HilightImage = loadImage("/images/oldShip4Hilight.png");

			oldShip5Image = loadImage("/images/oldShip5.png");
			oldShip5RedImage = loadImage("/images/oldShip5Red.png");
			oldShip5HilightImage = loadImage("/images/oldShip5Hilight.png");
			
			seaXXBackgroundAnimation = new Animation(Sprite.loadSpriteSheet("/images/seaXXBackgroundSprite.png", Orientation.HORIZONTAL, 16), 6);
			seaXVIBackgroundAnimation = new Animation(Sprite.loadSpriteSheet("/images/seaXVIBackgroundSprite.png", Orientation.HORIZONTAL, 16), 6);
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
	
	public BufferedImage getOldShip2Image() {
		return oldShip2Image;
	}

	public BufferedImage getOldShip2RedImage() {
		return oldShip2RedImage;
	}

	public BufferedImage getOldShip2HilightImage() {
		return oldShip2HilightImage;
	}

	public BufferedImage getOldShip3Image() {
		return oldShip3Image;
	}

	public BufferedImage getOldShip3RedImage() {
		return oldShip3RedImage;
	}

	public BufferedImage getOldShip3HilightImage() {
		return oldShip3HilightImage;
	}

	public BufferedImage getOldShip4Image() {
		return oldShip4Image;
	}

	public BufferedImage getOldShip4RedImage() {
		return oldShip4RedImage;
	}

	public BufferedImage getOldShip4HilightImage() {
		return oldShip4HilightImage;
	}

	public BufferedImage getOldShip5Image() {
		return oldShip5Image;
	}

	public BufferedImage getOldShip5RedImage() {
		return oldShip5RedImage;
	}

	public BufferedImage getOldShip5HilightImage() {
		return oldShip5HilightImage;
	}

	public Animation getSeaXXBackgroundAnimation() {
		return seaXXBackgroundAnimation;
	}
	
	public Animation getHitAnimation() {
		return hitAnimation;
	}
	
	public Animation getMissAnimation() {
		return missAnimation;
	}

	public Animation getSeaXVIBackgroundAnimation() {
		return seaXVIBackgroundAnimation;
	}
	
	/**
	 * Retourne l'instance du singleton.
	 * @return L'instance du singleton.
	 */
    public static ImageFactory getInstance() {
        return instance;
    }
	
	/**
	 * Charge une image � partir du nom de son fichier.
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
