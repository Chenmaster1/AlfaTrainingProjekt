package InGameGUI;

import Heroes.Hero;
import Hideouts.Hideout;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Das Panel mit dem eigentlichen Spielfeld bzw der Arena.
 * TODO: Skalierbar machen
 */
public class MapPanel extends JPanel {

    public final static int MAPSTATE_REGULAR = 0, MAPSTATE_AIMING = 1;
    private final static int PANELSIZE = 1080;

    private final static double HEROICON_DISTANCE_RELATIVE = 0.25;
    private final static double HEROICON_SIZE_RELATIVE = 0.07;
    private final static float HEROICON_HIDDEN_ALPHA = 0.5f;

    private Image backgroundImage;
    private Image aimOverlay;
    private ArrayList<Image> inactiveFieldOverlays;

    private int mapState;
    private HashMap<Hideout, Hero> hideoutHeroes;
    private ArrayList<Hideout> hideouts;
    private Hero currentHero;
    private int currentAimedAtField;

    public MapPanel(ArrayList<Hideout> hideouts) {
        super();

        setLayout(null);
        setPreferredSize(new Dimension(PANELSIZE, PANELSIZE));

        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/Gameboard_Empty.png"))
                .getImage();
        aimOverlay = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/Tower_Aim.png")).getImage();

        inactiveFieldOverlays = new ArrayList<>();
        for (int i = 0; i < hideouts.size(); i++) {
            inactiveFieldOverlays
                    .add(new ImageIcon(getClass().getClassLoader().getResource("Gameboard/" + i + "-Deactivated.png"))
                            .getImage());
        }

        hideoutHeroes = null;
        currentHero = null;
        this.hideouts = hideouts;
        mapState = MAPSTATE_REGULAR;
        currentAimedAtField = 0;

        addAimMouseListener();
    }

    public void setCurrentHero(Hero currentHero) {
        this.currentHero = currentHero;
    }

    public void setHideoutHeroes(HashMap<Hideout, Hero> hideoutHeroes) {
        this.hideoutHeroes = hideoutHeroes;
    }

    public void setMapState(int mapState) {
        this.mapState = mapState;

    }

    public int getCurrentAimedAtField() {
        return currentAimedAtField;
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        // Grundkarte ganz unten
        g2d.drawImage(backgroundImage, 0, 0, PANELSIZE, PANELSIZE, this);

        // Overlays für zerstörte Verstecke
        drawDisabledFields(g2d);
        // ggf Overlay für den Zielmechanismus
        if (mapState == MAPSTATE_AIMING) {
            drawAimOverlay(g2d);
        }

        // zuletzt Overlays für Helden
        drawVisibleHeros(g2d);
    }

    /**
     * Zeichnet für alle zerstörten Verstecke das entsprechende Overlay.
     */
    private void drawDisabledFields(Graphics2D g2d) {
        for (int i = 0; i < hideouts.size(); i++) {
            if (!hideouts.get(i).isActive()) {
                g2d.drawImage(inactiveFieldOverlays.get(i), 0, 0, this);
            }
        }
    }

    /**
     * Zeichnet die Zielmaske, abhängig vom aktuell anvisierten Feld.
     *
     * @param g2d
     */
    private void drawAimOverlay(Graphics2D g2d) {
        AffineTransform oldTransform = g2d.getTransform();

        g2d.rotate(getRadiant(currentAimedAtField), PANELSIZE / 2, PANELSIZE / 2);

        g2d.drawImage(aimOverlay, PANELSIZE / 2 - aimOverlay.getWidth(this) / 2,
                PANELSIZE / 2 - aimOverlay.getHeight(this) / 2, this);

        g2d.setTransform(oldTransform);
    }

    /**
     * Ermittelt die Position eines Feldes in Radiant (Pi inklusive bis 3 Pi
     * exklusive). Feld 0 befindet sich oben in der Mitte, der Rest folgt im
     * Uhrzeigersinn.
     *
     * @param aimedAtField Die Feldnummer des Feldes, dessen Winkel bestimmt
     * werden soll
     * @return Der Winkel in Radiant des übergebenen Feldes zu Feld 0 (mittig
     * oben).
     */
    private double getRadiant(int aimedAtField) {

        return Math.toRadians((aimedAtField) * (360.0 / hideouts.size()) + 180);
    }

