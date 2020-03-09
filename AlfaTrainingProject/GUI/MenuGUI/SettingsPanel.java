package MenuGUI;

import jaco.mp3.player.MP3Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SoundThread.mp3test;


/**
 * Game Settings
 * volume
 * language
 * 
 * fullwidth?
 * gameFrame size
 * 
 * 
 * @author
 *
 */
@SuppressWarnings("serial")
public class SettingsPanel extends JPanel{

	private MainFramePanel parentPanel;
	private JFrame frame;
	private Image backgroundImage;
	
	private JButton cancelBtn;
	private JButton saveBtn;
        private JLabel settingLbl;
	
	public SettingsPanel(MainFramePanel parentPanel, JFrame frame) {
		backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Images/BackGround_FullScreenBlurred.png")).getImage();	
		this.parentPanel = parentPanel;
		this.frame = frame;
		frame.setContentPane(this);
		setLayout(null);
		settingLbl = new JLabel(MyFrame.bundle.getString("settingLbl"));
                cancelBtn = new JButton(MyFrame.bundle.getString("btnCancel"));
		saveBtn = new JButton(MyFrame.bundle.getString("btnSave"));
                
		fillPanel();
                sound(1);

	}

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(backgroundImage, 0, 0, this);
    }
    
    public void sound(int v){
       
      SoundThread.mp3test.setVolume(v);
        System.out.println("settingPanel sagt RUHE");
 
    }
    
   
	
	private void fillPanel() {
		setSize(frame.getSize());
                settingLbl.setBounds(getWidth()/2-300, 400, 600, 50);
                settingLbl.setBackground(Color.lightGray);
                settingLbl.setOpaque(true);
                settingLbl.setHorizontalAlignment((int) CENTER_ALIGNMENT);
           
                add(settingLbl);

		cancelBtn.setBounds(getWidth()/2-60,800,100,30);
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelClicked();
				
			}
		});
		add(cancelBtn);
		
		saveBtn.setBounds(getWidth()/2+60,800 , 100, 30);
		saveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveClicked();
				
			}
		});
		add(saveBtn);
		repaint();
	}
	
	private void cancelClicked() {
		
//		frame.remove(this);
		frame.setContentPane(parentPanel);
		frame.repaint();
	}
	
	private void saveClicked() {
		
//		frame.remove(this);
		frame.setContentPane(parentPanel);
		frame.repaint();
	}
}
