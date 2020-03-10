package MenuGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


import Database.Database;
import SoundThread.MainTheme;

/**
 * Diese Klasse dient zum Starten des Programms und Aufbau des Grundfensters
 * 
 * @author Kevin
 *
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame{
        private static String chooseBundle = "/Bundle_DE";
        
        public static void setLanguage(String Bundle)
        {
            chooseBundle = Bundle;
            System.out.println("myframe choose : " +chooseBundle);
            
        }
                
	public static ResourceBundle bundle = ResourceBundle.getBundle("LanguagePackages"+chooseBundle); //Bundle, bzw path aus der Datenbank holen

	//-------------------------Panels-------------------------//
	private JPanel panel;

	/**
	 * Hier werden alle Componenten initialisiert. Zudem werden <code>initializeFrame()</code>
	 * und <code>pack()</code> ausgeführt
	 * 
	 * @author Kevin
	 */
	public MyFrame () {
		setUndecorated(true);
		//System.out.println(bundle.getString("btnNew")); //Beispiel für Mehrsprachigkeit
		
		pack();
	}
	
	//-------------------------public methods-------------------------//
	/**
	 * Dient zum Starten des Programms. Hier werden entsprechend Attribute 
	 * und Der Frame auf Visible gesetzt.
	 * 
	 * @author Kevin
	 */
	public void start() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//panel = new MainFramePanel(this);
		panel = new LoginPanel(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}	

}