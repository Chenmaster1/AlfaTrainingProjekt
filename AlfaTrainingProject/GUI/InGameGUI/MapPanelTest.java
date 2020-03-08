package InGameGUI;

import Hideouts.Hideout;
import Hideouts.HideoutType;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * Eine Testklasse f�r die Arena
 */
public class MapPanelTest extends JFrame{
    
    private boolean state;
    public MapPanelTest()
    {
        
        ArrayList<Hideout> hideoutArray = new ArrayList<>();
        for (int i = 0;i<20;i++)
        {
            Hideout h = new Hideout(0,HideoutType.FOREST);
            h.setActive(true);
            hideoutArray.add(h);
            
        }
        MapPanel mp = new MapPanel(hideoutArray);
        setContentPane(mp);
        mp.setMapState(MapPanel.MAPSTATE_AIMING);
        
        mp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
//                if (state)
//                {
//                    mp.setMapState(MapPanel.MAPSTATE_REGULAR);
//                    mp.repaint();
//                }
//                else
//                {
//                    mp.setMapState(MapPanel.MAPSTATE_AIMING);
//                    mp.repaint();
//                }
//                state = !state;
                hideoutArray.get(mp.getCurrentAimedAtField()).setActive(!hideoutArray.get(mp.getCurrentAimedAtField()).isActive());
                repaint();
            }
        });
        
//        getContentPane().setPreferredSize(new Dimension(1080, 1080));
         setLocation(-1080, 0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
    
    public static final void main(String args[])
    {
        MapPanelTest mpt = new MapPanelTest();
        
        
    }

}