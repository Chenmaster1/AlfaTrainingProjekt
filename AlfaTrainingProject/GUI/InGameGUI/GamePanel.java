package InGameGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import MenuGUI.MainFramePanel;

/**
 * Das Gesamtpanel, dass während des Spiels gezeigt wird. Beinhaltet das
 * eigentliche Spielfeld sowie die Sidebar mit Helden, Aktionen usw.
 *
 */
public class GamePanel extends JPanel {

    private MapPanel mapPanel;
    private GameSidePanel gameSidePanel;
    private MainFramePanel mainFramePanel;
    private JFrame frame;
    
    private static final int MAPPANEL_STANDARD_SIZE = 1080;

    public GamePanel(MapPanel mp, GameSidePanel gsp, JFrame mainFrame) {
        super();
        frame = mainFrame;
        //frame.setContentPane(this);
        mapPanel = mp;
        mapPanel.setPreferredSize(new Dimension(MAPPANEL_STANDARD_SIZE, MAPPANEL_STANDARD_SIZE));

        gameSidePanel = gsp;

        setLayout(new BorderLayout());

        add(mapPanel, BorderLayout.LINE_START);
        add(gameSidePanel, BorderLayout.LINE_END);
    }
    
    public GamePanel(MapPanel mp, GameSidePanel gsp, JFrame mainFrame, MainFramePanel mainFramePanel) {
        super();
        frame = mainFrame;
        //frame.setContentPane(this);
        this.mainFramePanel = mainFramePanel;
        mapPanel = mp;
        mapPanel.setPreferredSize(new Dimension(MAPPANEL_STANDARD_SIZE, MAPPANEL_STANDARD_SIZE));

        gameSidePanel = gsp;

        setLayout(new BorderLayout());

        add(mapPanel, BorderLayout.LINE_START);
        add(gameSidePanel, BorderLayout.LINE_END);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        //MapPanel quadratisch links hinein
//        mapPanel.setPreferredSize(new Dimension(getHeight(), getHeight()));

//        System.out.println("MapPanel(W/H): " + mapPanel.getWidth() + " " + mapPanel.getHeight() + 
//                "  GameSidePanel(W/H): " + gameSidePanel.getWidth() + " " + gameSidePanel.getHeight());

        //GameSidePanel füllt den Rest
//        gameSidePanel.setPreferredSize(new Dimension(getWidth() - getHeight(), getHeight()));

        super.paintComponent(grphcs);
    }

	public MapPanel getMapPanel() {
		return mapPanel;
	}

	public GameSidePanel getGameSidePanel() {
		return gameSidePanel;
	}

	public MainFramePanel getMainFramePanel() {
		return mainFramePanel;
	}
}
