package InGameGUI;

import Heroes.Hero;
import Hideouts.Hideout;
import resourceLoaders.AnimationLoader;
import resourceLoaders.AnimationName;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

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
 * 
 */
public class MapPanel extends JPanel implements Runnable {

	public final static int MAPSTATE_REGULAR = 0, MAPSTATE_PLAYER_AIMING = 1, MAPSTATE_KI_AIMING = 2;
//    private final static int PANELSIZE = 1080;

	private final static double HEROICON_DISTANCE_RELATIVE = 0.25;
	private final static double HEROICON_SIZE_RELATIVE = 0.07;
	private final static float HEROICON_HIDDEN_ALPHA = 0.5f;

	private final static double TOWER_SCALE = 1 / 1080.0;

	private final static double ANIMATION_SCALE = 1 / 1080.0;
	private final static double ANIMATION_SCALE_BEAM = 1.2 / 1080.0;

	private final static double AIMOVERLAY_SIZE_RELATIVE_X = 537 / 1080.0;
	private final static double AIMOVERLAY_SIZE_RELATIVE_Y = 748 / 1080.0;

	public final static int ANIMATIONTYPE_SCAN = 1, ANIMATIONTYPE_FIRE = 2, ANIMATIONTYPE_ELIMINATE = 3;

	private Image backgroundImage;
	private Image aimOverlay;
	private Image towerImage;
	private ArrayList<Image> inactiveFieldOverlays;

	private ArrayList<Image> towerChargeAnimation;
	private ArrayList<Image> towerFireAnimation;
	private ArrayList<Image> towerScanAnimation;
	private ArrayList<Image> towerEliminateAnimation;

	private int currentAnimationType;
	private int currentAnimationFrame;
	private int targetAnimationFrame;

	private Thread threadMapPanelAnimation;

	private int mapState;
	private HashMap<Hideout, Hero> hideoutHeroes;
	private ArrayList<Hideout> hideouts;

	private Hero mainHero;
	private int currentAimedAtField;
	private int currentFiredAtField;
	private MouseMotionListener mousePositionListener;

