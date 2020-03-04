package GUI;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * Das Panel, auf dem die Sidebar mit Helden, Aktionen usw angezeigt wird.
 */
class GameSidePanel extends JPanel {

    private final static int PANELSIZE_X = 500;
    private final static int PANELSIZE_Y = 1080;

    public GameSidePanel() {
        super();

        setPreferredSize(new Dimension(PANELSIZE_X, PANELSIZE_Y));
        
        
        //TODO: Layout (evtl. weitere Unterteilungen), Elemente hinzufügen
    }
}
