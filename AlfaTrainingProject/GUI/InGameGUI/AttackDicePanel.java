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
 *
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

    public void setRollResult(int result) {
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
                if (currentAnimationFrame < 210 && currentAnimationFrame >= 90) {
                    targetAnimationFrame = 100;
                }
                if (currentAnimationFrame < 10 || currentAnimationFrame >= 210) {
                    targetAnimationFrame = 120;
                }
                if (currentAnimationFrame < 90 && currentAnimationFrame >= 10) {
                    targetAnimationFrame = 200;
                }
                break;
            case AttackDice.RESULT_RIGHT_CENTER_HIT:
                if (currentAnimationFrame < 170 || currentAnimationFrame >= 270) {
                    targetAnimationFrame = 60;
                }
                if (currentAnimationFrame < 270 && currentAnimationFrame >= 170) {
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

    //TODO: Nur starten, wenn tatsächlich gewürfelt wird und anschließend bis zum nächsten Wurf pausieren
    @Override
    public void run() {
        while (true) {
            if (currentAnimationFrame != targetAnimationFrame) {
                currentAnimationFrame = (currentAnimationFrame + 1) % 220;
                repaint();
            }

            try {
                Thread.sleep(20);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
