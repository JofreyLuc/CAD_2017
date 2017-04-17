package fr.univ_lorraine.battleship.view.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Classe repr�sentant une animation graphique.
 */
public class Animation {

	/**
	 * Les images qui composent l'animation.
	 */
    protected BufferedImage[] frames; 
	    
    /**
     * Bool�en indiquant si l'animation est en cours ou non.
     */
    protected boolean stopped;
    
    /**
     * Bool�en indiquant si l'animation doit s'effectuer en continu.
     */
    protected boolean loop;
    
    /**
     * Le nombre de tick avant de changer d'image.
     */
    protected int frameDuration;
    
    /**
     * Compteur de tick
     */
    protected int tickCount;

    /**
     * Index de l'image courante de l'animation.
     */
    protected int index;

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
     * Retourne la largeur de l'image courante.
     * @return La largeur de l'image courante.
     */
    public int getWidth() {
    	return frames[index].getWidth();
    }
    
    /**
     * Retourne la hauteur de l'image courante.
     * @return La hauteur de l'image courante.
     */
    public int getHeight() {
    	return frames[index].getHeight();
    }
    
    /**
     * Active l'animation.
     */
    public void start() {
        this.stopped = false;
    }
    
    /**
     * Termine l'animation.
     * C'est-�-dire "place son �tat � la fin".
     */
    public void end() {
    	this.index = frames.length - 1;
    	this.tickCount = this.frameDuration;
    }
    
    /**
     * Stoppe l'animation.
     */
    public void stop() {
    	this.stopped = true;
    }

    /**
     * Recommence l'animation
     */
    public void restart() {
    	this.stopped = false;
    	this.index = 0;
        this.tickCount = 0;
    }

    /**
     * R�initialise l'animation.
     */
    public void reset() {
        this.stopped = true;
        this.index = 0;
        this.tickCount = 0;
    }

    /**
     * Met � jour l'animation.
     */
    protected void update() {
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