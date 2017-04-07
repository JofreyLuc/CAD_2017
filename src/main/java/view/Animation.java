package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	/**
	 * Les images qui composent l'animation.
	 */
    private BufferedImage[] frames; 
	    
    /**
     * Bool�en indiquant si l'animation est en cours ou non.
     */
    private boolean stopped;
    
    /**
     * Bool�en indiquant si l'animation doit s'effectuer en continu.
     */
    private boolean loop;
    
    /**
     * Le nombre de tick avant de changer d'image.
     */
    private int frameDuration;
    
    /**
     * Compteur de tick
     */
    private int tickCount;

    /**
     * Index de l'image courante de l'animation.
     */
    private int index;

    /**
     * Construit une animation � partir d'un tableau d'images et dur�e d'affichage par image.
     * @param frames Le tableau d'images.
     * @param frameDuration La dur�e d'affichage par image.
     */
    public Animation(BufferedImage[] frames, int frameDuration) {
        this.frames = frames;
        this.frameDuration = frameDuration;
        
        this.stopped = false;
        this.loop = true;
        this.tickCount = 0;
        this.index = 0;
    }

    /**
     * Construit une animation poss�dant les m�mes charact�ristiques qu'une autre animation.
     * @param animation L'animation � copier.
     */
    public Animation(Animation animation) {
        this.frames = animation.frames;
        this.frameDuration = animation.frameDuration;
        this.loop = animation.loop;
        
        this.stopped = false;
        this.tickCount = 0;
        this.index = 0;
    }
    
    /**
     * Fixe le bool�en indiquant si l'animation doit boucler.
     * @param loop Bool�en indiquant si l'animation doit boucler.
     */
    public void setLoop(boolean loop) {
    	this.loop = loop;
    }
    
    /**
     * Met � jour l'animation.
     */
    private void update() {
    	// Si l'animation n'est pas activ�e
    	// ou si l'animation est � la derni�re image et ne doit pas boucler
        if (stopped || (index >= frames.length - 1 && !loop)) {
        	return;
        }
        	
        tickCount++;
        if (tickCount > frameDuration) {
            tickCount = 0;
            
            if (index >= frames.length - 1) {
            	index = 0;
            }
            else {
            	index ++;
            }
        }
    }
    
    /**
     * Active l'animation.
     */
    public void start() {
        stopped = false;
    }
    
    /**
     * Stoppe l'animation.
     */
    public void stop() {
        stopped = true;
    }

    /**
     * Recommence l'animation
     */
    public void restart() {
        stopped = false;
        index = 0;
    }

    /**
     * R�initialise l'animation.
     */
    public void reset() {
        this.stopped = true;
        this.tickCount = 0;
        this.index = 0;
    }

    /**
     * Dessine l'image courante de l'animation
     * et met � jour cette derni�re.
     * @param g L'objet Graphics.
     * @param x L'abscisse.
     * @param y L'ordonn�e.
     */
    public void draw(Graphics g, int x, int y){
        g.drawImage(frames[index], x, y, null);
        update();
    }
    
    /**
     * Dessine l'image courante de l'animation.
     * et met � jour cette derni�re.
     * @param g L'objet Graphics.
     * @param x L'abscisse.
     * @param y L'ordonn�e.
     * @param width La largeur.
     * @param height La hauteur.
     */
    public void draw(Graphics g, int x, int y, int width, int height){
        g.drawImage(frames[index], x, y, width, height, null);
        update();
    }
    
}