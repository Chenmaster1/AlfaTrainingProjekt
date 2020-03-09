package InGameGUI;

import Dice.AttackDice;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 */
public class AttackDicePanel extends JPanel {

    //TODO: anpassen
    //relative Position/Größe der Grafiken im Bezug auf die Panelgröße
    private static final double SPOTLIGHTCIRCLE_POSITION_RELATIVE_X = 0.4;
    private static final double SPOTLIGHTCIRCLE_POSITION_RELATIVE_Y = 0.1;
    private static final double SPOTLIGHTCIRCLE_SIZE_RELATIVE_X = 0.6;
    private static final double SPOTLIGHTCIRCLE_SIZE_RELATIVE_Y = 0.9;

    private static final double SPOTLIGHTCONE_POSITION_RELATIVE_X = 0.25;
    private static final double SPOTLIGHTCONE_POSITION_RELATIVE_Y = 0.1;
    private static final double SPOTLIGHTCONE_SIZE_RELATIVE_X = 0.6;
    private static final double SPOTLIGHTCONE_SIZE_RELATIVE_Y = 0.9;

    private static final double DIE_POSITION_RELATIVE_X = 0.6;
    private static final double DIE_POSITION_RELATIVE_Y = 0.2;
    private static final double DIE_SIZE_RELATIVE_X = 0.4;
    private static final double DIE_SIZE_RELATIVE_Y = 0.6;

    private ArrayList<Image> animationImages;
    private Image towerImage;
    private Image spotlightConeImage;
    private Image spotlightCircleImage;

    private int currentAnimationFrame;

    public AttackDicePanel() {

        //Animationsbilder laden
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

            frame = new ImageIcon(getClass().getClassLoader().getResource(sb.toString()))
                    .getImage();
            animationImages.add(frame);
        }

        //Tower und Spotlight laden
        towerImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/Tower.png"))
                .getImage();
        spotlightConeImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/spot_left.png"))
                .getImage();
        spotlightCircleImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/spot_tower.png"))
                .getImage();
    }

    @Override
    public void paintComponent(Graphics g) {

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
                (int) (getHeight() * SPOTLIGHTCONE_SIZE_RELATIVE_Y), this);

        // Dann den Turm
        //und zuletzt den Würfel
        g2d.drawImage(animationImages.get(currentAnimationFrame),
                (int) (getWidth() * DIE_POSITION_RELATIVE_X),
                (int) (getHeight() * DIE_POSITION_RELATIVE_Y),
                (int) (getWidth() * DIE_SIZE_RELATIVE_X),
                (int) (getHeight() * DIE_SIZE_RELATIVE_Y), this);
    }

    public void showRollResult(int result) {
        switch (result) {
            case AttackDice.RESULT_CENTER_HIT:
                break;
            case AttackDice.RESULT_LEFT_CENTER_HIT:
                break;
            case AttackDice.RESULT_RIGHT_CENTER_HIT:
                break;
            case AttackDice.RESULT_OUTER_LEFT_HIT:
                break;
            case AttackDice.RESULT_OUTER_RIGHT_HIT:
                break;

        }
    }

}
