package SoundThread;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class MainTheme implements Runnable{

	@Override
	public void run() {
		//InputStream is = getClass().getClassLoader().getResourceAsStream("Sounds/MainSound.mp4");
		try {                    
			File file = new File(getClass().getClassLoader().getResource("MainSound.wav").getFile());
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
            //clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception ex) {
			System.out.println("sound klappt nicht: " + ex.getMessage());
		}
	}
}
