package fr.univ_lorraine.battleship.view;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import fr.univ_lorraine.battleship.view.graphics.Animation;
import fr.univ_lorraine.battleship.view.graphics.Sprite;
import fr.univ_lorraine.battleship.view.graphics.Sprite.Orientation;



/**
 * Singleton permettant de récupérer les images et les animations
 * nécessaires au jeu.
 */
public class ImageFactory {

	/**
	 * L'instance du singleton.
	 */
    private static ImageFactory instance = new ImageFactory();
	
	// Images battleship
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
	
	// Images diverses
	private BufferedImage escapeKeyImage;
	
	// Animations battleship
	private Animation seaXXBackgroundAnimation;
	private Animation hitAnimation;
	private Animation missAnimation;
	
	private Animation seaXVIBackgroundAnimation;

	private ImageFactory() {
		try {
			gridImage = loadImage("/images/battleship/grid.png");
			activeBorderImage = loadImage("/images/battleship/gridActiveBorder.png");
			sightImage = loadImage("/images/battleship/sight.png");
			
			shipCarrierImage = loadImage("/images/battleship/shipCarrier.png");
			shipCarrierRedImage = loadImage("/images/battleship/shipCarrierRed.png");
			shipCarrierHilightImage = loadImage("/images/battleship/shipCarrierHilight.png");
			
			shipBattleshipImage = loadImage("/images/battleship/shipBattleship.png");
			shipBattleshipRedImage = loadImage("/images/battleship/shipBattleshipRed.png");
			shipBattleshipHilightImage = loadImage("/images/battleship/shipBattleshipHilight.png");

			shipSubmarineImage = loadImage("/images/battleship/shipSubmarine.png");
			shipSubmarineRedImage = loadImage("/images/battleship/shipSubmarineRed.png");
			shipSubmarineHilightImage = loadImage("/images/battleship/shipSubmarineHilight.png");

			shipPtBoatImage = loadImage("/images/battleship/shipPtBoat.png");
			shipPtBoatRedImage = loadImage("/images/battleship/shipPtBoatRed.png");
			shipPtBoatHilightImage = loadImage("/images/battleship/shipPtBoatHilight.png");
			
			oldShip2Image = loadImage("/images/battleship/oldShip2.png");
			oldShip2RedImage = loadImage("/images/battleship/oldShip2Red.png");
			oldShip2HilightImage = loadImage("/images/battleship/oldShip2Hilight.png");

			oldShip3Image = loadImage("/images/battleship/oldShip3.png");
			oldShip3RedImage = loadImage("/images/battleship/oldShip3Red.png");
			oldShip3HilightImage = loadImage("/images/battleship/oldShip3Hilight.png");

			oldShip4Image = loadImage("/images/battleship/oldShip4.png");
			oldShip4RedImage = loadImage("/images/battleship/oldShip4Red.png");
			oldShip4HilightImage = loadImage("/images/battleship/oldShip4Hilight.png");

			oldShip5Image = loadImage("/images/battleship/oldShip5.png");
			oldShip5RedImage = loadImage("/images/battleship/oldShip5Red.png");
			oldShip5HilightImage = loadImage("/images/battleship/oldShip5Hilight.png");
			
			escapeKeyImage = loadImage("/images/misc/escapeKey.png");
			
			seaXXBackgroundAnimation = new Animation(Sprite.loadSpriteSheet("/images/battleship/seaXXBackgroundSprite.png", Orientation.HORIZONTAL, 16), 6);
			seaXVIBackgroundAnimation = new Animation(Sprite.loadSpriteSheet("/images/battleship/seaXVIBackgroundSprite.png", Orientation.HORIZONTAL, 16), 6);
			hitAnimation = new Animation(Sprite.loadSpriteSheet("/images/battleship/gridHit.png", Orientation.HORIZONTAL, 24), 1);
			hitAnimation.setLoop(false);
			missAnimation = new Animation(Sprite.loadSpriteSheet("/images/battleship/gridMiss.png", Orientation.HORIZONTAL, 16), 1);
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
	
	public BufferedImage getEscapeKeyImage() {
		return escapeKeyImage;
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
