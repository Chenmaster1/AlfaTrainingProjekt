package SoundThread;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class MainTheme implements Runnable{

	@Override
	public void run() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("Sounds/MainSound.mp4");
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(is);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		}catch(Exception ex) {
			System.out.println("sound klappt nicht: " + ex.getMessage());
		}
	}

	
}
