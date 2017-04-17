package fr.univ_lorraine.battleship.view.graphics;

/**
 * Animation avec un callback à la fin de l'animation.
 */
public class AnimationWithCallback extends Animation {

	/**
	 * Callback de l'animation.
	 */
	private Callback callback;
	
	/**
	 * Indique si le callback a déjà été appelé.
	 */
	private boolean callbackDone;
	
	/**
	 * Construit une animation avec un callback.
	 * @param animation L'animation.
	 * @param callback Le callback de cette animation.
	 */
	public AnimationWithCallback(Animation animation, Callback callback) {
		super(animation);
		this.callback = callback;
		this.callbackDone = false;
	}

	@Override
	public void reset() {
		super.reset();
		this.callbackDone = false;
	}
	
	@Override
	public void restart() {
		super.restart();
		this.callbackDone = false;
	}
	
	/**
	 * {@inheritDoc}
	 * Lance également le callback de l'animation si l'animation est terminée.
	 */
	@Override
	protected void update() {
		super.update();
		// On lance la méthode completed du callback si l'animation n'est pas infinie et qu'elle est terminée
		if (!callbackDone && !loop && index == frames.length-1 && callback != null) {
			callback.completed(this);
			this.callbackDone = true;
		}
	}
	
	/**
	 * Callback abstrait.
	 */
	public static abstract class Callback {
		
		/**
		 * Méthode à implémenter qui est lancée à l'appel du callback.
		 * @param source La source du callback.
		 */
		public abstract void completed(Animation source);
		
	}
	
}
