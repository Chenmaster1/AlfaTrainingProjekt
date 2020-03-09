package InGameGUI;

import Heroes.Hero;
import static InGameGUI.MapPanel.MAPSTATE_AIMING;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Das Panel, auf dem die Sidebar mit Helden, Aktionen usw angezeigt wird.
 */
class GameSidePanel extends JPanel {

    private final static int PANELSIZE_X = 840;
    private final static int PANELSIZE_Y = 1080;

    private Image backgroundImage;
    private ArrayList<Hero> otherHeroes;

    private Hero playerHero;

    private JPanel panelOtherHeroes;

    public GameSidePanel(ArrayList<Hero> otherHeroes, Hero playerHero) {
        super();
        this.otherHeroes = otherHeroes;
        this.playerHero = playerHero;

        setPreferredSize(new Dimension(PANELSIZE_X, PANELSIZE_Y));
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/Gameboard_Right.png"))
                .getImage();

        //TODO: Layout (evtl. weitere Unterteilungen), Elemente hinzufügen
        setLayout(new BorderLayout());

        panelOtherHeroes = new JPanel(new FlowLayout());
        for (Hero h : this.otherHeroes) {
            panelOtherHeroes.add(new HeroPanelSmall(h));
        }

        add(panelOtherHeroes, BorderLayout.PAGE_START);

    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        // Hintergrund ganz unten
        g2d.drawImage(backgroundImage, 0, 0, PANELSIZE_X, PANELSIZE_Y, this);

        // restliche Elemente
//        paintComponents(g);
    }

    public void setOtherHeroes(ArrayList<Hero> otherHeroes) {
        this.otherHeroes = otherHeroes;
    }
}
