package InGameGUI;

import Heroes.*;
import Hideouts.Hideout;
import Hideouts.HideoutType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Eine Testklasse für das GamePanel. Zeigt ein GamePanel standalone in einem
 * JFrame an.
 */
public class GamePanelTest extends JFrame {

    public GamePanelTest() {
        
            //testArray hideouts
            ArrayList<Hideout> hideoutArray = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Hideout h = new Hideout(0, HideoutType.FOREST);
                h.setActive(true);
                hideoutArray.add(h);

            }
            
            //testArray Gegnerhelden
            ArrayList<Hero> otherHeroes = new ArrayList<>();
            otherHeroes.add(new HeroWorok());
            otherHeroes.add(new HeroDahlia());
            otherHeroes.add(new HeroTolpanLongbeard());
            otherHeroes.add(new HeroWorok());
            
            
            
            MapPanel mp = new MapPanel(hideoutArray);
            GameSidePanel gsp = new GameSidePanel(otherHeroes, new HeroWorok());
            GamePanel gp = new GamePanel(mp, gsp);

            setContentPane(gp);

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

    public static final void main(String args[]) {
        GamePanelTest gpt = new GamePanelTest();

    }
}
