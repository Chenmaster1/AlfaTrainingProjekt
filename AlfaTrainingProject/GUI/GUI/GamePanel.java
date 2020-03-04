package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * Das Gesamtpanel, dass während des Spiels gezeigt wird. Beinhaltet das 
 * eigentliche Spielfeld sowie die Sidebar mit Helden, Aktionen usw.
 * 
*/
public class GamePanel extends JPanel{

    private MapPanel mapPanel;
    private  GameSidePanel gameSidePanel;
    
    public GamePanel(MapPanel mp, GameSidePanel gsp)
    {
        super();
        
        mapPanel = mp;
        gameSidePanel = gsp;
        
        setLayout(new BorderLayout());
        
        add(mapPanel, BorderLayout.LINE_START);
        add(gameSidePanel, BorderLayout.LINE_END);
        
        
    }
}
