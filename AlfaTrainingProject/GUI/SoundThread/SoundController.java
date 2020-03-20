package SoundThread;

import MenuGUI.MyFrame;

public class SoundController {

    private static int volumeBackgroundMusic;
    private static int volumeSounds = 33;

    public static MP3Runnable musicTitle;
    // "Intro_Main.mp3"

    public static void setBackgroundMusic(String pathtoMusic) {
        if (musicTitle != null) {
            musicTitle.stopPlayer();
        }
        if (pathtoMusic != null) {
            musicTitle = new MP3Runnable(pathtoMusic, true, Integer.parseInt(MyFrame.volume));
            new Thread(musicTitle).start();
        } else {
            musicTitle.stopPlayer();
        }

    }

    public static void playSound(String pathtoSound) {
        new Thread(new MP3Runnable(pathtoSound, false, Integer.parseInt(MyFrame.effectVolume))).start();
        
    }

    public static void setVolumeBackgroundMusic(int newVolume) {
        
        volumeBackgroundMusic = newVolume;
        if (musicTitle != null){
        musicTitle.setVolume(newVolume);
        }
    }

    public static void setVolumeSounds(int newVolume) {
        MyFrame.effectVolume = Integer.toString(newVolume);
    }

    public static int getVolumeBackgroundMusic() {
        return volumeBackgroundMusic;
    }

    public static int getVolumeSounds() {
        return Integer.parseInt(MyFrame.effectVolume);
    }
}
