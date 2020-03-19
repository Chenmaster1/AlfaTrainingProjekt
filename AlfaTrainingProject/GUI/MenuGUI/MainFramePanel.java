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
import SoundThread.MP3Runnable;
import SoundThread.SoundController;
import alfatrainingprojekt.AlfaTrainingProjekt;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

@SuppressWarnings("serial")
public class MainFramePanel extends JPanel {

	private Image backgroundImage;

	// Fuer eventuelle Spracheisntellungen

	private MyFrame frame;

	// -------------------------Buttons-------------------------//
	private JButton btnNew;
	private JButton btnLoad;
	private JButton btnSettings;
	private JButton btnClose;
        private JButton btnInstruction;

	public MainFramePanel(MyFrame frame) {
		this.frame = frame;
		setLayout(null);
		frame.setContentPane(this);
		setSize(frame.getSize());
		backgroundImage = ImageLoader.getInstance().getImage(ImageName.MENU_BACKGROUND);

		ImageIcon buttonImageIcon = new ImageIcon(ImageLoader.getInstance().getImage(ImageName.BUTTON));
		btnNew = new MyButton(MyFrame.bundle.getString("btnNew"), buttonImageIcon);
		btnLoad = new MyButton(MyFrame.bundle.getString("btnLoad"), buttonImageIcon);
		btnSettings = new MyButton(MyFrame.bundle.getString("btnSettings"), buttonImageIcon);
		btnClose = new MyButton(MyFrame.bundle.getString("btnClose"), buttonImageIcon);
                btnInstruction = new MyButton(MyFrame.bundle.getString("btnInstruction"), buttonImageIcon);
		initializeButtons();
	}

	@Override
	public void paintComponent(Graphics g) {

		g.drawImage(backgroundImage, 0, 0, this);
	}

	// -------------------------private methods-------------------------//

	/**
	 * Hier werden alle Buttons hinzugefuegt
	 * 
	 * @author Kevin
	 */
	private void initializeButtons() {
		addButton(btnNew, getWidth() / 2 - 100, getHeight() / 2 - 90, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onNewClicked();
			}
		});

		addButton(btnLoad, getWidth() / 2 - 100, getHeight() / 2 - 30, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onLoadClicked();
			}
		});

		btnLoad.setEnabled(false);

		addButton(btnSettings, getWidth() / 2 - 100, getHeight() / 2 + 30, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onSettingsClicked();
			}
		});

		addButton(btnInstruction, getWidth() / 2 - 100, getHeight() / 2 + 90, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onInstructionClicked();
			}
		});

       addButton(btnClose, getWidth() / 2 - 100, getHeight() / 2 + 150, new ActionListener() {

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
	 * @param posX   Die Position x
	 * @param posY   Die Position y
	 */
	private void addButton(JButton button, int posX, int posY, ActionListener action) {
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBounds(posX, posY, 200, 50);
		button.addActionListener(action);
		add(button);
	}

	/**
	 * Wird aufgerufen, sobald der Button "Neues Spiel" gedrückt wurde. Das Programm
	 * wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onNewClicked() {
		frame.remove(this); // altes Panel entfernen
		new NewGamePanel(this, frame); // neues Panel hinzufuegen
		repaint(); // neu darstellen
	}

	/**
	 * Wird aufgerufen, sobald der Button "Laden" gedrueckt wurde. Das Programm wird
	 * beendet.
	 * 
	 * @author Kevin
	 */
	private void onLoadClicked() {
		// TODO
	}

	/**
	 * Wird aufgerufen, sobald der Button "Einstellungen" gedrueckt wurde. Das
	 * Programm wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onSettingsClicked() {
		new SettingsPanel(this, frame); // setzt sich selbst als ContentPane
//		repaint();		//nicht noetig
	}

	/**
	 * Wird aufgerufen, sobald der Button "Beenden" gedrückt wurde. Das Programm
	 * wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onCloseClicked() {
		SoundController.setBackgroundMusic(null);
//		frame.dispose();
		// Alles killen, nicht nur das Frame
		System.exit(0);
	}
        
        /**
	 * Wird aufgerufen, sobald der Button "Beenden" gedrückt wurde. Das Programm
	 * wird beendet.
	 * 
	 * @author Kevin
	 */
	private void onInstructionClicked() {
		new InstructionPanel(frame, this);
	}
}
