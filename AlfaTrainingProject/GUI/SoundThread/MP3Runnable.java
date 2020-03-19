package SoundThread;

import jaco.mp3.player.MP3Player;

import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JFrame;


/**
 * Eine Testklasse für mp3 & mp4
 */
public class MP3Runnable implements Runnable
{

    private MP3Player player;
    private int volume;
    private String pathtoMusic;
    private boolean isRepeated;

    public MP3Runnable(String pathtoMusic, boolean isRepeated) {
        player = new MP3Player();
        this.pathtoMusic = pathtoMusic;
        this.isRepeated = isRepeated;
    }    

    public MP3Runnable(String pathtoMusic, boolean isRepeated, int volume) {
		this(pathtoMusic,isRepeated);
		this.volume = volume;
	}

	@Override
    public void run()
    {

        try
        {
            player.setRepeat(isRepeated);
            player.addToPlayList(getClass().getClassLoader().getResource(pathtoMusic));
            
             player.play();
            player.setVolume(volume);

        }
        catch (Exception ex)
        {
            System.out.println("Exception in mp3test");
        }
    }

    /**
     * changes the volume of the mp3 player from SettingsPanel
     *  @author Yovo
     */
    public void setVolume(int volume)
    {
    	this.volume = volume;
        player.setVolume(volume);

    }


    /**
     * used upon starting the Programm <br>
     * in MyFrame reads volume from settings.txt and sets mp3 volume
     * 
     *  @author Yovo
     */
    public void setVolumeInitialize(int volumeInitialize)
    {
        volume = volumeInitialize;

    }


    public void stopPlayer()
    {
        player.stop();
    }

	public int getVolume() {
		return volume;
	}


}