	public MapPanel(ArrayList<Hideout> hideouts, HashMap<Hideout, Hero> hideoutHeroes, Hero mainHero) {
		super();

		setLayout(null);

		backgroundImage = ImageLoader.getInstance().getImage(ImageName.GAMEBOARD_EMPTY);
		aimOverlay = ImageLoader.getInstance().getImage(ImageName.TOWER_AIM);
		towerImage = ImageLoader.getInstance().getImage(ImageName.TOWER);
		inactiveFieldOverlays = AnimationLoader.getInstance().getAnimation(AnimationName.DEACTIVATED_HIDEOUTS);

		towerChargeAnimation = AnimationLoader.getInstance().getAnimation(AnimationName.TOWER_CHARGE);
		towerFireAnimation = AnimationLoader.getInstance().getAnimation(AnimationName.TOWER_FIRE);
		towerScanAnimation = AnimationLoader.getInstance().getAnimation(AnimationName.TOWER_SCAN);
		towerEliminateAnimation = AnimationLoader.getInstance().getAnimation(AnimationName.TOWER_ELIMINATE);

		this.hideoutHeroes = hideoutHeroes;
		this.mainHero = mainHero;
		this.hideouts = hideouts;
		setMapState(MAPSTATE_REGULAR);
		currentAimedAtField = 0;
		mousePositionListener = new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent me) {
				int newField = calculateField(me.getPoint());

				if (!(newField == currentAimedAtField)) {
					setCurrentAimedAtField(newField);
				}
			}

		};

		threadMapPanelAnimation = new Thread(this);
		threadMapPanelAnimation.start();
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		// Grundkarte ganz unten
		g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

		// Overlays für zerstörte Verstecke
		drawDisabledFields(g2d);

		// Den Turm
		drawTower(g2d);

		// ggf Overlay für den Zielmechanismus
		if (mapState == MAPSTATE_PLAYER_AIMING || mapState == MAPSTATE_KI_AIMING) {
			drawAimOverlay(g2d);
		}

		// dann Overlays für Helden
		drawVisibleHeros(g2d);

		// zuletzt aktuelles Animationsframe zeichnen
		drawCurrentAnimationFrame(g2d);
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
	 * Zeichnet den Turm in die Mitte des Feldes
	 */
	private void drawTower(Graphics2D g2d) {
		int totalImageWidth = (int) (towerImage.getWidth(this) * TOWER_SCALE * this.getWidth());

		int totalImageHeight = (int) (towerImage.getHeight(this) * TOWER_SCALE * this.getHeight());

		g2d.drawImage(towerImage, getWidth() / 2 - (totalImageWidth / 2), getHeight() / 2 - (totalImageHeight / 2),
				totalImageWidth, totalImageHeight, this);

	}

	/**
	 * Zeichnet die Zielmaske, abhängig vom aktuell anvisierten Feld.
	 *
	 * @param g2d
	 */
	private void drawAimOverlay(Graphics2D g2d) {
		AffineTransform oldTransform = g2d.getTransform();

		g2d.rotate(getRadiant(currentAimedAtField), getWidth() / 2, getHeight() / 2);

		int absoluteWidth = (int) (AIMOVERLAY_SIZE_RELATIVE_X * getWidth());
		int absoluteHeight = (int) (AIMOVERLAY_SIZE_RELATIVE_Y * getHeight());

		g2d.drawImage(aimOverlay, getWidth() / 2 - absoluteWidth / 2, getHeight() / 2 - absoluteHeight / 2,
				absoluteWidth, absoluteHeight, this);

		g2d.setTransform(oldTransform);
	}

	/**
	 * Ermittelt die Position eines Feldes in Radiant (Pi inklusive bis 3 Pi
	 * exklusive). Feld 0 befindet sich oben in der Mitte, der Rest folgt im
	 * Uhrzeigersinn.
	 *
	 * @param aimedAtField Die Feldnummer des Feldes, dessen Winkel bestimmt werden
	 *                     soll
	 * @return Der Winkel in Radiant des übergebenen Feldes zu Feld 0 (mittig oben).
	 */
	private double getRadiant(int aimedAtField) {

		return Math.toRadians((aimedAtField) * (360.0 / hideouts.size()) + 180);
	}

	/**
	 * Zeichnet alle sichtbaren Helden-Icons auf die entsprechenden Felder. Der
	 * eigene Held (mainHero) ist immer sichtbar und wird halb transparent
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

			// Vorwärtsdrehen aufs richtige Feld (um den Panelmittelpunkt)
			g2d.rotate(getRadiant(h.getFieldNumber()), getWidth() / 2, getHeight() / 2);
			// Rückwärtsdrehen auf der Stelle, damit das Icon gerade ist
			g2d.rotate(-getRadiant(h.getFieldNumber()), getWidth() / 2,
					(getHeight() / 2) + (int) (HEROICON_DISTANCE_RELATIVE * getHeight()) + heroIconTotalHeight / 2);
			// Wenn der Held sichtbar ist, immer opak anzeigen
			if (occupyingHero.isVisible()) {

				g2d.drawImage(heroIcon, getWidth() / 2 - heroIconTotalWidth / 2,
						getHeight() / 2 + (int) (HEROICON_DISTANCE_RELATIVE * getHeight()), heroIconTotalWidth,
						heroIconTotalHeight, this);
			} // Wenn der Held versteckt ist, nur halbtransparent anzeigen und nur, wenn es
				// sich um den Spielerheld handelt
			else {
				if (occupyingHero == mainHero) {
					Composite oldComposite = g2d.getComposite();
					Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
							HEROICON_HIDDEN_ALPHA);
					g2d.setComposite(alphaComposite);

					g2d.drawImage(heroIcon, getWidth() / 2 - heroIconTotalWidth / 2,
							getHeight() / 2 + (int) (HEROICON_DISTANCE_RELATIVE * getHeight()), heroIconTotalWidth,
							heroIconTotalHeight, this);

					g2d.setComposite(oldComposite);
				}
			}

			// Graphics2d wieder auf Ausgangsposition
			g2d.setTransform(oldTransform);

		}

	}

	/**
	 * Zeichnet das aktuelle Frame der Schuss- bzw Scananimation.
	 * 
	 * @param g2d
	 */
	private void drawCurrentAnimationFrame(Graphics2D g2d) {

		Image imageToDraw = null;

		if (currentAnimationFrame < towerChargeAnimation.size()) {
			imageToDraw = towerChargeAnimation.get(currentAnimationFrame);
			int totalImageWidth = (int) (imageToDraw.getWidth(this) * ANIMATION_SCALE * this.getWidth());

			int totalImageHeight = (int) (imageToDraw.getHeight(this) * ANIMATION_SCALE * this.getHeight());

			g2d.drawImage(imageToDraw, getWidth() / 2 - (totalImageWidth / 2), getHeight() / 2 - (totalImageHeight / 2),
					totalImageWidth, totalImageHeight, this);

		} else if (currentAnimationFrame < (towerChargeAnimation.size() + towerFireAnimation.size())) {

			switch (currentAnimationType) {
			case ANIMATIONTYPE_SCAN:
				imageToDraw = towerScanAnimation.get(currentAnimationFrame - towerChargeAnimation.size());
				break;
			case ANIMATIONTYPE_FIRE:
				imageToDraw = towerFireAnimation.get(currentAnimationFrame - towerChargeAnimation.size());
				break;
			case ANIMATIONTYPE_ELIMINATE:
				imageToDraw = towerEliminateAnimation.get(currentAnimationFrame - towerChargeAnimation.size());
				break;
			}

			AffineTransform oldTransform = g2d.getTransform();
			g2d.rotate(getRadiant(currentFiredAtField), getWidth() / 2, getHeight() / 2);

			int totalImageWidth = (int) (imageToDraw.getWidth(this) * ANIMATION_SCALE_BEAM * this.getWidth());
			int totalImageHeight = (int) (imageToDraw.getHeight(this) * ANIMATION_SCALE_BEAM * this.getHeight());

			g2d.drawImage(imageToDraw, getWidth() / 2 - totalImageWidth / 2, getHeight() / 2, totalImageWidth,
					totalImageHeight, this);

			g2d.setTransform(oldTransform);

		}

	}

	/**
	 * Errechnet für einen beliebigen Punkt des Panels, welchem Hideout er
	 * entspricht (für den Zielmechanismus).
	 *
	 * @param aimedAtPoint Position auf dem Panel
	 * @return Die ID des hideouts, dass am ehesten der angegebenen Position
	 *         entspricht
	 */
	public int calculateField(Point aimedAtPoint) {
		int numFields = hideouts.size();
		int degreesPerField = 360 / numFields;

		Point center = new Point(getWidth() / 2, getHeight() / 2);

		int x_diff = aimedAtPoint.x - center.x;
		int y_diff = aimedAtPoint.y - center.y;

		double atan = Math.toDegrees(Math.atan2(x_diff, y_diff));

		double aimedAtDegree = (atan - 180.0) * -1;

		int aimedAtField = ((((int) aimedAtDegree + (360 / hideouts.size() / 2)) % 360) / degreesPerField);

		return aimedAtField;
	}

	public void startAnimation(int animationType, int firedAtField) {
		currentAnimationFrame++;

		currentAnimationType = animationType;
		currentFiredAtField = firedAtField;

		synchronized (this) {
			// System.out.println("MapPanel Animation aufwecken");
			this.notify();
		}

		// Warten, bis Animation vorbei
		synchronized (this) {
			try {
				// System.out.println("Waiting");
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Die Animationen in eigenem Thread behandeln
	@Override
	public void run() {
		while (true) {
			if (currentAnimationFrame != targetAnimationFrame) {
				currentAnimationFrame = (currentAnimationFrame + 1)
						% (towerChargeAnimation.size() + towerFireAnimation.size());
				repaint();
				try {
					Thread.sleep(20);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} else {
				// SingleplayerGame aufwecken
				synchronized (this) {
					this.notify();
				}

				// Warten, bis wieder animiert werden soll
				synchronized (this) {
					try {
						// System.out.println("Waiting");
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

	// -------- SETTER ------------

	public void setMainHero(Hero mainHero) {
		this.mainHero = mainHero;
	}

	public void setHideoutHeroes(HashMap<Hideout, Hero> hideoutHeroes) {
		this.hideoutHeroes = hideoutHeroes;
	}

	public void setCurrentAimedAtField(int currentAimedAtField) {
		this.currentAimedAtField = currentAimedAtField;
		repaint();
	}

	public void setMapState(int mapState) {
		switch (mapState) {
		case MAPSTATE_PLAYER_AIMING:
			addMouseMotionListener(mousePositionListener);
			break;
		case MAPSTATE_KI_AIMING:
			removeMouseMotionListener(mousePositionListener);
			break;
		case MAPSTATE_REGULAR:
			removeMouseMotionListener(mousePositionListener);
			break;

		}

		this.mapState = mapState;

	}

	// -------- GETTER ------------

	public int getCurrentAimedAtField() {
		return currentAimedAtField;
	}

}
