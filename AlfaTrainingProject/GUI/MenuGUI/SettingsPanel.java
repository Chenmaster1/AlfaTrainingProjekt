package MenuGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * Game Settings volume language
 *
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
        
        volumeSlider.setMinimum(0);
        volumeSlider.setMaximum(100);
        volumeSlider.setValue(50);
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
                
                MyFrame.setLanguage("/Bundle_DE");
                ResourceBundle bundle = ResourceBundle.getBundle("LanguagePackages/Bundle_DE");
                
                repaint();
                
                       
            }
        });
        add(langGerBtn);
       
        langEngBtn.setBounds(getWidth() / 2+5, 820, 100, 30);
        langEngBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
               MyFrame.setLanguage("/Bundle_EN");
                ResourceBundle bundle = ResourceBundle.getBundle("LanguagePackages/Bundle_EN");
                repaint();
            }
        });
        add(langEngBtn);
        
        
        
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
//		frame.remove(this);
        frame.setContentPane(parentPanel);
        frame.repaint();
    }
}

