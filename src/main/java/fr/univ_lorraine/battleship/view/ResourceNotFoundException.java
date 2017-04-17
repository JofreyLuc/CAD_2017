package fr.univ_lorraine.battleship.view;

/**
 * Exception lev�e lorsqu'une ressource (image, son, etc.) est introuvable.
 */
@SuppressWarnings("serial")
public class ResourceNotFoundException extends Exception {

	public ResourceNotFoundException() {
		super("Ressource non trouv�e !");
	}

	public ResourceNotFoundException(String message) {
		super("Ressource non trouv�e : " + message);
	}

}
