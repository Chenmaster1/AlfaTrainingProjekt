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
import SoundThread.SoundController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
public class MyFrame extends JFrame
{
    /**
     * global settings for volume, language get infos from
     * <code>HeroesOfTheArena\hota_setting.txt</code> if no file is found, use
     * <code>public static String language = "/Bundle_DE", volume = "50"</code>
     * filepath <code>public static String path;</xode> is prepared * @author
     * Yovo
     */
    public static String path;
    public static String language = "/Bundle_DE";
    public static Boolean volumFromFile = true, effectVolumeFromFile = true;


    // get filepath
    static
    {
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
            path += "\\HeroesOfTheArena";
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }

        // read file and save to volume and language
        if (Files.exists(Paths.get(path + "\\hota_setting.txt")))
        {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(path + "\\hota_setting.txt"));
                if (volumFromFile)
                {
                    String volume = br.readLine();
                    SoundController.setVolumeBackgroundMusic(Integer.parseInt(volume));
                }

                language = br.readLine();
                if (language.equalsIgnoreCase("german"))
                {
                    language = "/Bundle_DE";
                }
                if (language.equalsIgnoreCase("english"))
                {
                    language = "/Bundle_EN";
                }
                
                if (effectVolumeFromFile)
                {
                    String effectVolume = br.readLine();
                    SoundController.setVolumeSounds(Integer.parseInt(effectVolume));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                
            }
        }
        //if no files exists
        else
        {
            try
            {
                
              
                if (!Files.isDirectory(Paths.get(MyFrame.path + "\\")))
                {
                    Files.createDirectory(Paths.get(MyFrame.path + "\\"));
                    Files.createFile(Paths.get(MyFrame.path + "\\hota_setting.txt"));
                }
                else if (!Files.isDirectory(Paths.get(MyFrame.path + "\\hota_setting.txt")))
                {
                    Files.createFile(Paths.get(MyFrame.path + "\\hota_setting.txt"));
                }

                FileWriter fw = new FileWriter(MyFrame.path + "\\hota_setting.txt");
                fw.write("50" + "\n" + "/Bundle_DE"+ "\n" + "60");
                fw.close();
                language = "/Bundle_DE";
                SoundController.setVolumeBackgroundMusic(50);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }


    // load language package 
    public static ResourceBundle bundle = ResourceBundle.getBundle("LanguagePackages" + language); //Bundle, bzw path aus der Datenbank holen

    //-------------------------Panels-------------------------//
    private JPanel panel;


    /**
     * Hier werden alle Componenten initialisiert. Zudem werden
     * <code>initializeFrame()</code> und <code>pack()</code> ausgeführt
     *
     * @author Kevin
     */
    public MyFrame()
    {
        setUndecorated(true);
        //System.out.println(bundle.getString("btnNew")); //Beispiel für Mehrsprachigkeit

        pack();
    }

    //-------------------------public methods-------------------------//

    /**
     * Dient zum Starten des Programms. Hier werden entsprechend Attribute und
     * Der Frame auf Visible gesetzt.
     *
     * @author Kevin
     */
    public void start()
    {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //panel = new MainFramePanel(this);
        panel = new LoginPanel(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


}

