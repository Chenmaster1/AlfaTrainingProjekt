package InGameGUI;

import Heroes.Hero;
import Hideouts.Hideout;
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
 */
public class MapPanel extends JPanel {

	public final static int MAPSTATE_REGULAR = 0, MAPSTATE_AIMING = 1;
	private final static int PANELSIZE = 1080;

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
//        drawVisibleHeros(g2d);
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

		g2d.rotate(getDegree(currentAimedAtField), PANELSIZE / 2, PANELSIZE / 2);

		g2d.drawImage(aimOverlay, PANELSIZE / 2 - aimOverlay.getWidth(this) / 2,
				PANELSIZE / 2 - aimOverlay.getHeight(this) / 2, this);

		g2d.setTransform(oldTransform);
	}

	/**
	 * Ermittelt die Position eines Feldes in Grad (0 inklusive bis 360.0
	 * exklusive). Feld 0 befindet sich oben in der Mitte, der Rest folgt im
	 * Uhrzeigersinn.
	 * 
	 * @param aimedAtField Die Feldnummer des Feldes, dessen Winkel bestimmt werden soll
	 * @return Der Winkel in Grad des übergebenen Feldes zu Feld 0 (mittig oben).
	 */
	private double getDegree(int aimedAtField) {
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
			if (occupyingHero.isVisible()) {
				// TODO: Hero-Icon zeichnen
			}
			if ((occupyingHero == currentHero) && !occupyingHero.isVisible()) {
				// TODO: Hero-Icon halbtransparent zeichnen
			}

		}
		;

	}

	/**
	 * Fügt die konstanten MouseListener hinzu, damit das anvisierte Feld bestimmt
	 * werden kann.
	 *
	 * TODO Optimierung: Listener als interne Klasse definieren und nur anschalten,
	 * solange mapState == MAPSTATE_AIMING ist (in setMapState(int))
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
	 *         entspricht
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
