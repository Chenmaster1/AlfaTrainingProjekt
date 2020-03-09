package SoundThread;

import jaco.mp3.player.MP3Player;
import jaco.mp3.player.plaf.MP3PlayerUICompact;

import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

/**
 * Eine Testklasse für mp4
 */
public class mp3test implements Runnable{

	private static MP3Player player;
	
	@Override
	public void run() {
		
		try {
			player = new MP3Player();	
		    player.setRepeat(true);   
		    player.addToPlayList(getClass().getClassLoader().getResource("MainSound.mp4"));	
		    player.play();
		}catch (Exception ex){
			
		}
	}

    public static void setVolume(int volume)
    {
        if (volume == 1)
        {
            //player.volume oder so
            player.pause();
        }

    }

}

