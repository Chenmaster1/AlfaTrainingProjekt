package GUI;

import Heroes.Hero;
import Hideouts.Hideout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
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
    private Image aimOverlay;
    private ArrayList<Image> inactiveFieldOverlays;

    private int mapState;
    private HashMap<Hideout, Hero> visibleHeroes;
    private ArrayList<Hideout> hideouts;
    private Hero currentHero;
    private int currentAimedAtField;

    public MapPanel(ArrayList<Hideout> hideouts) {
        super();

        //TODO: korrekte Bilder referenzieren
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Images/TODOTODOTODO.png")).getImage();
        aimOverlay = new ImageIcon(getClass().getClassLoader().getResource("Images/TODOTODOTODO.png")).getImage();
        inactiveFieldOverlays = new ArrayList<>();
        for (int i = 0; i < hideouts.size(); i++) {
            inactiveFieldOverlays.add(new ImageIcon(getClass().getClassLoader().getResource("Images/" + i + "_TODOTODOTODO.png")).getImage());
        }

        visibleHeroes = null;
        currentHero = null;
        this.hideouts = hideouts;
        mapState = MAPSTATE_REGULAR;
        currentAimedAtField = 0;

        addAimMouseListener();
    }

    public void setCurrentHero(Hero currentHero) {
        this.currentHero = currentHero;
    }

    public void setVisibleHeroes(HashMap<Hideout, Hero> visibleHeroes) {
        this.visibleHeroes = visibleHeroes;
    }

    public void setMapState(int mapState) {
        this.mapState = mapState;
    }

    public int getCurrentAimedAtField() {
        return currentAimedAtField;
    }

    @Override
    public void paintComponent(Graphics g) {

        //Grundkarte ganz unten
        g.drawImage(backgroundImage, 0, 0, PANELSIZE, PANELSIZE, this);

        //Overlays für zerstörte Verstecke
        drawDisabledFields(g);

        //ggf Overlay für den Zielmechanismus
        if (mapState == MAPSTATE_AIMING) {
            drawAimOverlay(g);
        }

        //zuletzt Overlays für Helden
        drawVisibleHeros(g);

    }

    private void drawDisabledFields(Graphics g) {
        for (int i = 0; i < hideouts.size(); i++) {
            if (!hideouts.get(i).isActive()) {
                g.drawImage(inactiveFieldOverlays.get(i), 0, 0, PANELSIZE, PANELSIZE, this);
            }
        }
    }

    private void drawAimOverlay(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void drawVisibleHeros(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void addAimMouseListener() {
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent me) {
                currentAimedAtField = calculateField(me.getPoint());
            }

        });
    }

    private int calculateField(Point aimedAtPoint) {
        int numFields = hideouts.size();
        int degreesPerField = 360 / numFields;
        int aimedAtField = -1;
        double aimedAtDegree = 0.0;

        Point center = new Point(PANELSIZE / 2, PANELSIZE / 2);

        int x_diff = aimedAtPoint.x - center.x;
        int y_diff = aimedAtPoint.y - center.y;

        aimedAtDegree = Math.toDegrees(Math.atan2(x_diff, y_diff));
        
        aimedAtDegree = aimedAtDegree + 180;
        
        aimedAtField = (int)aimedAtDegree / degreesPerField;
        
        return aimedAtField;
    }
}
