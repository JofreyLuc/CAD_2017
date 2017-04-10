package view;

import java.awt.Graphics;

import model.Ship;

public class ShipOnPlacingView extends ShipView {
	
	/**
	 * Indique si le positionnement du bateau est valide
	 */
	private boolean validPlacement;
	
	public ShipOnPlacingView(Ship ship) {
		super(ship);
	}
	
	/**
	 * Indique si le bateau en cours de positionnement est à un emplacement valide.
	 * @param validPlacement Booléen indiquant si le bateau en cours de positionnement est à un emplacement valide.
	 */
	public void setValidPlacement(boolean validPlacement) {
		this.validPlacement = validPlacement;
		attachImage();
	}
	
	@Override
	public void setShipVisibleOnlyWhenDead(boolean b) {
		// Variable non utilisée dans cette vue
	}
	
	@Override
	protected void attachImage() {
		if (ship == null) {
			shipImage = null;
			return;
		}
		
		ImageFactory imgFac = ImageFactory.getInstance();
		switch(ship.getEpoch().getEpochName()) {
			case XX_SIECLE:
				switch(ship.getSize()) {
					case 5: 
						if (!validPlacement) {
							shipImage = imgFac.getShipCarrierRedImage();
						}
						else {
							shipImage = imgFac.getShipCarrierHilightImage();
						}
						break;
					case 4: 
						if (!validPlacement) {
							shipImage = imgFac.getShipBattleshipRedImage();
						}
						else {
							shipImage = imgFac.getShipBattleshipHilightImage();
						}
						break;
					case 3: 
						if (!validPlacement) {
							shipImage = imgFac.getShipSubmarineRedImage();
						}
						else {
							shipImage = imgFac.getShipSubmarineHilightImage();
						}
						break;
					case 2: 
						if (!validPlacement) {
							shipImage = imgFac.getShipPtBoatRedImage();
						}
						else {
							shipImage = imgFac.getShipPtBoatHilightImage();
						}
						break;
					default:
				}
				break;
			case XVI_SIECLE:
				switch(ship.getSize()) {
					case 5: 
						if (!validPlacement) {
							
						}
						else {
							
						}
						break;
					case 4: 
						if (!validPlacement) {
							
						}
						else {
							
						}
						break;
					case 3: 
						if (!validPlacement) {
							
						}
						else {
							
						}
						break;
					case 2: 
						if (!validPlacement) {
							
						}
						else {
							
						}
						break;
					default:
				}
				break;
			default:
				throw new AssertionError("Epoque inconnu " + ship.getEpoch().getEpochName());
		}
	}

	@Override
	public void draw(Graphics g, int seaBoxViewSize) {
		if (ship != null && ship.getPosition() != null) {
			super.draw(g, seaBoxViewSize);
		}
	}
}
