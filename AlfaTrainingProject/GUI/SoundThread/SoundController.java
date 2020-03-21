package SoundThread;

import MenuGUI.Settings;
import MenuGUI.SettingsListener;

public class SoundController implements SettingsListener {
	
	private SoundController() {} //Dummy-Instanz für Listener
	
	static {
		Settings.INSTANCE.subscribe(new SoundController());
	}

    public static MP3Runnable musicTitle;
    // "Intro_Main.mp3"

    public static void setBackgroundMusic(String pathtoMusic) {
        if (musicTitle != null) {
            musicTitle.stopPlayer();
        }
        if (pathtoMusic != null) {
            musicTitle = new MP3Runnable(pathtoMusic, true, Settings.INSTANCE.getVolume());
            new Thread(musicTitle).start();
        } else {
            musicTitle.stopPlayer();
        }

    }

    public static void playSound(String pathtoSound) {
        new Thread(new MP3Runnable(pathtoSound, false, Settings.INSTANCE.getEffectVolume())).start();
    }


	@Override
	public void propertyChanged(String prop, Object value) {
		switch(prop) {
		case "language":
			//Hier könnte man den Titel austauschen, wenn dieser sprachabhängig ist
			break;
		case "volume":
	        if (musicTitle != null) {
	            musicTitle.setVolume((Integer)value);
	        }
			break;
		case "effectVolume":
			break;
		}
		
	}
}
