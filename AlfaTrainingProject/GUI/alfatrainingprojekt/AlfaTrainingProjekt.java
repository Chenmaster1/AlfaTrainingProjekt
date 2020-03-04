package alfatrainingprojekt;

import GUI.MyFrame;
import SoundThread.MainTheme;

public class AlfaTrainingProjekt {
	
	public final static Thread musicThread = new Thread(new MainTheme());
	
    public static void main(String[] args) {
    	MyFrame frame = new MyFrame();
    	frame.start();
    	
    	musicThread.start();
    }

}