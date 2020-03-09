package alfatrainingprojekt;

import java.util.Arrays;

import MenuGUI.MyFrame;
import SoundThread.MainTheme;
import SoundThread.mp3test;

public class AlfaTrainingProjekt {
	
	//public final static Thread musicThread = new Thread(new MainTheme());
	public final static Thread musicThread = new Thread(new mp3test());
	
    public static void main(String[] args) {
    	MyFrame frame = new MyFrame();
    	frame.start();
    	musicThread.start();	
    }

}