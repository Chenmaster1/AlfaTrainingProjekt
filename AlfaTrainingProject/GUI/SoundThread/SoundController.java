package SoundThread;

public class SoundController {

    private static int volumeBackgroundMusic;
    private static int volumeSounds = 50;

    public static MP3Runnable musicTitle;
    // "Intro_Main.mp3"

    public static void setBackgroundMusic(String pathtoMusic) {
        if (musicTitle != null) {
            musicTitle.stopPlayer();
        }
        if (pathtoMusic != null) {
            musicTitle = new MP3Runnable(pathtoMusic, true, volumeBackgroundMusic);
            new Thread(musicTitle).start();
        } else {
            musicTitle.stopPlayer();
        }

    }

    public static void playSound(String pathtoSound) {
        new Thread(new MP3Runnable(pathtoSound, false, volumeSounds)).start();

    }

    public static void setVolumeBackgroundMusic(int newVolume) {
        
        volumeBackgroundMusic = newVolume;
        if (musicTitle != null){
        musicTitle.setVolume(newVolume);
        }
    }

    public static void setVolumeSounds(int newVolume) {
        volumeSounds = newVolume;
    }

    public static int getVolumeBackgroundMusic() {
        return volumeBackgroundMusic;
    }

    public static int getVolumeSounds() {
        return volumeSounds;
    }
}
