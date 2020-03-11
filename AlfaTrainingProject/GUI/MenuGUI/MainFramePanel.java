package MenuGUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Database.Database;
import Hideouts.Hideout;
import Hideouts.HideoutType;
import SoundThread.MainTheme;
import SoundThread.mp3test;
import alfatrainingprojekt.AlfaTrainingProjekt;

@SuppressWarnings("serial")
public class MainFramePanel extends JPanel{

    private Image backgroundImage;
    
  //Fuer eventuelle Spracheisntellungen
  	
  	
  	private MyFrame frame;
  	
  	//-------------------------Buttons-------------------------//
  	private JButton btnNew;
  	private JButton btnLoad;
  	private JButton btnSettings;
  	private JButton btnClose;
    
    
    public MainFramePanel(MyFrame frame){
    	this.frame = frame;
		setLayout(null);
		frame.setContentPane(this);
    	setSize(frame.getSize());
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Images/BackGround_FullScreen.png")).getImage();
        
        btnNew = new MyButton(MyFrame.bundle.getString("btnNew"), 
				new ImageIcon(getClass().getClassLoader().getResource("Images/Button.png")));
		btnLoad = new MyButton(MyFrame.bundle.getString("btnLoad"), 
				new ImageIcon(getClass().getClassLoader().getResource("Images/Button.png")));
		btnSettings = new MyButton(MyFrame.bundle.getString("btnSettings"), 
				new ImageIcon(getClass().getClassLoader().getResource("Images/Button.png")));
		btnClose = new MyButton(MyFrame.bundle.getString("btnClose"), 
				new ImageIcon(getClass().getClassLoader().getResource("Images/Button.png")));
		initializeButtons();
    }
    
    @Override
    public void paintComponent(Graphics g){
    	
        g.drawImage(backgroundImage, 0, 0, this);
    }
    

	
	//-------------------------private methods-------------------------//
		
		/**
		 * Hier werden alle Buttons hinzugefuegt
		 * 
		 * @author Kevin
		 */
	private void initializeButtons() {
		addButton(btnNew, getWidth()/2 - 100, getHeight()/2 - 90, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onNewClicked();
			}
		});
		
		addButton(btnLoad, getWidth()/2 - 100, getHeight()/2 - 30, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onLoadClicked();
			}
		});
		
		addButton(btnSettings, getWidth()/2 - 100, getHeight()/2 + 30, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onSettingsClicked();
			}
		});
		
		addButton(btnClose, getWidth()/2 - 100, getHeight()/2 + 90, new ActionListener() {
			
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
	private void addButton(JButton button,int posX,int posY, ActionListener action) {
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBounds(posX, posY, 200, 50);
		button.addActionListener(action);
		add(button);
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
	 * Wird aufgerufen, sobald der Button "Laden" gedrueckt wurde.
	 * Das Programm wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onLoadClicked() {
		//TODO
	}
	
	/**
	 * Wird aufgerufen, sobald der Button "Einstellungen" gedrueckt wurde.
	 * Das Programm wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onSettingsClicked() {
		//TODO so kann man das machen ohne ein neues Fenster öffnen zu müssen
		frame.remove(this);	//altes Panel entfernen
		new SettingsPanel(this, frame);		//neues Panel hinzufuegen
		repaint();		//neu darstellen
	}
	
	/**
	 * Wird aufgerufen, sobald der Button "Beenden" gedrückt wurde.
	 * Das Programm wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onCloseClicked() {
		mp3test.stopPlayer();
		frame.dispose();
	}
}
