package view;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends Exception {

	public ResourceNotFoundException() {
		super("Ressource non trouv� !");
	}

	public ResourceNotFoundException(String message) {
		super("Ressource non trouv� : " + message);
	}

}
