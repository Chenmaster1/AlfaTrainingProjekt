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
public class SettingsPanel extends JPanel
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

    private Boolean createFile = true;
    private File file;
    //private String path;
    
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
        getSettings();
        fillPanel();

    }


    /**
     * set volumeSlider to
     * MyFrame-<code>public static String volume = "50";</code>
     *
     * check if <code>MyFrame.path\hota_setting.txt</code> exists, if file
     * exists, set <code>createFile = false</code>
     *
     * @author Yovo
     *
     */
    private void getSettings()
    {
        if(MyFrame.volumFromFile)
        {volumeSlider.setValue(SoundController.getVolumeBackgroundMusic());}

        //check if file exists, if not set flag createFile = false
        if (Files.exists(Paths.get(MyFrame.path + "\\hota_setting.txt")))
        {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(MyFrame.path + "\\hota_setting.txt"));
                createFile = false;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

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
        volumeSlider.setValue(SoundController.getVolumeBackgroundMusic());
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
                    SoundController.setVolumeBackgroundMusic(volumeSlider.getValue());
                     MyFrame.volumFromFile = false;

        
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
        effectVolumeSlider.setValue(SoundController.getVolumeSounds());
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
                    SoundController.setVolumeSounds(effectVolumeSlider.getValue());
                    MyFrame.effectVolumeFromFile = false;
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
        langGerBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MyFrame.language = "german";
            }


        });
        add(langGerBtn);

        langEngBtn.setBounds(getWidth() / 2 + 5, 750, 100, 30);
        langEngBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                MyFrame.language = "english";
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
        if (createFile)
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
                fw.write(volumeSlider.getValue() + "\n" + MyFrame.language + "\n" + effectVolumeSlider.getValue());
                fw.close();
               

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        /**
         * setting file exists <br>
         * override volume and language at
         * <code>MyFrame.path\hota_setting.txt</code>
         *
         * @author Yovo
         *
         */
        if (!createFile)
        {
             try
            {
                FileWriter fw = new FileWriter(MyFrame.path + "\\hota_setting.txt");
                fw.write(volumeSlider.getValue() + "\n" + MyFrame.language + "\n" + effectVolumeSlider.getValue());
                fw.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
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


}

