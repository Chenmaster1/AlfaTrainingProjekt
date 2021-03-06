package InGameGUI;

import Dice.AttackDice;
import Dice.HideDice;
import SoundThread.SoundController;
import resourceLoaders.AnimationLoader;
import resourceLoaders.AnimationName;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Panel zur Darstellung des HideDice. Gr��e ist dynamisch �nderbar (Inhalte
 * werden entsprechend gestreckt).
 */
public class HideDicePanel extends JPanel implements Runnable {

    // relative Position/Gr��e der Grafiken im Bezug auf die Panelgr��e
    private static final double SPOTLIGHTCIRCLE_POSITION_RELATIVE_X = -0.01;
    private static final double SPOTLIGHTCIRCLE_POSITION_RELATIVE_Y = 0.04;
    private static final double SPOTLIGHTCIRCLE_SIZE_RELATIVE_X = 0.6;
    private static final double SPOTLIGHTCIRCLE_SIZE_RELATIVE_Y = 0.85;

    private static final double SPOTLIGHTCONE_POSITION_RELATIVE_X = 0.25;
    private static final double SPOTLIGHTCONE_POSITION_RELATIVE_Y = 0.09;
    private static final double SPOTLIGHTCONE_SIZE_RELATIVE_X = 0.55;
    private static final double SPOTLIGHTCONE_SIZE_RELATIVE_Y = 0.74;

    private static final double DIE_POSITION_RELATIVE_X = -0.21;
    private static final double DIE_POSITION_RELATIVE_Y = -0.1;
    private static final double DIE_SIZE_RELATIVE_X = 1;
    private static final double DIE_SIZE_RELATIVE_Y = 1.2;

    private static final double CARD_POSITION_RELATIVE_X = 0.59;
    private static final double CARD_POSITION_RELATIVE_Y = 0.0;
    private static final double CARD_SIZE_RELATIVE_X = 0.42;
    private static final double CARD_SIZE_RELATIVE_Y = 1.0;

    private final static int ANIMATION_FRAME_PERIOD_DICEPANELS = GamePanel.ANIMATION_FRAME_PERIOD_MAPPANEL;

    private ArrayList<Image> animationImages;
    private Image cardImage;
    private Image spotlightConeImage;
    private Image spotlightCircleImage;

    private int currentAnimationFrame;
    private int targetAnimationFrame;

    public HideDicePanel() {

	// Animationsbilder laden
	Image frame;
	animationImages = AnimationLoader.getInstance().getAnimation(AnimationName.HIDEDICE);

	// Tower und Spotlight laden
	cardImage = ImageLoader.getInstance().getImage(ImageName.HIDEOUT_CARD);
	spotlightConeImage = ImageLoader.getInstance().getImage(ImageName.SPOT_RIGHT);
	spotlightCircleImage = ImageLoader.getInstance().getImage(ImageName.SPOT_HIDEOUT);

//        setBackground(Color.BLACK);
	setOpaque(false);
    }

    /**
     * Zeichnet das Panel. Zuerst werden die statischen Elemente gezeichnet, zuletzt
     * das zum aktuellen currentAnimationFrame passende Bild des W�rfels.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2d = (Graphics2D) g;

	// Spotlight Kreis ganz unten
	g2d.drawImage(spotlightCircleImage, (int) (getWidth() * SPOTLIGHTCIRCLE_POSITION_RELATIVE_X),
		(int) (getHeight() * SPOTLIGHTCIRCLE_POSITION_RELATIVE_Y),
		(int) (getWidth() * SPOTLIGHTCIRCLE_SIZE_RELATIVE_X),
		(int) (getHeight() * SPOTLIGHTCIRCLE_SIZE_RELATIVE_Y), this);

	// Die Karte daneben
	g2d.drawImage(cardImage, (int) (getWidth() * CARD_POSITION_RELATIVE_X),
		(int) (getHeight() * CARD_POSITION_RELATIVE_Y), (int) (getWidth() * CARD_SIZE_RELATIVE_X),
		(int) (getHeight() * CARD_SIZE_RELATIVE_Y), this);

	// den Cone darauf
	g2d.drawImage(spotlightConeImage, (int) (getWidth() * SPOTLIGHTCONE_POSITION_RELATIVE_X),
		(int) (getHeight() * SPOTLIGHTCONE_POSITION_RELATIVE_Y),
		(int) (getWidth() * SPOTLIGHTCONE_SIZE_RELATIVE_X), (int) (getHeight() * SPOTLIGHTCONE_SIZE_RELATIVE_Y),
		this);

	// und zuletzt den W�rfel
	g2d.drawImage(animationImages.get(currentAnimationFrame), (int) (getWidth() * DIE_POSITION_RELATIVE_X),
		(int) (getHeight() * DIE_POSITION_RELATIVE_Y), (int) (getWidth() * DIE_SIZE_RELATIVE_X),
		(int) (getHeight() * DIE_SIZE_RELATIVE_Y), this);
    }

    /**
     * L�sst das Panel eine Animation anzeigen, bei der der W�rfel schlie�lich das
     * �bergebene Ergebnis anzeigt.
     *
     * @param result Das gew�nschte W�rfelergebnis. Sollte durch HideDice erzeugt
     *               werden.
     */
    public void setRollResult(int result) {
	currentAnimationFrame++;

	switch (result) {
	case HideDice.RESULT_NOTHING:
	    if (currentAnimationFrame < 60 && currentAnimationFrame >= 1) {
		targetAnimationFrame = 0;
	    }
	    if (currentAnimationFrame < 100 && currentAnimationFrame >= 60) {
		targetAnimationFrame = 40;
	    }
	    if (currentAnimationFrame < 1 || currentAnimationFrame >= 100) {
		targetAnimationFrame = 60;
	    }
	    break;
	case HideDice.RESULT_RED:

	    targetAnimationFrame = 20;

	    break;
	case HideDice.RESULT_GREEN:
	    if (currentAnimationFrame < 20 || currentAnimationFrame >= 81) {
		targetAnimationFrame = 80;
	    }
	    if (currentAnimationFrame < 81 && currentAnimationFrame >= 20) {
		targetAnimationFrame = 100;
	    }
	    break;

	}
	SoundController.playSound("W6_Dice_Roll.mp3");
	
	// HideDice Animation starten und auf Abschluss warten
	synchronized (this) {
	    new Thread(this).start();
	    
	    try {
		this.wait();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    /**
     * Die Animation wird von einem eigenen Thread behandelt, in dem in kurzen
     * Abst�nden getestet wird, ob das gezeigte Frame ge�ndert werden muss. Weckt
     * den gameLogicThread auf und beendet, wenn das erw�nschte Frame erreicht
     * wurde.
     */
    @Override
    public void run() {
	synchronized (this) {
	    while (currentAnimationFrame != targetAnimationFrame) {
		currentAnimationFrame = (currentAnimationFrame + 1) % 120;
		repaint();
		try {
		    Thread.sleep(ANIMATION_FRAME_PERIOD_DICEPANELS);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    // GameLogicThread aufwecken (Verstecken auswerten)
	    this.notify();
	}
    }
}
