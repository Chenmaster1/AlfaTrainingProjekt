
package MenuGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NewGamePanel extends JPanel { 
    
    private MainFramePanel parentPanel;
    private JFrame frame;
    private Image backgroundImage;
    private Image PlayerImageBackground;
    private JButton cancelBtn;
    private Image AvatarImage1;
    private Image AvatarImage2;
    private Image AvatarImage3;
    private Image AvatarImage4;
    private Image AvatarImage5;
    private Image Deactived_AvatarImage1;
    private Image Deactived_AvatarImage2;
    private Image Deactived_AvatarImage3;
    private Image Deactived_AvatarImage4;
    private Image Deactived_AvatarImage5;   
    
    public NewGamePanel(MainFramePanel parentPanel, JFrame frame)
    {
     backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Images/BackGround_FullScreenBlurred.png")).getImage();
     this.parentPanel = parentPanel;
     this.frame = frame;
     frame.setContentPane(this);
     setLayout(null);
     
     cancelBtn = new JButton(MyFrame.bundle.getString("btnCancel"));
     PlayerImageBackground = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Hero_Front_Empty_tall.jpg")).getImage().getScaledInstance(1116, 787, java.awt.Image.SCALE_SMOOTH);
     AvatarImage1 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Avatar_Balthur.jpg")).getImage().getScaledInstance(360, 379, java.awt.Image.SCALE_SMOOTH);
     AvatarImage2 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Avatar_Dahlia.jpg")).getImage().getScaledInstance(200, 211, java.awt.Image.SCALE_SMOOTH);
     AvatarImage3 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Avatar_Flint.jpg")).getImage().getScaledInstance(200, 211, java.awt.Image.SCALE_SMOOTH);
     AvatarImage4 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Avatar_Talpan.jpg")).getImage().getScaledInstance(200, 211, java.awt.Image.SCALE_SMOOTH);
     AvatarImage5 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Avatar_Worok.jpg")).getImage().getScaledInstance(200, 211, java.awt.Image.SCALE_SMOOTH);
     /**Deactived_AvatarImage1 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Deactived_Avatar_Balthur.jpg")).getImage();
     Deactived_AvatarImage2 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Deactived_Avatar_Dahlia.jpg")).getImage();
     Deactived_AvatarImage3 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Deactived_Avatar_Flint.jpg")).getImage();
     Deactived_AvatarImage4 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Deactived_Avatar_Talpan.jpg")).getImage();
     Deactived_AvatarImage5 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Deactived_Avatar_Worok.jpg")).getImage();
     * */
      
          
     fillPanel();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(backgroundImage, 0, 0, this);
        g.drawImage(PlayerImageBackground, 400, 50, this);
        g.drawImage(AvatarImage1, 1105, 100, this);
        g.drawImage(AvatarImage2, 100, 50, this);
        g.drawImage(AvatarImage3, 100, 270, this);
        g.drawImage(AvatarImage4, 100, 490, this);
        g.drawImage(AvatarImage5, 100, 710, this);
    }

    private void cancelClicked()
    {
        frame.setContentPane(parentPanel);
        frame.repaint();
    }    
    
    private void fillPanel()
    {
        setSize(frame.getSize());
        
        cancelBtn.setBounds(getWidth() / 2 - 105, 1000, 100, 30);
        cancelBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                cancelClicked();
            }


        });
        add(cancelBtn);
    }

}
