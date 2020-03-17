package InGameGUI;

import Dice.AttackDice;
import MenuGUI.MainFramePanel;
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
 * Panel zur Darstellung des AttackDice. Gr��e ist dynamisch �nderbar (Inhalte
 * werden entsprechend gestreckt).
 */
public class AttackDicePanel extends JPanel implements Runnable {

	// relative Position/Gr��e der Grafiken im Bezug auf die Panelgr��e
	private static final double SPOTLIGHTCIRCLE_POSITION_RELATIVE_X = 0.41;
	private static final double SPOTLIGHTCIRCLE_POSITION_RELATIVE_Y = 0.04;
	private static final double SPOTLIGHTCIRCLE_SIZE_RELATIVE_X = 0.6;
	private static final double SPOTLIGHTCIRCLE_SIZE_RELATIVE_Y = 0.85;

	private static final double SPOTLIGHTCONE_POSITION_RELATIVE_X = 0.22;
	private static final double SPOTLIGHTCONE_POSITION_RELATIVE_Y = 0.07;
	private static final double SPOTLIGHTCONE_SIZE_RELATIVE_X = 0.56;
	private static final double SPOTLIGHTCONE_SIZE_RELATIVE_Y = 0.9;

	private static final double DIE_POSITION_RELATIVE_X = 0.2;
	private static final double DIE_POSITION_RELATIVE_Y = -0.12;
	private static final double DIE_SIZE_RELATIVE_X = 1;
	private static final double DIE_SIZE_RELATIVE_Y = 1.2;

	private static final double TOWER_CARD_POSITION_RELATIVE_X = 0.0;
	private static final double TOWER_CARD_POSITION_RELATIVE_Y = 0.0;
	private static final double TOWER_CARD_SIZE_RELATIVE_X = 0.4;
	private static final double TOWER_CARD_SIZE_RELATIVE_Y = 1.0;

	private ArrayList<Image> animationImages;
	private Image towerImage;
	private Image spotlightConeImage;
	private Image spotlightCircleImage;

	private int currentAnimationFrame;
	private int targetAnimationFrame;

	public AttackDicePanel() {

		// Animationsbilder laden
		Image frame;
		animationImages = AnimationLoader.getInstance().getAnimation(AnimationName.ATTACKDICE);
		

		// Tower und Spotlight laden
		towerImage =  ImageLoader.getInstance().getImage(ImageName.TOWER_CARD);
		
		spotlightConeImage =  ImageLoader.getInstance().getImage(ImageName.SPOT_LEFT);
		
		spotlightCircleImage =  ImageLoader.getInstance().getImage(ImageName.SPOT_TOWER);
		
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

		// Dann die Karte
				g2d.drawImage(towerImage, (int) (getWidth() * TOWER_CARD_POSITION_RELATIVE_X),
						(int) (getHeight() * TOWER_CARD_POSITION_RELATIVE_Y), (int) (getWidth() * TOWER_CARD_SIZE_RELATIVE_X),
						(int) (getHeight() * TOWER_CARD_SIZE_RELATIVE_Y), this);

		
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
	 * @param result Das gew�nschte W�rfelergebnis. Sollte durch AttackDice erzeugt
	 *               werden.
	 */
	public void setRollResult(int result) {
		currentAnimationFrame++;

		synchronized (this) {
			// System.out.println("WakingUp");
			this.notify();
		}

		switch (result) {
		case AttackDice.RESULT_CENTER_HIT:
			if (currentAnimationFrame < 130 && currentAnimationFrame >= 70) {
				targetAnimationFrame = 20;
			}
			if (currentAnimationFrame < 150 && currentAnimationFrame >= 130) {
				targetAnimationFrame = 40;
			}
			if (currentAnimationFrame < 190 && currentAnimationFrame >= 150) {
				targetAnimationFrame = 80;
			}
			if (currentAnimationFrame < 70 || currentAnimationFrame >= 190) {
				targetAnimationFrame = 180;
			}
			break;
		case AttackDice.RESULT_LEFT_CENTER_HIT:
			if (currentAnimationFrame < 210 && currentAnimationFrame >= 101) {
				targetAnimationFrame = 100;
			}
			if (currentAnimationFrame < 10 || currentAnimationFrame >= 210) {
				targetAnimationFrame = 120;
			}
			if (currentAnimationFrame < 101 && currentAnimationFrame >= 10) {
				targetAnimationFrame = 200;
			}
			break;
		case AttackDice.RESULT_RIGHT_CENTER_HIT:
			if (currentAnimationFrame < 170 && currentAnimationFrame >= 61) {
				targetAnimationFrame = 60;
			}
			if (currentAnimationFrame < 61 || currentAnimationFrame >= 170) {
				targetAnimationFrame = 160;
			}
			break;
		case AttackDice.RESULT_OUTER_LEFT_HIT:
			targetAnimationFrame = 0;
			break;
		case AttackDice.RESULT_OUTER_RIGHT_HIT:
			targetAnimationFrame = 140;
			break;

		}
	}

	/**
	 * Die Animation wird von einem eigenen Thread behandelt, in dem in kurzen
	 * Abst�nden getestet wird, ob das gezeigte Frame ge�ndert werden muss.
	 *
	 */
	@Override
	public void run() {
		while (true) {
			if (currentAnimationFrame != targetAnimationFrame) {
				currentAnimationFrame = (currentAnimationFrame + 1) % 220;
				repaint();
			} else {
				// SingleplayerGame aufwecken (Attacke auswerten)
				synchronized (this) {
					this.notify();
				}

				
				//Warten, bis wieder animiert werden soll
				synchronized (this) {
					try {
						// System.out.println("Waiting");
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			try {
				Thread.sleep(20);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
}
