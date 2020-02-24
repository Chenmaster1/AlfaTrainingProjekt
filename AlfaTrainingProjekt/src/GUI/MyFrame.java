package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Database.Database;

/**
 * Diese Klasse dient zum Starten des Programms und Aufbau des Grundfensters
 * 
 * @author Kevin
 *
 */
public class MyFrame extends JFrame{

	private JButton btnClose;
	private JPanel pnl;
	
	/**
	 * Hier werden alle Componenten initialisiert. Zudem werden <code>initializeFrame()</code>
	 * und <code>pack()</code> ausgeführt
	 * 
	 * @author Kevin
	 */
	public MyFrame () {
		btnClose = new JButton("Beenden");
		pnl = new JPanel();
		initializeFrame();
		pack();
	}
	
	/**
	 * Dient zum Starten des Programms. Hier werden entsprechend Attribute 
	 * und Der Frame auf Visible gesetzt.
	 * 
	 * @author Kevin
	 */
	public void start() {
		//Panel adden
		this.add(pnl);
		
		//Buttons
		initializeButtons();
		
		//Datenbankverbindung
		connectDatabase();
		
		this.setVisible(true);		
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
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				onCloseClicked();
				
			}
		});
		pnl.add(btnClose, BorderLayout.PAGE_END);
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
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}