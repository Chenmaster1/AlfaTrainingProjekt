package InGameGUI;

import Heroes.Hero;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 */
public class OtherHeroesPanel extends JPanel{
    
    private Image backgroundImage;
    public OtherHeroesPanel(ArrayList<Hero> otherHeroes, Image backgroundImage)
    {
        super(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        this.backgroundImage = backgroundImage;
        
        for (Hero h : otherHeroes) {
            add(new HeroPanelSmall(h));
        }
        
//        setOpaque(false);
    }
}
