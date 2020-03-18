package SoundThread;

import jaco.mp3.player.MP3Player;

/**
 * Eine Testklasse für mp4
 */
public class mp3test implements Runnable
{

    private static MP3Player player;
    private static int volume;


    @Override
    public void run()
    {

        try
        {
            player = new MP3Player();
            player.setRepeat(true);
            player.addToPlayList(getClass().getClassLoader().getResource("MainSound.mp4"));
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
    public static void setVolume(int volume)
    {

        player.setVolume(volume);

    }


    /**
     * used upon starting the Programm <br>
     * in MyFrame reads volume from settings.txt and sets mp3 volume
     * 
     *  @author Yovo
     */
    public static void setVolumInitialize(int volumeInitialice)
    {
        volume = volumeInitialice;

    }


    public static void stopPlayer()
    {
        player.stop();
    }


}

