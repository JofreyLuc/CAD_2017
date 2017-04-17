package fr.univ_lorraine.battleship.view;

/**
 * Exception levée lorsqu'une ressource (image, son, etc.) est introuvable.
 */
@SuppressWarnings("serial")
public class ResourceNotFoundException extends Exception {

	public ResourceNotFoundException() {
		super("Ressource non trouvée !");
	}

	public ResourceNotFoundException(String message) {
		super("Ressource non trouvée : " + message);
	}

}
