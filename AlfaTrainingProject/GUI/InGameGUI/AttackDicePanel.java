package InGameGUI;

import Dice.AttackDice;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Panel zur Darstellung des AttackDice. Größe ist dynamisch änderbar (Inhalte
 * werden entsprechend gestreckt).
 */
public class AttackDicePanel extends JPanel implements Runnable {

    // relative Position/Größe der Grafiken im Bezug auf die Panelgröße
    private static final double SPOTLIGHTCIRCLE_POSITION_RELATIVE_X = 0.35;
    private static final double SPOTLIGHTCIRCLE_POSITION_RELATIVE_Y = 0.0;
    private static final double SPOTLIGHTCIRCLE_SIZE_RELATIVE_X = 0.65;
    private static final double SPOTLIGHTCIRCLE_SIZE_RELATIVE_Y = 1.0;

    private static final double SPOTLIGHTCONE_POSITION_RELATIVE_X = 0.22;
    private static final double SPOTLIGHTCONE_POSITION_RELATIVE_Y = 0.07;
    private static final double SPOTLIGHTCONE_SIZE_RELATIVE_X = 0.5;
    private static final double SPOTLIGHTCONE_SIZE_RELATIVE_Y = 0.9;

    private static final double DIE_POSITION_RELATIVE_X = 0.17;
    private static final double DIE_POSITION_RELATIVE_Y = -0.1;
    private static final double DIE_SIZE_RELATIVE_X = 1;
    private static final double DIE_SIZE_RELATIVE_Y = 1.2;

    private static final double TOWER_POSITION_RELATIVE_X = 0.0;
    private static final double TOWER_POSITION_RELATIVE_Y = 0.15;
    private static final double TOWER_SIZE_RELATIVE_X = 0.4;
    private static final double TOWER_SIZE_RELATIVE_Y = 0.85;

    private ArrayList<Image> animationImages;
    private Image towerImage;
    private Image spotlightConeImage;
    private Image spotlightCircleImage;

    private int currentAnimationFrame;
    private int targetAnimationFrame;

    public AttackDicePanel() {

        // Animationsbilder laden
        Image frame;
        animationImages = new ArrayList<>();
        for (int i = 0; i < 220; i++) {
            //Pfad-String zusammenbasteln. image 0 -> 0000, 15 -> 0015 etc
            StringBuilder sb = new StringBuilder();
            sb.append("dice_w10/testsequence");
            int additionalZeros = 4 - ("" + i).length();

            for (int j = 0; j < additionalZeros; j++) {
                sb.append("0");
            }
            sb.append(i + ".png");

            frame = new ImageIcon(getClass().getClassLoader().getResource(sb.toString())).getImage();
            animationImages.add(frame);
        }

        // Tower und Spotlight laden
        towerImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/Tower.png")).getImage();
        spotlightConeImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/spot_left.png"))
                .getImage();
        spotlightCircleImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/spot_tower.png"))
                .getImage();

//        setBackground(Color.BLACK);
        setOpaque(false);
    }

    /**
     * Zeichnet das Panel. Zuerst werden die statischen Elemente gezeichnet,
     * zuletzt das zum aktuellen currentAnimationFrame passende Bild des
     * Würfels.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Spotlight Kreis ganz unten
        g2d.drawImage(spotlightCircleImage,
                (int) (getWidth() * SPOTLIGHTCIRCLE_POSITION_RELATIVE_X),
                (int) (getHeight() * SPOTLIGHTCIRCLE_POSITION_RELATIVE_Y),
                (int) (getWidth() * SPOTLIGHTCIRCLE_SIZE_RELATIVE_X),
                (int) (getHeight() * SPOTLIGHTCIRCLE_SIZE_RELATIVE_Y), this);

        // den Cone darauf
        g2d.drawImage(spotlightConeImage,
                (int) (getWidth() * SPOTLIGHTCONE_POSITION_RELATIVE_X),
                (int) (getHeight() * SPOTLIGHTCONE_POSITION_RELATIVE_Y),
                (int) (getWidth() * SPOTLIGHTCONE_SIZE_RELATIVE_X),
                (int) (getHeight() * SPOTLIGHTCONE_SIZE_RELATIVE_Y),
                this);

        // Dann den Turm
        g2d.drawImage(towerImage, (int) (getWidth() * TOWER_POSITION_RELATIVE_X),
                (int) (getHeight() * TOWER_POSITION_RELATIVE_Y),
                (int) (getWidth() * TOWER_SIZE_RELATIVE_X),
                (int) (getHeight() * TOWER_SIZE_RELATIVE_Y), this);

        // und zuletzt den Würfel
        g2d.drawImage(animationImages.get(currentAnimationFrame),
                (int) (getWidth() * DIE_POSITION_RELATIVE_X),
                (int) (getHeight() * DIE_POSITION_RELATIVE_Y),
                (int) (getWidth() * DIE_SIZE_RELATIVE_X),
                (int) (getHeight() * DIE_SIZE_RELATIVE_Y), this);
    }

    /**
     * Lässt das Panel eine Animation anzeigen, bei der der Würfel schließlich
     * das übergebene Ergebnis anzeigt.
     *
     * @param result Das gewünschte Würfelergebnis. Sollte durch AttackDice
     * erzeugt werden.
     */
    public void setRollResult(int result) {
        currentAnimationFrame++;

        synchronized(this)
    	{
        	//System.out.println("WakingUp");
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
     * Abständen getestet wird, ob das gezeigte Frame geändert werden muss.
     *
     */
    @Override
    public void run() {
        while (true) {
            if (currentAnimationFrame != targetAnimationFrame) {
                currentAnimationFrame = (currentAnimationFrame + 1) % 220;
                repaint();
            }
            else
            {
            	synchronized(this)
            	{
            		try {
            			//System.out.println("Waiting");
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
