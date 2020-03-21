package MenuGUI;

import SoundThread.SoundController;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JPanel;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;


/**
 * Game Settings volume language if no file is found MyFrame-<ode>public static
 * String MyFrame.path</code> use
 * MyFrame-<code>public static String language = "/Bundle_DE", volume = "50";</code>
 *
 * Changes to setting will be stored in
 * <code>MyFrame.path\hota_setting.txt</code>
 *
 * @author Yovo
 *
 */
@SuppressWarnings("serial")
public class SettingsPanel extends JPanel implements SettingsListener
{

    private MainFramePanel parentPanel;
    private JFrame frame;
    private Image backgroundImage;

    private JLabel settingLbl, volumeLbl, effectVolumeLbl;
    private JSlider volumeSlider, effectVolumeSlider;
    private JLabel languageLbl;
    private JButton langGerBtn, langEngBtn;
    private JButton cancelBtn;
    private JButton saveBtn;

    
    private JOptionPane saveMessage;
    //language should be loaded somewhere else
    //private String language = "german";
    //


    public SettingsPanel(MainFramePanel parentPanel, JFrame frame)
    {
        backgroundImage = ImageLoader.getInstance().getImage(ImageName.MENU_BACKGROUND_BLURRY);
        this.parentPanel = parentPanel;
        this.frame = frame;
        frame.setContentPane(this);
        setLayout(null);
        settingLbl = new JLabel(MyFrame.bundle.getString("settingLbl"));

        volumeLbl = new JLabel(MyFrame.bundle.getString("volumeLbl"));
        volumeSlider = new JSlider();
        
        effectVolumeLbl = new JLabel(MyFrame.bundle.getString("effectVolumeLbl"));
        effectVolumeSlider = new JSlider();

        languageLbl = new JLabel(MyFrame.bundle.getString("languageLbl"));
        langGerBtn = new JButton(MyFrame.bundle.getString("langGerBtn"));
        langEngBtn = new JButton(MyFrame.bundle.getString("langEngBtn"));

        cancelBtn = new JButton(MyFrame.bundle.getString("btnCancel"));
        saveBtn = new JButton(MyFrame.bundle.getString("btnSave"));
        saveMessage = new JOptionPane();
        fillPanel();

    }


    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(backgroundImage, 0, 0, this);
    }


    private void fillPanel()
    {
        setSize(frame.getSize());
        settingLbl.setBounds(getWidth() / 2 - 300, 200, 600, 50);
        settingLbl.setBackground(Color.lightGray);
        settingLbl.setOpaque(true);
        settingLbl.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        add(settingLbl);

        volumeLbl.setBounds(getWidth() / 2 - 300, 300, 600, 50);
        volumeLbl.setBackground(Color.lightGray);
        volumeLbl.setOpaque(true);
        volumeLbl.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        add(volumeLbl);

        // Volume controll over changelistener ->SoundThread.mp3test.setVolume(volumeSlider.getValue());
        volumeSlider.setMinimum(0);
        volumeSlider.setMaximum(100);
        volumeSlider.setValue(Settings.INSTANCE.getVolume());
        volumeSlider.setMinorTickSpacing(10);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setBounds(getWidth() / 2 - 300, 350, 600, 100);
        add(volumeSlider);
        volumeSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent ce)
            {
                if (ce.getSource() == volumeSlider)
                {
                	Settings.INSTANCE.setVolume(volumeSlider.getValue());
                }
            }
        });
        
        
        effectVolumeLbl.setBounds(getWidth() / 2 - 300, 500, 600, 50);
        effectVolumeLbl.setBackground(Color.lightGray);
        effectVolumeLbl.setOpaque(true);
        effectVolumeLbl.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        add(effectVolumeLbl);

        // Volume controll over changelistener ->SoundThread.mp3test.setVolume(volumeSlider.getValue());
        effectVolumeSlider.setMinimum(0);
        effectVolumeSlider.setMaximum(100);
        effectVolumeSlider.setValue(Settings.INSTANCE.getEffectVolume());
        effectVolumeSlider.setMinorTickSpacing(10);
        effectVolumeSlider.setMajorTickSpacing(25);
        effectVolumeSlider.setPaintTicks(true);
        effectVolumeSlider.setPaintLabels(true);
        effectVolumeSlider.setBounds(getWidth() / 2 - 300, 550, 600, 100);
        add(effectVolumeSlider);
        effectVolumeSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent ce)
            {
                if (ce.getSource() == effectVolumeSlider)
                {
                    Settings.INSTANCE.setEffectVolume(effectVolumeSlider.getValue());
                    //SoundController.playSound("W10_Dice_Roll.mp3"); //akustisches Feedback wäre nice to have, funktioniert aber leider irgendwie nicht
                }
            }
        });
        
        
        
        

        // Language controll over actionPerformed -> save to file 
        languageLbl.setBounds(getWidth() / 2 - 300, 700, 600, 50);
        languageLbl.setBackground(Color.lightGray);
        languageLbl.setOpaque(true);
        languageLbl.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        add(languageLbl);

        langGerBtn.setBounds(getWidth() / 2 - 105, 750, 100, 30);
        if (Settings.INSTANCE.getLanguage() == "german") {
        	langGerBtn.setEnabled(false);
        }
        langGerBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	Settings.INSTANCE.setLanguage("german");
            	((AbstractButton) e.getSource()).setEnabled(false);
            	langEngBtn.setEnabled(true);
            }


        });
        add(langGerBtn);

        langEngBtn.setBounds(getWidth() / 2 + 5, 750, 100, 30);
        if (Settings.INSTANCE.getLanguage() == "english") {
        	langEngBtn.setEnabled(false);
        }
        langEngBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Settings.INSTANCE.setLanguage("english");
                ((AbstractButton) e.getSource()).setEnabled(false);
                langGerBtn.setEnabled(true);
            }


        });
        add(langEngBtn);

        // cancel and save button 
        cancelBtn.setBounds(getWidth() / 2 - 105, 850, 100, 30);
        cancelBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                cancelClicked();
            }


        });
        add(cancelBtn);

        saveBtn.setBounds(getWidth() / 2 + 5, 850, 100, 30);
        saveBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                saveClicked();
            }


        });
        add(saveBtn);
        repaint();
    }


    private void cancelClicked()
    {
//		frame.remove(this);
        frame.setContentPane(parentPanel);
//        frame.repaint();
    }


    /**
     * no setting file exists <br>
     * create the File and save volume and language to
     * <code>MyFrame.path\hota_setting.txt</code>
     *
     * @author Yovo
     *
     */
    private void saveClicked()
    {
    	Settings.INSTANCE.save(); //TODO: Fehlerhandling, wenn kein Schreibzugriff auf Verzeichnis oder Platte voll
        
        JOptionPane.showMessageDialog(null, 
                              MyFrame.bundle.getString("settingSavedMessage"), 
                              MyFrame.bundle.getString("settingSavedTitle"), 
                              JOptionPane.INFORMATION_MESSAGE);
        
//        name.setMessageType(INFORMATION_MESSAGE);
//        name.setMessage("hello Word");
        //name.showInputDialog(parent,"What is your name?", null);
        

        // frame.setContentPane(parentPanel);
        //frame.repaint();
//depricated		frame.remove(this);        
    }


	@Override
	public void propertyChanged(String prop, Object value) {
		switch(prop) {
		case "language":
			switch((String) value) {
				case "german":
	            {
	            	langGerBtn.setEnabled(false);
	            	langEngBtn.setEnabled(true);
	            	break;
	            }
				case "english":
	            {
	            	langGerBtn.setEnabled(true);
	            	langEngBtn.setEnabled(false);
	            	break;
	            }
			}
			break;
		case "volume":
			volumeSlider.setValue((Integer)value);
			break;
		case "effectVolume":
			effectVolumeSlider.setValue((Integer)value);
			break;
		}
		
	}


}

