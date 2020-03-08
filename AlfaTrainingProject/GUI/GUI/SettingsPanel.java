package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SettingsPanel extends JPanel{

	private MainFramePanel parentPanel;
	private JFrame frame;
	private Image backgroundImage;
	
	private JButton cancelBtn;
	private JButton saveBtn;
	
	public SettingsPanel(MainFramePanel parentPanel, JFrame frame) {
		backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Images/BackGround_FullScreenBlurred.png")).getImage();	
		this.parentPanel = parentPanel;
		this.frame = frame;
		frame.setContentPane(this);
		setLayout(null);
		cancelBtn = new JButton(MyFrame.bundle.getString("btnCancel"));
		saveBtn = new JButton(MyFrame.bundle.getString("btnClose"));
		fillPanel();
	}

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(backgroundImage, 0, 0, this);
    }
    
	
	private void fillPanel() {
		setSize(frame.getSize());

		cancelBtn.setBounds(getWidth()/2-60,10,100,30);
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelClicked();
				
			}
		});
		add(cancelBtn);
		
		saveBtn.setBounds(getWidth()/2+60,10 , 100, 30);
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
