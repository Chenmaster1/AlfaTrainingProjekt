package MenuGUI;

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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Game Settings volume language save settings in file
 *
 *
 * @author
 *
 */
@SuppressWarnings("serial")
public class SettingsPanel extends JPanel
{

    private MainFramePanel parentPanel;
    private JFrame frame;
    private Image backgroundImage;

    private JLabel settingLbl, volumeLbl;
    private JSlider volumeSlider;
    private JLabel languageLbl;
    private JButton langGerBtn, langEngBtn;
    private JButton cancelBtn;
    private JButton saveBtn;

    private File file;
    private String path;
    //language should be loaded somewhere else
    private String language = "german";
    private Boolean createFile = true;


    public SettingsPanel(MainFramePanel parentPanel, JFrame frame)
    {
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Images/BackGround_FullScreenBlurred.png")).getImage();
        this.parentPanel = parentPanel;
        this.frame = frame;
        frame.setContentPane(this);
        setLayout(null);
        settingLbl = new JLabel(MyFrame.bundle.getString("settingLbl"));

        volumeLbl = new JLabel(MyFrame.bundle.getString("volumeLbl"));
        volumeSlider = new JSlider();

        languageLbl = new JLabel(MyFrame.bundle.getString("languageLbl"));
        langGerBtn = new JButton(MyFrame.bundle.getString("langGerBtn"));
        langEngBtn = new JButton(MyFrame.bundle.getString("langEngBtn"));

        cancelBtn = new JButton(MyFrame.bundle.getString("btnCancel"));
        saveBtn = new JButton(MyFrame.bundle.getString("btnSave"));
        getSettings();
        fillPanel();

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
                System.out.println("string language " + language);
//String Volume und language wären dann die in der txt gespeicherten Daten                                        
                volumeSlider.setValue(Integer.parseInt(volume));
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
        settingLbl.setBounds(getWidth() / 2 - 300, 400, 600, 50);
        settingLbl.setBackground(Color.lightGray);
        settingLbl.setOpaque(true);
        settingLbl.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        add(settingLbl);

        volumeLbl.setBounds(getWidth() / 2 - 300, 500, 600, 50);
        volumeLbl.setBackground(Color.lightGray);
        volumeLbl.setOpaque(true);
        volumeLbl.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        add(volumeLbl);

        // Volume controll over changelistener ->SoundThread.mp3test.setVolume(volumeSlider.getValue());
        volumeSlider.setMinimum(0);
        volumeSlider.setMaximum(100);

        volumeSlider.setMinorTickSpacing(10);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setBounds(getWidth() / 2 - 300, 600, 600, 100);
        add(volumeSlider);
        volumeSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent ce)
            {
                if (ce.getSource() == volumeSlider)
                {
                    SoundThread.mp3test.setVolume(volumeSlider.getValue());
                }
            }


        });

        // Language controll over actionPerformed -> im File speicher 
        languageLbl.setBounds(getWidth() / 2 - 300, 750, 600, 50);
        languageLbl.setBackground(Color.lightGray);
        languageLbl.setOpaque(true);
        languageLbl.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        add(languageLbl);

        langGerBtn.setBounds(getWidth() / 2 - 105, 820, 100, 30);
        langGerBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                language = "german";
                //MyFrame.setLanguage("/Bundle_DE");
                //ResourceBundle bundle = ResourceBundle.getBundle("LanguagePackages/Bundle_DE");

                //repaint();
            }


        });
        add(langGerBtn);

        langEngBtn.setBounds(getWidth() / 2 + 5, 820, 100, 30);
        langEngBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                language = "english";
                // MyFrame.setLanguage("/Bundle_EN");
                //ResourceBundle bundle = ResourceBundle.getBundle("LanguagePackages/Bundle_EN");
                //repaint();
            }


        });
        add(langEngBtn);

        // cancel and save button 
        cancelBtn.setBounds(getWidth() / 2 - 105, 900, 100, 30);
        cancelBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                cancelClicked();
            }


        });
        add(cancelBtn);

        saveBtn.setBounds(getWidth() / 2 + 5, 900, 100, 30);
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
        frame.repaint();
    }


    private void saveClicked()
    {
        if (createFile)
        {
            System.out.println("save that new bitch");

            try
            {

                if (!Files.isDirectory(Paths.get(path + "\\")))
                {
                    Files.createDirectory(Paths.get(path + "\\"));
                    Files.createFile(Paths.get(path + "\\hota_setting.txt"));
                }
                else if (!Files.isDirectory(Paths.get(path + "\\hota_setting.txt")))
                {
                    Files.createFile(Paths.get(path + "\\hota_setting.txt"));
                }

                FileWriter fw = new FileWriter(path + "\\hota_setting.txt");

                //save current settings to hota_setting.txt
                fw.write(volumeSlider.getValue() + "\n" + language);
                fw.close();

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        
        
        
        
        
        if (!createFile)
        {
            System.out.println("supdate that bitch");

            try
            {

                FileWriter fw = new FileWriter(path + "\\hota_setting.txt");
               

                //save current settings to hota_setting.txt
                fw.write(volumeSlider.getValue() + "\n" + language);
                fw.close();

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        

        // frame.setContentPane(parentPanel);
        //frame.repaint();
//depricated		frame.remove(this);        
    }


}

