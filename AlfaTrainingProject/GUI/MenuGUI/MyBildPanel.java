/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuGUI;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

public class MyBildPanel extends JPanel
{

     private Image image;

    public MyBildPanel() {
                    
          image = ImageLoader.getInstance().getImage(ImageName.GAMEBOARD_INSTRUCTION);
       
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0,100,110, null); // see javadoc for more info on the parameters  
       // g.drawImage(img, x, y, neueBreite, neueHoehe, null);
    }

    
    
    
    
    
    
    
  
    // --------------------------------------------------------------
}

