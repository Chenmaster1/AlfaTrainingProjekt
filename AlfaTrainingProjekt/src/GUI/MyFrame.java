package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

	private JButton btnNew;
	private JButton btnLoad;
	private JButton btnSettings;
	private JButton btnClose;
	private JPanel panel;
	
	/**
	 * Hier werden alle Componenten initialisiert. Zudem werden <code>initializeFrame()</code>
	 * und <code>pack()</code> ausgeführt
	 * 
	 * @author Kevin
	 */
	public MyFrame () {
		ImageIcon image = new ImageIcon("AlfaTrainingProjekt/src/Images/BackGround_FullScreen.png");
		panel = new MainFramePanel(image.getImage());
		
		btnNew = new JButton("Neues Spiel");
		btnLoad = new JButton("Laden");
		btnSettings = new JButton("Einstellungen");
		btnClose = new JButton("Beenden");
		setUndecorated(true);
		pack();
	}
	
	/**
	 * Dient zum Starten des Programms. Hier werden entsprechend Attribute 
	 * und Der Frame auf Visible gesetzt.
	 * 
	 * @author Kevin
	 */
	public void start() {
		initializeFrame();
		//Panel adden
		getContentPane().add(panel);
		
		//Buttons
		initializeButtons();
		//Datenbankverbindung
		connectDatabase();
			
	}
	
	/**
	 * Wird aufgerufen, sobald der Button Beenden gedrückt wurde.
	 * Das Programm wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onCloseClicked() {
		this.dispose();
	}
	
	/**
	 * Hier werden alle Buttons fertig initialisiert.
	 * 
	 * @author Kevin
	 */
	private void initializeButtons() {
		
		btnNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//TODO
				
			}
		});
		btnNew.setBounds((getWidth() /2 ) - 100, (getHeight()/2) - 90, 200, 50);
		panel.add(btnNew);
		
		btnLoad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//TODO
				
			}
		});
		btnLoad.setBounds((getWidth() /2 ) - 100, (getHeight()/2) - 30, 200, 50);
		panel.add(btnLoad);
		
		btnSettings.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//TODO
				
			}
		});
		btnSettings.setBounds((getWidth() /2 ) - 100, (getHeight()/2) + 30, 200, 50);
		panel.add(btnSettings);
		
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				onCloseClicked();
				
			}
		});
		btnClose.setBounds((getWidth() /2 ) - 100, (getHeight()/2) + 90, 200, 50);
		panel.add(btnClose);
	}
	
	/**
	 * Baut die Verbindung zur Datenbank auf. Falls nicht möglich, 
	 * wird der Fehler in der Konsole ausgegeben.
	 * 
	 * @author Kevin
	 */
	private void connectDatabase() {
		try {
			Database.getInstance().createDatabase();
			Database.getInstance().createTables();
		}catch(Exception ex) {
			System.out.println("Keine Datenbank verbindung");
		}
	}
	
	/**
	 * Initialisiert den Frame zuende
	 * 
	 * @author Kevin
	 */
	private void initializeFrame(){
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(3);
		//this.setVisible(true);	
	}
}