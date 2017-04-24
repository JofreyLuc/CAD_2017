package fr.univ_lorraine.battleship.view;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
	
	public static void playSound(String soundFileName) throws ResourceNotFoundException {
		// specify the sound to play
	    // (assuming the sound can be played by the audio system)
		try {
			URL soundURL = SoundPlayer.class.getResource(soundFileName);
			if (soundURL == null)
				throw new ResourceNotFoundException(soundFileName);
		    AudioInputStream sound = AudioSystem.getAudioInputStream(soundURL);
		
		    // load the sound into memory (a Clip)
		    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
		    Clip clip = (Clip) AudioSystem.getLine(info);
		    
				clip.open(sound);
		
		    // due to bug in Java Sound, explicitly exit the VM when
		    // the sound has stopped.
		    clip.addLineListener(new LineListener() {
		      public void update(LineEvent event) {
		        if (event.getType() == LineEvent.Type.STOP) {
		          event.getLine().close();
		          //System.exit(0);
		        }
		      }
		    });
		
		    // play the sound clip
		    clip.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
