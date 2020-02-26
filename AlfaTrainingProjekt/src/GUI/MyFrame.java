package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


import Database.Database;

/**
 * Diese Klasse dient zum Starten des Programms und Aufbau des Grundfensters
 * 
 * @author Kevin
 *
 */
@SuppressWarnings("serial")
public class MyFrame extends JFrame{

	//Für eventuelle Spracheisntellungen
	ResourceBundle bundle = ResourceBundle.getBundle("LanguagePackages/Bundle_D");
	
	//-------------------------Buttons-------------------------//
	private JButton btnNew;
	private JButton btnLoad;
	private JButton btnSettings;
	private JButton btnClose;
	
	//-------------------------Panels-------------------------//
	private MainFramePanel panel;

	/**
	 * Hier werden alle Componenten initialisiert. Zudem werden <code>initializeFrame()</code>
	 * und <code>pack()</code> ausgeführt
	 * 
	 * @author Kevin
	 */
	public MyFrame () {
		panel = new MainFramePanel(new ImageIcon("AlfaTrainingProjekt/src/Images/BackGround_FullScreen.png").getImage());

		btnNew = new JButton(bundle.getString("newBtn"), new ImageIcon("AlfaTrainingProjekt/src/Images/Button.png"));
		btnLoad = new JButton("Laden", new ImageIcon("AlfaTrainingProjekt/src/Images/Button.png"));
		btnSettings = new JButton("Einstellungen", new ImageIcon("AlfaTrainingProjekt/src/Images/Button.png"));
		btnClose = new JButton("Beenden", new ImageIcon("AlfaTrainingProjekt/src/Images/Button.png"));
		
		setUndecorated(true);
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
		initializeFrame();
		initializePanel();
		initializeButtons();

		//Datenbankverbindung
		connectDatabase();
	}
	
	//-------------------------private methods-------------------------//
	/**
	 * Baut die Verbindung zur Datenbank auf. Falls nicht möglich, 
	 * wird der Fehler in der Konsole ausgegeben.
	 * 
	 * @author Kevin
	 */
	private void connectDatabase() {
		try {
			Database.getInstance().connect();
		}catch(Exception ex) {
			System.out.println("Keine Datenbank verbindung");
		}
	}
	
	/**
	 * Das Frame wird fertig aufgebaut
	 * 
	 * @author Kevin
	 */
	private void initializeFrame(){
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(3);
		setVisible(true);	
	}
	
	/**
	 * Hier wird das Panel hinzugefuegt
	 */
	private void initializePanel() {
		panel.setLayout(null);
		getContentPane().add(panel);
	}
	
	/**
	 * Hier werden alle Buttons hinzugefuegt
	 * 
	 * @author Kevin
	 */
	private void initializeButtons() {
		addButton(btnNew, getWidth()/2 - 100, getHeight()/2 - 90);
		addButton(btnLoad, getWidth()/2 - 100, getHeight()/2 - 30);
		addButton(btnSettings, getWidth()/2 - 100, getHeight()/2 + 30);
		addButton(btnClose, getWidth()/2 - 100, getHeight()/2 + 90);
		
		btnNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onNewClicked();
			}
		});
		
		btnLoad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onLoadClicked();
			}
		});
		
		btnSettings.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onSettingsClicked();
			}
		});
		
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onCloseClicked();			
			}
		});	
	}
	
	/**
	 * Fuegt einen Button hinzu
	 * 
	 * @param button der Button
	 * @param posX	Die Position x
	 * @param posY Die Position y
	 */
	private void addButton(JButton button,int posX,int posY) {
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBounds(posX, posY, 200, 50);
		panel.add(button);
	}
	
	/**
	 * Wird aufgerufen, sobald der Button "Neues Spiel" gedrückt wurde.
	 * Das Programm wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onNewClicked() {
		//TODO
	}
	
	/**
	 * Wird aufgerufen, sobald der Button "Laden" gedrückt wurde.
	 * Das Programm wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onLoadClicked() {
		//TODO
	}
	
	/**
	 * Wird aufgerufen, sobald der Button "Einstellungen" gedrückt wurde.
	 * Das Programm wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onSettingsClicked() {
		//TODO so kann man das machen ohne ein neues Fenster öffnen zu müssen
		//remove(panel);	//altes Panel entfernen
		//add(panel);		//neues Panel hinzufuegen
		//repaint();		//neu darstellen
	}
	
	/**
	 * Wird aufgerufen, sobald der Button "Beenden" gedrückt wurde.
	 * Das Programm wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onCloseClicked() {
		dispose();
	}

}

enum Language{
	EN, DE
}