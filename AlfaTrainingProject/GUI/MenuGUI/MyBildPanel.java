/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuGUI;



import Heroes.Hero;
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
import InGameGUI.HeroPanelLarge;
import java.util.ArrayList;
import Heroes.Hero;
import Heroes.HeroBalthur;
import Heroes.HeroDahlia;
import Heroes.HeroFlint;
import Heroes.HeroTolpanLongbeard;
import Heroes.HeroWorok;

public class MyBildPanel extends JPanel
{

     private Image image;
     private HeroPanelLarge heroPanelLarge;
     private ArrayList<Hero> heroList;
     //private boolean isHero = false;
     private Hero displayedHero;

    public MyBildPanel() {
          
         
          image = ImageLoader.getInstance().getImage(ImageName.GAMEBOARD_INSTRUCTION);
          
          heroList = new ArrayList<Hero>();
		heroList.add(new HeroBalthur());
		heroList.add(new HeroDahlia());
		heroList.add(new HeroFlint());
		heroList.add(new HeroTolpanLongbeard());
		heroList.add(new HeroWorok());
          heroPanelLarge = new HeroPanelLarge(heroList.get(1)); 
       
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0,100,110, null); // see javadoc for more info on the parameters  
       // g.drawImage(img, x, y, neueBreite, neueHoehe, null);
    }
    
    
    public void displayHeroLargePanel(){
        
              
        this.removeAll();
        heroPanelLarge.setBounds(300, 100, 850, 500);
        System.out.println(getWidth());
        this.add(heroPanelLarge);
                
              //heroPanelLarge = new HeroPanelLarge();
          
    }

    
    
    
    
    
    
    
  
    // --------------------------------------------------------------
}

