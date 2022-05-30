package main;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	//thuoc tinh am thanh
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sounds/BlueBoyAdventure.wav");
		soundURL[1] = getClass().getResource("/sounds/coin.wav");
		soundURL[2] = getClass().getResource("/sounds/powerup.wav");
		soundURL[3] = getClass().getResource("/sounds/unlock.wav");
		soundURL[4] = getClass().getResource("/sounds/fanfare.wav");
		soundURL[5] = getClass().getResource("/sounds/hitmonster.wav");
		soundURL[6] = getClass().getResource("/sounds/receivedamage.wav");
		soundURL[7] = getClass().getResource("/sounds/cuttree.wav");
		soundURL[8] = getClass().getResource("/sounds/levelup.wav");
		soundURL[9] = getClass().getResource("/sounds/cursor.wav");
		soundURL[10] = getClass().getResource("/sounds/burning.wav");
		soundURL[11] = getClass().getResource("/sounds/cuttree.wav");
		
		//soundURL[12] = getClass().getResource("/sounds/gameover.wav");
		
		
		
		
		
		
	}
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}
}
