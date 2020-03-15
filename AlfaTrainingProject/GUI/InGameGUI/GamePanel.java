package InGameGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import MenuGUI.MainFramePanel;
import MenuGUI.MyFrame;

/**
 * Das Gesamtpanel, dass während des Spiels gezeigt wird. Beinhaltet das
 * eigentliche Spielfeld sowie die Sidebar mit Helden, Aktionen usw.
 *
 */
public class GamePanel extends JPanel {

	private MapPanel mapPanel;
	private GameSidePanel gameSidePanel;
	private JFrame frame;
	
	private static final int MAPPANEL_STANDARD_SIZE = 1080;

	private static final int GAMESIDEPANEL_STANDARD_SIZE_X = 840;
	private static final int GAMESIDEPANEL_STANDARD_SIZE_Y = 1080;

	public GamePanel(MapPanel mp, GameSidePanel gsp, JFrame mainFrame) {
		super();
		frame = mainFrame;
		//funktioniert noch nicht
//		String name = "Escape"; // I think the exact name doesn't matter
//		
//		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
//                "showDialog");
//		getActionMap().put("showDialog", new AbstractAction() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int input = JOptionPane.showOptionDialog(getParent(), 
//						"Beenden?", 
//						"", 
//						JOptionPane.OK_CANCEL_OPTION, 
//						JOptionPane.QUESTION_MESSAGE, 
//						null, 
//						new String[]{"Abbrechen","Beenden"}, 
//						"Beenden");
//				System.out.println(input);
//			}
//		});
//		isFocusable();
		// frame.setContentPane(this);
		mapPanel = mp;
		mapPanel.setPreferredSize(new Dimension(MAPPANEL_STANDARD_SIZE, MAPPANEL_STANDARD_SIZE));

		gameSidePanel = gsp;
		gameSidePanel.setPreferredSize(new Dimension(GAMESIDEPANEL_STANDARD_SIZE_X, GAMESIDEPANEL_STANDARD_SIZE_Y));

		setLayout(new BorderLayout());

		add(mapPanel, BorderLayout.LINE_START);
		add(gameSidePanel, BorderLayout.LINE_END);
	}

	@Override
	protected void paintComponent(Graphics grphcs) {
		// MapPanel quadratisch links hinein
		mapPanel.setPreferredSize(new Dimension(getHeight(), getHeight()));
//        System.out.println("MapPanel(W/H): " + mapPanel.getWidth() + " " + mapPanel.getHeight()); 

		// GameSidePanel füllt den Rest
		gameSidePanel.setPreferredSize(new Dimension(getWidth() - getHeight(), getHeight()));
//        System.out.println("GameSidePanel(W/H): " + gameSidePanel.getWidth() + " " + gameSidePanel.getHeight());

	}

	public MapPanel getMapPanel() {
		return mapPanel;
	}

	public GameSidePanel getGameSidePanel() {
		return gameSidePanel;
	}
	
}
