package SoundThread;

import jaco.mp3.player.MP3Player;

/**
 * Eine Testklasse f�r mp3 & mp4
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
            player.setVolume(volume);
            player.play();
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


    public void stopPlayer()
    {
        player.stop();
    }

	public int getVolume() {
            
		return volume;
                
	}


}

