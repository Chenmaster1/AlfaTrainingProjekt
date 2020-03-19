package alfatrainingprojekt;

import java.util.Arrays;

import MenuGUI.MyFrame;
import SoundThread.MP3Runnable;
import SoundThread.SoundController;

public class AlfaTrainingProjekt {
	
	//public final static Thread musicThread = new Thread(new MainTheme());
    public static void main(String[] args) {
    	MyFrame frame = new MyFrame();
    	frame.start();
    	SoundController.setBackgroundMusic("Intro_Main.mp3");
    }

}