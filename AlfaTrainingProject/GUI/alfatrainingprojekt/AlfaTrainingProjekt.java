package alfatrainingprojekt;

import java.util.Arrays;

import MenuGUI.MyFrame;
//import SoundThread.MainTheme;
import SoundThread.MP3Runnable;

public class AlfaTrainingProjekt {
	
	//public final static Thread musicThread = new Thread(new MainTheme());
	public final static MP3Runnable musicTitle = new MP3Runnable("Intro_Main.mp3", true);
	public final static Thread musicThread = new Thread(musicTitle);
        
	public final static MP3Runnable soundTitle = new MP3Runnable("W10_Dice_Roll.mp3", false);
	public final static Thread soundThread = new Thread(soundTitle);
	
    public static void main(String[] args) {
    	MyFrame frame = new MyFrame();
    	frame.start();
    	musicThread.start();
    }

}