package view;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends Exception {

	public ResourceNotFoundException() {
		super("Ressource non trouvé !");
	}

	public ResourceNotFoundException(String message) {
		super("Ressource non trouvé : " + message);
	}

}
