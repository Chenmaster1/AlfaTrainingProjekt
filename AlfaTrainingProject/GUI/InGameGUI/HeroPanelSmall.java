package InGameGUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import Heroes.Hero;
import java.awt.Dimension;
import javax.swing.ImageIcon;

/**
 * Panel für die Darstellung eines Helden mit aktueller Anzahl an Lebens- und
 * Aktionspunkten sowie Verzögerungsmarken. Zur verkleinerten Darstellung
 * anderer Spieler.
 *
 * @author Peter
 */
public class HeroPanelSmall extends JPanel {

    private Hero displayedHero;
    private Image hitPointImage;
    private Image hitPointUsedImage;
    private Image actionPointImage;
    private Image actionPointUsedImage;
    private Image delayTokenImage;

    //TODO: Passende Werte finden, evtl. weitere definieren 
    //(z.b. für einen Abstand vom oberen Rand etc)
    private static final double POINTICON_TOPMARGIN_RELATIVE_Y = 0.05;
    private static final double POINTICON_SIDEMARGIN_RELATIVE_X = 0.05;
    private static final double POINTICON_SIZE_RELATIVE_X = 0.125;
    private static final double POINTICON_SIZE_RELATIVE_Y = 0.125;

    private static final double DELAYTOKEN_SIZE_RELATIVE_X = 0.1;
    private static final double DELAYTOKEN_SIZE_RELATIVE_Y = 0.18;
    private static final double DELAYTOKEN_BOTTOMMARGIN_RELATIVE_Y = 0.05;

    private static final int PANELSIZE_X = 180;
    private static final int PANELSIZE_Y = 187;

    public HeroPanelSmall(Hero hero) {

        displayedHero = hero;

        //TODO: Auf gemeinsame Image-Objekte zugreifen, damit nicht jedes Panel seine eigenen instanziiert.
        hitPointImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Heart_Activated.png"))
                .getImage();
        hitPointUsedImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Heart_Deactivated.png"))
                .getImage();
        actionPointImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Action_Activated.png"))
                .getImage();
        actionPointUsedImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Action_Deactivated.png"))
                .getImage();
        delayTokenImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Delay.png"))
                .getImage();

        setLayout(null);
        setPreferredSize(new Dimension(PANELSIZE_X, PANELSIZE_Y));

    }

    /**
     * Zeichnet das Panel. Die Elemente werden dynamisch auf die aktuelle Größe
     * angepasst and angeordnet.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        // Avatar als Hintergrund
        g2d.drawImage(displayedHero.getAvatar().getImage(), 0, 0, getWidth(), getHeight(), this);

        //reale Abmessungen der Aktions- und Lebenspunkte basierend auf der aktuellen Panelgröße
        int iconSize_X = (int) (POINTICON_SIZE_RELATIVE_X * getWidth());
        int iconSize_Y = (int) (POINTICON_SIZE_RELATIVE_Y * getHeight());
        int sideMargin = (int) (getWidth() * POINTICON_SIDEMARGIN_RELATIVE_X);
        int topMargin = (int) (getHeight() * POINTICON_TOPMARGIN_RELATIVE_Y);

        // Overlays für Actionpoints (links)
        drawActionPointIcons(g2d, iconSize_X, iconSize_Y, sideMargin, topMargin);

        // Overlays für Hitpoints (rechts)
        drawHitPointIcons(g2d, iconSize_X, iconSize_Y, sideMargin, topMargin);

        //reale Abmessungen der DelayTokens basierend auf der aktuellen Panelgröße
        int delayTokenSize_X = (int) (DELAYTOKEN_SIZE_RELATIVE_X * getWidth());
        int delayTokenSize_Y = (int) (DELAYTOKEN_SIZE_RELATIVE_Y * getHeight());

        // Overlays für Delaytokens (unten)
        drawDelayTokenIcons(g2d, delayTokenSize_X, delayTokenSize_Y);
    }

    /**
     * Zeichnet die DelayTokens zentriert an den unteren Rand des Panels. Nimmt
     * reale Größen (in Pixel) für die Abmessungen eines einzelnen Tokens
     * entgegen.
     *
     * @param g2d
     * @param delayTokenSize_X
     * @param delayTokenSize_Y
     */
    private void drawDelayTokenIcons(Graphics2D g2d, int delayTokenSize_X, int delayTokenSize_Y) {
        int numDelayTokens = displayedHero.getDelayTokens();
        int totalSize_X = numDelayTokens * delayTokenSize_X;

        for (int i = 0; i < numDelayTokens; i++) {
            int position_x = ((getWidth() - totalSize_X) / 2) + (i * delayTokenSize_X);
            int position_y = getHeight() - delayTokenSize_Y - (int) (DELAYTOKEN_BOTTOMMARGIN_RELATIVE_Y * getHeight());

            g2d.drawImage(delayTokenImage,
                    position_x,
                    position_y,
                    delayTokenSize_X,
                    delayTokenSize_Y,
                    this);
        }

    }

    /**
     * Zeichnet die Icons der Action Points an den linken Rand des Panels,
     * angefangen in der oberen linken Ecke. Nimmt reale Größen (in Pixel) für
     * die Abmessungen eines einzelnen Icons sowie einen Seitenabstand entgegen.
     * entgegen.
     *
     * @param g2d
     * @param iconSize_X
     * @param iconSize_Y
     * @param sideMargin
     */
    private void drawActionPointIcons(Graphics2D g2d, int iconSize_X, int iconSize_Y, int sideMargin, int topMargin) {
        int maxPoints = displayedHero.getMaxActionPoints();
        int currentPoints = displayedHero.getCurrentActionPoints();

        for (int i = 0; i < maxPoints; i++) {
            if (currentPoints > 0) {
                g2d.drawImage(actionPointImage,
                        sideMargin, i * (iconSize_Y) + topMargin,
                        iconSize_X, iconSize_Y,
                        this);
                currentPoints--;
            } else {
                g2d.drawImage(actionPointUsedImage,
                        sideMargin, i * (iconSize_Y) + topMargin,
                        iconSize_X, iconSize_Y,
                        this);
            }

        }

    }

    /**
     * Zeichnet die Icons der Hit Points an den rechten Rand des Panels,
     * angefangen in der oberen rechten Ecke. Nimmt reale Größen (in Pixel) für
     * die Abmessungen eines einzelnen Icons sowie einen Seitenabstand entgegen.
     *
     * @param g2d
     * @param iconSize_X
     * @param iconSize_Y
     * @param sideMargin
     */
    private void drawHitPointIcons(Graphics2D g2d, int iconSize_X, int iconSize_Y, int sideMargin, int topMargin) {
        int maxPoints = displayedHero.getMaxHitPoints();
        int currentPoints = displayedHero.getCurrentHitPoints();

        for (int i = 0; i < maxPoints; i++) {
            if (currentPoints > 0) {
                g2d.drawImage(hitPointImage,
                        getWidth() - sideMargin - iconSize_X, i * (iconSize_Y) + topMargin,
                        iconSize_X, iconSize_Y,
                        this);
                currentPoints--;
            } else {
                g2d.drawImage(hitPointUsedImage,
                        getWidth() - sideMargin - iconSize_X, i * (iconSize_Y) + topMargin,
                        iconSize_X, iconSize_Y,
                        this);
            }
        }

    }
}