    /**
     * Zeichnet alle sichtbaren Helden-Icons auf die entsprechenden Felder. Der
     * eigene Held (currentHero) ist immer sichtbar und wird halb transparent
     * dargestellt, wenn er versteckt ist.
     *
     * @param g2d
     */
    private void drawVisibleHeros(Graphics2D g2d) {
        for (Hideout h : hideoutHeroes.keySet()) {
            Hero occupyingHero = hideoutHeroes.get(h);
            Image heroIcon = occupyingHero.getMapIcon();

            AffineTransform oldTransform = g2d.getTransform();

            int heroIconTotalWidth = (int) (HEROICON_SIZE_RELATIVE * getWidth());
            int heroIconTotalHeight = (int) (HEROICON_SIZE_RELATIVE * getHeight());

            //Vorwärtsdrehen aufs richtige Feld (um den Panelmittelpunkt)
            g2d.rotate(getRadiant(h.getFieldNumber()), PANELSIZE / 2, PANELSIZE / 2);
            //Rückwärtsdrehen auf der Stelle, damit das Icon gerade ist
            g2d.rotate(-getRadiant(h.getFieldNumber()), PANELSIZE / 2,
                    (PANELSIZE / 2)
                    + (int) (HEROICON_DISTANCE_RELATIVE * getHeight())
                    + heroIconTotalHeight / 2);
            //Wenn der Held sichtbar ist, immer opak anzeigen
            if (occupyingHero.isVisible()) {

                g2d.drawImage(heroIcon,
                        PANELSIZE / 2 - heroIconTotalWidth / 2,
                        PANELSIZE / 2 + (int) (HEROICON_DISTANCE_RELATIVE * getHeight()),
                        heroIconTotalWidth,
                        heroIconTotalHeight, this);
            } //Wenn der Held versteckt ist, nur halbtransparent anzeigen und nur, wenn es sich um den Spielerheld handelt
            else {
                if (occupyingHero == currentHero) {
                    Composite oldComposite = g2d.getComposite();
                    Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, HEROICON_HIDDEN_ALPHA);
                    g2d.setComposite(alphaComposite);

                    g2d.drawImage(heroIcon, PANELSIZE / 2 - heroIconTotalWidth / 2,
                            PANELSIZE / 2 + (int) (HEROICON_DISTANCE_RELATIVE * getHeight()), heroIconTotalWidth,
                            heroIconTotalHeight, this);

                    g2d.setComposite(oldComposite);
                }
            }

            //Graphics2d wieder auf Ausgangsposition
            g2d.setTransform(oldTransform);

        }

    }

    /**
     * Fügt die konstanten MouseListener hinzu, damit das anvisierte Feld
     * bestimmt werden kann.
     *
     * TODO Optimierung: Listener als interne Klasse definieren und nur
     * anschalten, solange mapState == MAPSTATE_AIMING ist (in setMapState(int))
     */
    private void addAimMouseListener() {
        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent me) {
                int newField = calculateField(me.getPoint());

                if (!(newField == currentAimedAtField)) {
                    currentAimedAtField = newField;
                    repaint();
                }
            }

        });
    }

    /**
     * Errechnet für einen beliebigen Punkt des Panels, welchem Hideout er
     * entspricht (für den Zielmechanismus).
     *
     * @param aimedAtPoint Position auf dem Panel
     * @return Die ID des hideouts, dass am ehesten der angegebenen Position
     * entspricht
     */
    private int calculateField(Point aimedAtPoint) {
        int numFields = hideouts.size();
        int degreesPerField = 360 / numFields;

        Point center = new Point(PANELSIZE / 2, PANELSIZE / 2);

        int x_diff = aimedAtPoint.x - center.x;
        int y_diff = aimedAtPoint.y - center.y;

        double atan = Math.toDegrees(Math.atan2(x_diff, y_diff));

        double aimedAtDegree = (atan - 180.0) * -1;

        int aimedAtField = ((((int) aimedAtDegree + (360 / hideouts.size() / 2)) % 360) / degreesPerField);

        return aimedAtField;
    }
}
