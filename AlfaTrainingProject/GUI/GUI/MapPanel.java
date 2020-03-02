package GUI;

import Heroes.Hero;
import Hideouts.Hideout;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 */
public class MapPanel extends JPanel {

    public final static int MAPSTATE_REGULAR = 0, MAPSTATE_AIMING = 1;
    private final static int PANELSIZE = 600;

    private Image backgroundImage;
    private int mapState;
    private HashMap<Hideout, Hero> visibleHeroes;

    public MapPanel() {
        super();
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Images/BackGround_FullScreen.png")).getImage();
        visibleHeroes = null;
        mapState = MAPSTATE_REGULAR;
    }

    public void setVisibleHeroes(HashMap<Hideout, Hero> visibleHeroes) {
        this.visibleHeroes = visibleHeroes;
    }

    public void setMapState(int mapState) {
        this.mapState = mapState;
    }

    @Override
    public void paintComponent(Graphics g) {

        //Grundkarte ganz unten
        g.drawImage(backgroundImage, 0, 0, PANELSIZE, PANELSIZE, this);

        //Overlays für zerstörte Verstecke
        drawDisabledFields(g);
        
        //ggf Overlay für den Zielmechanismus
        if (mapState == MAPSTATE_AIMING)
        {
            drawAimOverlay();
        }
        
        //zuletzt Overlays für Helden
        drawVisibleHeros(g);
        
    }

    private void drawDisabledFields(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void drawAimOverlay() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void drawVisibleHeros(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
