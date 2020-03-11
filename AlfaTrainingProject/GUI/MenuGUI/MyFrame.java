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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Diese Klasse dient zum Starten des Programms und Aufbau des Grundfensters
 * 
 * @author Kevin
 *
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame{
    
        // get Settings (language / volume)
        private File file;
        private String path;
        private String language = "german";
        
        
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
            getSettings();
            
            
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
        
        private void getSettings()
    {
        //get setting from HeroesOfTheArena\hota_setting.txt
        // get path
        try
        {
            Process p = Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
            p.waitFor();

            InputStream in = p.getInputStream();
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();

            path = new String(b);
            path = path.split("\\s\\s+")[4];
            //final path:
            path += "\\HeroesOfTheArena";

        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }

        //get setting from HeroesOfTheArena\hota_setting.txt
        // read file and save to volume and language
        if (Files.exists(Paths.get(path + "\\hota_setting.txt")))
        {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(path + "\\hota_setting.txt"));
                String volume = br.readLine();
                String language = br.readLine();
                if (language == "german")
                {
                    setLanguage("/Bundle_DE");
                   
                }
                if (language == "english")
                {
                    setLanguage("/Bundle_EN");
                   
                }
                
                    
                System.out.println("string language " + language);
                                       
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

}