package SoundThread;

import jaco.mp3.player.MP3Player;

/**
 * Eine Testklasse für mp4
 */
public class mp3test implements Runnable{

	@Override
	public void run() {
		
		try {
			
			MP3Player player = new MP3Player();	
		    player.setRepeat(true);   
		    player.addToPlayList(getClass().getClassLoader().getResource("MainSound.mp4"));	
		    player.play();
		    
		    
		}catch (Exception ex){
			
		}
	}

}
