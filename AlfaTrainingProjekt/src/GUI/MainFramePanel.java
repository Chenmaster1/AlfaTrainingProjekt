package GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainFramePanel extends JPanel{

    private Image backgroundImage;
    
    public MainFramePanel(Image img){
        backgroundImage = img;
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.drawImage(backgroundImage, 0, 0, this);
    }
}
