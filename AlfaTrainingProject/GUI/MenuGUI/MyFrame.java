package MenuGUI;

import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Diese Klasse dient zum Starten des Programms und Aufbau des Grundfensters
 *
 * @author Kevin
 *
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame implements SettingsListener
{

    {
    	Settings.INSTANCE.subscribe(this);
    	Settings.INSTANCE.load();
    }


    // load language package 
    public static ResourceBundle bundle;

    //-------------------------Panels-------------------------//
    private JPanel panel;


    /**
     * Hier werden alle Componenten initialisiert. Zudem werden
     * <code>initializeFrame()</code> und <code>pack()</code> ausgef�hrt
     *
     * @author Kevin
     */
    public MyFrame()
    {
//        setUndecorated(true);
        //System.out.println(bundle.getString("btnNew")); //Beispiel f�r Mehrsprachigkeit

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
        
        setLanguage(Settings.INSTANCE.getLanguage());
        
        panel = new LoginPanel(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

	@Override
	public void propertyChanged(String prop, Object value) {
		switch(prop) {
		case "language":
			{
				setLanguage((String)value);
			}
			break;
		}
	}
	
	private void setLanguage(String language) {
		switch(language) {
			case "english": {
	        	bundle = ResourceBundle.getBundle("LanguagePackages/Bundle_EN");
	        	break;
			}
			case "german": default: {
	        	bundle = ResourceBundle.getBundle("LanguagePackages/Bundle_DE");
	        	break;
			}
		}
	}


}

